package entities;

import io.MonthlyStatsOutput;

import java.util.ArrayList;
import java.util.List;

public final class Producer extends Entity {
    private EnergyType energyType;

    private double priceKW;

    private double energyPerDistributor;

    private int maxDistributors;

    /*
    * Needed for the output
    * */
    private List<MonthlyStatsOutput> monthlyStats;

    /*
    * List with all the distributors supplied by the current producer
    * */
    private List<Distributor> clients;

    public Producer() {
    }

    public Producer(final int id,
                    final EnergyType energyType,
                    final double priceKW,
                    final double energyPerDistributor,
                    final int maxDistributors) {
        super(id, 0);

        this.energyType = energyType;

        this.priceKW = priceKW;

        this.energyPerDistributor = energyPerDistributor;

        this.maxDistributors = maxDistributors;

        this.monthlyStats = new ArrayList<MonthlyStatsOutput>();

        this.clients = new ArrayList<Distributor>();
    }

    public EnergyType getEnergyType() {
        return energyType;
    }

    public void setEnergyType(final EnergyType energyType) {
        this.energyType = energyType;
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

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(final int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }

    public List<MonthlyStatsOutput> getMonthlyStats() {
        return monthlyStats;
    }

    public void setMonthlyStats(final List<MonthlyStatsOutput> monthlyStats) {
        this.monthlyStats = monthlyStats;
    }

    public List<Distributor> getClients() {
        return clients;
    }

    public void setClients(final List<Distributor> clients) {
        this.clients = clients;
    }
}
