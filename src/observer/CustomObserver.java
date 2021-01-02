package observer;

import entities.Producer;

import java.util.List;

public interface CustomObserver {
    /**
     * TODO
     * @param producers
     */
    void update(List<Producer> producers);
}
