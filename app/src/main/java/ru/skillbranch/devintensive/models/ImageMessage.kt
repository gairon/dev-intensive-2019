package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.extensions.humanizeDiff
import java.util.*

class ImageMessage(
    id: String,
    from: User?,
    chat: Chat,
    isIncoming: Boolean,
    date: Date,
    val payload: String?
) :
    BaseMessage(id, from, chat, isIncoming, date) {

    override fun formatMessage(): String {
        return "id:$id ${from?.firstName} ${if (isIncoming) "получил" else "отправил"}" +
                " изображение \"$payload\" ${date.humanizeDiff()}"
    }
}