package main;

import java.util.List;

/**
 * Класс для случайной генерации заказов.
 * Producer-Consumer реализация.
 * RandomOrderGeneratorTask у нас Producer, т.к. производит заказы.
 * Cook - это Consumer, т.к. обрабатывает заказы
 */
public class RandomOrderGeneratorTask implements Runnable {
    // Хранилище всех планшетов
    private List<Tablet> tablets;
    // Интервал времени создания заказа
    private int interval;

    public RandomOrderGeneratorTask(List<Tablet> tablets, int interval) {
        this.tablets = tablets;
        this.interval = interval;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // выбираем номер случайного планшета
                int k = (int) (Math.random() * tablets.size());
                // выбираем планшет
                Tablet expected = tablets.get(k);
                // создаем заказ
                expected.createTestOrder();
                Thread.sleep(interval);
            }
        } catch (InterruptedException e) {
        }
    }
}

