package observer;

import entities.Distributor;
import entities.Producer;

import java.util.List;

public interface CustomObservable {
    /**
     * TODO
     * @param newObserver
     */
    void addObserver(Distributor newObserver);

    /**
     * TODO
     * @param observerToBeRemoved
     */
    void removeObserver(Distributor observerToBeRemoved);

    /**
     * TODO
     * @param observers
     * @param producerList
     */
    void notifyAllObservers(List<Distributor> observers,
                            List<Producer> producerList);
}
