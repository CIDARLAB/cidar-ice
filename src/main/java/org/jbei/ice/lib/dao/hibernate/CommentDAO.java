package org.jbei.ice.lib.dao.hibernate;

import java.util.ArrayList;

import org.jbei.ice.lib.common.logging.Logger;
import org.jbei.ice.lib.dao.DAOException;
import org.jbei.ice.lib.entry.model.Entry;
import org.jbei.ice.lib.models.Comment;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * DAO for comment objects
 *
 * @author Hector Plahar
 */
public class CommentDAO extends HibernateRepository<Comment> {

    @SuppressWarnings("unchecked")
    public ArrayList<Comment> retrieveComments(Entry entry) {
        try {
            Query query = currentSession().createQuery("from " + Comment.class.getName() + " where entry=:entry");
            query.setParameter("entry", entry);
            return new ArrayList<Comment>(query.list());
        } catch (HibernateException he) {
            Logger.error(he);
            throw new DAOException(he);
        }
    }

    public int getCommentCount(Entry entry) {
        Number itemCount = (Number) currentSession().createCriteria(Comment.class)
                .setProjection(Projections.countDistinct("id"))
                .add(Restrictions.eq("entry", entry)).uniqueResult();
        return itemCount.intValue();
    }

    @Override
    public Comment get(long id) {
        return super.get(Comment.class, id);
    }
}
