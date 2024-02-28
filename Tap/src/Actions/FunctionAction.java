package Actions;

import java.util.function.Function;

/**
 * Adapts a function to the `Action` interface.
 * @param <T>
 * @param <R>
 */
public class FunctionAction<T, R> implements Action<T, R> {
    private final Function<T, R> function;
    private final int memory;

    /**
     *
     * @param function Function to be adapted.
     * @param memory Function memory requirement.
     */
    public FunctionAction(Function<T, R> function, int memory) {
        this.function = function;
        this.memory = memory;
    }

    /**
     * Run the adapted function.
     * @param arg Receives an input argument of type `T`.
     * @return Function output value.
     */
    @Override
    public R run(T arg) {
        return function.apply(arg);
    }

    /**
     * Returns the memory requirement of the adapted function.
     * @return Function memory requirement.
     */
    @Override
    public int getMemory() {
        return memory;
    }
}
