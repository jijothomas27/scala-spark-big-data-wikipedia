import scala.util.matching.Regex

val langs = List("Scala", "Java", "Groovy", "Haskell", "Erlang","JavaScript","C++","C#")
def regex(lang: String) = new Regex("(\\+)").replaceAllIn(lang,"\\\\$1")
val langsRegex = new Regex(langs.map(l => l match {
  case "Java" => "Java\\b+"
  case s:String => regex(s)
}).mkString("|"))


val articles = List(
  "Groovy is pretty interesting, and so is Erlang",
  "Scala and Java run on the JVM",
  "Scala is not purely functional, Scala is Neither",
  "The cool kids like Haskell more than Java",
  "Java is for enterprise developers",
  "JavaScript is not",
  "I love C++."
)

articles.flatMap(a => langsRegex.findAllIn(a)).groupBy(a => a)