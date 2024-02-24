import { derived, type Readable } from "svelte/store";

// A Svelte store that ignores updates that don't actually modify the values of a store.
export const dedupe = <T>(store: Readable<T>): Readable<T> => {
    let previousValue: T;

    return derived<Readable<T>, T>(store, ($value, set) => {
        if ($value !== previousValue) {
            previousValue = $value;
            set($value);
        }
    });
}