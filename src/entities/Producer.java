package entities;

import io.MonthlyStatsOutput;
import observer.CustomObserver;

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

    private List<Distributor> observers;

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

        this.observers = new ArrayList<Distributor>();
    }

    /**
     * TODO
     * @param observer
     */
    public void addObserver(final Distributor observer) {
        this.observers.add(observer);
    }

    /**
     * TODO
     *
     * @param producerList
     */
    public void notifyAllObservers(final List<Producer> producerList) {
        for (Distributor observer : observers) {
            observer.update(producerList);
        }
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

    /**
     * TODO
     * @param energyAmount
     * @param producerList
     */
    public void setEnergyPerDistributor(final double energyAmount,
                                        final List<Producer> producerList) {
        this.energyPerDistributor = energyAmount;

        notifyAllObservers(producerList);
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

    public List<Distributor> getObservers() {
        return observers;
    }

    public void setObservers(List<Distributor> observers) {
        this.observers = observers;
    }
}
