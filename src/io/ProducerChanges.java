package io;

public final class ProducerChanges {
    private int id;

    private double energyPerDistributor;

    public ProducerChanges() {
    }

    public ProducerChanges(final int id,
                           final double energyPerDistributor) {
        this.id = id;
        this.energyPerDistributor = energyPerDistributor;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public double getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(final double energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }
}
