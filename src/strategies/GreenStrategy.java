package strategies;

import entities.Distributor;
import entities.Producer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class GreenStrategy extends EnergyChoiceStrategy {
    @Override
    public void chooseProducer(final Distributor distributor,
                               final List<Producer> producers) {
        List<Producer> sortedProducers = new ArrayList<Producer>(producers);

        // sort the producers by the energy type, then by price, then by the energy provided
        Collections.sort(sortedProducers, ((Comparator<Producer>) (p1, p2) -> {
            boolean b1 = p1.getEnergyType().isRenewable();
            boolean b2 = p2.getEnergyType().isRenewable();

            return Boolean.compare(b2, b1);
        }).thenComparing(Producer::getPriceKW)
          .thenComparing(Producer::getEnergyPerDistributor, Comparator.reverseOrder())
          .thenComparing(Producer::getId));

        // remove the distributor from any producer clients list
        distributor.getProducers().forEach((p) -> p.getClients().remove(distributor));

        // clear all the distributor previous producers
        distributor.getProducers().clear();

        // search for new producers
        searchNewProducers(distributor, sortedProducers);
    }
}
