package main.ad;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализуем третий и четвертый пункт статистики - список активных и неактивных роликов.
 * Для этого проще использовать доступ к хранилищу рекламных роликов - класс AdvertisementStorage.
 * Класс, который будет предоставлять информацию из AdvertisementStorage
 * в нужном нам виде. Сделаем его синглтоном.
 */
public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager ourInstance = new StatisticAdvertisementManager();
    // Переменная для доступа к хранилищу видео рекламы
    private AdvertisementStorage storage = AdvertisementStorage.getInstance();

    // Метод, который возвращает наш объект, который существует в единственном экземпляре
    public static StatisticAdvertisementManager getInstance() {
        return ourInstance;
    }

    // Приватный конструктор
    private StatisticAdvertisementManager() {
    }

    // Метод, который достанет из хранилища список активных/неактивных рекламных роликов
    // Если isActive = true - активных, isActive = false - неактивных
    public List<Advertisement> getVideoSet(boolean isActive) {
        List<Advertisement> result = new ArrayList<>();
        for (Advertisement advertisement : storage.list()) {
            if (!isActive ^ advertisement.isActive()) {
                result.add(advertisement);
            }
        }
        return result;
    }
}

