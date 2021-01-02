package io;

public final class DistributorChanges {
    private int id;

    private double infrastructureCost;

    public DistributorChanges() {
    }

    public DistributorChanges(final int id,
                              final double infrastructureCost) {
        this.id = id;
        this.infrastructureCost = infrastructureCost;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public double getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(final double infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }
}
