package actions;

import entities.Consumer;
import entities.Distributor;
import entities.Producer;
import factory.FactorySingleton;
import io.ConsumerInput;
import io.DistributorChanges;
import io.MonthlyStatsOutput;
import io.MonthlyUpdatesInput;
import io.ProducerChanges;
import observer.Subject;
import utils.Contract;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static utils.Constants.CONSUMER;
import static utils.Constants.PRODUCTION_COST_PERCENTAGE;
import static utils.Constants.PROFIT_PERCENTAGE;
import static utils.Constants.DEBT_PERCENTAGE;


public final class Simulator {
    /*
    * for coding style
    * */
    private Simulator() {
    }

    /**
     * Starts the simulation, execute each step.
     *
     * @param numberOfTurns The number of rounds
     * @param consumers The list of consumers to be executed on
     * @param distributors The list of distributors to be executed on
     * @param producers The list of producers to be executed on
     * @param monthlyUpdatesInputs The list that contains each round update
     */
    public static void startSimulation(final int numberOfTurns,
                                       final List<Consumer> consumers,
                                       final List<Distributor> distributors,
                                       final List<Producer> producers,
                                       final List<MonthlyUpdatesInput> monthlyUpdatesInputs) {
        // create the observable subject with all the producers
        Subject observableSubject = new Subject(producers);

        // initial round
        // each distributor chooses the producers
        chooseProducers(distributors, producers);

        // compute the production cost
        computeProductionCost(distributors);

        // compute the contract price
        computeContractCostList(0, distributors);

        // each consumers pay
        chooseContracts(consumers, distributors, getBestDistributor(distributors));

        // each distributor pay
        payTotalCost(consumers, distributors);

        for (int i = 1; i <= numberOfTurns; i++) {
            // add new consumers or update distributors cost/producer energy
            // based on the round update input
            doUpdate(i, consumers, distributors, monthlyUpdatesInputs);

            // compute each distributor contract price
            computeContractCostList(i, distributors);

            // now each consumer should update it's contract and pay it
            chooseContracts(consumers, distributors, getBestDistributor(distributors));

            // each distributor should now pay it's monthly expenses
            payTotalCost(consumers, distributors);

            // check for monthly updates of producers
            updateProducersEnergyPerDistributor(i, distributors, monthlyUpdatesInputs,
                                                observableSubject);

            // remove any bankrupt distributor from each producer clients list
            removeBankruptDistributors(producers);

            // save the amount of distributors each producer has at the end of the month
            saveDistributors(i, producers);
        }
    }

    /**
     * Updates producers energy if given from the input.
     *
     * @param currentRoundNo The month number
     * @param distributors The list of distributors
     * @param monthlyUpdatesInputs The monthly updates
     * @param observableSubject The Observable object
     */
    public static void updateProducersEnergyPerDistributor(final int currentRoundNo,
                                                           final List<Distributor> distributors,
                                           final List<MonthlyUpdatesInput> monthlyUpdatesInputs,
                                                           final Subject observableSubject) {
        List<ProducerChanges> producerChangesList = monthlyUpdatesInputs
                                    .get(currentRoundNo - 1).getProducerChanges();

        // check if any changes have been given
        if (producerChangesList != null) {
            // update the producers energy
            observableSubject.updateProducersEnergy(producerChangesList);

            // compute the new production cost for the distributors
            computeProductionCost(distributors);
        }
    }

    /**
     * Compute the production cost for each distributor
     *
     * @param distributors The list of distributors
     */
    public static void computeProductionCost(final List<Distributor> distributors) {
        for (Distributor iterator : distributors) {
            if (!iterator.isBankrupt()) {
                double cost = 0;

                // sum each producer cost from the distributor producers list
                for (Producer producerIterator : iterator.getProducers()) {
                    cost += producerIterator.getEnergyPerDistributor()
                            * producerIterator.getPriceKW();
                }

                // set the new production cost
                iterator.setProductionCost(Math.round(Math.floor(cost
                                                                * PRODUCTION_COST_PERCENTAGE)));
            }
        }
    }

    /**
     * Removes the bankrupt distributors from each producer clients list.
     *
     * @param producers The producers list
     */
    public static void removeBankruptDistributors(final List<Producer> producers) {
        for (Producer iterator : producers) {
            if (!iterator.getClients().isEmpty()) {
                // iterate through each producer clients and remove the bankrupt ones
                Iterator<Distributor> clientIter = iterator.getClients().iterator();

                while (clientIter.hasNext()) {
                    Distributor currentClient = clientIter.next();

                    if (currentClient.isBankrupt()) {
                        clientIter.remove();
                    }
                }
            }
        }
    }

