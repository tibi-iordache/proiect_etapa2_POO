package observer;

import entities.Distributor;
import io.ProducerChanges;

import java.util.List;

public interface CustomObservable {
    /**
     * TODO
     * @param distributorsToBeNotified
     */
    void notifyDistributors(List<Distributor> distributorsToBeNotified);

    /**
     * TODO
     * @param producerChangesList
     */
    void updateProducersEnergy(List<ProducerChanges> producerChangesList);
}
