package observer;

import entities.Distributor;
import entities.Producer;
import io.ProducerChanges;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Subject implements CustomObservable {
    private List<Producer> producerList;

    public Subject() {
    }

    public Subject(final List<Producer> producerList) {
        this.producerList = producerList;
    }

    @Override
    public void notifyDistributors(List<Distributor> distributorsToBeNotified) {
        distributorsToBeNotified.forEach((d) -> d.update(producerList));
    }

    @Override
    public void updateProducersEnergy(List<ProducerChanges> producerChangesList) {
        List<Distributor> distributorsToBeNotified = new ArrayList<Distributor>();

        // iterate through producer changes
        for (ProducerChanges producerChanges : producerChangesList) {
            // iterate through each producer until we find the one that need to be updated
            for (Producer producerIterator : producerList) {
                if (producerChanges.getId() == producerIterator.getId()) {
                    // update the cost
                    producerIterator
                            .setEnergyPerDistributor(producerChanges.getEnergyPerDistributor());

                    // save the distributors that need to be notified
                    for (Distributor toBeAdded : producerIterator.getClients()) {
                        if (!distributorsToBeNotified.contains(toBeAdded)) {
                            distributorsToBeNotified.add(toBeAdded);
                        }
                    }
                }
            }
        }

        // sort the distributors by id
        distributorsToBeNotified.sort(Comparator.comparing(Distributor::getId));

        // notify the distributors that a producer has changed it's energy
        notifyDistributors(distributorsToBeNotified);
    }

    public List<Producer> getProducerList() {
        return producerList;
    }

    public void setProducerList(final List<Producer> producerList) {
        this.producerList = producerList;
    }
}
