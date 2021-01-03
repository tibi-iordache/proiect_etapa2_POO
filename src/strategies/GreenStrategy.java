package strategies;

import entities.Distributor;
import entities.Producer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class GreenStrategy implements EnergyChoiceStrategy {
    @Override
    public void chooseProducer(final Distributor distributor,
                               final List<Producer> producers) {

        // sort the producers by the energy renewable, then price, then amount of energy
        Collections.sort(producers, new Comparator<Producer>() {
            @Override
            public int compare(Producer t1, Producer t2) {
                boolean b1 = t1.getEnergyType().isRenewable();
                boolean b2 = t2.getEnergyType().isRenewable();

                return Boolean.compare(b2, b1);
            }
        }.thenComparing(Producer::getPriceKW)
                .thenComparing(Producer::getEnergyPerDistributor, Comparator.reverseOrder())
                .thenComparing(Producer::getId));

        double sum = 0;

        for (Producer p : distributor.getProducers()) {
            p.removeObserver(distributor);
        }

        distributor.getProducers().clear();

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
