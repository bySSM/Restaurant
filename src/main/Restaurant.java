package main;

import main.kitchen.Cook;
import main.kitchen.Order;
import main.kitchen.Waiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    // Интервал создания заказов
    private static final int ORDER_CREATING_INTERVAL = 100;
    // Очередь на 200 заказов
    private static final LinkedBlockingQueue<Order> ORDER_QUEUE = new LinkedBlockingQueue<>(200);

    public static void main(String[] args) {
        // Создаем планшеты
        List<Tablet> tablets = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Tablet tablet = new Tablet(i + 1);
            tablet.setQueue(ORDER_QUEUE);
            tablets.add(tablet);
        }

        // Создаем первого повара
        Cook cook1 = new Cook("Amigo");
        cook1.setQueue(ORDER_QUEUE);
        // Создаем второго повара
        Cook cook2 = new Cook("Diego");
        cook2.setQueue(ORDER_QUEUE);

        // Создаем официанта
        Waiter waiter = new Waiter();

        // Добавляем официанта наблюдающим за поварами
        cook1.addObserver(waiter);
        cook2.addObserver(waiter);

        Thread cook11 = new Thread(cook1);
        cook11.start();

        Thread cook22 = new Thread(cook2);
        cook22.start();

        // Создаем и запускаем тред на основе созданных объектов
        Thread thread = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
        thread.start();

        try {
            Thread.sleep(1000);
            thread.interrupt();
            thread.join();
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {
        }

        // Создаем планшет директора
        DirectorTablet directorTablet = new DirectorTablet();

        // Выводим статистику для директора
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();
    }
}

