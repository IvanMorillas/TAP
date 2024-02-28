package Controller

import Action.Action
import Action.FunctionAction
import Invoker.Invoker
import Observer.{Metrics, Observer}
import PolicyManager.PolicyManager

import java.util
import java.util.Map
import java.util.function.Function
import java.util.stream.Collectors
import scala.collection.convert.ImplicitConversions.`collection asJava`
import scala.collection.mutable

class Controller(val maxInvokers: Int, val policy: PolicyManager) extends Observer {
  private var invokersList: List[Invoker] = List.fill(maxInvokers)(null)
  private val actionsList: mutable.Map[String, Action[_, _]] = mutable.HashMap()
  private val metricsList = scala.collection.mutable.ArrayBuffer[Metrics]()

  def registerInvoker(invokersList: List[Invoker]): Unit = {
    this.invokersList = invokersList
  }

  @throws[Exception]
  def registerAction[T, R](id: String, function: Function[T, R], memory: Int): Unit = {
    actionsList.put(id, new FunctionAction[T, R](function, memory))
    println("Action "+ id +" Registered")
  }

  def invoke[T, R](id: String, input: Object): R = {
    val executeAction = actionsList(id).asInstanceOf[Action[T, R]]
    val invoker = policy.selectInvoker(this, executeAction)
    val result = invoker.invokeAction(executeAction, input)
    //println(s"Action $id invoked in ${invoker.getId}")
    result
  }

  def getInvokers: List[Invoker] = invokersList

  override def toString: String = {
    s"Controller(invokers=$invokersList, policyManager=$policy, actions=$actionsList)"
  }

  def updateMetrics(metric: Metrics): Unit = {
    metricsList.append(metric)
    println("Metric received: " + metric)
  }

  def showMetrics(): Unit = {
    println(metricsList.toString)
  }

  def analyzeMetrics(): Unit = {
    println("\nMetrics:")
    val averageTime: Double = metricsList.map(_.getExecutionTime).sum / metricsList.length
    println("\tAverage time: " + averageTime + " ms")
    val maximumTime: Long = metricsList.maxBy(_.getExecutionTime).executionTime
    println("\tMaximum time: " + maximumTime + " ms")
    val minimumTime: Long = metricsList.minBy(_.getExecutionTime).executionTime
    println("\tMinimum time: " + minimumTime + " ms")
    val totalTime: Long = metricsList.map(_.getExecutionTime).sum
    println("\tTotal execution time: " + totalTime + " ms")
  }
}
