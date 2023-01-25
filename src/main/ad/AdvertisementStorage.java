package main.ad;

import java.util.LinkedList;
import java.util.List;

/**
 * AdvertisementStorage - хранилище рекламных роликов.
 * Так как хранилище рекламных роликов единственное для всего ресторана,
 * то сделаем его синглтоном
 */

public class AdvertisementStorage {
    // класс, который создаем объект в единственном экземпляре
    private static class InstanceHolder {
        private static final AdvertisementStorage ourInstance = new AdvertisementStorage();
    }

    // Метод, который возвращает наш объект, который существует в единственном экземпляре
    public static AdvertisementStorage getInstance() {
        return InstanceHolder.ourInstance;
    }

    // Список с видео, которые у нас есть
    private final List<Advertisement> videos = new LinkedList<>();

    // Приватный конструктор, чтобы создать объект можно было только в методе
    private AdvertisementStorage() {
        // Добавим в список videos рандомные данные
        Object someContent = new Object();

        add(new Advertisement(someContent, "First Video", 5000, 100, 3 * 60)); // 3 min
        add(new Advertisement(someContent, "Second Video", 100, 10, 15 * 60)); //15 min
        add(new Advertisement(someContent, "Third Video", 400, 2, 10 * 60)); //10 min
    }

    // Метод, который вернет список всех существующих доступных видео
    public List<Advertisement> list() {
        return videos;
    }

    // Метод, который добавит новое видео в наш список видео
    public void add(Advertisement advertisement) {
        videos.add(advertisement);
    }
}

