package main.ad;

/**
 * Advertisement - рекламное объявление
 */

public class Advertisement {
    // Видеоконтент, который мы будем показывать
    private Object content;

    // Имя/название
    private String name;

    // Начальная сумма, стоимость рекламы в копейках.
    // Используем long, чтобы избежать проблем с округлением
    private long initialAmount;

    // Количество оплаченных показов
    private int hits;

    // Продолжительность видео в секундах
    private int duration;

    // Стоимость одного показа рекламного ролика в копейках
    private long amountPerOneDisplaying;


    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;

        if (hits > 0) {
            amountPerOneDisplaying = initialAmount / hits;
        }
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }

    // Метод для повторной проверки показа роликов
    public void revalidate() {
        // Если поле hits равно 0, то бросаем исключение и уменьшаем количество
        if (hits == 0) {
            throw new UnsupportedOperationException();
        }
        hits--;
    }

    // Метод, который следит за тем, чтобы количество hits было больше 0
    public boolean isActive() {
        return hits > 0;
    }

    public int getHits() {
        return hits;
    }
}
