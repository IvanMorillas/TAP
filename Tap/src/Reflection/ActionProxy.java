package Reflection;

import Controller.Controller;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * A class that dynamically generates proxies for controllers.
 */
public class ActionProxy implements InvocationHandler {

    /**
     * The controller for which to create a proxy.
     */
    private final  Controller controller;

    /**
     * Creates a new `ActionProxy` instance for the specified controller.
     *
     * @param controller The controller for which to create a proxy
     */
    public ActionProxy(Controller controller){
        this.controller = controller;
    }

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
        String actionName = method.getName();
        System.out.println("Invocando acción: " + actionName);
        return controller.invoke(actionName, args[0]);
    }
}
