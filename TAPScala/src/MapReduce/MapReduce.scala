package MapReduce

import java.io.{BufferedReader, File, FileReader, IOException}
import java.util
import java.util.{HashMap, Map}
import java.util.function.Function

object MapReduce {

  def leer(title: String): String = {
    val file = new java.io.File(title)
    val bufferedReader = new java.io.BufferedReader(new java.io.FileReader(file))
    val content = new StringBuilder()
    var line: String = null
    while ({ line = bufferedReader.readLine(); line != null }) {
      content.append(line).append("\n")
    }
    bufferedReader.close()
    content.toString()
  }

  val countWords: Function[String, Int] = (input: Object) => {
    val stringText: String = input.asInstanceOf[String]
    if (stringText == null || stringText.isEmpty) {
      0
    } else {
      stringText.split("\\s+").length
    }
  }

  var wordCount: Function[AnyRef, AnyRef] = (text: AnyRef) => {
    val inputText: String = text.asInstanceOf[String]
    val wordCounts: util.Map[String, Integer] = new util.HashMap[String, Integer]
    val words: Array[String] = inputText.split("\\s+")
    for (word <- words) {
      if (word.nonEmpty) wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1)
    }
    wordCounts
  }
}
