package org.jbei.ice.lib.net;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.jbei.ice.ControllerException;
import org.jbei.ice.lib.account.AccountController;
import org.jbei.ice.lib.common.logging.Logger;
import org.jbei.ice.lib.config.ConfigurationController;
import org.jbei.ice.lib.dao.DAOException;
=======
import org.jbei.ice.lib.account.AccountController;
import org.jbei.ice.lib.account.TokenHash;
import org.jbei.ice.lib.common.logging.Logger;
import org.jbei.ice.lib.config.ConfigurationController;
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
import org.jbei.ice.lib.dao.DAOFactory;
import org.jbei.ice.lib.dao.hibernate.RemotePartnerDAO;
import org.jbei.ice.lib.dto.ConfigurationKey;
import org.jbei.ice.lib.dto.web.RegistryPartner;
import org.jbei.ice.lib.dto.web.RemotePartnerStatus;
import org.jbei.ice.lib.dto.web.WebOfRegistries;
<<<<<<< HEAD
import org.jbei.ice.lib.utils.Utils;
import org.jbei.ice.services.rest.RestClient;
=======
import org.jbei.ice.lib.executor.IceExecutorService;
import org.jbei.ice.lib.utils.Utils;
import org.jbei.ice.services.rest.IceRestClient;

import java.util.ArrayList;
import java.util.List;
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c

/**
 * Controller for Web of Registries functionality
 *
 * @author Hector Plahar
 */
public class WoRController {

    private final RemotePartnerDAO dao;

    public WoRController() {
        dao = DAOFactory.getRemotePartnerDAO();
    }

    /**
     * @return true if the administrator of this registry instance has explicitly
     * enable the web of registries functionality
     */
    public boolean isWebEnabled() {
        String value = new ConfigurationController().getPropertyValue(
                ConfigurationKey.JOIN_WEB_OF_REGISTRIES);
        return "yes".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value);
<<<<<<< HEAD
    }

    /**
     * @param partnerId unique identifier for web partner
     * @param apiKey    authentication key known only to this partner
     * @return true if partner identified by the id is determined to be a valid
     *         web of registries partner for part transfer based on the status and authentication key
     */
    public boolean isValidWebPartner(String partnerId, String apiKey) {
        RemotePartner partner = dao.getByUrl(partnerId);
        return partner != null && partner.getPartnerStatus() == RemotePartnerStatus.APPROVED
                && apiKey != null && apiKey.equalsIgnoreCase(partner.getAuthenticationToken());
=======
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
    }

    public WebOfRegistries getRegistryPartners(boolean approvedOnly) {
        String value = new ConfigurationController().getPropertyValue(ConfigurationKey.JOIN_WEB_OF_REGISTRIES);
        WebOfRegistries webOfRegistries = new WebOfRegistries();
        webOfRegistries.setWebEnabled("yes".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value));

        // retrieve actual partners
<<<<<<< HEAD
        ArrayList<RemotePartner> partners = dao.retrieveRegistryPartners();
