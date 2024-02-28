package Action

object Actions {


  def addAction(inputData2: Map[String, Int]): Int = {
    inputData2.foldLeft(0)((sum, value) => sum + value._2)
  }
  def subAction(inputData: Map[String, Int]): Int = {
    inputData.values.foldLeft(0)((sub, value) => sub - value)
  }

  def mulAction(inputData3: Map[String, Int]): Int = {
    inputData3.foldLeft(0)((mul, value) => mul * value._2)
  }

  def divAction(inputData4: Map[String, Int]): Int = {
    inputData4.foldLeft(0)((div, value) => div / value._2)
  }

  /*def factorial(inputData: Map[String, Int], n:Int): Int = {
    (1 to n).product
  }*/
}
