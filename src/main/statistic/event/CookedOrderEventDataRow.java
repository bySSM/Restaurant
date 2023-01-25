package main.statistic.event;

import main.kitchen.Dish;

import java.util.Date;
import java.util.List;

/**
 * Класс представляет событие "повар приготовил заказ"
 */

public class CookedOrderEventDataRow implements EventDataRow {
    // имя планшета
    private String tabletName;
    // имя повара
    private String cookName;
    // время приготовления заказа в секундах
    private int cookingTimeSeconds;
    // список блюд для приготовления
    private List<Dish> cookingDishes;
    // текущая дата
    private Date currentDate;

    public CookedOrderEventDataRow(String tabletName, String cookName, int cookingTimeSeconds, List<Dish> cookingDishes) {
        this.tabletName = tabletName;
        this.cookName = cookName;
        this.cookingTimeSeconds = cookingTimeSeconds;
        this.cookingDishes = cookingDishes;
        this.currentDate = new Date();
    }

    @Override
    public EventType getType() {
        return EventType.COOKED_ORDER;
    }

    @Override
    public Date getDate() {
        return currentDate;
    }

    @Override
    public int getTime() {
        return cookingTimeSeconds;
    }

    public String getCookName() {
        return cookName;
    }
}

