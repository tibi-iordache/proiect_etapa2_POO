package observer;

import entities.Distributor;
import entities.Producer;

import java.util.List;

public final class Subject implements CustomObservable {
    /*
    * The observers will be all the distributors
    * */
    private List<Distributor> observers;

    public Subject() {
    }

    public Subject(final List<Distributor> observers) {
        this.observers = observers;
    }

    @Override
    public void addObserver(final Distributor newObserver) {
        this.observers.add(newObserver);
    }

    @Override
    public void removeObserver(final Distributor observerToBeRemoved) {
        this.observers.remove(observerToBeRemoved);
    }

    @Override
    public void notifyAllObservers(final Producer producerModified,
                                   final double newEnergyPerDistributor,
                                   final List<Producer> producersList) {
        producerModified.setEnergyPerDistributor(newEnergyPerDistributor);

        for (Distributor observer : observers) {
            observer.update(producersList);
        }
    }

    public List<Distributor> getObservers() {
        return observers;
    }

    public void setObservers(final List<Distributor> observers) {
        this.observers = observers;
    }
}
