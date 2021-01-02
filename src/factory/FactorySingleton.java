package factory;

import entities.Consumer;
import entities.Distributor;
import entities.EnergyType;
import entities.Entity;
import entities.Producer;

import static utils.Constants.CONSUMER;
import static utils.Constants.DISTRIBUTOR;
import static utils.Constants.PRODUCER;

public final class FactorySingleton {
    /*
    * The singleton instance of Factory
    * */
    private static FactorySingleton instance = null;

    /*
    * Private constructor to respect the singleton pattern
    * */
    private  FactorySingleton() {
    }

    /**
     * The method used to get a single instance of Factory.
     *
     * @return The Factory instance
     */
    public static FactorySingleton getInstance() {
        if (instance == null) {
            instance = new FactorySingleton();
        }

        return instance;
    }

    /**
     * Create a new entity based on the given type.
     *
     * @param entityType The entity type to be created
     * @param id The entity id
     * @param initialBudget The entity initial budget
     * @param monthlyIncome The monthlyIncome for the consumer type
     * @param contractLength The contract length for the distributor type
     * @param initialInfrastructureCost The initial infrastructure cost for the distributor type
     * @param initialProductionCost The initial production cost for the distributor type
     * @return The new Entity
     */
    public Entity createEntity(final String entityType,
                               final int id,
                               final double initialBudget,
                               final double monthlyIncome,
                               final int contractLength,
                               final double initialInfrastructureCost,
                               final double initialProductionCost,
                               final double energyNeededKW,
                               final String producerStrategy,
                               final EnergyType energyType,
                               final double priceKW,
                               final double energyPerDistributor,
                               final int maxDistributors) {
        return switch (entityType) {
            default -> null;

            case CONSUMER -> new Consumer(id, initialBudget, monthlyIncome);

            case DISTRIBUTOR -> new Distributor(id, contractLength, initialBudget,
                                                initialInfrastructureCost, initialProductionCost,
                                                energyNeededKW, producerStrategy);

            case PRODUCER -> new Producer(id, energyType, priceKW, energyPerDistributor,
                                            maxDistributors);
        };
    }
}
