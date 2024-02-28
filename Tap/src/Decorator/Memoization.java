package Decorator;

import java.util.HashMap;
import java.util.function.Function;

public class Memoization implements Function<Object, Object> {
    /**
     * A HashMap to store the cached results of the wrapped function.
     */
    private final HashMap<Object, Object> cache;
    /**
     * The encapsulated function to be memoed.
     */
    private final Function<Object, Object> function;
    /**
     * Creates a new `Memoization` instance wrapping the specified function.
     *
     * @param function Function to be memoed and wrapped
     */
    public Memoization(Function<Object, Object> function) {
        this.function = function;
        this.cache = new HashMap<>();
    }
    /**
     * Applies the encapsulated function and caches the result if not already cached.
     *
     * @param input Input argument for the function
     * @return Result of the function execution
     */
    public Object apply(Object input) {
        if(this.cache.containsKey(input)){
            System.out.println("Recovering cache result for function with parameter " + input);
            return this.cache.get(input);
        }else{
            System.out.println("Executing function with parameter " + input);
            Object result = this.function.apply(input);
            this.cache.put(input, result);
            return result;
        }
    }
    /**
     * Composes the `Memoization` instance with another function before applying the encapsulated function.
     *
     * @param before Function to be applied before the encapsulated function
     * @param <V> Type of the argument for the before function
     * @return Composite function
     */
    public <V> Function<V, Object> compose(Function<? super V, ?> before) {
        return Function.super.compose(before);
    }
    /**
     * AndThens the `Memoization` instance with another function after applying the encapsulated function.
     *
     * @param after Function to be applied after the encapsulated function
     * @param <V> Type of the result of the encapsulated function
     * @return Composite function
     */
    public <V> Function<Object, V> andThen(Function<? super Object, ? extends V> after) {
        return Function.super.andThen(after);
    }
}