=======
        List<RemotePartner> partners = dao.getRegistryPartners();
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c

        ArrayList<RegistryPartner> registryPartners = new ArrayList<>();
        for (RemotePartner partner : partners) {
            if (approvedOnly && partner.getPartnerStatus() != RemotePartnerStatus.APPROVED)
                continue;
            registryPartners.add(partner.toDataTransferObject());
        }

        webOfRegistries.setPartners(registryPartners);
        return webOfRegistries;
    }

    // serves the dual purpose of :
    // please add me as a partner to your list with token
    // add accepted; use this as the authorization token
    public boolean addRemoteWebPartner(RegistryPartner request) {
        Logger.info("Adding remote partner [" + request.toString() + "]");

        String myURL = Utils.getConfigValue(ConfigurationKey.URI_PREFIX);
        if (request.getUrl().equalsIgnoreCase(myURL))
            return false;

        // request should contain api key for use to contact third party
        RemotePartner partner = dao.getByUrl(request.getUrl());
        if (partner != null) {
            if (request.getApiKey() == null) {
                Logger.error("Attempting to add partner (" + request.getUrl() + ") that already exists");
                return false;
            }

            // just update the authorization token
            partner.setApiKey(request.getApiKey());
            partner.setPartnerStatus(RemotePartnerStatus.APPROVED);
            dao.update(partner);
            return true;
        }

        // check for api key
        if (request.getApiKey() == null) {
            Logger.error("No api key found for " + request.toString());
            return false;
        }

        // todo : contact request.getUrl() at /rest/accesstoken to validate api key

        // save in db with status pending approval
        partner = new RemotePartner();
        partner.setName(request.getName());
        partner.setUrl(request.getUrl());
        partner.setApiKey(request.getApiKey());
        partner.setAdded(new Date());
        partner.setPartnerStatus(RemotePartnerStatus.PENDING_APPROVAL);
        partner.setAuthenticationToken(Utils.generateToken());
        dao.create(partner);
        return true;
    }

    /**
     * Removes the web partner uniquely identified by the url
     *
<<<<<<< HEAD
     * @param userId  id of user performing action (must have admin privs)
     * @param partner registry partner object that contains unique uniform resource identifier for the registry
     * @return add partner ofr
     */
    public RegistryPartner addWebPartner(String userId, RegistryPartner partner) {
        boolean isAdmin = new AccountController().isAdministrator(userId);
        if (!isAdmin || partner.getUrl() == null)
            return null;

        // todo check if partner already exists
        RemotePartner remotePartner = dao.getByUrl(partner.getUrl());
        if (remotePartner != null) {
            return null;
        }

        Logger.info("Adding partner [" + partner.getUrl() + "]");

        String myURL = Utils.getConfigValue(ConfigurationKey.URI_PREFIX);
        String myName = Utils.getConfigValue(ConfigurationKey.PROJECT_NAME);

        RegistryPartner thisPartner = new RegistryPartner();
        thisPartner.setUrl(myURL);
        thisPartner.setName(myName);
        String apiKey = Utils.generateToken();
        thisPartner.setApiKey(apiKey);  // key to use in contacting this instance

        // send notice to remote for key
        RestClient client = RestClient.getInstance();
        try {
            client.post(partner.getUrl(), "/rest/web/partner/remote", thisPartner, RegistryPartner.class);

            // save
            remotePartner = new RemotePartner();
            remotePartner.setName(partner.getName());
            remotePartner.setUrl(partner.getUrl());
            remotePartner.setPartnerStatus(RemotePartnerStatus.PENDING);
            remotePartner.setAuthenticationToken(apiKey);
            remotePartner.setAdded(new Date());
            return dao.create(remotePartner).toDataTransferObject();
        } catch (Exception e) {
            Logger.error(e);
            return null;
        }
    }

    /**
     * Adds a web of registries partner using the specificd url and name in the parameter
     * if another partner does not already exist with the same url
     *
     * @param url  url for ICE instance
     * @param name display name for remote ICE instance
     * @return created partner
     */
    private RegistryPartner addRegistryPartner(String url, String name) {
        RemotePartner partner = dao.getByUrl(url);

        if (partner != null) {
            partner.setName(name);
            if (partner.getAuthenticationToken() == null) {
                partner.setAuthenticationToken(Utils.generateToken());
                partner.setPartnerStatus(RemotePartnerStatus.APPROVED);
            }
            return dao.update(partner).toDataTransferObject();
        } else {
            if (name == null || name.trim().isEmpty())
                name = url;

            partner = new RemotePartner();
            partner.setUrl(url);
            partner.setName(name);
            partner.setAdded(new Date());
            partner.setPartnerStatus(RemotePartnerStatus.APPROVED);
            partner.setAuthenticationToken(Utils.generateToken());
            return dao.create(partner).toDataTransferObject();
        }
    }

    /**
     * Removes the web partner uniquely identified by the url
     *
     * @param partnerUrl url identifier for partner
     */
    public boolean removeWebPartner(String userId, String partnerUrl) {
        RemotePartner partner = dao.getByUrl(partnerUrl);
        if (partner == null)
            return true;

        dao.delete(partner);
        return true;
    }

    public boolean updateWebPartner(String userId, String url, RegistryPartner partner) {
        if (!new AccountController().isAdministrator(userId))
            return false;

        Logger.info(userId + ": updating partner (" + url + ") to " + partner.toString());
        RemotePartner existing = dao.getByUrl(url);
        if (existing == null)
            return false;

        RemotePartnerStatus newStatus = RemotePartnerStatus.APPROVED;
        if (newStatus == existing.getPartnerStatus())
            return true;

        // contact remote with new api key that allows them to contact this instance
        String apiKey = Utils.generateToken();
        String myURL = Utils.getConfigValue(ConfigurationKey.URI_PREFIX);
        String myName = Utils.getConfigValue(ConfigurationKey.PROJECT_NAME);

        RegistryPartner thisPartner = new RegistryPartner();
        thisPartner.setUrl(myURL);
        thisPartner.setName(myName);
        thisPartner.setApiKey(apiKey);  // key to use in contacting this instance

        RestClient client = RestClient.getInstance();
=======
     * @param partnerUrl url identifier for partner
     */
    public boolean removeWebPartner(String userId, String partnerUrl) {
        RemotePartner partner = dao.getByUrl(partnerUrl);
        if (partner == null)
            return true;

        dao.delete(partner);
        return true;
    }

    public boolean updateWebPartner(String userId, String url, RegistryPartner partner) {
        if (!new AccountController().isAdministrator(userId))
            return false;

        Logger.info(userId + ": updating partner (" + url + ") to " + partner.toString());
        RemotePartner existing = dao.getByUrl(url);
        if (existing == null)
            return false;

        RemotePartnerStatus newStatus = RemotePartnerStatus.APPROVED;
        if (newStatus == existing.getPartnerStatus())
            return true;

        // contact remote with new api key that allows them to contact this instance
        String apiKey = Utils.generateToken();
        String myURL = Utils.getConfigValue(ConfigurationKey.URI_PREFIX);
        String myName = Utils.getConfigValue(ConfigurationKey.PROJECT_NAME);

        RegistryPartner thisPartner = new RegistryPartner();
        thisPartner.setUrl(myURL);
        thisPartner.setName(myName);
        thisPartner.setApiKey(apiKey);  // key to use in contacting this instance

        IceRestClient client = IceRestClient.getInstance();
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        try {
            client.post(partner.getUrl(), "/rest/web/partner/remote", thisPartner, RegistryPartner.class);
            existing.setPartnerStatus(newStatus);
            existing.setAuthenticationToken(apiKey);
            dao.update(existing);
            return true;
        } catch (Exception e) {
            Logger.error(e);
            return false;
        }
    }

    /**
     * Runs the web of registries task for contacting the appropriate partners to enable or disable
     * web of registries functionality
     *
     * @param enable if true, enables WoR; disables it otherwise
<<<<<<< HEAD
     * @return list of received partners if WoR functionality is being enabled, is successful and
     *         this is not the master node, otherwise it just returns an empty list, or null in the event of an
     *         exception
     */
    public ArrayList<RegistryPartner> setEnable(String url, boolean enable) throws ControllerException {
        ConfigurationController controller = new ConfigurationController();
        String NODE_MASTER = Utils.getConfigValue(ConfigurationKey.WEB_OF_REGISTRIES_MASTER);

        try {
            controller.setPropertyValue(ConfigurationKey.JOIN_WEB_OF_REGISTRIES, Boolean.toString(enable));
            controller.setPropertyValue(ConfigurationKey.URI_PREFIX, url);

            if (NODE_MASTER.equalsIgnoreCase(url))
                return new ArrayList<>();

            // use url if name is empty
            String name = Utils.getConfigValue(ConfigurationKey.PROJECT_NAME);
            if (name.trim().isEmpty()) {
                name = url;
            }

            // tODO
//            IRegistryAPI api = client.getAPIPortForURL(NODE_MASTER);
//            WebOfRegistries wor = api.setRegistryPartnerAdd(url, name, enable);
//            if (!enable)
            return new ArrayList<>();

//            // set values
//            Iterator<RegistryPartner> iterator = wor.getPartners().iterator();
//            while (iterator.hasNext()) {
//                RegistryPartner partner = iterator.next();
//                if (partner.getUrl().isEmpty() || url.equalsIgnoreCase(partner.getUrl())) {
//                    iterator.remove();
//                    continue;
//                }
//                addRegistryPartner(partner.getUrl(), partner.getName());
//            }
//
//            WebOfRegistriesContactTask contactTask = new WebOfRegistriesContactTask(wor.getPartners());
//            IceExecutorService.getInstance().runTask(contactTask);
//            return wor.getPartners();
        } catch (Exception e) {
            Logger.warn("Error contacting master node to remove this server from web of registries");
=======
     */
    public void setEnable(boolean enable) {
        String thisUrl = Utils.getConfigValue(ConfigurationKey.URI_PREFIX);
        WebOfRegistriesTask contactTask = new WebOfRegistriesTask(thisUrl, enable);
        IceExecutorService.getInstance().runTask(contactTask);
    }

    public RegistryPartner getWebPartner(String userId, long partnerId) {
        return dao.get(partnerId).toDataTransferObject();
    }

    public RegistryPartner getRegistryPartner(String token, String url) {
        RemotePartner partner = dao.getByUrl(url);
        if (partner == null)
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
            return null;

        String encryptedToken = partner.getAuthenticationToken();
        TokenHash hash = new TokenHash();
        if (encryptedToken.equalsIgnoreCase(hash.encryptPassword(token, partner.getSalt()))) {
            partner.toDataTransferObject();
        }
        return null;
    }

    public List<RegistryPartner> getWebPartners(String apiKey, String url) {
        if (!isInWebOfRegistries())
            return null;

        RemotePartner partner = dao.getByUrl(url);
        if (partner == null)
            return null;
        TokenHash hash = new TokenHash();
        if (!partner.getAuthenticationToken().equals(hash.encryptPassword(apiKey, partner.getSalt())))
            return null;

<<<<<<< HEAD
            // generate authentication token
            token = Utils.generateToken();
            partner.setAuthenticationToken(token);
            dao.update(partner);
            return token;
        } catch (DAOException de) {
            throw new ControllerException(de);
        }
    }

    public RegistryPartner getWebPartner(String userId, long partnerId) {
        return dao.get(partnerId).toDataTransferObject();
    }

    /**
     * Retrieves the stored api key for the server specified in the parameter
     * and the server status is approved
     *
     * @param url remote registry identifier
     * @return stored api key if one exists
     * @throws ControllerException
     */
    public String getApiKey(String url) throws ControllerException {
        RemotePartner partner;
        try {
            partner = dao.getByUrl(url);
        } catch (DAOException e) {
            throw new ControllerException(e);
        }
        if (partner == null || partner.getPartnerStatus() == RemotePartnerStatus.BLOCKED)
=======
        return getWebPartners();
    }

    public List<RegistryPartner> getWebPartners(String userId) {
        if (!isInWebOfRegistries() || !new AccountController().isAdministrator(userId))
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
            return null;
        return getWebPartners();
    }

    protected List<RegistryPartner> getWebPartners() {
        List<RemotePartner> partners = DAOFactory.getRemotePartnerDAO().getRegistryPartners();
        List<RegistryPartner> registryPartners = new ArrayList<>();
        if (partners == null)
            return registryPartners;

        for (RemotePartner remotePartner : partners) {
            registryPartners.add(remotePartner.toDataTransferObject());
        }

        return registryPartners;
    }

    /**
     * Checks if the web of registries admin config value has been set to enable this ICE instance
     * to join the web of registries configuration
     *
     * @return true if value has been set to the affirmative, false otherwise
     */
<<<<<<< HEAD
    public void setApiKeyForPartner(String url, String apiKey) throws ControllerException {
        try {
            RemotePartner partner = dao.getByUrl(url);
            partner.setApiKey(apiKey);
            dao.update(partner);
        } catch (DAOException de) {
            throw new ControllerException(de);
        }
    }

    public String requestApiKeyForNewPartner(String url, String name, String authenticationKey)
            throws ControllerException {
        try {
            RemotePartner partner = dao.getByUrl(url);
            if (partner == null) {
                partner = new RemotePartner();
                partner.setUrl(url);
                partner.setName(name);
                partner.setAdded(new Date());
                partner.setPartnerStatus(RemotePartnerStatus.APPROVED);
                partner.setAuthenticationToken(UUID.randomUUID().toString());
                partner.setApiKey(authenticationKey);
                dao.create(partner);
            } else {
                if (partner.getAuthenticationToken() == null)
                    partner.setAuthenticationToken(UUID.randomUUID().toString());
                partner.setApiKey(authenticationKey);
                dao.update(partner);
            }
            return partner.getAuthenticationToken();
        } catch (DAOException ce) {
            throw new ControllerException(ce);
        }
    }

=======
    private boolean isInWebOfRegistries() {
        String value = Utils.getConfigValue(ConfigurationKey.JOIN_WEB_OF_REGISTRIES);
        return ("yes".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value));
    }
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
}
