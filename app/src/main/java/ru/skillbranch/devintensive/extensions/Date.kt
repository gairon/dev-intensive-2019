package ru.skillbranch.devintensive.extensions

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

const val SECOND: Long = 1000;
const val MINUTE: Long = 60 * SECOND;
const val HOUR: Long = 60 * MINUTE;
const val DAY: Long = 24 * HOUR;

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    this.time += when(units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }

    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val diff: Long = date.time - this.time;
    val negativeDiff: Long = this.time - date.time;

    val result: String;
    if (diff >= 0) {
        result = when {
            diff <= 1 * SECOND -> "только что"
            diff <= 45 * SECOND -> "несколько секунд назад"
            diff <= 75 * SECOND -> "минуту назад"
            diff > 75 * SECOND && diff <= 45 * MINUTE -> {
                val minutes: Long = diff / MINUTE;
                val loMin: Int = (minutes % 10).toInt()
                val units = when (loMin) {
                    2, 3, 4 -> "минуты"
                    else -> "минут"
                }

                "$minutes $units назад"
            }
            diff > 45 * MINUTE && diff <= 75 * MINUTE -> "час назад"
            diff > 75 * MINUTE && diff <= 22 * HOUR -> {
                val hours: Long = diff / HOUR;
                val loHours: Int = (hours % 10).toInt()
                val units = when (loHours) {
                    2, 3, 4 -> "часа"
                    else -> "часов"
                }

                "$hours $units назад"
            }
            diff > 22 * HOUR && diff <= 26 * HOUR -> "день назад"
            diff > 26 * HOUR && diff <= 360 * DAY -> {
                val days: Long = diff / DAY;
                val loDays: Int = (days % 10).toInt()
                val units = when (loDays) {
                    2, 3, 4 -> "дня"
                    else -> "дней"
                }

                "$days $units назад"
            }
            else -> "более года назад"
        }
    } else {
        result = when {
            negativeDiff <= 1 * SECOND -> "только что"
            negativeDiff <= 45 * SECOND -> "через несколько секунд"
            negativeDiff <= 75 * SECOND -> "через минуту"
            negativeDiff > 75 * SECOND && negativeDiff <= 45 * MINUTE -> {
                val minutes: Long = negativeDiff / MINUTE;
                val loMin: Int = (minutes % 10).toInt()
                val units = when (loMin) {
                    2, 3, 4 -> "минуты"
                    else -> "минут"
                }

                "через $minutes $units"
            }
            negativeDiff > 45 * MINUTE && negativeDiff <= 75 * MINUTE -> "через час"
            negativeDiff > 75 * MINUTE && negativeDiff <= 22 * HOUR -> {
                val hours: Long = negativeDiff / HOUR;
                val loHours: Int = (hours % 10).toInt()
                val units = when (loHours) {
                    2, 3, 4 -> "часа"
                    else -> "часов"
                }

                "через $hours $units"
            }
            negativeDiff > 22 * HOUR && negativeDiff <= 26 * HOUR -> "через день"
            negativeDiff > 26 * HOUR && negativeDiff <= 360 * DAY -> {
                val days: Long = negativeDiff / DAY;
                val loDays: Int = (days % 10).toInt()
                val units = when (loDays) {
                    2, 3, 4 -> "дня"
                    else -> "дней"
                }

                "через $days $units"
            }
            negativeDiff > 360 -> "более чем через год"
            else -> throw Exception("There is no such period")
        }
    }

    println("$this, $date, $diff, $negativeDiff, $result")

    return result;
}


enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(count: Int): String {
        val loCount: Int = (count% 10).toInt()

        val suffix = when (loCount) {
            1 -> when (this) {
                SECOND -> "секунду"
                MINUTE -> "минуту"
                HOUR -> "час"
                DAY -> "день"
            }
            2, 3, 4 -> when (this) {
                SECOND -> "секунды"
                MINUTE -> "минуты"
                HOUR -> "часа"
                DAY -> "дня"
            }
            else -> when (this) {
                SECOND -> "секунд"
                MINUTE -> "минут"
                HOUR -> "часов"
                DAY -> "дней"
            }
        }

        return "$count $suffix"
    }
}