package main;

import main.ad.AdvertisementManager;
import main.ad.NoVideoAvailableException;
import main.kitchen.Order;
import main.kitchen.TestOrder;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс планшет.
 */

public class Tablet {
    // Номер планшета, чтобы можно было однозначно установить,
    // откуда поступил заказ
    private final int number;
    // Логгер ошибок инициализированное именем класса
    private static Logger logger = Logger.getLogger(Tablet.class.getName());
    // Очередь заказов
    private LinkedBlockingQueue<Order> queue;

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public Tablet(int number) {
        this.number = number;
    }

    // Метод будет создавать заказ из тех блюд,
    // которые выберет пользователь
    public void createOrder() {
        Order order = null;
        try {
            // Создаем новый заказ
            order = new Order(this);
            processOrder(order);
        } catch (IOException e) {
            // Если возникает исключение, то в лог должно быть записано
            // сообщение "Console is unavailable.", уровень - SEVERE.
            logger.log(Level.SEVERE, "Console is unavailable.");
        } catch (NoVideoAvailableException e) {
            // Если нет рекламных видео, которые можно показать посетителю,
            // то записываем в лог сообщение
            logger.log(Level.INFO, "No video is available for the order " + order);
        }
    }

    // Метод обработки заказа
    private boolean processOrder(Order order) {
        ConsoleHelper.writeMessage(order.toString());
        // Если заказ пустой, то не отправляем его повару
        if (order.isEmpty())
            return true;

        // Добавляем заказ в очередь
        queue.offer(order);

        // Вызываем метод для показа видео рекламы
        new AdvertisementManager(order.getTotalCookingTime() * 60).processVideos();
        return false;
    }

    // Метод, который будет генерировать случайный заказ со случайными блюдами
    // не общаясь с реальным человеком
    public void createTestOrder() {
        Order order = null;
        try {
            order = new TestOrder(this);
            processOrder(order);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        } catch (NoVideoAvailableException e) {
            logger.log(Level.INFO, "No video is available for the order " + order);
        }
    }

    @Override
    public String toString() {
        return "Tablet{" +
                "number=" + number +
                '}';
    }
}

