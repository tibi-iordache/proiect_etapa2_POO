package io;

import java.util.List;

public final class InitialDataInput {
    /*
    * The consumers given in the input
    * */
    private List<ConsumerInput> consumers;

    /*
    * The distributors given in the input
    * */
    private List<DistributorInput> distributors;

    /*
     * The producers given in the input
     * */
    private List<ProducerInput> producers;

    public InitialDataInput() {
    }

    public InitialDataInput(final List<ConsumerInput> consumers,
                            final List<DistributorInput> distributors,
                            final List<ProducerInput> producers) {
        this.consumers = consumers;
        this.distributors = distributors;
        this.producers = producers;
    }

    public List<ConsumerInput> getConsumers() {
        return consumers;
    }

    public void setConsumers(final List<ConsumerInput> consumers) {
        this.consumers = consumers;
    }

    public List<DistributorInput> getDistributors() {
        return distributors;
    }

    public void setDistributors(final List<DistributorInput> distributors) {
        this.distributors = distributors;
    }

    public List<ProducerInput> getProducers() {
        return producers;
    }

    public void setProducers(final List<ProducerInput> producers) {
        this.producers = producers;
    }
}
