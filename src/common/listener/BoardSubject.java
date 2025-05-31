package common.listener;

import model.Board;
import java.util.ArrayList;
import java.util.List;

public class BoardSubject {

    private final List<BoardObserver> observers = new ArrayList<>();

    public void addObserver(BoardObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (BoardObserver observer : observers) {
            observer.update(this);
        }
    }

    public void refreshState() {
        notifyObservers();
    }

}
