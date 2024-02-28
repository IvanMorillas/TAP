package Action

import java.util.function.Function

class FunctionAction[T, R](private val function: Function[T, R], private val memory: Int) extends Action[T, R] {

  def run(arg: T): R = function.apply(arg)

  def getMemory: Int = memory
}
