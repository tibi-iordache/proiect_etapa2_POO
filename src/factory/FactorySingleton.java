package factory;

import entities.Consumer;
import entities.Distributor;
import entities.EnergyType;
import entities.Entity;
import entities.Producer;

import static utils.Constants.CONSUMER;
import static utils.Constants.DISTRIBUTOR;
import static utils.Constants.FIFTH_ARG;
import static utils.Constants.FIRST_ARG;
import static utils.Constants.FOURTH_ARG;
import static utils.Constants.PRODUCER;
import static utils.Constants.SECOND_ARG;
import static utils.Constants.SIXTH_ARG;
import static utils.Constants.THIRD_ARG;

public final class FactorySingleton {
    /*
    * The singleton instance of Factory
    * */
    private static FactorySingleton instance = null;

    /*
    * Private constructor to respect the singleton pattern
    * */
    private FactorySingleton() {
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
     * @param input Variable args based on the type
     * @return
     */
    public Entity createEntity(final String entityType,
                               final String... input) {
        switch (entityType) {
            default -> {
                return null;
            }

            case CONSUMER -> {
                int id = Integer.parseInt(input[FIRST_ARG]);

                double initialBudged = Double.parseDouble(input[SECOND_ARG]);

                double monthlyIncome = Double.parseDouble(input[THIRD_ARG]);

                return new Consumer(id, initialBudged, monthlyIncome);
            }


            case DISTRIBUTOR -> {
                int id = Integer.parseInt(input[FIRST_ARG]);

                int contractLength = Integer.parseInt(input[SECOND_ARG]);

                double initialBudget = Double.parseDouble(input[THIRD_ARG]);

                double infrastructureCost = Double.parseDouble(input[FOURTH_ARG]);

                double energyNeededKW = Double.parseDouble(input[FIFTH_ARG]);

                String producerStrategy = input[SIXTH_ARG];

                return new Distributor(id,
                                       contractLength,
                                       initialBudget,
                                       infrastructureCost,
                                       energyNeededKW,
                                       producerStrategy);
            }

            case PRODUCER -> {
                int id = Integer.parseInt(input[FIRST_ARG]);

                EnergyType energyType = EnergyType.valueOf(input[SECOND_ARG]);

                double priceKW = Double.parseDouble(input[THIRD_ARG]);

                double energyPerDistributor = Double.parseDouble(input[FOURTH_ARG]);

                int maxDistributors = Integer.parseInt(input[FIFTH_ARG]);

                return new Producer(id,
                                    energyType,
                                    priceKW,
                                    energyPerDistributor,
                                    maxDistributors);
            }
        }
    }
}
