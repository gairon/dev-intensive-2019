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