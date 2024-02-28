package Decorator;

import Invokers.Invoker;

import java.util.function.Function;

public class Timer extends Invoker implements Function<Object, Object> {
    /**
     * The encapsulated function to be timed.
     */
    private final Function<Object, Object> function;
    /**
     * Creates a new `Timer` instance wrapping the specified function.
     *
     * @param function Function to be timed and wrapped
     */
    public Timer(Function<Object, Object> function, int id, double memory){
        super(id, memory);
        this.function = function;
    }
    /**
     * Applies the encapsulated function and measures its execution time.
     *
     * @param args Arguments for the function
     * @return Result of the function execution
     */
    public Object apply(Object args) {
        long start = System.currentTimeMillis();
        Object result = function.apply(args);
        long end = System.currentTimeMillis();
        System.out.println("Execution time: " + (end - start) + " ms");
        return result;
    }
    /**
     * Composes the `Timer` instance with another function before applying the encapsulated function.
     *
     * @param before Function to be applied before the encapsulated function
     * @param <V> Type of the argument for the before function
     * @return Composite function
     */
    @Override
    public <V> Function<V, Object> compose(Function<? super V, ?> before) {
        return Function.super.compose(before);
    }
    /**
     * The `Timer` instance with another function after applying the encapsulated function.
     *
     * @param after Function to be applied after the encapsulated function
     * @param <V> Type of the result of the encapsulated function
     * @return Composite function
     */
    public <V> Function<Object, V> andThen(Function<? super Object, ? extends V> after) {
        return Function.super.andThen(after);
    }
}
