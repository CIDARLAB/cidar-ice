package org.jbei.ice.lib.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.jbei.ice.lib.common.logging.Logger;
import org.jbei.ice.lib.dao.DAOException;
import org.jbei.ice.lib.net.RemotePartner;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

/**
 * Data Accessor Object for managing {@link org.jbei.ice.lib.net.RemotePartner} Objects
 *
 * @author Hector Plahar
 */
public class RemotePartnerDAO extends HibernateRepository<RemotePartner> {

    @SuppressWarnings("unchecked")
    public ArrayList<RemotePartner> retrieveRegistryPartners() throws DAOException {
        try {
            List list = currentSession().createCriteria(RemotePartner.class).list();
            return new ArrayList<RemotePartner>(list);
        } catch (HibernateException he) {
            Logger.error(he);
            throw new DAOException(he);
        }
    }

    /**
     * Retrieves remote partners by url. the url is also a unique identifier
     * for a partner
     *
     * @param url partner url to retrieve by
     * @return partner is found, null otherwise
     */
    public RemotePartner getByUrl(String url) throws DAOException {
        try {
            Object object = currentSession().createCriteria(RemotePartner.class.getName())
                    .add(Restrictions.eq("url", url)).uniqueResult();
            if (object == null)
                return null;

            return (RemotePartner) object;
        } catch (HibernateException he) {
            Logger.error(he);
            throw new DAOException(he);
        }
    }

    @Override
    public RemotePartner get(long id) {
        return super.get(RemotePartner.class, id);
    }
}
