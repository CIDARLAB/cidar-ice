package org.jbei.ice.lib.dao.hibernate;

<<<<<<< HEAD
import java.util.HashSet;
import java.util.List;
import java.util.Set;

=======
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
import org.jbei.ice.lib.account.model.Account;
import org.jbei.ice.lib.common.logging.Logger;
import org.jbei.ice.lib.dao.DAOException;

<<<<<<< HEAD
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
=======
import java.util.HashSet;
import java.util.List;
import java.util.Set;
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c

/**
 * DAO to manipulate {@link Account} objects in the database.
 *
 * @author Hector Plahar, Timothy Ham, Zinovii Dmytriv
 */
public class AccountDAO extends HibernateRepository<Account> {

    /**
     * Retrieve {@link Account} by id from the database.
     *
     * @param id unique local identifier for object
     * @return Account
     * @throws DAOException
     */
    public Account get(long id) throws DAOException {
        return super.get(Account.class, id);
    }

<<<<<<< HEAD
    @SuppressWarnings("unchecked")
    public Set<Account> getMatchingAccounts(String token, int limit) {
        Session session = currentSession();
        try {
            token = token.toUpperCase();
            String queryString = "from " + Account.class.getName()
                    + " where (UPPER(firstName) like '%" + token
                    + "%') OR (UPPER(lastName) like '%" + token
                    + "%') OR (UPPER(email) like '%" + token + "%')";
            Query query = session.createQuery(queryString);
            if (limit > 0)
                query.setMaxResults(limit);

            return new HashSet<Account>(query.list());
=======
    /**
     * Retrieves accounts whose firstName, lastName, or email fields match the specified token up to the specified limit
     *
     * @param token filter for the account fields
     * @param limit maximum number of matching accounts to return; 0 to return all
     * @return list of matching accounts
     */
    @SuppressWarnings("unchecked")
    public Set<Account> getMatchingAccounts(String token, int limit) {
        try {
            Criteria criteria = currentSession().createCriteria(Account.class)
                    .add(Restrictions.disjunction()
                            .add(Restrictions.ilike("firstName", token, MatchMode.ANYWHERE))
                            .add(Restrictions.ilike("lastName", token, MatchMode.ANYWHERE))
                            .add(Restrictions.ilike("email", token, MatchMode.ANYWHERE)));

            if (limit > 0)
                criteria.setMaxResults(limit);
            return new HashSet<>(criteria.list());
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        } catch (HibernateException e) {
            Logger.error(e);
            throw new DAOException(e);
        }
    }

    /**
     * Retrieve an {@link Account} by the email field.
     *
     * @param email unique email identifier for account
<<<<<<< HEAD
     * @return Account
     */
    public Account getByEmail(String email) {
        Account account = null;
        Session session = currentSession();
        try {
            Query query = session.createQuery("from " + Account.class.getName() + " where LOWER(email) = :email");
            query.setParameter("email", email.toLowerCase());
            Object result = query.uniqueResult();

            if (result != null) {
                account = (Account) result;
            }
=======
     * @return Account record referenced by email or null if email is null
     */
    public Account getByEmail(String email) {
        if (email == null)
            return null;

        try {
            return (Account) currentSession().createCriteria(Account.class)
                    .add(Restrictions.eq("email", email).ignoreCase())
                    .uniqueResult();
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        } catch (HibernateException e) {
            Logger.error(e);
            throw new DAOException("Failed to retrieve Account by email: " + email, e);
        }
<<<<<<< HEAD
        return account;
=======
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
    }

    @SuppressWarnings("unchecked")
    public List<Account> getAccounts(int offset, int limit, String sort, boolean asc) {
        return super.getList(Account.class, offset, limit, sort, asc);
    }

    public long getAccountsCount() {
        try {
            Number itemCount = (Number) currentSession().createCriteria(Account.class.getName())
                    .setProjection(Projections.countDistinct("id")).uniqueResult();
            return itemCount.longValue();
        } catch (HibernateException he) {
            Logger.error(he);
            throw new DAOException(he);
        }
    }
}
