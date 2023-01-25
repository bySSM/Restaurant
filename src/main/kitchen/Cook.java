package main.kitchen;

import main.ConsoleHelper;
import main.statistic.StatisticManager;
import main.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Класс повар, он будет готовить блюда
 * Повар - наблюдаемый (Observable) для Waiter
 * Producer-Consumer реализация.
 * RandomOrderGeneratorTask у нас Producer, т.к. производит заказы.
 * Cook - это Consumer, т.к. обрабатывает заказы
 */

public class Cook extends Observable implements Runnable {
    // Имя повара
    private final String name;

    // Занят повар или нет
    private boolean busy;

    // Очередь заказов
    private LinkedBlockingQueue<Order> queue;

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public Cook(String name) {
        this.name = name;
    }

    public boolean isBusy() {
        return busy;
    }

    // Метод начала готовки
    public void startCookingOrder(Order order) {
        // Говорим, что данный повар занят, приступил к работе
        this.busy = true;

        ConsoleHelper.writeMessage(name + " Start cooking - " + order);

        // Время готовки
        int totalCookingTime = order.getTotalCookingTime();

        // Регистрируем событие для повара во время приготовления еды.
        CookedOrderEventDataRow row = new CookedOrderEventDataRow(order.getTablet().toString(), name, totalCookingTime * 60, order.getDishes());
        StatisticManager.getInstance().register(row);

        // Имитируем время готовки заказа (время заказа х 10)
        try {
            Thread.sleep(totalCookingTime * 10L);
        } catch (InterruptedException ignored) {
        }

        // Устанавливаем флаг
        setChanged();
        // Отправляем наблюдателю заказ
        notifyObservers(order);

        // Освобождаем повара
        this.busy = false;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(10);
                // Если в очереди есть заказы, то есть очередь не пустая
                if (!queue.isEmpty()) {
                    // Если повар не занят
                    if (!this.isBusy()) {
                        // Даем заказ повару
                        this.startCookingOrder(queue.take());
                    }
                }
            }
        } catch (InterruptedException e) {
        }
    }
}
