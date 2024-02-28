package Observer

trait Observer {
  def updateMetrics(metrics: Metrics): Unit
}
