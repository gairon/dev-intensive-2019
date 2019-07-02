package ru.skillbranch.devintensive.utils

val TransliterationMap: Map<String, String> = mapOf(
    "а" to "a",
    "б" to "b",
    "в" to "v",
    "г" to "g",
    "д" to "d",
    "е" to "e",
    "ё" to "e",
    "ж" to "zh",
    "з" to "z",
    "и" to "i",
    "й" to "i",
    "к" to "k",
    "л" to "l",
    "м" to "m",
    "н" to "n",
    "о" to "o",
    "п" to "p",
    "р" to "r",
    "с" to "s",
    "т" to "t",
    "у" to "u",
    "ф" to "f",
    "х" to "h",
    "ц" to "c",
    "ч" to "ch",
    "ш" to "sh",
    "щ" to "sh'",
    "ъ" to "",
    "ы" to "i",
    "ь" to "",
    "э" to "e",
    "ю" to "yu",
    "я" to "ya",
    "А" to "A",
    "Б" to "B",
    "В" to "V",
    "Г" to "G",
    "Д" to "D",
    "Е" to "E",
    "Ё" to "E",
    "Ж" to "ZH",
    "З" to "Z",
    "И" to "I",
    "Й" to "I",
    "К" to "K",
    "Л" to "L",
    "М" to "M",
    "Н" to "N",
    "О" to "O",
    "П" to "P",
    "Р" to "R",
    "С" to "S",
    "Т" to "T",
    "У" to "U",
    "Ф" to "F",
    "Х" to "H",
    "Ц" to "C",
    "Ч" to "CH",
    "Ш" to "SH",
    "Щ" to "SH'",
    "Ъ" to "",
    "Ы" to "I",
    "Ь" to "",
    "Э" to "E",
    "Ю" to "YU",
    "Я" to "YA"
)

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val pair: List<String>? = fullName?.trim()?.split(" ")
        val firstName = pair?.getOrNull(0);
        val lastName = pair?.getOrNull(1);

        return (if (firstName != null && firstName.length > 0) firstName else null) to if (lastName != null && lastName.length > 0) lastName else null;
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        var firstName2 = firstName?.trim()
        var lastName2 = lastName?.trim()

        firstName2 = if (firstName2.isNullOrEmpty()) null else firstName2;
        lastName2 = if (lastName2.isNullOrEmpty()) null else lastName2;

        if (firstName2 == null && lastName2 == null) {
            return null;
        }

        var result = "";
        if (firstName2 != null) {
            result += firstName2[0].toUpperCase()
        }

        if (lastName2 != null) {
            result += lastName2[0].toUpperCase()
        }

        return result;
    }

    fun transliteration(payload: String, divider: String = " "): String {
        return payload.replace(" ", divider)
            .split("")
            .map { char -> if (TransliterationMap.containsKey(char)) TransliterationMap.getValue(char) else char }
            .joinToString("")
    }
}