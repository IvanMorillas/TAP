package PolicyManager

import Action.Action
import Controller.Controller
import Invoker.Invoker
import scala.collection.convert.ImplicitConversions.`seq AsJavaList`

class RoundRobin extends PolicyManager {

  private var index = 0

  override def selectInvoker(controller: Controller, action: Action[_, _]): Invoker = {
    val memory: Int = action.getMemory
    val invokers = controller.getInvokers
    var invokersTried: Int = 0
    while (invokersTried < invokers.size) {
      val selectedInvoker: Invoker = invokers.get(index)
      index = (index + 1) % invokers.size
      if (selectedInvoker.hasEnoughMemory(memory)) {
        println("InvokerSelect: " + selectedInvoker.getId)
        return selectedInvoker
      }
      invokersTried += 1
    }
    throw new RuntimeException("Any invoker has available resources.")
  }
}
