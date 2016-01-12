package org.jbei.ice.lib.net;

import org.apache.commons.lang.StringUtils;
import org.jbei.ice.lib.access.RemotePermission;
import org.jbei.ice.lib.account.AccountTransfer;
import org.jbei.ice.lib.common.logging.Logger;
import org.jbei.ice.lib.config.ConfigurationController;
import org.jbei.ice.lib.dao.DAOFactory;
import org.jbei.ice.lib.dao.hibernate.RemotePartnerDAO;
import org.jbei.ice.lib.dao.hibernate.RemotePermissionDAO;
import org.jbei.ice.lib.dto.ConfigurationKey;
import org.jbei.ice.lib.dto.Setting;
import org.jbei.ice.lib.dto.comment.UserComment;
<<<<<<< HEAD
import org.jbei.ice.lib.dto.entry.AttachmentInfo;
import org.jbei.ice.lib.dto.entry.PartData;
import org.jbei.ice.lib.dto.entry.PartStatistics;
=======
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
import org.jbei.ice.lib.dto.entry.TraceSequenceAnalysis;
import org.jbei.ice.lib.dto.folder.FolderDetails;
import org.jbei.ice.lib.dto.permission.RemoteAccessPermission;
import org.jbei.ice.lib.dto.sample.PartSample;
import org.jbei.ice.lib.dto.web.RegistryPartner;
<<<<<<< HEAD
import org.jbei.ice.lib.dto.web.RemotePartnerStatus;
import org.jbei.ice.lib.dto.web.WebEntries;
=======
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
import org.jbei.ice.lib.dto.web.WebOfRegistries;
import org.jbei.ice.lib.entry.EntrySelection;
import org.jbei.ice.lib.executor.IceExecutorService;
import org.jbei.ice.lib.executor.TransferTask;
import org.jbei.ice.lib.utils.Utils;
import org.jbei.ice.lib.vo.FeaturedDNASequence;
<<<<<<< HEAD
import org.jbei.ice.services.rest.RestClient;

import java.io.File;
=======
import org.jbei.ice.services.rest.IceRestClient;

>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Controller for access remote registries. This registries must be in a web of registries configuration
 * with them since it requires an api key for communication.
 *
 * @author Hector Plahar
 */
public class RemoteAccessController {

    private final RemotePermissionDAO dao;
    private final WoRController webController;
    private final RemotePartnerDAO remotePartnerDAO;
<<<<<<< HEAD
    private final RestClient restClient;
=======
    private final IceRestClient iceRestClient;
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c

    public RemoteAccessController() {
        this.dao = DAOFactory.getRemotePermissionDAO();
        this.remotePartnerDAO = DAOFactory.getRemotePartnerDAO();
        webController = new WoRController();
<<<<<<< HEAD
        restClient = RestClient.getInstance();
=======
        iceRestClient = IceRestClient.getInstance();
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
    }

    /**
     * Retrieves all folders with status "PUBLIC" on the registry partner with id specified in parameter
     *
     * @param partnerId unique (local) identifier for remote partner
     * @return list of folders returned by the partner that are marked with status "PUBLIC",
     *         null on exception
     */
<<<<<<< HEAD
=======
    @SuppressWarnings("unchecked")
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
    public ArrayList<FolderDetails> getAvailableFolders(long partnerId) {
        RemotePartner partner = this.remotePartnerDAO.get(partnerId);
        if (partner == null)
            return null;

        try {
            String restPath = "/rest/folders/public";
<<<<<<< HEAD
            return (ArrayList) restClient.get(partner.getUrl(), restPath, ArrayList.class);
=======
            return (ArrayList) iceRestClient.get(partner.getUrl(), restPath, ArrayList.class);
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        } catch (Exception e) {
            Logger.error(e);
            return null;
        }
    }

    public Setting getMasterVersion() {
        String value = new ConfigurationController().getPropertyValue(ConfigurationKey.JOIN_WEB_OF_REGISTRIES);
        if (StringUtils.isEmpty(value))
            return new Setting("version", ConfigurationKey.APPLICATION_VERSION.getDefaultValue());

        // retrieve version
<<<<<<< HEAD
        return (Setting) restClient.get(value, "/rest/config/version");
    }

