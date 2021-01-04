package strategies;

import entities.Distributor;
import entities.Producer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class PriceStrategy implements EnergyChoiceStrategy {
    @Override
    public void chooseProducer(final Distributor distributor,
                               final List<Producer> producers) {

        List<Producer> copyProducers = new ArrayList<Producer>(producers);

        // sort the producers by price, then by amount of energy given
        Collections.sort(copyProducers, Comparator.comparing(Producer::getPriceKW)
                    .thenComparing(Producer::getEnergyPerDistributor, Comparator.reverseOrder())
                    .thenComparing(Producer::getId));

        for (Producer p : distributor.getProducers()) {
            if (p.getClients().contains(distributor)) {
                p.getClients().remove(distributor);
            }
        }

        distributor.getProducers().clear();

        double sum = 0;

        for (Producer iterator : copyProducers) {
            if ((sum <= distributor.getEnergyNeededKW())
                    && (iterator.getMaxDistributors() > iterator.getClients().size())) {
                // find the corespondend producer from the original list
                for (Producer original : producers) {
                    if (original.getId() == iterator.getId()) {
                        // add the current producer to the distributor list
                        distributor.getProducers().add(original);

                        original.getClients().add(distributor);

                        sum += original.getEnergyPerDistributor();
                    }
                }
            }
        }
    }
}
