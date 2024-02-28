package Observer

case class Metrics(val id: Int, val executionTime: Long, val usedMemory: Int) {
  override def toString: String = {
    s"Metrics(invokerId=$id, executionTime=$executionTime, memoryUsage=$usedMemory)"
  }
  def getId: Int = id

  def getExecutionTime: Long = executionTime

  def getUsedMemory: Int = usedMemory
}
