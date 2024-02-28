package Invoker

import Action.Action
import Observer.{Metrics, Observable, Observer}

import java.util
import java.util.{ArrayList, List}
import scala.collection.immutable.List

class Invoker(private val id: Int, private val totalMemory: Double) extends Observable{
  private var usedMemory: Int = 0

  private val observerList = scala.collection.mutable.ArrayBuffer[Observer]()


  def getId: Int = this.id

  def hasEnoughMemory(memory: Int): Boolean = {
    (memory + usedMemory) <= totalMemory
  }

  def getMemory: Int = usedMemory

  @throws[Exception]
  def invokeAction[T, R](action: Action[T, R], input: AnyRef): R = {
    val start = System.currentTimeMillis
    val memory = action.getMemory
    var result: R = null.asInstanceOf[R]
    if ((usedMemory + memory) <= totalMemory) {
      usedMemory += memory
      try result = action.run(input.asInstanceOf[T])
      catch {
        case e: Exception =>
          throw new Exception("Error when executing this " + action + " : " + e.getMessage, e)
      } finally {
        val end = System.currentTimeMillis
        val metrics = Metrics(id, end - start, usedMemory)
        this.notifyObservers(metrics)
      }
    }
    else throw new RuntimeException("Not enough resources for this action.")
    result
  }

  def subscribe(observer: Observer): Unit = {
    observerList.append(observer)
    println("Invoker " + this.id + " subscribed")
  }

  def unsubscribe(observer: Observer): Unit = {
    observerList -= observer
    println("Invoker " + this.id + " unsubscribed")
  }

  def notifyObservers(metric: Metrics): Unit = {
    for (observer <- observerList) {
      observer.updateMetrics(metric)
    }
    println("Invoker "+ this.id +" notified");
  }
}
