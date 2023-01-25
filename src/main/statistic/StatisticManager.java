package main.statistic;

import main.statistic.event.CookedOrderEventDataRow;
import main.statistic.event.EventDataRow;
import main.statistic.event.EventType;
import main.statistic.event.VideoSelectedEventDataRow;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Класс, с помощью которого будет регистрировать события в хранилище.
 * У нас должно быть одно хранилище с одной точкой входа.
 * Поэтому сделаем StatisticManager синглтоном.
 */

public class StatisticManager {
    private static StatisticManager ourInstance = new StatisticManager();

    // Метод, который возвращает наш объект, который существует в единственном экземпляре
    public static StatisticManager getInstance() {
        return ourInstance;
    }

    // Менеджер хранилища
    private StatisticStorage statisticStorage = new StatisticStorage();

    // Приватный конструктор
    private StatisticManager() {
    }

    /**
     * Хранилище связано 1 к 1 с менеджером, т.е. один менеджер и одно
     * хранилище на приложение. К хранилищу может иметь доступ только StatisticManager
     * Поэтому хранилище должно быть приватным inner классом.
     */
    private class StatisticStorage {
        // Хранилище данных событий
        private Map<EventType, List<EventDataRow>> storage = new HashMap<>();

        private StatisticStorage() {
            // Заполняем хранилище событиями
            for (EventType type : EventType.values()) {
                this.storage.put(type, new ArrayList<EventDataRow>());
            }
        }

        // Метод для добавления данных в карты
        private void put(EventDataRow data) {
            EventType type = data.getType();
            // Если такого события нет в хранилище, то бросаем исключение
            if (!this.storage.containsKey(type))
                throw new UnsupportedOperationException();

            this.storage.get(type).add(data);
        }

        // Метод, который дает доступ к данным хранилища
        private List<EventDataRow> get(EventType type) {
            if (!this.storage.containsKey(type))
                throw new UnsupportedOperationException();

            return this.storage.get(type);
        }
    }

    // Метод, который регистрирует событие в хранилище
    public void register(EventDataRow data) {
        this.statisticStorage.put(data);
    }

    // Метод, который из хранилища достанет все данные, относящиеся к
    // отображению рекламы, и посчитает общую прибыль за каждый день
    public Map<String, Long> getProfitMap() {
        Map<String, Long> res = new HashMap<>();
        List<EventDataRow> rows = statisticStorage.get(EventType.SELECTED_VIDEOS);
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        long total = 0L;
        for (EventDataRow row : rows) {
            VideoSelectedEventDataRow dataRow = (VideoSelectedEventDataRow) row;
            String date = format.format(dataRow.getDate());
            if (!res.containsKey(date)) {
                res.put(date, 0L);
            }
            total += dataRow.getAmount();
            res.put(date, res.get(date) + dataRow.getAmount());
        }

        res.put("Total", total);

        return res;
    }

    // Метод, который из хранилища достанет все данные, относящиеся к
    // длительности работы повара за день
    public Map<String, Map<String, Integer>> getCookWorkloadingMap() {
        Map<String, Map<String, Integer>> res = new HashMap<>(); //name, time
        List<EventDataRow> rows = statisticStorage.get(EventType.COOKED_ORDER);
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        for (EventDataRow row : rows) {
            CookedOrderEventDataRow dataRow = (CookedOrderEventDataRow) row;
            String date = format.format(dataRow.getDate());
            if (!res.containsKey(date)) {
                res.put(date, new HashMap<String, Integer>());
            }
            Map<String, Integer> cookMap = res.get(date);
            String cookName = dataRow.getCookName();

            if (!cookMap.containsKey(cookName)) {
                cookMap.put(cookName, 0);
            }

            Integer totalTime = cookMap.get(cookName);
            cookMap.put(cookName, totalTime + dataRow.getTime());
        }

        return res;
    }
}
