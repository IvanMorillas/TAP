import Actions.Actions;
import Actions.IActions;
import Controller.Controller;
import Invokers.Invoker;
import MapReduce.MapReduce;
import Observer.Metrics;
import Decorator.Memoization;
import Decorator.Timer;
import PolicyManager.BigGroup;
import PolicyManager.GreedyGroup;
import PolicyManager.RoundRobin;
import PolicyManager.UniformGroup;
import Reflection.ActionProxy;
import Reflection.DynamicProxy;

import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) throws Exception {





        List<Integer> listInt = Arrays.asList(1,2,3);

        List<Integer> parell = listInt.stream().filter(x -> x%2==0).collect(Collectors.toList());











        Scanner scanner = new Scanner(System.in);
        System.out.println("How many invokers do you want?");
        int numInv = scanner.nextInt();
        int option;
        List<Invoker> invokersList = new ArrayList<>();
        Invoker invoker = new Invoker(1,1000);
        Invoker invoker2 = new Invoker(2,1000);
        Invoker invoker3 = new Invoker(3,1000);
        invokersList.add(invoker);
        invokersList.add(invoker2);
        invokersList.add(invoker3);
        Controller controller = new Controller(numInv, new RoundRobin());
        //Controller controller = new Controller(numInv, new GreedyGroup());
        //Controller controller = new Controller(numInv, new UniformGroup());
        //Controller controller = new Controller(numInv, new BigGroup());
        controller.registerInvoker(invokersList);
        for(Invoker i : invokersList){
            i.subscribe(controller);
        }
        invoker2.unsubscribe(controller);
        Map<String, Integer> testData = Map.of("x", 15, "y", 3);
        List<Map<String, Integer>> input = Arrays.asList(
                Map.of("x", 6, "y", 3),
                Map.of("x", 9, "y", 7),
                Map.of("x", 8, "y", 1)
        );

        //System.out.println("REGISTERS:");
        controller.registerAction("addAction", Actions.addAction, 37);
        controller.registerAction("subAction", Actions.subAction, 13);
        controller.registerAction("add", Actions.testAdd, 30);
        controller.registerAction("sub", Actions.testSub, 15);
        controller.registerAction("sleep", Actions.sleep, 20);
        controller.registerAction("factorial", Actions.factorial, 25);

        do {
            System.out.println("What do you want to execute?");
            System.out.println("1. Invokes");
            System.out.println("2. Future");
            System.out.println("3. Observer");
            System.out.println("4. Decorator");
            System.out.println("5. Reflection");
            System.out.println("6. Map Reduce");
            System.out.println("7. Exit");
            option = scanner.nextInt();
            switch (option){
                case 1:
                    System.out.println("\nINVOKES:");
                    Object resultAction1 = controller.invoke("addAction", testData);
                    System.out.println("resultAction1: " + resultAction1+"\n");
                    Object resultAction2 = controller.invoke("subAction", testData);
                    System.out.println("resultAction2: " + resultAction2+"\n");
                    Object resultAction3 = controller.invoke("add", input);
                    System.out.println("resultAction3: " + resultAction3+"\n");
                    Object resultAction4 = controller.invoke("sub", input);
                    System.out.println("resultAction4: " + resultAction4+"\n");
                    Object resultAction5 = controller.invoke("factorial", testData);
                    System.out.println("resultAction5: " + resultAction5+"\n");
                    Object resultAction14 = controller.invoke("sub", input);
                    System.out.println("resultAction14: " + resultAction14+"\n");
                    Object resultAction15 = controller.invoke("factorial", testData);
                    System.out.println("resultAction15: " + resultAction15+"\n");
                    break;
                case 2:
                    System.out.println("\nFUTURE:");
                    Future<Object> future1 = controller.invokeAsync("add", input);
                    Future<Object> future2 = controller.invokeAsync("sub", input);
                    Future<Object> future3 = controller.invokeAsync("addAction", testData);
                    Future<Object> future4 = controller.invokeAsync("subAction", testData);
                    System.out.println("future1: " + future1.get());
                    System.out.println("future2: " + future2.get());
                    System.out.println("future3: " + future3.get());
                    System.out.println("future4: " + future4.get());
                    Future<Object> future5 = controller.invokeAsync("sleep", 5);
                    Future<Object> future6 = controller.invokeAsync("sleep", 5);
                    Future<Object> future7 = controller.invokeAsync("sleep", 5);
                    System.out.println("future5: " + future5.get());
                    System.out.println("future6: " + future6.get());
                    System.out.println("future7: " + future7.get());
                    break;
                case 3:
                    System.out.println("\nOBSERVER:");
                    Object resultAction6 = controller.invoke("addAction", testData);
                    System.out.println("ResultAction6: " + resultAction6);
                    Object resultAction7 = controller.invoke("subAction", testData);
                    System.out.println("ResultAction7: " + resultAction7);
                    Object resultAction8 = controller.invoke("add", input);
                    System.out.println("ResultAction8: " + resultAction8);
                    Object resultAction9 = controller.invoke("sub", input);
                    System.out.println("ResultAction9: " + resultAction9);
                    Object resultAction10 = controller.invoke("factorial", testData);
                    System.out.println("ResultAction10: " + resultAction10);
                    Metrics metric5 = new Metrics(5, 1000, 175);
                    Metrics metric4 = new Metrics(4, 900, 150);
                    controller.updateMetrics(metric5);
                    controller.updateMetrics(metric4);
                    System.out.println("\n");
                    controller.showMetrics();
                    controller.analyzeMetrics();
                    break;
                case 4:
                    System.out.println("\nDECORATOR:");
                    System.out.println("\nTIMER:");
                    Timer factorialTimer = new Timer(Actions.factorial, invoker.getId(), invoker.getMemory());
                    controller.registerAction("factorialTimer", factorialTimer, 11);
                    Object resultTimer = controller.invoke("factorialTimer", Map.of("x", 5));
                    Object resultTimer2 = controller.invoke("factorialTimer", Map.of("x", 10));
                    System.out.println("ResultTimer: "+resultTimer);
                    System.out.println("ResultTimer2: "+resultTimer2);
                    System.out.println("\nMEMOIZATION:");
                    Memoization factorialMemoization = new Memoization(Actions.factorial);
                    controller.registerAction("factorialMemoization", factorialMemoization, 13);
                    Object resultMemoization = controller.invoke("factorialMemoization",Map.of("x", 10));
                    Object resultMemoization2 = controller.invoke("factorialMemoization",Map.of("x", 15));
                    System.out.println("ResultMemoization: "+resultMemoization);
                    System.out.println("ResultMemoization2: "+resultMemoization2);
                    break;
                case 5:
                    System.out.println("\nREFLECTION:");
                    controller.registerAction("add", Actions.addAction, 1);
                    controller.registerAction("sub", Actions.subAction, 1);
                    controller.registerAction("fact", Actions.factAction, 1);
                    IActions actionsProxy = (IActions) Proxy.newProxyInstance(IActions.class.getClassLoader(),new Class<?>[]
                                            {IActions.class},new ActionProxy(controller));
                    //IActions dynamicProxy = (IActions) Proxy.newProxyInstance(IActions.class.getClassLoader(), IActions.class.getInterfaces(),new DynamicProxy(controller));
                    Object resultProxy = actionsProxy.add(testData);
                    System.out.println("ResultProxy: "+resultProxy);
                    Object resultProxy2 = actionsProxy.sub(testData);
                    System.out.println("ResultProxy2: "+resultProxy2);
                    Object resultProxy3 = actionsProxy.fact(Map.of("x", 3));
                    System.out.println("ResultProxy3: "+resultProxy3);
                    /*Object resultProxy4 = dynamicProxy.add(testData);
                    System.out.println("ResultProxy4: "+resultProxy4);
                    Object resultProxy5 = dynamicProxy.sub(testData);
                    System.out.println("ResultProxy5: "+resultProxy5);
                    Object resultProxy6 = dynamicProxy.fact(Map.of("x", 3));
                    System.out.println("ResultProxy6: "+resultProxy6);*/
                    break;
                case 6:
                    System.out.println("\nMAPREDUCE:");
                    Object text = MapReduce.leer("src/libro.txt");
                    //System.out.println(inputs);
                    controller.registerAction("wordCount", MapReduce.wordCount, 50);
                    controller.registerAction("countWords", MapReduce.countWords, 50);
                    Object resultCountWords = controller.invoke("countWords", text);
                    System.out.println("ResultWordCount: "+resultCountWords);
                    Object resultWordCount = controller.invoke("wordCount", text);
                    System.out.println("ResultWordCount: "+resultWordCount);
                    break;
                case 7:
                    System.out.println("Leaving the program...");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        } while (option != 7);
    }
}