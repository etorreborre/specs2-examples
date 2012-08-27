package examples

import org.specs2.matcher.DataTables
import org.specs2.mutable.{FragmentsBuilder, Specification}

/**
 * Notes:
 *
 *  - DataTables are fully type-checked, so after the first row is entered, all the elements in subsequent rows must
 *    have the same types as in the first one
 */
class WhenDisplayingTagNamesInAReadableForm extends Specification with Tables {

  "The inflector should transform singular nouns into plurals" >> {
                                                                                                """
    when I find the plural form of a single word, then the plural form should be
    gramatically correct:
                                                                                                """ >> {
      "single form"  | "plural form"  |>
      "epic"         ! "epics"        |
      "feature"      ! "features"     |
      "story"        ! "story"        |
      "stories"      ! "stories"      |
      "octopus"      ! "octopi"       |
      "sheep"        ! "sheep"        | { (singleForm, pluralForm) =>

        Inflection.of(singleForm).inPluralForm.toString === pluralForm

      }
    }
  }
}

// This trait solves an implicit conflict and should be added to specs2 in the future for convenience
// see the note here: http://etorreborre.github.com/specs2/guide/org.specs2.guide.Matchers.html#DataTables
trait Tables extends DataTables with FragmentsBuilder {
  override def forExample(desc: String): ExampleDesc = super.forExample(desc)
}