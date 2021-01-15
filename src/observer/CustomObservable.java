package observer;

import entities.Distributor;
import io.ProducerChanges;

import java.util.List;

public interface CustomObservable {
    /**
     * Notify the distributors that a producer energy has changed.
     *
     * @param distributorsToBeNotified The distributors that will be notified
     */
    void notifyDistributors(List<Distributor> distributorsToBeNotified);

    /**
     * Updates producers energy from the given input of a month.
     *
     * @param producerChangesList The list of changes for the current month
     */
    void updateProducersEnergy(List<ProducerChanges> producerChangesList);
}
