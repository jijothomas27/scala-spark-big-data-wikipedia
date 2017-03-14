package wikipedia

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD

import scala.util.matching.Regex

case class Article(title: String, text: String)

/**
  * Created by jijo.thomas on 3/13/17.
  */
object TryWiki {

  val langs = List(
    "JavaScript", "Java", "PHP", "Python", "C#", "C++", "Ruby", "CSS",
    "Objective-C", "Perl", "Scala", "Haskell", "MATLAB", "Clojure", "Groovy")

  val langsRegex = new Regex(langs.map(lr).mkString("|"))

  val conf: SparkConf = new SparkConf().setAppName("wikipedia").setMaster("local")
  val sc: SparkContext = new SparkContext(conf)

  // Hint: use a combination of `sc.textFile`, `WikipediaData.filePath` and `WikipediaData.parse`
  val wikiRdd: RDD[Article] = sc.textFile(WikipediaData.filePath).map(WikipediaData.parsea).persist()

  def regex(lang: String) = new Regex("(\\+)").replaceAllIn(lang,"\\\\$1")
  def lr(lang: String) = new Regex(regex(lang) match {
    case "Java" => "Java\\b+"
    case s:String => s
  })

  /** Returns the number of articles on which the language `lang` occurs.
    *  Hint1: consider using method `aggregate` on RDD[T].
    *  Hint2: should you count the "Java" language when you see "JavaScript"?
    *  Hint3: the only whitespaces are blanks " "
    *  Hint4: no need to search in the title :)
    */
  def occurrencesOfLang(lang: String, rdd: RDD[Article]): Int = rdd.
    filter(wa => lr(lang).findAllIn(wa.text).size > 0).
    aggregate(0)((acc,wa) => acc+1, _+_)

  def maina(args: Array[String]): Unit = {
    println(langsRegex)
    val inter = wikiRdd.flatMap(a => langsRegex.findAllIn(a.text).toSet).groupBy(a => a)

    inter.foreach(println)

    sc.stop()
  }

}
