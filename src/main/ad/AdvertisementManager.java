package main.ad;

import main.statistic.StatisticManager;
import main.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * У каждого планшета будет свой объект менеджера,
 * который будет подбирать оптимальный набор роликов
 * и их последовательность для каждого заказа.
 * Он также будет взаимодействовать с плеером и отображать ролики.
 */

public class AdvertisementManager {
    // Ссылка на экземпляр AdvertisementStorage
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();

    /**
     * Определим необходимые данные для объекта AdvertisementManager - это время
     * выполнения заказа поваром. Т.к. продолжительность видео у нас хранится в
     * секундах, то и время выполнения заказа тоже будем принимать в секундах.
     */
    private int timeSeconds;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    // Метод обработки рекламного видео
    public void processVideos() {
        this.totalTimeSecondsLeft = Integer.MAX_VALUE;
        obtainOptimalVideoSet(new ArrayList<Advertisement>(), timeSeconds, 0l);

        // Регистрируем событие "видео выбрано"
        VideoSelectedEventDataRow row = new VideoSelectedEventDataRow(optimalVideoSet, maxAmount, timeSeconds - totalTimeSecondsLeft);
        StatisticManager.getInstance().register(row);

        displayAdvertisement();
    }

    // Рекурсия
    // максимальное количество
    private long maxAmount;
    // оптимальный список видео
    private List<Advertisement> optimalVideoSet;
    // общее время оставшихся секунд
    private int totalTimeSecondsLeft;

    // Получаем оптимальные настройки видео
    private void obtainOptimalVideoSet(List<Advertisement> totalList, int currentTimeSecondsLeft, long currentAmount) {
        if (currentTimeSecondsLeft < 0) {
            return;
        } else if (currentAmount > maxAmount
                || currentAmount == maxAmount && (totalTimeSecondsLeft > currentTimeSecondsLeft
                || totalTimeSecondsLeft == currentTimeSecondsLeft && totalList.size() < optimalVideoSet.size())) {
            this.totalTimeSecondsLeft = currentTimeSecondsLeft;
            this.optimalVideoSet = totalList;
            this.maxAmount = currentAmount;
            if (currentTimeSecondsLeft == 0) {
                return;
            }
        }

        ArrayList<Advertisement> tmp = getActualAdvertisements();
        tmp.removeAll(totalList);
        for (Advertisement ad : tmp) {
            if (!ad.isActive()) continue;
            ArrayList<Advertisement> currentList = new ArrayList<>(totalList);
            currentList.add(ad);
            obtainOptimalVideoSet(currentList, currentTimeSecondsLeft - ad.getDuration(),
                    currentAmount + ad.getAmountPerOneDisplaying());
        }
    }

    private ArrayList<Advertisement> getActualAdvertisements() {
        ArrayList<Advertisement> advertisements = new ArrayList<>();
        for (Advertisement ad : storage.list()) {
            if (ad.isActive()) {
                advertisements.add(ad);
            }
        }
        return advertisements;
    }

    private void displayAdvertisement() {
        //TODO displaying
        if (optimalVideoSet == null || optimalVideoSet.isEmpty()) {
            throw new NoVideoAvailableException();
        }

        Collections.sort(optimalVideoSet, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                long l = o2.getAmountPerOneDisplaying() - o1.getAmountPerOneDisplaying();
                return (int) (l != 0 ? l : o2.getDuration() - o1.getDuration());
            }
        });

        for (Advertisement ad : optimalVideoSet) {
            displayInPlayer(ad);
            ad.revalidate();
        }
    }

    private void displayInPlayer(Advertisement advertisement) {
        //TODO get Player instance and display content
        System.out.println(advertisement.getName() + " is displaying... " + advertisement.getAmountPerOneDisplaying() +
                ", " + (1000 * advertisement.getAmountPerOneDisplaying() / advertisement.getDuration()));
    }
}

