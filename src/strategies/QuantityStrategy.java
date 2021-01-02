package strategies;

import entities.Distributor;
import entities.Producer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class QuantityStrategy implements EnergyChoiceStrategy {
    @Override
    public void chooseProducer(final Distributor distributor,
                               final List<Producer> producers) {
        // sort only by the amount of energy given
        Collections.sort(producers, Comparator
                .comparing(Producer::getEnergyPerDistributor, Comparator.reverseOrder())
                .thenComparing(Producer::getId));

        double sum = 0;

        for (Producer iterator : producers) {
            if (sum <= distributor.getEnergyNeededKW()) {
                // add the current producer to the distributor list
                distributor.getProducers().add(iterator);

                iterator.addObserver(distributor);

                sum += iterator.getEnergyPerDistributor();
            }
        }
    }
}