    /**
     * Compute the amount of clients each producer had at then end of a month.
     *
     * @param currentRoundNo The month number
     * @param producers The producers list
     */
    public static void saveDistributors(final int currentRoundNo,
                                        final List<Producer> producers) {
        for (Producer iterator : producers) {
            // create the current month stats
            iterator.getMonthlyStats().add(new MonthlyStatsOutput(currentRoundNo));

            if (iterator.getClients() != null) {
                // sort the distributors before
                iterator.getClients().sort(Comparator.comparing(Distributor::getId));

                for (Distributor distIter : iterator.getClients()) {
                    if (distIter != null) {
                        // add the client id to the list
                        iterator.getMonthlyStats().get(currentRoundNo - 1)
                                .getDistributorsIds().add(distIter.getId());
                    }
                }
            }
        }
    }

    /**
     * Adds new consumers or updates the distributors costs based on the input for each round.
     *
     * @param currentTurn The index of the current round
     * @param consumers The list of consumers
     * @param distributors The list of distributors
     * @param monthlyUpdatesInputs The updates for each round
     */
    public static void doUpdate(final int currentTurn,
                                final List<Consumer> consumers,
                                final List<Distributor> distributors,
                                final List<MonthlyUpdatesInput> monthlyUpdatesInputs) {
        // there are no updates for the first round, so we skip 0
        if (currentTurn != 0) {
            MonthlyUpdatesInput monthUpdate = monthlyUpdatesInputs.get(currentTurn - 1);

            // add new consumers
            for (ConsumerInput newConsumer : monthUpdate.getNewConsumers()) {
                consumers.add((Consumer) FactorySingleton
                         .getInstance().createEntity(CONSUMER, String.valueOf(newConsumer.getId()),
                                                   String.valueOf(newConsumer.getInitialBudget()),
                                                   String.valueOf(newConsumer.getMonthlyIncome())));
            }

            // change the distributor prices
            for (DistributorChanges costChanges : monthUpdate.getDistributorChanges()) {
                if (costChanges != null) {
                    // set the new infrastructure cost
                    distributors.get(costChanges.getId())
                                .setInfrastructureCost(costChanges.getInfrastructureCost());
                }
            }
        }
    }

    /**
     * Each distributor will choose it's producers
     *
     * @param distributors The list of distributors
     * @param producers The list of producers
     */
    public static void chooseProducers(final List<Distributor> distributors,
                                       final List<Producer> producers) {
        distributors.forEach((d) -> d.executeStrategy(producers));
    }

    /**
     * Compute each distributor's contract price for the current round.
     *
     * @param currentTurn The index of the current round
     * @param distributors The distributors list
     */
    public static void computeContractCostList(final int currentTurn,
                                               final List<Distributor> distributors) {
        if (currentTurn == 0) {
            // initial round, no consumers
            for (Distributor iterator : distributors) {
                // also check if the current distributor is not bankrupt
                if (!iterator.isBankrupt()) {
                    iterator.setContractPrice(computeContractCost(iterator, 0));
                }
            }
        } else {
            for (Distributor iterator : distributors) {
                if (!iterator.isBankrupt()) {
                    // compute the price depending on the number of consumers that the
                    // distributor signed with
                    if (iterator.getContractList() == null) {
                        iterator.setContractPrice(computeContractCost(iterator, 0));
                    } else {
                        iterator.setContractPrice(computeContractCost(iterator,
                                                                iterator.getContractList().size()));
                    }
                }
            }
        }
    }

    /**
     * Computes the price for the contract of a given Distributor, depending on the numbers of
     * consumers that it requires.
     *
     * @param distributor The distributor witch will make the contract
     * @param numberOfConsumers The number of consumers that the distributor signed with
     * @return  The price of the contract
     */
    public static double computeContractCost(final Distributor distributor,
                                             final int numberOfConsumers) {
        double price;

        double profit = Math.round(Math.floor(PROFIT_PERCENTAGE * distributor.getProductionCost()));

        if (numberOfConsumers == 0) {
            price = distributor.getInfrastructureCost() + distributor.getProductionCost()
                                                        + profit;
        } else {
            price = Math.round(Math.floor(distributor.getInfrastructureCost() / numberOfConsumers)
                                                        + distributor.getProductionCost() + profit);
        }

        return price;
    }

