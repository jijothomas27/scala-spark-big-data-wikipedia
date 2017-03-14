import scala.util.matching.Regex

val reg = new Regex("\\b+(Java)\\b+?|\\b+C\\+\\+|(Objective-C)|C#").unanchored

reg.findAllIn("IC# Java love JavaScript, kC++ is good Objective-C,C#").mkString