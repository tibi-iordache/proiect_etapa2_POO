package strategies;

import entities.Distributor;
import entities.Producer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class PriceStrategy extends EnergyChoiceStrategy {
    @Override
    public void chooseProducer(final Distributor distributor,
                               final List<Producer> producers) {
        // sort the producers by price, then by the energy provided
        List<Producer> sortedProducers = producers.stream().sorted(Comparator
                        .comparing(Producer::getPriceKW)
                        .thenComparing(Producer::getEnergyPerDistributor, Comparator.reverseOrder())
                        .thenComparing(Producer::getId))
                        .collect(Collectors.toList());

        // remove the distributor from any producer clients list
        distributor.getProducers().forEach((p) -> p.getClients().remove(distributor));

        // clear all the distributor previous producers
        distributor.getProducers().clear();

        // search for new producers
        searchNewProducers(distributor, sortedProducers);
    }
}
