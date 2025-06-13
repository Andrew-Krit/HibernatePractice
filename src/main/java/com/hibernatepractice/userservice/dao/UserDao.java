package com.hibernatepractice.userservice.dao;

import com.hibernatepractice.userservice.model.User;
import com.hibernatepractice.userservice.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Класс UserDao для отделения логики от БД. Реализованы все CRUD операции.
 */
public class UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    public void saveUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            logger.info("Пользователь сохранён: {}.", user);
        } catch (HibernateException e) {
            if(transaction != null) {
                transaction.rollback();
            }
            logger.error("Ошибка во время сохранения пользователя.", e);
        }
    }

    public User getUserById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            logger.info("Пользователь найден по id {}: {}.", id, user);
            return user;
        } catch (HibernateException e) {
            logger.error("Ошибка во время нахождения пользователя по id.", e);
            return null;
        }
    }

    public List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<User> users = session.createQuery("from User", User.class).list();
            logger.info("Всё пользователи выведены. Количество пользователей={}.", users.size());
            return users;
        } catch (HibernateException e) {
            logger.error("Ошибка во время вывода пользователей.", e);
            return List.of();
        }
    }

    public void updateUser(User user)  {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            logger.info("Пользователь обнвлён: {}.", user);
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            logger.error("Ошибка по время обновления пользователя.", e);
        }
    }

    public void deleteUser(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                logger.info("Пользователь удалён: {}.", user);
            } else {
                logger.warn("Пользователь не найдён для удаления по id={}.", id);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            logger.error("Ошибка во время удаления пользователя.", e);
        }
    }
}
