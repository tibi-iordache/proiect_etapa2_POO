package strategies;

import entities.Distributor;
import entities.Producer;

import java.util.List;

public interface EnergyChoiceStrategy {
    /**
     * TODO
     * @param distributor
     * @param producers
     */
    void chooseProducer(Distributor distributor, List<Producer> producers);
}
