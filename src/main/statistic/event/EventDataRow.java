package main.statistic.event;

import java.util.Date;

/**
 * Внутри пакета event хранятся классы, связанные с событиями.
 * EventDataRow является интерфейсом-маркером по нему мы определяем,
 * является ли переданный объект событием или нет.
 * EventDataRow - строка данных события
 */
public interface EventDataRow {
    // Метод для хранения своего типа в каждом наследнике
    EventType getType();

    // Метод, который вернет дату создания записи
    Date getDate();

    // Метод, который вернет время или продолжительность
    int getTime();
}

