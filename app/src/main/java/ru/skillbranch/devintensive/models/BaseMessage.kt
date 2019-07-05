package ru.skillbranch.devintensive.models

import java.util.*

abstract class BaseMessage(
    val id: String,
    val from: User?,
    val chat: Chat,
    val isIncoming: Boolean = false,
    val date: Date = Date()
) {
    abstract fun formatMessage(): String

    companion object AbstractFactory {
        var nextId = 0;

        fun makeMessage(fromUser: User, fromChat: Chat, date: Date = Date(), type: String, payload: String, isIncoming: Boolean = false): BaseMessage {
            val nextIdStr = "${nextId++}";
            return when (type) {
                "text" -> TextMessage(nextIdStr, fromUser, fromChat, isIncoming, date, payload)
                else -> ImageMessage(nextIdStr, fromUser, fromChat, isIncoming, date, payload)
            }
        }
    }
}