package nl.mehh.dta.assignment2.models;

public class Individual<T> {
    private T value;

    public Individual(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
