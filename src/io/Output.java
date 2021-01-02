package io;

import java.util.List;

public final class Output {
    /*
    * List that contains each Consumer data after the simulation
    * */
    private List<ConsumerOutput> consumers;

    /*
    * List that contains each Distributor data after the simulation
    * */
    private List<DistributorOutput> distributors;

    private List<ProducerOutput> energyProducers;

    public Output() {
    }

    public Output(final List<ConsumerOutput> consumers,
                  final List<DistributorOutput> distributors,
                  final List<ProducerOutput> energyProducers) {
        this.consumers = consumers;
        this.distributors = distributors;
        this.energyProducers = energyProducers;
    }

    public List<ConsumerOutput> getConsumers() {
        return consumers;
    }

    public void setConsumers(final List<ConsumerOutput> consumers) {
        this.consumers = consumers;
    }

    public List<DistributorOutput> getDistributors() {
        return distributors;
    }

    public void setDistributors(final List<DistributorOutput> distributors) {
        this.distributors = distributors;
    }

    public List<ProducerOutput> getEnergyProducers() {
        return energyProducers;
    }

    public void setEnergyProducers(final List<ProducerOutput> energyProducers) {
        this.energyProducers = energyProducers;
    }
}
