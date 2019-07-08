package ru.skillbranch.devintensive.extensions

val multipleSpacecPattern = "\\s+".toRegex()
val htmlTagPattern = "<[^>]+>".toRegex()

fun String.stripHtml(): String {
    return this
        .replace(htmlTagPattern, "")
        .replace("&amp;", "&")
        .replace("&apos;", "'")
        .replace("&quot;", "\"")
        .replace("&lt;", "<")
        .replace("&gt;", ">")
        .replace(multipleSpacecPattern, " ")
}

fun String.truncate(toLength: Int = 16): String {
    var result = this.trimEnd()
    if (toLength == 0 || result.length <= toLength) return result

    result = this.substring(0, toLength).trimEnd()
    result += "..."

    return result
}
