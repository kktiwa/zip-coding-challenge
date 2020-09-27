package au.com.zip.util


import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory

object Logging {
  @transient val logger = Logger(LoggerFactory.getLogger(this.getClass))


  def log(msg: String): Unit = {
    System.out.println(msg)
  }
}
