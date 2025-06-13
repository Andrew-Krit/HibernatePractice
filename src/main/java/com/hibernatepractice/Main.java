package com.hibernatepractice;

import com.hibernatepractice.userservice.dao.UserDao;
import com.hibernatepractice.userservice.model.User;

import java.util.List;
import java.util.Scanner;

/**
 * Точка входа в наше приложение.
 */
public class Main {
    public static void main(String[] args) {

        UserDao userDao = new UserDao();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    1. Создать пользователя
                    2. Показать всех пользователей
                    3. Найти пользователя по ID
                    4. Обновить пользователя
                    5. Удалить пользователя
                    0. Выход
                    """);

            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 :
                    scanner.nextLine();
                    System.out.print("Имя: ");
                    String name = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Возраст: ");
                    int age = scanner.nextInt();

                    userDao.saveUser(new User(name, email, age));
                    break;
                case 2:
                    List<User> users = userDao.getAllUsers();
                    users.forEach(System.out::println);
                    break;
                case 3:
                    System.out.print("ID: ");
                    System.out.println(userDao.getUserById(scanner.nextInt()));
                    break;
                case 4:
                    System.out.print("ID пользователя для обновления: ");
                    Integer id_editUser = scanner.nextInt();
                    scanner.nextLine();
                    User user = userDao.getUserById(id_editUser);

                    if (user == null) {
                        System.out.println("Пользователь не найден.");
                        break;
                    }

                    System.out.print("Новое имя: ");
                    user.setName(scanner.nextLine());
                    System.out.print("Новый email: ");
                    user.setEmail(scanner.nextLine());
                    System.out.print("Новый возраст: ");
                    user.setAge(scanner.nextInt());

                    userDao.updateUser(user);
                    break;
                case 5:
                    System.out.print("ID для удаления: ");
                    userDao.deleteUser(scanner.nextLong());
                    break;
                case 0:
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Неверный выбор.");
                    break;
            }
        }
    }
}