package examples

case class Sale(qty: Int = 0, product: String = "", price: Double = 0.0) {
  def totalPrice = price
  def forANetPriceOf(price: Double) = copy(price = price)
  def gstIs(gst: Double) = this
}

object Sale {
  def of(qty: Int, product: String) = Sale(qty, product)
}

case class Sales(gstProvider: GSTProvider = FixedGSTProvider(10), deliveryService: DeliveryService = NoDelivery) {
  def makeSaleOf(qty: Int, product: String) = Sale(qty, product)
}

trait GSTProvider {
  def rate: Double
}

case class FixedGSTProvider(gst: Double) extends GSTProvider {
  def rate = gst
}

trait DeliveryService {
  def dispatch(item: String): Unit
}
object NoDelivery extends DeliveryService {
  def dispatch(item: String) {}
}