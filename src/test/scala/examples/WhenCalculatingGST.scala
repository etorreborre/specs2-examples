package examples

import org.specs2.mutable._
import org.specs2.mock.Mockito

/**
 * Notes:
 *
 *  - in Scala, the variables have to be declared locally, this is why they are declared at the end of the specification
 *
 *  - the === operator does not give as detailed results as Spock but Peter Niederwieser has a project to do so with Scala 2.10
 *    https://github.com/pniederw/expecty
 *
 *  - a specification has to be declared "sequential" or be executed with "sequential" as a command line arg otherwise all the
 *    examples are executed concurrently by default. This would not be necessarily bad if we have several Given independent steps
 *
 *  - there is no prescription for the keywords Given-When-Then to be used. I used them here but generally I prefer some more
 *    free text
 *
 */
class WhenCalculatingGST extends Specification { sequential

  "GST should apply on ordinary articles" >> {
    "Given we are selling a shirt" >> {
      sale = Sale.of(1, "shirt").forANetPriceOf(10.00)
    }
    "When we calculate the price including GST" >> {
      totalPrice = sale.totalPrice
    }
    "Then the price should include a GST of 10%" >> {
      totalPrice === 11.00
    }
  }

  var sale = Sale(); var totalPrice = 0.0
}

/**
 * Notes:
 *
 *  - mocking and stubbing in done via an additional trait using Mockito
 *  - the syntax is more English-like than Spock: "returns" for ">>" and "there was one..." for "dispatch(_)"
 */
class WhenCalculatingGST2 extends Specification with Mockito { sequential

  "GST should apply on ordinary articles" >> {
    "Given we are selling a shirt" >> {
      val sales = Sales(mock[GSTProvider])
      sales.gstProvider.rate returns 12.5

      sale = sales.makeSaleOf(1, "shirt").forANetPriceOf(10.00)
    }
    "When we calculate the price including GST" >> {
      totalPrice = sale.totalPrice
    }
    "Then the price should include a GST of 12.5%" >> {
      totalPrice === 11.25
    }
  }

  var sale = Sale(); var totalPrice = 0.0
}
