package main.kitchen;

import main.ConsoleHelper;

import java.util.Observable;
import java.util.Observer;

/**
 * Класс - официант. Официант будет относить заказы назад к столику.
 * Официант будет безымянным.
 * Официант - наблюдатель (Observer) для Cook
 */
public class Waiter implements Observer {
    @Override
    public void update(Observable cook, Object order) {
        ConsoleHelper.writeMessage(order + " was cooked by " + cook);
    }
}
