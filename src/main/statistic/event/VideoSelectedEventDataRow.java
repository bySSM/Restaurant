package main.statistic.event;

import main.ad.Advertisement;

import java.util.Date;
import java.util.List;

/**
 * Класс представляет событие "выбрали набор видео-роликов для заказа"
 */

public class VideoSelectedEventDataRow implements EventDataRow {
    // сумма денег в копейках
    private long amount;
    // список видео-роликов, отобранных для показа
    private List<Advertisement> optimalVideoSet;
    // общая продолжительность показа отобранных рекламных роликов
    private int totalDuration;
    // текущая дата
    private Date currentDate;

    public VideoSelectedEventDataRow(List<Advertisement> optimalVideoSet, long amount, int totalDuration) {
        this.amount = amount;
        this.optimalVideoSet = optimalVideoSet;
        this.totalDuration = totalDuration;
        this.currentDate = new Date();
    }

    @Override
    public EventType getType() {
        return EventType.SELECTED_VIDEOS;
    }

    @Override
    public Date getDate() {
        return currentDate;
    }

    @Override
    public int getTime() {
        return totalDuration;
    }

    public long getAmount() {
        return amount;
    }
}
