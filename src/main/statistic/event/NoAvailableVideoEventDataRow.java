package main.statistic.event;

import java.util.Date;

/**
 * Класс представляет событие "нет ни одного видео-ролика,
 * который можно показать во время приготовления заказа"
 */

public class NoAvailableVideoEventDataRow implements EventDataRow {
    // время приготовления заказа в секундах
    private int totalDuration;
    // текущая дата
    private Date currentDate;

    public NoAvailableVideoEventDataRow(int totalDuration) {
        this.totalDuration = totalDuration;
        this.currentDate = new Date();
    }

    @Override
    public EventType getType() {
        return EventType.NO_AVAILABLE_VIDEO;
    }

    @Override
    public Date getDate() {
        return currentDate;
    }

    @Override
    public int getTime() {
        return totalDuration;
    }
}

