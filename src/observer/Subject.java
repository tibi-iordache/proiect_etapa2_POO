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
    public void notifyAllObservers(final List<Distributor> observersToBeNotified,
                                   final List<Producer> producerList) {

        for (Distributor observer : observersToBeNotified) {
            observer.update(producerList);
        }
    }



    public List<Distributor> getObservers() {
        return observers;
    }

    public void setObservers(final List<Distributor> observers) {
        this.observers = observers;
    }
}
