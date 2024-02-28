package Action

abstract class Action[T, R] {

  @throws[Exception]
  def run(args: T): R

  def getMemory: Int
}