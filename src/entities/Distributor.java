package entities;

import observer.CustomObserver;
import strategies.EnergyChoiceStrategy;
import strategies.EnergyChoiceStrategyType;
import strategies.GreenStrategy;
import strategies.PriceStrategy;
import strategies.QuantityStrategy;
import utils.Contract;
import java.util.ArrayList;
import java.util.List;

public final class Distributor extends Entity implements CustomObserver {
    /*
    * The length of the distributor's contract
    * */
    private int contractLength;

    /*
    * Distributor's infrastructure cost
    * */
    private double infrastructureCost;

    /*
    * Distributor's production cost
    * */
    private double productionCost;

    /*
    * Distributor's current round contract price
    * */
    private double contractPrice;

    /*
    * List that contains all the distributor's contracts
    * */
    private ArrayList<Contract> contractList;

    /*
    * Distributor's bankrupt status
    * */
    private boolean isBankrupt;

    /*
    * Required energy
    * */
    private double energyNeededKW;

    /*
    * The distributor strategy (needed for the output)
    * */
    private String producerStrategy;

    /*
    * The current producers of the distributor
    * */
    private List<Producer> producers;

    /*
    * The distributor strategy
    * */
    private EnergyChoiceStrategy strategy;

    public Distributor() {
    }

    public Distributor(final int id,
                       final int contractLength,
                       final double initialBudget,
                       final double infrastructureCost,
                       final double productionCost,
                       final double energyNeededKW,
                       final String producerStrategy) {
        super(id, initialBudget);

        this.contractLength = contractLength;

        this.infrastructureCost = infrastructureCost;

        this.productionCost = productionCost;

        this.contractList = new ArrayList<Contract>();

        this.energyNeededKW = energyNeededKW;

        this.producerStrategy = producerStrategy;

        this.producers = new ArrayList<Producer>();

        if (EnergyChoiceStrategyType.valueOf(producerStrategy)
                .equals(EnergyChoiceStrategyType.GREEN)) {
            this.strategy = new GreenStrategy();
        }

        if (EnergyChoiceStrategyType.valueOf(producerStrategy)
                .equals(EnergyChoiceStrategyType.PRICE)) {
            this.strategy = new PriceStrategy();
        }

        if (EnergyChoiceStrategyType.valueOf(producerStrategy)
                .equals(EnergyChoiceStrategyType.QUANTITY)) {
            this.strategy = new QuantityStrategy();
        }
    }

    /**
     * TODO
     *
     * @param producersList
     */
    public void executeStrategy(List<Producer> producersList) {
        this.strategy.chooseProducer(this, producersList);
    }

    /**
     * TODO
     * @param producersList
     */
    @Override
    public void update(List<Producer> producersList) {
        if (!isBankrupt) {
            executeStrategy(producersList);
        }
    }

    public int getContractLength() {
        return contractLength;
    }

    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    public double getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(final double infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    public double getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(final double productionCost) {
        this.productionCost = productionCost;
    }

    public double getContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(final double contractPrice) {
        this.contractPrice = contractPrice;
    }

    public ArrayList<Contract> getContractList() {
        return contractList;
    }

    public void setContractList(final ArrayList<Contract> contractList) {
        this.contractList = contractList;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public double getEnergyNeededKW() {
        return energyNeededKW;
    }

    public void setEnergyNeededKW(final double energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public String getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(final String producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public void setProducers(final List<Producer> producers) {
        this.producers = producers;
    }
}
