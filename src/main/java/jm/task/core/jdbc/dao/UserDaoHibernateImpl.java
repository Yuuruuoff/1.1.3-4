package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.LoggerClass;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory;

    private final Logger logger = new LoggerClass().getLogger();

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getInstance().getSourceSessionFactory();
    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE Users (id bigint GENERATED ALWAYS AS IDENTITY, name varchar, lastname varchar, age smallint)").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE Users").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.severe(e.getMessage());
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.severe(e.getMessage());
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.byId(User.class).load(id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<User> users = session.createQuery("from User").list();
            session.getTransaction().commit();
            return users;
        } catch (HibernateException e) {
            logger.severe(e.getMessage());
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE Users").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.severe(e.getMessage());
        }
    }
}
