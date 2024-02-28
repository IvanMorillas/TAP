package Observer

abstract class Observable {

  def subscribe(o: Observer): Unit

  def unsubscribe(o: Observer): Unit

  def notifyObservers(m: Metrics): Unit
}
