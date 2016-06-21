package com.flashboomlet.preproccessing

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

/**
  * DateUtil is used to handle the date conversions from various sources into the proper
  * mongoDB Date/Time format to conform to the BSON ISODate format
  *
  * Converts all dates to ISO format in a normalized standard using the UTC time standard.
  *
  * http://stackoverflow.com/questions/3778428/best-way-to-store-date-time-in-mongodb
  *
  */
object DateUtil {

  private val NytQueryDateFormat = "yyyyMMdd"

  /**
    * Formatter for twitter dates.
    *
    * @return a formatter for Twitter Date Times
    */
  private def twitterFormatter(): DateTimeFormatter = {
    DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss ZZZ yyyy").withZone(ZoneOffset.UTC)
  }

  /**
    * Formatter for ISO Dates
    *
    * @return a formatter for ISO Date Times
    */
  private def isoFormatter(): DateTimeFormatter = {
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:zz'Z'").withZone(ZoneOffset.UTC)
  }

  private def shortDateFormatter(): DateTimeFormatter = {
    DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneOffset.UTC)
  }

  /**
    * getToday returns today's date time in iso format
    * @return iso formatted dateTime
    */
  def getToday(): String = {
    isoFormatter.format(Instant.now())
  }

  def getNytToday(): String = {
    val simpleDateFormat = new SimpleDateFormat(NytQueryDateFormat)
    val calendar: Calendar = Calendar.getInstance()
    simpleDateFormat.format(calendar.getTime)
  }

  /**
    * convertTwitterDate converts a twitters date to the proper ISO format on the UTC time standard
    *
    * @param date twitter date
    * @return iso formatted dateTime in a String
    */
  def convertTwitterDate(date: Date): String = {
    isoFormatter.format(twitterFormatter.parse(date.toString))
  }

  /**
    * Takes a short date and converts it to a full ISO format.
    *
    * @param date date
    * @return iso formatted dateTime in a String
    */
  def shortDateNormalize(date: Date): String = {
    isoFormatter.format(shortDateFormatter.parse(date.toString))
  }
}
