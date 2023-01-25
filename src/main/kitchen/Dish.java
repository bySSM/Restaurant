package main.kitchen;

public enum Dish {
    FISH(25),
    STEAK(30),
    SOUP(15),
    JUICE(5),
    WATER(3);

    // Длительность приготовления заказа в минутах
    private int duration;

    Dish(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    // Метод, который показывает все блюда пользователю
    public static String allDishesToString() {
        String result = "";

        // Проходимся по enum Dish
        for (Dish dish : Dish.values()) {
            // Если наш список еще пустой, то добавляем название блюда
            if ("".equals(result)) {
                result += dish.name();
                // Если строка не пустая, то добавляем в нее через запятую другие блюда
            } else {
                result += ", " + dish.name();
            }
        }

        return result;
    }
}
