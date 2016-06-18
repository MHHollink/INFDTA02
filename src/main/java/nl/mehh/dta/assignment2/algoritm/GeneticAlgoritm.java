package nl.mehh.dta.assignment2.algoritm;

import nl.mehh.dta.assignment2.models.Tuple;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GeneticAlgoritm<T> {
    private double crossoverRate;
    private double mutationRate;
    private boolean elitism;
    private int populationSize;
    private int iterations;
    private Random r;
    private double[] finalFitnesses;

    public GeneticAlgoritm(double crossoverRate, double mutationRate, boolean elitism, int populationSize, int iterations) {
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.elitism = elitism;
        this.populationSize = populationSize;
        this.iterations = iterations;
        r = new Random();
    }

    public T Run(Supplier<T> createIndividual,
                 Function<T, Double> calculateFitness,
                 BiFunction<List<T>, double[], Supplier<Tuple<T,T>>> selectTwoParents,
                 Function<Tuple<T,T>,Tuple<T,T>> crossoverFunction,
                 BiFunction<T, Double, T> mutation) {

        T[] population = (T[]) Stream.generate(createIndividual).limit(populationSize).toArray();
        T[] currentPopulation = population;
        for (int i = 0; i < iterations; i++) {
            T[] finalCurrentPopulation = currentPopulation;
            double[] fitness = IntStream.range(0, populationSize).mapToDouble(index -> calculateFitness.apply(finalCurrentPopulation[index])).toArray();
            T[] newPopulation = (T[]) new Object[populationSize];

            //apply elitism
            int startIndex;
            if(elitism){
                startIndex = 1;
                List<Tuple<T, Double>> populationWithFitness =
                        IntStream
                                .range(0, populationSize)
                                .mapToObj(index -> new Tuple<>(finalCurrentPopulation[index], fitness[index]))
                                .collect(Collectors.toList());

                List<Tuple<T, Double>> populationSorted =
                        populationWithFitness.stream()
                                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                                .collect(Collectors.toList());

                Tuple<T, Double> bestIndividual = populationSorted.stream()
                        .findFirst()
                        .get();

                newPopulation[0] = bestIndividual.getKey();
            } else
            {
                startIndex = 0;
            }

            Supplier<Tuple<T,T>> getTwoParents = selectTwoParents.apply(new LinkedList<T>(Arrays.asList(currentPopulation)), fitness);

            for (int i1 = startIndex; i1 < populationSize; i1++) {
                Tuple<T,T> parents = getTwoParents.get();

                Tuple<T,T> offspring;
                if (r.nextDouble() < crossoverRate)
                    offspring = crossoverFunction.apply(parents);
                else
                    offspring = parents;

                newPopulation[i1++] = mutation.apply(offspring.getKey(), mutationRate);
                if(i1 < populationSize)
                    newPopulation[i1] = mutation.apply(offspring.getValue(), mutationRate);
            }

            currentPopulation = newPopulation;
        }

        T[] finalCurrentPopulation = currentPopulation;
        finalFitnesses = IntStream.range(0, populationSize).mapToDouble(index -> calculateFitness.apply(finalCurrentPopulation[index])).toArray();
        return IntStream
                .range(0, currentPopulation.length)
                .mapToObj(index -> new Tuple<>(finalCurrentPopulation[index], finalFitnesses[index]))
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .findFirst()
                .get()
                .getKey();
    }

    public double[] getFitnessArray(){
        return finalFitnesses;
    }
}
