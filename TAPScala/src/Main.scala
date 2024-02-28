import scala.collection.mutable
import Action.{Action, Actions}
import Controller.Controller
import Invoker.Invoker
import MapReduce.MapReduce
import Observer.Metrics
import PolicyManager.RoundRobin

object Main {
  def main(args: Array[String]): Unit = {
    var invokersList = List[Invoker]()
    val invoker: Invoker = new Invoker(1, 500)
    val invoker2: Invoker = new Invoker(2, 500)
    invokersList :+= invoker
    invokersList :+= invoker2
    val controller = new Controller(2, new RoundRobin)
    controller.registerInvoker(invokersList)
    controller.registerAction("add", Actions.addAction, 5)
    for (i <- invokersList) {
      i.subscribe(controller)
    }
    val inputData1 = Map("x" -> 10, "y" -> 10)
    val inputData2 = Map("x" -> 150, "y" -> 10)
    println("\nINVOKES:")
    val result1 = controller.invoke[Map[String, Int], Int]("add", inputData1)
    val result2 = controller.invoke[Map[String, Int], Int]("add", inputData2)
    println("Result of action 'add': " + result1)
    println("Result of action 'add': " + result2)
    println("\nOBSERVER:")
    val metric3 = Metrics(3, 1000, 175)
    controller.updateMetrics(metric3)
    controller.showMetrics()
    controller.analyzeMetrics()
    println("\nMAP REDUCE:")
    val text = MapReduce.leer("src/libro.txt")
    //println(text)
    controller.registerAction("countWords", MapReduce.countWords, 50)
    controller.registerAction("wordCounts", MapReduce.wordCount, 50)
    println("ResultCountWords: " + controller.invoke("countWords", text))
    println("ResultWordCounts: " + controller.invoke("wordCounts", text))
  }
}