package io;

public final class ProducerInput {
    private int id;

    private String energyType;

    private int maxDistributors;

    private double priceKW;

    private double energyPerDistributor;

    public ProducerInput() {
    }

    public ProducerInput(final int id,
                         final String energyType,
                         final int maxDistributors,
                         final double priceKW,
                         final double energyPerDistributor) {
        this.id = id;
        this.energyType = energyType;
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyPerDistributor = energyPerDistributor;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getEnergyType() {
        return energyType;
    }

    public void setEnergyType(final String energyType) {
        this.energyType = energyType;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(final int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }

    public double getPriceKW() {
        return priceKW;
    }

    public void setPriceKW(final double priceKW) {
        this.priceKW = priceKW;
    }

    public double getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(final double energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }
}
