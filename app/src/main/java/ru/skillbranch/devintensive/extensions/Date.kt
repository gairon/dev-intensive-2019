package ru.skillbranch.devintensive.extensions

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
//    0с - 1с "только что"
//    1с - 45с "несколько секунд назад"
//    45с - 75с "минуту назад"
//    75с - 45мин "N минут назад"
//    45мин - 75мин "час назад"
//    75мин 22ч "N часов назад"
//    22ч - 26ч "день назад"
//    26ч - 360д "N дней назад"
//    >360д "более года назад"

    val diff: Long = date.time - this.time;
    println("$this, $date, $diff")
    val result = when {
        diff <= 1 * SECOND -> "только что"
        diff <= 45 * SECOND -> "несколько секунд назад"
        diff <= 75 * SECOND -> "минуту назад"
        diff > 75 * SECOND && diff <= 45 * MINUTE -> {
            val minutes: Long = diff / MINUTE;
            val loMin: Int = (minutes % 10).toInt()
            val units = when(loMin) {
                2, 3, 4 -> "минуты"
                else -> "минут"
            }
            return "$minutes $units назад"
        }
        diff > 45 * MINUTE && diff <= 75 * MINUTE -> "час назад"
        diff > 75 * MINUTE && diff <= 22 * HOUR -> {
            val hours: Long = diff / HOUR;
            val loHours: Int = (hours % 10).toInt()
            val units = when(loHours) {
                2, 3, 4 -> "часа"
                else -> "часов"
            }
            return "$hours $units назад"
        }
        diff > 22 * HOUR && diff <= 26 * HOUR -> "день назад"
        diff > 26 * HOUR && diff <= 360 * DAY -> {
            val days: Long = diff / DAY;
            val loDays: Int = (days % 10).toInt()
            val units = when(loDays) {
                2, 3, 4 -> "дня"
                else -> "дней"
            }
            return "$days $units назад"
        }
        else -> "более года назад"
    }

    return result;
}


enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY,
}