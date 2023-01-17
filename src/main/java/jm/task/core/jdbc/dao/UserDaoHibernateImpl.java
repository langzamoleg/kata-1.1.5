package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()){
            transaction = session.getTransaction();
            transaction.begin();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS user_for_task (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(30), lastName VARCHAR(35), age INT);").addEntity(User.class).executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Ошибка при создании таблицы!");
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()){
            transaction = session.getTransaction();
            transaction.begin();
            session.createSQLQuery("DROP TABLE IF EXISTS test.user_for_task;").executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Ошибка при удалении таблицы!");
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            User user_new = new User(name, lastName, age);
            session.save(user_new);
            transaction.commit();
            System.out.println("Пользователь сохранён!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Ошибка при сохранении пользователя!");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()){
            transaction = session.getTransaction();
            transaction.begin();
            session.delete(String.valueOf(id), session.load(User.class, id));
            transaction.commit();
            System.out.println("Пользователь удален!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Ошибка при удалении пользователя по Id!");
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> result = new ArrayList<>();
        try (Session session = getSessionFactory().openSession()){
            transaction = session.getTransaction();
            transaction.begin();
            result = session.createQuery("FROM User").list();
            transaction.commit();
            System.out.println("Пользователи получены!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Ошибка при получении пользователей!");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
            System.out.println("Таблица очищена!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Ошибка при очистке таблицы!");
            e.printStackTrace();
        }
    }
}