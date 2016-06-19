package nl.mehh.dta.assignment2;

import nl.mehh.dta.assignment2.algoritm.GeneticAlgoritm;
import nl.mehh.dta.assignment2.models.Individual;
import nl.mehh.dta.assignment2.models.Tuple;

import java.util.Random;

public class Application<T> {
    public static void main(String[] args) {
        double crossoverRate    = Double.parseDouble(args[0]);
        double mutationRate     = Double.parseDouble(args[1]);
        boolean elitsm          = Boolean.parseBoolean(args[2]);
        int populationSize      = Integer.parseInt(args[3]);
        int iterations          = Integer.parseInt(args[4]);
        if(populationSize < 2) {
            System.err.println("You need at least two parents.");
            return;
        }

        Application<Byte> application = new Application<>();
        application.start(crossoverRate, mutationRate, elitsm, populationSize, iterations);
    }

    private void start(double crossoverRate, double mutationRate, boolean elitsm, int populationSize, int iterations) {
        Random r = new Random();

        GeneticAlgoritm<Individual<Byte>> algoritm = new GeneticAlgoritm<>(crossoverRate, mutationRate, elitsm, populationSize, iterations);
        Individual<Byte> solution = algoritm.Run(
                () -> new Individual<Byte>((byte) r.nextInt(31)),
                (individual) -> {
                    double x = (Math.pow(-individual.getValue(), 2)+(7*individual.getValue()));
                    return x;
                },
                (individuals, fitnesses) ->
                        () -> {
                            return new Tuple<>(individuals.get(r.nextInt(individuals.size() - 1)), individuals.get(r.nextInt(individuals.size() - 1)));
                        },
                tuple -> {
                    Byte byte1 = tuple.getKey().getValue();
                    Byte byte2 = tuple.getValue().getValue();

                    int child1 = ((byte1 >>> 4 << 4) | (byte2 & 0x0F));
                    int child2 = ((byte2 >>> 4 << 4) | (byte1 & 0x0F));

                    return new Tuple<>(new Individual<>((byte) child1), new Individual<>((byte) child2));
                },
                (individual, mutationrate) -> individual
            );

        double avgFitness = 0;
        for (double v : algoritm.getFitnessArray()) {
            avgFitness += v;
        }
        avgFitness = avgFitness/algoritm.getFitnessArray().length;

        System.out.println("Average fitness: " + avgFitness);
        System.out.println("Best fitness: " + algoritm.getFitnessArray()[0]);
        System.out.println("Best individual: " + solution.getValue());
    }

}
