package aykhan.task.exchange.manager

import android.content.Context
import android.content.SharedPreferences
import aykhan.task.exchange.R
import aykhan.task.exchange.utils.SingletonHolder

class PreferencesManager private constructor(val context: Context) {

    companion object : SingletonHolder<PreferencesManager, Context>(::PreferencesManager)

    private var sharedPreferences: SharedPreferences? = null

    init {
        sharedPreferences = context.getSharedPreferences(
            context.resources.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
    }

    fun setStringElement(key: String, value: String) {
        with(sharedPreferences?.edit()) {
            this?.putString(key, value)
            this?.apply()
        }
    }

    fun getStringElement(key: String, defaultValue: String): String? {
        return sharedPreferences?.getString(key, defaultValue)
    }

}