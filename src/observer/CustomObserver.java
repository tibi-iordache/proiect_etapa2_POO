package observer;

import entities.Producer;

import java.util.List;

public interface CustomObserver {
    /**
     * Reapplies the distributors strategy if he isn't bankrupt.
     *
     * @param producers The producers list from which the distributor will search
     */
    void update(List<Producer> producers);
}
