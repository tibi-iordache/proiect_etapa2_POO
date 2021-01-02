package io;

import java.util.List;

public final class ProducerOutput {
    private int id;

    private int maxDistributors;

    private double priceKW;

    private String energyType;

    private int energyPerDistributor;

    private List<MonthlyStatsOutput> monthlyStats;

    public ProducerOutput() {
    }

    public ProducerOutput(final int id,
                          final int maxDistributors,
                          final double priceKW,
                          final String energyType,
                          final int energyPerDistributor,
                          final List<MonthlyStatsOutput> monthlyStats) {
        this.id = id;
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyType = energyType;
        this.energyPerDistributor = energyPerDistributor;
        this.monthlyStats = monthlyStats;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
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

    public String getEnergyType() {
        return energyType;
    }

    public void setEnergyType(final String energyType) {
        this.energyType = energyType;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(final int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    public List<MonthlyStatsOutput> getMonthlyStats() {
        return monthlyStats;
    }

    public void setMonthlyStats(final List<MonthlyStatsOutput> monthlyStats) {
        this.monthlyStats = monthlyStats;
    }
}
