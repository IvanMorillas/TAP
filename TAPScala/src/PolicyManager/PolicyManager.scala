package PolicyManager

import Action.Action
import Controller.Controller
import Invoker.Invoker

abstract class PolicyManager {

  def selectInvoker(controller: Controller, action: Action[_, _]): Invoker
}
