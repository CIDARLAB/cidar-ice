package org.jbei.ice.lib.dao.hibernate;

import java.util.ArrayList;

import org.jbei.ice.lib.common.logging.Logger;
import org.jbei.ice.lib.dao.DAOException;
import org.jbei.ice.lib.entry.model.Entry;
import org.jbei.ice.lib.models.Audit;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * Accessor for {@link Audit} objects
 *
 * @author Hector Plahar
 */
@SuppressWarnings("unchecked")
public class AuditDAO extends HibernateRepository<Audit> {

    /**
     * Retrieves audit referenced by unique identifier
     *
     * @param id unique identifier for audit class
     * @return Audit object if one is found with identifier
     */
    @Override
    public Audit get(long id) {
        return super.get(Audit.class, id);
    }

    public ArrayList<Audit> getAuditsForEntry(Entry entry) {
        try {
            Query query = currentSession().createQuery("from " + Audit.class.getName() + " where entry=:entry");
            query.setParameter("entry", entry);
            return new ArrayList<Audit>(query.list());
        } catch (HibernateException he) {
            Logger.error(he);
            throw new DAOException(he);
        }
    }

    public int getHistoryCount(Entry entry) {
        Number itemCount = (Number) currentSession().createCriteria(Audit.class)
                .setProjection(Projections.countDistinct("id"))
                .add(Restrictions.eq("entry", entry)).uniqueResult();
        if (itemCount != null)
            return itemCount.intValue();
        return 0;
    }
}
