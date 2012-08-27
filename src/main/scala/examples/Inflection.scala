package examples

object Inflection {
  def of(single: String) = Inflection(single)
}
case class Inflection(single: String) {
  def inPluralForm: PluralForm = PluralForm(single)
}
case class PluralForm(plural: String) {
  override def toString = plural
}