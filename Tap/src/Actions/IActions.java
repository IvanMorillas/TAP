package Actions;

import java.util.Map;

/**
 * Defines a set of operations that can be performed as functions within the framework.
 * These operations include basic arithmetic operations (addition, subtraction, multiplication, and division), as well as sleep and factorial functions.
 */
public interface IActions {
    /**
     * Adds the values of the specified numbers.
     * @param params Map of parameters that contains the numbers to add.
     * @return The sum of the values of the specified numbers.
     */
    Integer add(Map<String, Integer> params);

    /**
     * Subtracts the values of the specified numbers.
     * @param params Map of parameters that contains the numbers to subtract.
     * @return The difference between the values of the specified numbers.
     */
    Integer sub(Map<String, Integer> params);

    /**
     * Multiplies the values of the specified numbers.
     * @param params Map of parameters that contains the numbers to multiply.
     * @return The product of the values of the specified numbers.
     */
    Integer mult(Map<String, Integer> params);

    /**
     * Divides the values of the specified numbers.
     * @param params Map of parameters that contains the numbers to divide.
     * @return The quotient of the values of the specified numbers.
     */
    Integer div(Map<String, Integer> params);

    /**
     * Pauses the execution of the framework for the specified time in milliseconds.
     * @param params Map of parameters that contains the pause time.
     * @return The specified pause time.
     */
    Integer sleeps(Map<String, Integer> params);

    /**
     * Calculates the factorial of the specified number.
     * @param params Map of parameters that contains the number to calculate the factorial.
     * @return The factorial of the specified number.
     */
    Integer fact(Map<String, Integer> params);
}
