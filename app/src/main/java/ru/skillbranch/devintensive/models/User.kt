package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User(
    var id: String,
    var firstName: String?,
    var lastName: String?,
    var avatar: String?,
    var rating: Int = 0,
    var respect: Int = 0,
    var lastVisit: Date? = Date(),
    var isOnline: Boolean = false
) {
    constructor(id: String) : this(id, "Jone", "Doe", null)

    companion object Factory {
        var nextId = 0;

        fun makeUser(fullName: String?): User {
            val ( firstName, lastName ) = Utils.parseFullName(fullName)

            return User("${nextId++}", firstName, lastName, null)
        }
    }

    class Builder() {
        private var user: User = makeUser(null)

        fun id(str: String): Builder {
            user.id = str
            return this
        }

        fun firstName(str: String?): Builder {
            user.firstName = str
            return this
        }

        fun lastName(str: String?): Builder {
            user.lastName = str
            return this
        }

        fun avatar(str: String?): Builder {
            user.avatar = str
            return this
        }

        fun rating(num: Int): Builder {
            user.rating = num
            return this
        }

        fun respect(num: Int): Builder {
            user.respect = num
            return this
        }

        fun lastVisit(date: Date?): Builder {
            user.lastVisit = date
            return this
        }

        fun isOnline(bool: Boolean): Builder {
            user.isOnline = bool
            return this
        }

        fun build(): User {
            return user;
        }
    }
}