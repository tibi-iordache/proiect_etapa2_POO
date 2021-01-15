package strategies;

import entities.Distributor;
import entities.Producer;

import java.util.List;

public abstract class EnergyChoiceStrategy {
    /**
     * Reapplies the strategy of the distributor by removing the previous producers and searching
     * for new ones.
     *
     * @param distributor The distributor that will reapply the strategy
     * @param producers The producers list
     */
    public abstract void chooseProducer(Distributor distributor, List<Producer> producers);

    /**
     * Search for new producers for a distributor, until it gets enough energy.
     *
     * @param distributor The distributor that searches producers
     * @param sortedProducers The producers sorted based on the distributor strategy
     */
    public void searchNewProducers(Distributor distributor,
                                   List<Producer> sortedProducers) {
        double sum = 0;

        // search for new producers
        for (Producer iterator : sortedProducers) {
            if ((sum < distributor.getEnergyNeededKW())
                    && (iterator.getMaxDistributors() > iterator.getClients().size())) {
                // add the current producer to the distributor list
                distributor.getProducers().add(iterator);

                iterator.getClients().add(distributor);

                sum += iterator.getEnergyPerDistributor();
            }
        }
    }
}
