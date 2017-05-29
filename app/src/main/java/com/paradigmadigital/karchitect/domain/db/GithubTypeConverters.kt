package com.paradigmadigital.karchitect.domain.db

import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.util.StringUtil

class GithubTypeConverters {
    @TypeConverter
    fun stringToIntList(data: String?): List<Int> {
        if (data == null) {
            return emptyList()
        }
        return ArrayList(StringUtil.splitToIntList(data))
    }

    @TypeConverter
    fun intListToString(ints: List<Int>): String? {
        return StringUtil.joinIntoString(ints)
    }
}
