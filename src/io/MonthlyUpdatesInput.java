package io;

import java.util.List;

public final class MonthlyUpdatesInput {
    /*
    * New consumers given for each round
    * */
    private List<ConsumerInput> newConsumers;

    /*
    * Distributors changes
    * */
    private List<DistributorChanges> distributorChanges;

    /*
    * Producers changes
    * */
    private List<ProducerChanges> producerChanges;

    public MonthlyUpdatesInput() {
    }

    public MonthlyUpdatesInput(final List<ConsumerInput> newConsumers,
                               final List<DistributorChanges> distributorChanges,
                               final List<ProducerChanges> producerChanges) {
        this.newConsumers = newConsumers;
        this.distributorChanges = distributorChanges;
        this.producerChanges = producerChanges;
    }

    public List<ConsumerInput> getNewConsumers() {
        return newConsumers;
    }

    public void setNewConsumers(final List<ConsumerInput> newConsumers) {
        this.newConsumers = newConsumers;
    }

    public List<DistributorChanges> getDistributorChanges() {
        return distributorChanges;
    }

    public void setDistributorChanges(final List<DistributorChanges> distributorChanges) {
        this.distributorChanges = distributorChanges;
    }

    public List<ProducerChanges> getProducerChanges() {
        return producerChanges;
    }

    public void setProducerChanges(final List<ProducerChanges> producerChanges) {
        this.producerChanges = producerChanges;
    }
}
