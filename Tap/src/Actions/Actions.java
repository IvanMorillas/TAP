package Actions;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * This class provides static methods for performing basic arithmetic operations and testing the results.
 */
public class Actions {
    /**
     * Takes a map of arguments as input and returns the sum of the values of the `x` and `y` keys.
     *
     * @param args Map of arguments
     * @return The sum of the values of the `x` and `y` keys
     */
    public static Function<Map<String, Integer>, Integer> addAction = args -> args.get("x") + args.get("y");
    /**
     * Takes a map of arguments as input and returns the difference between the values of the `x` and `y` keys.
     *
     * @param args Map of arguments
     * @return The difference between the values of the `x` and `y` keys
     */
    public static Function<Map<String, Integer>, Integer> subAction = args -> args.get("x") - args.get("y");
    /**
     * Takes a map of arguments as input and returns the product of the values of the `x` and `y` keys.
     *
     * @param args Map of arguments
     * @return The product of the values of the `x` and `y` keys
     */
    public static Function<Map<String, Integer>, Integer> mulAction = args -> args.get("x") * args.get("y");
    /**
     * Takes a map of arguments as input and returns the quotient of the values of the `x` and `y` keys.
     *
     * @param args Map of arguments
     * @return The quotient of the values of the `x` and `y` keys
     */
    public static Function<Map<String, Double>, Double> divAction = args -> args.get("x") / args.get("y");
    /**
     * Takes a map of arguments as input and returns the factorial of the value of the `x` key.
     *
     * @param x Map of arguments
     * @return The factorial of the value of the `x` key
     */
    public static Function<Map<String, Object>, Object> factAction = x -> {
        Object xValue = x.get("x");
        if (xValue instanceof Integer) {
            Integer result = 1;
            for (int i = 1; i <= (int)xValue; i++) {
                result = result * i;
            }
            return result;
        }
        return null;
    };
    /**
     * Takes an iterable as input and returns a list of the results of adding the values of the `x` and `y` keys in each element of the iterable.
     *
     * @param x Iterable of arguments
     * @return List of the results of adding the values of the `x` and `y` keys
     */
    public static Function<Object, Object> testAdd = x -> {
        List<Object> results = new ArrayList<>();
        for (Object o : (Iterable<?>) x) {
            if (o instanceof Map<?, ?> map) {
                Object xValue = map.get("x");
                Object yValue = map.get("y");
                if (xValue instanceof Integer && yValue instanceof Integer) {
                    results.add((int) xValue + (int) yValue);
                }
            }
        }
        return results;
    };
    /**
     * Takes an iterable as input and returns a list of the results of subtracting the values of the `x` and `y` keys in each element of the iterable.
     *
     * @param x Iterable of arguments
     * @return List of the results of subtracting the values of the `x` and `y` keys
     */
    public static Function<Object, Object> testSub = x -> {
        List<Object> results = new ArrayList<>();
        for (Object o : (Iterable<?>) x) {
            if (o instanceof Map<?, ?> map) {
                Object xValue = map.get("x");
                Object yValue = map.get("y");
                if (xValue instanceof Integer && yValue instanceof Integer) {
                    results.add((int) xValue - (int) yValue);
                }
            }
        }
        return results;
    };
    /**
     * Takes an iterable as input and returns a list of the results of multiplying the values of the `x` and `y` keys in each element of the iterable.
     *
     * @param x Iterable of arguments
     * @return List of the results of multiplying the values of the `x` and `y` keys
     */
    public static Function<Object, Object> testMult = x -> {
        List<Object> results = new ArrayList<>();
        for (Object o : (Iterable<?>) x) {
            if (o instanceof Map<?, ?> map) {
                Object xValue = map.get("x");
                Object yValue = map.get("y");
                if (xValue instanceof Integer && yValue instanceof Integer) {
                    results.add((int) xValue * (int) yValue);
                }
            }
        }
        return results;
    };
    /**
     * Takes an iterable as input and returns a list of the results of dividing the values of the `x` and `y` keys in each element of the iterable.
     *
     * @param x Iterable of arguments
     * @return List of the results of dividing the values of the `x` and `y` keys
     */
    public static Function<Object, Object> testDiv = x -> {
        List<Object> results = new ArrayList<>();
        for (Object o : (Iterable<?>) x) {
            if (o instanceof Map<?, ?> map) {
                Object xValue = map.get("x");
                Object yValue = map.get("y");
                if (xValue instanceof Integer && yValue instanceof Integer) {
                    results.add((double) xValue / (double) yValue);
                }
            }
        }
        return results;
    };
    /**
     * Takes an integer as input and pauses the execution for the specified number of seconds before returning the string "Done!".
     *
     * @param s Time in seconds to sleep
     * @return String "Done!"
     */
    public static Function<Integer, String> sleep = s -> {
        try {
            TimeUnit.SECONDS.sleep(s);
            return "Done!";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    };
    /**
     * Takes a map of arguments as input and returns the factorial of the value of the `x` key, using BigInteger for large values.
     *
     * @param x Map of arguments
     * @return Factorial of the value of the `x` key
     */
    public static Function<Object, Object> factorial = x -> {
        if (x instanceof Map<?, ?> map) {
            Object xValue = map.get("x");
            if (xValue instanceof Integer) {
                BigInteger result = new BigInteger("1");
                for (int i = 1; i <= (int)xValue; i++) {
                    result = result.multiply(BigInteger.valueOf(i));
                }
                return result;
            }
        }
        return null;
    };
}