package nl.mehh.dta.assignment2.models;

import java.util.function.Supplier;

public class Individual<T> {
    private T value;

    public Individual(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
