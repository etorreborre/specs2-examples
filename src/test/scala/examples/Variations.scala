package examples

import org.specs2._
import specification.{Step, Then, When, Given}

/**
 * Notes:
 *
 *  - in this kind of specification all the text is maintained in one place and annotated on the side with the operations to perform
 *
 *  - we also use regular expression to extract the interesting bits in the description text
 *  
 *  - note that no variables are used, the current "state" is passed around by using functions which should have the proper type
 *    i.e after a Given[Sale] I can have a When[Sale, Sale] but not a When[Int, Sale]
 */
class Variation1 extends Specification { def is =

  "GST should apply on ordinary articles"                    ^
    "Given we are selling a shirt for a price of 10"         ^ sale^
    "When we calculate the price including a GST of 10%"     ^ calculate^
    "Then the price should include the GST and be 11"        ^ price


  def sale: Given[Sale] = groupAs("\\d+") and { (price: String) =>
    Sale.of(1, "shirt").forANetPriceOf(price.toDouble)
  }

  def calculate: When[Sale, Sale] = groupAs("\\d+") and { (sale: Sale) => (gst: String) =>
    sale.gstIs(gst.toDouble)
  }

  def price: Then[Sale] = groupAs("\\d+") then { (sale: Sale) => (price: String) =>
    sale.totalPrice === price.toDouble
  }
}

/**
 * Notes:
 *
 *  - this version uses a local variable and a mutable specification to modify the "Sale" state
 */
class Variation2 extends mutable.Specification {
  var sale = Sale()

  "GST should apply on ordinary articles"                    >> {
    "Given we are selling a shirt for a price of 10"         << groupAs("\\d+") { (price: String) =>
      sale = Sale.of(1, "shirt").forANetPriceOf(price.toDouble)
    }
    "When we calculate the price including a GST of 10%"     << groupAs("\\d+") { (gst: String) =>
      sale = sale.gstIs(gst.toDouble)
    }
    "Then the price should include the GST and be 11"        << groupAs("\\d+") { (price: String) =>
      sale.totalPrice === price.toDouble
    }
  }
}

/**
 * Notes:
 *
 *  - this version uses text delimiters to specify what to extract
 *  - the markers are not printed on the console when the specification is executed
 */
class Variation3 extends mutable.Specification {
  var sale = Sale()

  "GST should apply on ordinary articles"                    >> {
    "Given we are selling a shirt for a price of ${10}"         << { (price: String) =>
      sale = Sale.of(1, "shirt").forANetPriceOf(price.toDouble)
    }
    "When we calculate the price including a GST of ${10}%"     << { (gst: String) =>
      sale = sale.gstIs(gst.toDouble)
    }
    "Then the price should include the GST and be ${11}"        << { (price: String) =>
      sale.totalPrice === price.toDouble
    }
  }
}

/**
 * Notes:
 *
 *  - this variation uses the fact that any name can be a method in Scala +
 *    auto-examples, that is the ability to extract text directly from the source file
 *  - it is however slightly incorrect because the first 2 steps are then interpreted as Examples, when they are just
 *    setup
 */
class Variation4 extends Specification { def is =

  "GST should apply on ordinary articles"                    ^
    `Given we are selling a shirt for a price of 10`         ^
    `When we calculate the price including a GST of 10%`     ^
    `Then the price should include the GST and be 11`


  var sale = Sale()

  def `Given we are selling a shirt for a price of 10` =
  { Sale.of(1, "shirt").forANetPriceOf(10); ok }

  def `When we calculate the price including a GST of 10%` =
  { sale.gstIs(10); ok }

  def `Then the price should include the GST and be 11` =
    sale.totalPrice === 11
}

/**
 * Notes:
 *
 *  - if you have a readable DSL, text can sometimes be omitted (I wouldn't say it's the case here)
 */
class Variation5 extends Specification { def is =

  "GST should apply on ordinary articles" ^
   { Sale.of(1, "shirt").forANetPriceOf(10).gstIs(10).totalPrice === 11  }
}
