package io;

public final class DistributorInput {
    private int id;

    private int contractLength;

    private double initialBudget;

    private double initialInfrastructureCost;

    private double energyNeededKW;

    private String producerStrategy;

    public DistributorInput() {
    }

    public DistributorInput(final int id,
                            final int contractLength,
                            final double initialBudget,
                            final double initialInfrastructureCost,
                            final double energyNeededKW,
                            final String producerStrategy) {
        this.id = id;
        this.contractLength = contractLength;
        this.initialBudget = initialBudget;
        this.initialInfrastructureCost = initialInfrastructureCost;
        this.energyNeededKW = energyNeededKW;
        this.producerStrategy = producerStrategy;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getContractLength() {
        return contractLength;
    }

    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    public double getInitialBudget() {
        return initialBudget;
    }

    public void setInitialBudget(final double initialBudget) {
        this.initialBudget = initialBudget;
    }

    public double getInitialInfrastructureCost() {
        return initialInfrastructureCost;
    }

    public void setInitialInfrastructureCost(final double initialInfrastructureCost) {
        this.initialInfrastructureCost = initialInfrastructureCost;
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
}
