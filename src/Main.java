import actions.Simulator;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Consumer;
import entities.Distributor;
import entities.Producer;
import io.Input;
import io.Loader;
import io.Output;

import java.io.File;
import java.util.ArrayList;

import static utils.Constants.FIRST_ARG;
import static utils.Constants.SECOND_ARG;

/**
 * Entry point to the simulation
 */
public final class Main {

    private Main() { }

    /**
     * Main function which reads the input file and starts simulation
     *
     * @param args input and output files
     * @throws Exception might error when reading/writing/opening files, parsing JSON
     */
    public static void main(final String[] args) throws Exception {
        // read from the input file and compute the Input file
        ObjectMapper objectMapper = new ObjectMapper();

        Input input = objectMapper.readValue(new File(args[FIRST_ARG]), Input.class);

        // compute the consumers and distributors
        ArrayList<Consumer> consumers = Loader.loadInputConsumers(input);

        ArrayList<Distributor> distributors = Loader.loadInputDistributors(input);

        ArrayList<Producer> producers = Loader.loadInputProducers(input);

        // start the simulation
        Simulator.startSimulation(input.getNumberOfTurns(),
                consumers,
                distributors,
                input.getMonthlyUpdates());

        // compute the Output class
        Output out = Loader.loadOutput(consumers, distributors, producers);

        // write the results of the simulation in the output file
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(args[SECOND_ARG]), out);
    }
}