    public PartData getPublicEntry(long remoteId, long entryId) {
        RemotePartner partner = this.remotePartnerDAO.get(remoteId);
        if (partner == null || partner.getPartnerStatus() != RemotePartnerStatus.APPROVED)
            return null;

        return (PartData) restClient.get(partner.getUrl(), "/rest/parts/" + entryId, PartData.class);
    }

    public PartData getPublicEntryTooltip(long remoteId, long entryId) {
        RemotePartner partner = this.remotePartnerDAO.get(remoteId);
        if (partner == null || partner.getPartnerStatus() != RemotePartnerStatus.APPROVED)
            return null;

        String path = "/rest/parts/" + entryId + "/tooltip";
        return (PartData) restClient.get(partner.getUrl(), path, PartData.class);
    }

    public PartStatistics getPublicEntryStatistics(long remoteId, long entryId) {
        RemotePartner partner = this.remotePartnerDAO.get(remoteId);
        if (partner == null || partner.getPartnerStatus() != RemotePartnerStatus.APPROVED)
            return null;

        String path = "/rest/parts/" + entryId + "/statistics";
        return (PartStatistics) restClient.get(partner.getUrl(), path, PartStatistics.class);
    }

    public FeaturedDNASequence getPublicEntrySequence(long remoteId, long entryId) {
        RemotePartner partner = this.remotePartnerDAO.get(remoteId);
        if (partner == null || partner.getPartnerStatus() != RemotePartnerStatus.APPROVED)
            return null;

        String path = "/rest/parts/" + entryId + "/sequence";
        return (FeaturedDNASequence) restClient.get(partner.getUrl(), path, FeaturedDNASequence.class);
    }

    public ArrayList<AttachmentInfo> getPublicEntryAttachments(long remoteId, long entryId) {
        RemotePartner partner = this.remotePartnerDAO.get(remoteId);
        if (partner == null || partner.getPartnerStatus() != RemotePartnerStatus.APPROVED)
            return null;

        String path = "/rest/parts/" + entryId + "/attachments";
        return (ArrayList) restClient.get(partner.getUrl(), path, ArrayList.class);
    }

    public File getPublicAttachment(long remoteId, String fileId) {
        String path = "/rest/file/attachment/" + fileId; // todo
        return null;
    }

    public WebEntries getPublicEntries(long remoteId, int offset, int limit, String sort, boolean asc) {
        RemotePartner partner = this.remotePartnerDAO.get(remoteId);
        if (partner == null)
            return null;

        FolderDetails details;
        try {
            String restPath = "/rest/folders/public/entries";
            HashMap<String, Object> queryParams = new HashMap<>();
            queryParams.put("offset", offset);
            queryParams.put("limit", limit);
            queryParams.put("asc", asc);
            queryParams.put("sort", sort);
            details = (FolderDetails) restClient.get(partner.getUrl(), restPath, FolderDetails.class, queryParams);
        } catch (Exception e) {
            Logger.error(e);
            return null;
        }

        if (details == null)
            return null;

        WebEntries entries = new WebEntries();
        entries.setRegistryPartner(partner.toDataTransferObject());
        entries.setCount(details.getCount());
        entries.setEntries(details.getEntries());
        return entries;
=======
        return (Setting) iceRestClient.get(value, "/rest/config/version");
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
    }

    public void addPermission(String requester, RemoteAccessPermission permission) {
        WebOfRegistries registries = webController.getRegistryPartners(true);

        // search for partners with user
        for (RegistryPartner partner : registries.getPartners()) {
            String url = partner.getUrl();

            try {
<<<<<<< HEAD
                Object result = restClient.get(url, "/rest/users/" + permission.getUserId(), AccountTransfer.class);
=======
                Object result = iceRestClient.get(url, "/rest/users/" + permission.getUserId(), AccountTransfer.class);
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
                if (result == null)
                    continue;

                // user found, create new permission user secret sauce
                String privateSecret = Utils.encryptSha256(UUID.randomUUID().toString());
                String secretSource = Utils.encryptSha256(permission.getUserId() + privateSecret);
                if (StringUtils.isEmpty(secretSource))
                    continue;

                // local record
                RemotePermission remotePermission = new RemotePermission();
                RemotePartner remotePartner = remotePartnerDAO.getByUrl(url);
                remotePermission.setRemotePartner(remotePartner);
                remotePermission.setUserId(permission.getUserId());
                remotePermission.setSecret(privateSecret);
                remotePermission.setAccessType(permission.getAccessType());
                dao.create(remotePermission);
            } catch (Exception e) {
                Logger.error(e);
            }
        }
    }

