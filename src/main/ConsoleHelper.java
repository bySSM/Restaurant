package main;

import main.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для взаимодействия с консолью.
 * Так как этот класс не хранит никаких данных и состояний,
 * поэтому все методы будут статическими
 */

public class ConsoleHelper {
    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

    // Метод для вывода message в консоль
    public static void writeMessage(String message) {
        System.out.println(message);
    }

    // Метод для чтения строки с консоли
    public static String readString() throws IOException {
        return bis.readLine();
    }

    // Метод, который просит пользователя выбрать блюдо и добавляем его в список
    public static List<Dish> getAllDishesForOrder() throws IOException {
        // Создаем список блюд
        List<Dish> dishes = new ArrayList<>();

        // Просим написать блюда из предложенных или exit, чтобы завершить заказ
        writeMessage("Please choose a dish from the list: "
                + Dish.allDishesToString()
                + "\n or type 'exit' to complete the order");

        // Просим пользователя ввести строку - название блюда
        while (true) {
            // Читаем, что ввел пользователь
            String dishName = readString().trim();

            // Если пользователь ввел exit, то выходим из заказа
            if ("exit".equals(dishName)) {
                break;
            }

            try {
                // Если введенное блюдо есть в нашем enum, то добавляем его в список dishes
                Dish dish = Dish.valueOf(dishName);
                dishes.add(dish);
                // Пишем пользователю, что блюдо добавлено
                writeMessage(dishName + " has been successfully added to your order");
            } catch (Exception e) {
                // В случае ошибки, пишет, что блюдо не обнаружено
                writeMessage(dishName + " hasn't been detected");
            }
        }

        return dishes;
    }
}

