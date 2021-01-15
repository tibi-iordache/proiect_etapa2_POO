package io;

import entities.Consumer;
import entities.Distributor;
import entities.Producer;
import factory.FactorySingleton;
import utils.Contract;

import java.util.ArrayList;
import java.util.List;

import static utils.Constants.CONSUMER;
import static utils.Constants.DISTRIBUTOR;
import static utils.Constants.PRODUCER;

public final class Loader {
    /*
    * for coding style
    * */
    private Loader() {
    }

    /**
     * Loads all the given input for consumers into an ArrayList of Consumers.
     *
     * @param input The input class
     * @return An ArrayList of Consumers
     */
    public static ArrayList<Consumer> loadInputConsumers(final Input input) {
        ArrayList<Consumer> consumers = new ArrayList<Consumer>();

        List<ConsumerInput> consumersInputList = input.getInitialData().getConsumers();

        // iterate through each consumer from the input
        for (ConsumerInput iterator : consumersInputList) {
            String id = String.valueOf(iterator.getId());

            String initialBudget = String.valueOf(iterator.getInitialBudget());

            String monthlyIncome = String.valueOf(iterator.getMonthlyIncome());

            // use the factory to create new consumer instances
            consumers.add((Consumer) FactorySingleton.getInstance()
                                                     .createEntity(CONSUMER,
                                                                   id,
                                                                   initialBudget,
                                                                   monthlyIncome));
        }

        return consumers;
    }

    /**
     * Loads all the given input for distributors into an ArrayList of Distributors.
     *
     * @param input The Input class
     * @return An ArrayList of Distributors
     */
    public static ArrayList<Distributor> loadInputDistributors(final Input input) {
        ArrayList<Distributor> distributors = new ArrayList<Distributor>();

        List<DistributorInput> distributorsInputList = input.getInitialData().getDistributors();

        // iterate through each distributor from the input
        for (DistributorInput iterator : distributorsInputList) {
            String id = String.valueOf(iterator.getId());

            String initialBudget = String.valueOf(iterator.getInitialBudget());

            String contractLength = String.valueOf(iterator.getContractLength());

            String initialInfrastructureCost = String.valueOf(iterator
                                                              .getInitialInfrastructureCost());

            String producerStrategy = String.valueOf(iterator.getProducerStrategy());

            String energyNeededKW = String.valueOf(iterator.getEnergyNeededKW());

            // use the factory to create new distributor instances
            distributors.add((Distributor) FactorySingleton.getInstance()
                                                            .createEntity(DISTRIBUTOR,
                                                                          id,
                                                                          contractLength,
                                                                          initialBudget,
                                                                          initialInfrastructureCost,
                                                                          energyNeededKW,
                                                                          producerStrategy));
        }

        return distributors;
    }

    /**
     * Loads all the given input for producers into an ArrayList of Producers.
     *
     * @param input The Input class
     * @return An ArrayList of Producers
     */
    public static ArrayList<Producer> loadInputProducers(final Input input) {
        ArrayList<Producer> producers = new ArrayList<Producer>();

        List<ProducerInput> producerInputList = input.getInitialData().getProducers();

        // iterate through each producer from the input
        for (ProducerInput iterator : producerInputList) {
            String id = String.valueOf(iterator.getId());

            String energyType = iterator.getEnergyType();

            String priceKW = String.valueOf(iterator.getPriceKW());

            String energyPerDistributor = String.valueOf(iterator.getEnergyPerDistributor());

            String maxDistributors = String.valueOf(iterator.getMaxDistributors());

            // use the factory to create new producer instances
            producers.add((Producer) FactorySingleton.getInstance()
                    .createEntity(PRODUCER,
                                  id,
                                  energyType,
                                  priceKW,
                                  energyPerDistributor,
                                  maxDistributors));
        }

        return producers;
    }

    /**
     * Loads the results of the simulation into an Output class.
     *
     * @param consumers An ArrayList of Consumers
     * @param distributors An ArrayList of Distributors
     * @param producers An ArrayList of Producers
     * @return The Output class
     */
    public static Output loadOutput(final List<Consumer> consumers,
                                    final List<Distributor> distributors,
                                    final List<Producer> producers) {
        // compute each consumer data after the simulation
        ArrayList<ConsumerOutput> consumersOutput = new ArrayList<ConsumerOutput>();

        for (Consumer iterator : consumers) {
            ConsumerOutput c = new ConsumerOutput(iterator.getId(),
                                                  iterator.isBankrupt(),
                                                  (int) iterator.getBudget());
            consumersOutput.add(c);
        }

        // compute each distributor data after the simulation
        ArrayList<DistributorOutput> distributorsOutput = new ArrayList<DistributorOutput>();

        for (Distributor iterator : distributors) {
            List<ContractOutput> contractsOutput = new ArrayList<ContractOutput>();

            if (iterator.getContractList() != null) {
                for (Contract contractIterator : iterator.getContractList()) {
                    ContractOutput con = new ContractOutput(contractIterator.getConsumerId(),
                                                    (int) contractIterator.getPrice(),
                                                    contractIterator.getRemainedContractMonths());
                    contractsOutput.add(con);
                }
            }

            DistributorOutput d = new DistributorOutput(iterator.getId(),
                                                        (int) iterator.getEnergyNeededKW(),
                                                        (int) iterator.getContractPrice(),
                                                        (int) iterator.getBudget(),
                                                        iterator.getProducerStrategy(),
                                                        iterator.isBankrupt(),
                                                        contractsOutput);
            distributorsOutput.add(d);
        }

        // compute each producer data after the simulation
        ArrayList<ProducerOutput> producerOutputs = new ArrayList<ProducerOutput>();

        for (Producer iterator : producers) {
            producerOutputs.add(new ProducerOutput(iterator.getId(),
                                                   iterator.getMaxDistributors(),
                                                   iterator.getPriceKW(),
                                                   iterator.getEnergyType().getLabel(),
                                                   (int) iterator.getEnergyPerDistributor(),
                                                   iterator.getMonthlyStats()));
        }

        return new Output(consumersOutput, distributorsOutput, producerOutputs);
    }
}
