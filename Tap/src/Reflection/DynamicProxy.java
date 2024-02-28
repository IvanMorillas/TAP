package Reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * A class that dynamically generates proxies for target objects.
 */
public class DynamicProxy implements InvocationHandler {

    /**
     * The target object for which to create a proxy.
     */
    Object target;

    /**
     * Creates a new `DynamicProxy` instance for the specified target object.
     *
     * @param target The target object for which to create a proxy
     */
    public DynamicProxy(Object target){
        this.target = target;
    }

    /**
     * Creates and returns a new proxy instance for the specified target object.
     *
     * @param target The target object for which to create a proxy
     * @return A new proxy instance for the specified target object
     */
    /*public Object newInstance(Object target){
        Class<?> targetClass = target.getClass();
        return (Object) Proxy.newProxyInstance(targetClass.getClassLoader(), targetClass.getInterfaces(),
                new DynamicProxy(target));
    }*/

    /**
     *
     * @param proxy the proxy instance that the method was invoked on
     *
     * @param method the {@code Method} instance corresponding to
     * the interface method invoked on the proxy instance.  The declaring
     * class of the {@code Method} object will be the interface that
     * the method was declared in, which may be a superinterface of the
     * proxy interface that the proxy class inherits the method through.
     *
     * @param args an array of objects containing the values of the
     * arguments passed in the method invocation on the proxy instance,
     * or {@code null} if interface method takes no arguments.
     * Arguments of primitive types are wrapped in instances of the
     * appropriate primitive wrapper class, such as
     * {@code java.lang.Integer} or {@code java.lang.Boolean}.
     *
     * @return The result of the method invocation
     * @throws Throwable Any exceptions thrown by the method
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invocationResult = null;
        try {
            System.out.println("Method invoked from DynamicProxy: " + method.getName());
            invocationResult = method.invoke(this.target, args);
        } catch (InvocationTargetException ite) {
            throw ite.getTargetException();
        } catch (Exception e) {
            System.err.println("Invocation of " + method.getName() + " failed");
        }
        return invocationResult;
    }
}
