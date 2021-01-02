package io;

import java.util.List;

public final class DistributorOutput {
    private int id;

    private int energyNeededKW;

    private int contractCost;

    private int budget;

    private String producerStrategy;

    private boolean isBankrupt;

    private List<ContractOutput> contracts;

    public DistributorOutput() {
    }

    public DistributorOutput(final int id,
                             final int energyNeededKW,
                             final int contractCost,
                             final int budget,
                             final String producerStrategy,
                             final boolean isBankrupt,
                             final List<ContractOutput> contracts) {
        this.id = id;
        this.energyNeededKW = energyNeededKW;
        this.contractCost = contractCost;
        this.budget = budget;
        this.producerStrategy = producerStrategy;
        this.isBankrupt = isBankrupt;
        this.contracts = contracts;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public void setEnergyNeededKW(final int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public int getContractCost() {
        return contractCost;
    }

    public void setContractCost(int contractCost) {
        this.contractCost = contractCost;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public String getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(final String producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public void setIsBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public List<ContractOutput> getContracts() {
        return contracts;
    }

    public void setContracts(final List<ContractOutput> contracts) {
        this.contracts = contracts;
    }
}
