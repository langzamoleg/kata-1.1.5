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
        Session session = getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS user_for_task (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(30), lastName VARCHAR(35), age INT);").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица создана!");
        } catch (Exception e) {
            System.out.println("Ошибка при создании таблицы!");
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @Override
    public void dropUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.getTransaction();
            transaction.begin();
            session.createSQLQuery("DROP TABLE IF EXISTS test.user_for_task;").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица удалена!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Ошибка при удалении таблицы!");
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            User user_new = new User(name, lastName, age);
            session.save(user_new);
            session.getTransaction().commit();
            System.out.println("Пользователь сохранён!");
        } catch (Exception e) {
            System.out.println("Ошибка при сохранении пользователя!");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            session.delete(String.valueOf(id), session.load(User.class, id));
            session.getTransaction().commit();
            System.out.println("Пользователь удален!");
        } catch (Exception e) {
            System.out.println("Ошибка при удалении пользователя по Id!");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = getSessionFactory().openSession();
        List<User> result = new ArrayList<>();
        try {
            session.getTransaction().begin();
            result = session.createQuery("FROM User").list();
            session.getTransaction().commit();
            System.out.println("Пользователи получены!");
        } catch (Exception e) {
            System.out.println("Ошибка при получении пользователей!");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public void cleanUsersTable() {
        Session session = getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица очищена!");
        } catch (Exception e) {
            System.out.println("Ошибка при очистке таблицы!");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}