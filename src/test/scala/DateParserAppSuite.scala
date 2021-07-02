import org.scalatest.freespec.AnyFreeSpec
import wvlet.log.{LogLevel, Logger}

import java.text.ParseException

class DateParserAppSuite extends AnyFreeSpec {
  Logger.setDefaultLogLevel(LogLevel.DEBUG)
  val date_parser = new DateParserApp()
  "DateParserAppSuite" - {
    "date_parser" - {
      "should parse good date with good format " in {
        assert(date_parser.parse_date("yyyy-MM-dd", "2020-12-12").toString == "Sat Dec 12 00:00:00 UTC 2020")
      }
      "should raise exceptions and log messages for bad inputs" in {
        assertThrows[ParseException](date_parser.parse_date("yyyy-MM-dd", "aaag-12-12-12"))
        assertThrows[IllegalArgumentException](date_parser.parse_date("ggg-ggg-ggg", "aaag-12-12-12"))
        // TODO how to test log messages itself?
      }
    }
    "guess_date" - {
      "should guess nicely specified date" in {
        assert(date_parser.guess_date("2020-12-30").toString == "Wed Dec 30 00:00:00 UTC 2020")
        assert(date_parser.guess_date("2020-12-12").toString == "Sat Dec 12 00:00:00 UTC 2020")
        assert(date_parser.guess_date("2020.12.12").toString == "Sat Dec 12 00:00:00 UTC 2020")
      }
      "should raise understandable exception" in {
        assertThrows[IllegalArgumentException](date_parser.guess_date("20201230").toString == "Wed Dec 30 00:00:00 CET 2020")
        // TODO how to test log messages itself?
      }
    }
  }
}