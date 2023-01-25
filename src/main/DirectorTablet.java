package main;

import main.ad.Advertisement;
import main.ad.StatisticAdvertisementManager;
import main.statistic.StatisticManager;

import java.util.*;

/**
 * Планшет директора, где он может смотреть различную статистику
 */
public class DirectorTablet {

    // Метод, который показывает какую сумму заработали на рекламе
    public void printAdvertisementProfit() {
        StatisticManager statisticManager = StatisticManager.getInstance();
        Map<String, Long> profitMap = statisticManager.getProfitMap();
        ArrayList<String> list = new ArrayList(profitMap.keySet());
        Collections.sort(list);

        for (String key : list) {
            long aLong = profitMap.get(key);
            System.out.println(key + " - " + (aLong / 100) + "." + (aLong % 100));
        }
    }

    // Метод, который показывает загрузку (рабочее время повара)
    public void printCookWorkloading() {
        StatisticManager statisticManager = StatisticManager.getInstance();
        Map<String, Map<String, Integer>> cookWorkloadingMap = statisticManager.getCookWorkloadingMap();
        ArrayList<String> list = new ArrayList(cookWorkloadingMap.keySet());
        Collections.sort(list);

        for (String key : list) {
            Map<String, Integer> cookMap = cookWorkloadingMap.get(key);
            System.out.println(key);

            ArrayList<String> cookNames = new ArrayList(cookMap.keySet());
            Collections.sort(cookNames);
            for (String cookName : cookNames) {
                System.out.println(cookName + " - " + ((cookMap.get(cookName) + 59) / 60) + " min");
            }

            System.out.println();
        }
    }

    // Метод, который показывает список активных роликов и оставшееся
    // количество показов по каждому
    public void printActiveVideoSet() {
        List<Advertisement> videoSet = StatisticAdvertisementManager.getInstance().getVideoSet(true);
        Collections.sort(videoSet, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        });

        for (Advertisement advertisement : videoSet) {
            System.out.println(advertisement.getName() + " - " + advertisement.getHits());
        }

    }

    // Метод, который показывает список неактивных роликов
    // (с оставшемся количеством показов равным нулю)
    public void printArchivedVideoSet() {
        List<Advertisement> videoSet = StatisticAdvertisementManager.getInstance().getVideoSet(false);
        Collections.sort(videoSet, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        });

        for (Advertisement advertisement : videoSet) {
            System.out.println(advertisement.getName());
        }
    }
}