    /**
     * Compute the cheapest distributor based on it's contract price. Ignores distributors that are
     * bankrupt.
     *
     * @param distributors The list of distributors
     * @return The cheapest distributor from the list
     */
    public static Distributor getBestDistributor(final List<Distributor> distributors) {
        Distributor cheapest = null;

        if (distributors.stream().filter(distributor -> !distributor.isBankrupt())
                                 .min(Comparator.comparing(Distributor::getContractPrice))
                                 .isPresent()) {
            cheapest = distributors.stream().filter(distributor -> !distributor.isBankrupt())
                                   .min(Comparator.comparing(Distributor::getContractPrice)).get();
        }

        return cheapest;
    }

    /**
     * Update each consumer contract for the current round depending on it's budget.
     *
     * @param consumers The list of consumers
     * @param distributors The list of distributors
     * @param cheapest The cheapest distributor from the list
     */
    public static void chooseContracts(final List<Consumer> consumers,
                                       final List<Distributor> distributors,
                                       final Distributor cheapest) {
        for (Consumer consumer : consumers) {
            // ignore the consumers that are bankrupt
            if (!consumer.isBankrupt()) {
                // first the consumer gets it's monthly budget
                consumer.setBudget(Math.round(consumer.getBudget() + consumer.getMonthlyIncome()));

                // check if he already has a contract signed
                if (consumer.getContract() != null) {
                    // check if the contract is over
                    if (consumer.getContract().getRemainedContractMonths() == 0) {
                        // check if the consumer is currently in debt
                        if (consumer.isInDebt()) {
                            // compute the debt price for the old distributor
                            double debtPrice = Math.round(Math.floor(DEBT_PERCENTAGE
                                                            * consumer.getContract().getPrice()))
                                                            + consumer.getContract().getPrice();

                            int oldDistributorId = consumer.getContract().getDistributorId();

                            if (cheapest.getId() == oldDistributorId) {
                                // check if he can afford paying the old contract and sign a new one
                                // with the same distributor
                                if (consumer.getBudget() >= (cheapest.getContractPrice()
                                        + debtPrice)) {
                                    // first remove the old contract
                                    distributors.get(consumer.getContract().getDistributorId())
                                            .getContractList().remove(consumer.getContract());

                                    signNewContract(consumer, cheapest, false);

                                    // pay previous debt
                                    consumer.setBudget(consumer.getBudget() - debtPrice);

                                    distributors.get(oldDistributorId).setBudget(distributors
                                            .get(oldDistributorId).getBudget() + debtPrice);

                                    consumer.setInDebt(false);
                                } else {
                                    consumer.setBankrupt(true);
                                }
                            } else {
                                // another distributor
                                if (consumer.getBudget() >= (cheapest.getContractPrice()
                                        + debtPrice)) {
                                    // pay the debt and the new contract
                                    // first remove the old contract
                                    distributors.get(consumer.getContract().getDistributorId())
                                            .getContractList().remove(consumer.getContract());

                                    signNewContract(consumer, cheapest, false);

                                    // pay previous debt
                                    consumer.setBudget(consumer.getBudget() - debtPrice);

                                    distributors.get(oldDistributorId).setBudget(distributors
                                            .get(oldDistributorId).getBudget() + debtPrice);

                                    consumer.setInDebt(false);
                                } else {
                                    // check if he can pay only the debt
                                    if (consumer.getBudget() >= debtPrice) {
                                        // he can pay the debt and put the new one in debt also

                                        // first remove the old contract
                                        distributors.get(consumer.getContract().getDistributorId())
                                                .getContractList().remove(consumer.getContract());

                                        signNewContract(consumer, cheapest, true);

                                        // pay previous debt
                                        consumer.setBudget(consumer.getBudget() - debtPrice);

                                        distributors.get(oldDistributorId).setBudget(distributors
                                                .get(oldDistributorId).getBudget() + debtPrice);
                                    } else {
                                        consumer.setBankrupt(true);
                                    }
                                }
                            }
                        } else {
                            // he is not in debt, we can now check if he can sign a new one
                            distributors.get(consumer.getContract().getDistributorId())
                                        .getContractList().remove(consumer.getContract());

                            if (consumer.getBudget() >= cheapest.getContractPrice()) {
                                signNewContract(consumer, cheapest, false);
                            } else {
                                // sign the new contract in debt
                                signNewContract(consumer, cheapest, true);
                            }
                        }
                    } else if (consumer.isInDebt()) {
                        // the consumer has a contract signed, but is in debt
                        double debtPrice = Math.round(Math.floor(DEBT_PERCENTAGE
                                                            * consumer.getContract().getPrice()))
                                                            + consumer.getContract().getPrice();

                        payCurrentContract(consumer, distributors, true, debtPrice);
                    } else if (consumer.getBudget() >= consumer.getContract().getPrice()) {
                        // the consumer has a contract and can pay it
                        payCurrentContract(consumer, distributors, false, 0);

                    } else {
                        // the consumer can't afford the current round contract, will be put in debt
                        consumer.setInDebt(true);

                        consumer.getContract().setRemainedContractMonths(consumer
                                                   .getContract().getRemainedContractMonths() - 1);
                    }
                } else {
                    // consumer requires a new contract
                    if (consumer.getBudget() >= cheapest.getContractPrice()) {
                        signNewContract(consumer, cheapest, false);
                    } else {
                        // sign the contract in debt
                        signNewContract(consumer, cheapest, true);
                    }
                }
            }
        }
    }

