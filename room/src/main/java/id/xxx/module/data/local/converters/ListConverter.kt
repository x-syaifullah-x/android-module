package id.xxx.module.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverter {

    @TypeConverter
    fun toSource(value: String): List<*> {
        val type = object : TypeToken<List<*>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromSource(list: List<*>): String = Gson().toJson(list)
}