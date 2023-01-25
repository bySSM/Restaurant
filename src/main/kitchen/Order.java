package main.kitchen;

import main.ConsoleHelper;
import main.Tablet;

import java.io.IOException;
import java.util.List;

/**
 * В классе Order (заказ) должна быть информация, относящаяся к списку
 * выбранных пользователем блюд. Т.е. где-то должен быть список всех блюд,
 * и должен быть список выбранных блюд в классе Order
 */
public class Order {
    // Планшет
    private final Tablet tablet;

    public List<Dish> getDishes() {
        return dishes;
    }

    public Tablet getTablet() {
        return tablet;
    }

    // Список выбранных блюд
    protected List<Dish> dishes;

    // Метод для инициализации блюд
    protected void initDishes() throws IOException {
        this.dishes = ConsoleHelper.getAllDishesForOrder();
    }

    public Order(Tablet tablet) throws IOException {
        this.tablet = tablet;
        initDishes();
        ConsoleHelper.writeMessage(toString());
    }

    @Override
    public String toString() {
        if (!dishes.isEmpty()) {
            return "Your order: " + dishes +
                    " of " + tablet +
                    ", cooking time " + getTotalCookingTime() +
                    " min";
        } else {
            return "";
        }
    }

    // Метод, который определяет пустой или нет лист заказа
    public boolean isEmpty() {
        return dishes.isEmpty();
    }

    // Метод, который считает продолжительность приготовления заказа
    public int getTotalCookingTime() {
        int cookingTime = 0;
        for (Dish dish : dishes) {
            cookingTime += dish.getDuration();
        }
        return cookingTime;
    }
}