    public AccountTransfer getRemoteUser(long remoteId, String email) {
        RemotePartner partner = this.remotePartnerDAO.get(remoteId);
        if (partner == null)
            return null;

<<<<<<< HEAD
        Object result = restClient.get(partner.getUrl(), "/rest/users/" + email, AccountTransfer.class);
=======
        Object result = iceRestClient.get(partner.getUrl(), "/rest/users/" + email, AccountTransfer.class);
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        if (result == null)
            return null;

        return (AccountTransfer) result;
    }

    public FolderDetails getPublicFolderEntries(long remoteId, long folderId, String sort, boolean asc, int offset,
            int limit) {
        RemotePartner partner = this.remotePartnerDAO.get(remoteId);
        if (partner == null)
            return null;

        try {
            String restPath = "/rest/folders/" + folderId + "/entries";
            HashMap<String, Object> queryParams = new HashMap<>();
            queryParams.put("offset", offset);
            queryParams.put("limit", limit);
            queryParams.put("asc", asc);
            queryParams.put("sort", sort);
<<<<<<< HEAD
            Object result = restClient.get(partner.getUrl(), restPath, FolderDetails.class, queryParams);
=======
            Object result = iceRestClient.get(partner.getUrl(), restPath, FolderDetails.class, queryParams);
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
            if (result == null)
                return null;

            return (FolderDetails) result;
        } catch (Exception e) {
            Logger.error("Error getting public folder entries from \"" + partner.getUrl() + "\": " + e.getMessage());
            return null;
        }
    }

    public ArrayList<PartSample> getRemotePartSamples(long remoteId, long partId) {
        RemotePartner partner = this.remotePartnerDAO.get(remoteId);
        if (partner == null)
            return null;

        String restPath = "/rest/parts/" + partId + "/samples";
<<<<<<< HEAD
        return (ArrayList) restClient.get(partner.getUrl(), restPath, ArrayList.class);
=======
        return (ArrayList) iceRestClient.get(partner.getUrl(), restPath, ArrayList.class);
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
    }

    public ArrayList<UserComment> getRemotePartComments(long remoteId, long partId) {
        RemotePartner partner = this.remotePartnerDAO.get(remoteId);
        if (partner == null)
            return null;

        String restPath = "/rest/parts/" + partId + "/comments";
<<<<<<< HEAD
        return (ArrayList) restClient.get(partner.getUrl(), restPath, ArrayList.class);
=======
        return (ArrayList) iceRestClient.get(partner.getUrl(), restPath, ArrayList.class);
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
    }

    public void transferEntries(String userId, long remoteId, EntrySelection selection) {
        TransferTask task = new TransferTask(userId, remoteId, selection);
        IceExecutorService.getInstance().runTask(task);
    }

    public FeaturedDNASequence getRemoteSequence(long remoteId, long partId) {
        RemotePartner partner = this.remotePartnerDAO.get(remoteId);
        if (partner == null)
            return null;

        try {
            String restPath = "/rest/parts/" + partId + "/sequence";
<<<<<<< HEAD
            Object result = restClient.get(partner.getUrl(), restPath, FeaturedDNASequence.class);
=======
            Object result = iceRestClient.get(partner.getUrl(), restPath, FeaturedDNASequence.class);
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
            if (result == null)
                return null;

            return (FeaturedDNASequence) result;
        } catch (Exception e) {
            Logger.error(e.getMessage());
            return null;
        }
    }

    public ArrayList<TraceSequenceAnalysis> getRemoteTraces(long remoteId, long partId) {
        RemotePartner partner = this.remotePartnerDAO.get(remoteId);
        if (partner == null)
            return null;

        try {
            String restPath = "/rest/parts/" + partId + "/traces";
<<<<<<< HEAD
            Object result = restClient.get(partner.getUrl(), restPath, ArrayList.class);
=======
            Object result = iceRestClient.get(partner.getUrl(), restPath, ArrayList.class);
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
            if (result == null)
                return null;

            return (ArrayList) result;
        } catch (Exception e) {
            Logger.error(e.getMessage());
            return null;
        }
    }
}
