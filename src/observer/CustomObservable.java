package observer;

import entities.Distributor;
import entities.Producer;

import java.util.List;

public interface CustomObservable {
    /**
     * TODO
     * @param newObserver
     */
    void addObserver(final Distributor newObserver);

    /**
     * TODO
     * @param observerToBeRemoved
     */
    void removeObserver(final Distributor observerToBeRemoved);

    /**
     * TODO
     * @param producerModified
     * @param newEnergyPerDistributor
     * @param producerList
     */
    void notifyAllObservers(final Producer producerModified,
                            final double newEnergyPerDistributor,
                            final List<Producer> producerList);
}