    /**
     * Signs a contract for a given consumer, depending on it's budget.
     *
     * @param consumer The consumer that will sign
     * @param distributor The distributor that the consumer will sign with
     */
    public static void signNewContract(final Consumer consumer,
                                       final Distributor distributor,
                                       final boolean signInDebt) {
        // compute the new contract
        Contract newContract = new Contract(consumer.getId(),
                                            distributor.getId(),
                                            distributor.getContractPrice(),
                                            distributor.getContractLength() - 1);

        consumer.setContract(newContract);

        distributor.getContractList().add(newContract);

        // check if the consumer can afford it
        if (signInDebt) {
            consumer.setInDebt(true);
        } else {
            consumer.setBudget(consumer.getBudget() - consumer.getContract().getPrice());

            distributor.setBudget(distributor.getBudget() + consumer.getContract().getPrice());
        }
    }

    /**
     * Pays a given consumer contract based on it's budget and debt status.
     *
     * @param consumer The consumer that will pay the contract
     * @param distributors The distributor that will collect the money
     * @param isInDebt The consumer debt status
     * @param debtPrice The consumer debt price
     */
    public static void payCurrentContract(final Consumer consumer,
                                          final List<Distributor> distributors,
                                          final boolean isInDebt,
                                          final double debtPrice) {
        if (isInDebt) {
            if (consumer.getBudget() >= debtPrice) {
                // consumer can afford to pay the debt
                consumer.setBudget(consumer.getBudget() - debtPrice);

                consumer.getContract().setRemainedContractMonths(consumer.getContract()
                                                                 .getRemainedContractMonths() - 1);

                int distributorId = consumer.getContract().getDistributorId();

                distributors.get(distributorId).setBudget(distributors
                            .get(distributorId).getBudget() + debtPrice);

                consumer.setInDebt(false);
            } else {
                consumer.setBankrupt(true);
            }
        } else {
            // the consumer pays the normal price of the contract
            consumer.setBudget(consumer.getBudget() - consumer.getContract().getPrice());

            consumer.getContract().setRemainedContractMonths(consumer.getContract()
                                                             .getRemainedContractMonths() - 1);

            int distributorId = consumer.getContract().getDistributorId();

            distributors.get(distributorId).setBudget(distributors
                        .get(distributorId).getBudget() + consumer.getContract().getPrice());
        }
    }

    /**
     * Pays each distributor monthly expenses depending on the number of contracts he signed.
     *
     * @param consumers The list of consumers
     * @param distributors The list of distributors
     */
    public static void payTotalCost(final List<Consumer> consumers,
                                    final List<Distributor> distributors) {
        for (Distributor iterator : distributors) {
            // ignore bankrupt distributors
            if (!iterator.isBankrupt()) {
                double totalCost = 0d;

                // compute the total cost
                if (iterator.getContractList() == null) {
                    totalCost = iterator.getInfrastructureCost();
                } else {
                    totalCost = iterator.getInfrastructureCost()
                                + iterator.getProductionCost() * iterator.getContractList().size();
                }

                // pay the monthly expenses
                iterator.setBudget(Math.round(iterator.getBudget() - totalCost));

                if (iterator.getBudget() < 0) {
                    iterator.setBankrupt(true);
                }

                // check if any of the distributor's consumers went bankrupt
                // and remove it's contract from the list
                Iterator<Contract> contractsIterator = iterator.getContractList().iterator();

                while (contractsIterator.hasNext()) {
                    Contract current = contractsIterator.next();

                    if (consumers.get(current.getConsumerId()).isBankrupt()) {
                        contractsIterator.remove();
                    }
                }

                // if the distributor is now bankrupt, each consumer should choose a new
                // contract from the other distributors next round
                if (iterator.isBankrupt()) {
                    for (Contract contractToBeRemoved : iterator.getContractList()) {
                        consumers.get(contractToBeRemoved.getConsumerId()).setContract(null);
                    }
                }
            }
        }
    }
}
