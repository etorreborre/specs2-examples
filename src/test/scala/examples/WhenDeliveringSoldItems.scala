package examples

import org.specs2.mutable.Specification
import org.specs2.mock.Mockito


class WhenDeliveringSoldItems extends Specification with Mockito { sequential

  "Sold articles should be delivered" >> {
    "Given we are selling shirts online" >> {
      sales = Sales(mock[GSTProvider], mock[DeliveryService])
    }
    "When we sell a shirt" >> {
      sale = sales.makeSaleOf(1, "shirt").forANetPriceOf(10.00)
    }
    "Then the shirt should be sent to the delivery service" >> {
      there was one(sales.deliveryService).dispatch(anyString)
    }
  }

  var sale = Sale(); var sales = Sales()
}
