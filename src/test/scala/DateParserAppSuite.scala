import org.scalatest.freespec.AnyFreeSpec
import wvlet.log.{LogLevel, Logger}

import java.time.LocalDate
import java.time.format.DateTimeParseException

class DateParserAppSuite extends AnyFreeSpec {
  Logger.setDefaultLogLevel(LogLevel.DEBUG)
  val date_parser = new DateParserApp()
  "DateParserAppSuite" - {
    "date_parser" - {
      "should parse good date with good format " in {
        assert(date_parser.parse_date("yyyy-MM-dd", "2020-12-12") == LocalDate.parse("2020-12-12"))
      }
      "should raise exceptions for bad inputs" in {
        val ex = intercept[DateTimeParseException](date_parser.parse_date("yyyy-MM-dd", "aaag-12-12-12"))
        assert(ex.getMessage == "Can't parse aaag-12-12-12 for yyyy-MM-dd")
        val ex_ = intercept[IllegalArgumentException](date_parser.parse_date("ggg-ggg-ggg", "aaag-12-12-12"))
        assert(ex_.getMessage == "Wrong format: ggg-ggg-ggg")
      }
    }
    "guess_date" - {
      "should guess nicely specified date" in {
        assert(date_parser.guess_date("2020-12-30") == LocalDate.parse("2020-12-30"))
        assert(date_parser.guess_date("2020-12-12") == LocalDate.parse("2020-12-12"))
        assert(date_parser.guess_date("2020.12.12") == LocalDate.parse("2020-12-12"))
        assert(date_parser.guess_date("2020/12/12") == LocalDate.parse("2020-12-12"))
      }
      "should raise understandable exception" in {
        val ex = intercept[IllegalArgumentException](date_parser.guess_date("20201230"))
        assert(ex.getMessage.contains("Can't parse the 20201230 with the available formats"))
      }
    }
  }
}
