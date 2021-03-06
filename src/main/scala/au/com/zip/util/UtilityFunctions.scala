package au.com.zip.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object UtilityFunctions {

  def getMonth(txnDate: String): String = {
    val dateFormat = "yyyy-MM-dd"
    val formatter = DateTimeFormatter.ofPattern(dateFormat)
    val localDate = LocalDate.parse(txnDate, formatter)
    localDate.getMonth.name()
  }

}
