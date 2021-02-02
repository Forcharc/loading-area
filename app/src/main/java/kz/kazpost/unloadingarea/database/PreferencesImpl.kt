package kz.kazpost.unloadingarea.database

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

const val PREFERENCES_FILE_KEY = "kz.kazpost.unloadingarea.preferences"

class PreferencesImpl @Inject constructor(private val prefs: SharedPreferences) : Preferences {

    override var userLogin: String?
        get() = prefs.getString(KEY_LOGIN, null)
        set(value) = prefs.edit().putString(KEY_LOGIN, value).apply()

    override var userPassword: String?
        get() = prefs.getString(KEY_PASSWORD, null)
        set(value) = prefs.edit().putString(KEY_PASSWORD, value).apply()

    override var userDepartmentId: String?
        get() = prefs.getString(KEY_DEPARTMENT_ID, null)
        set(value) = prefs.edit().putString(KEY_DEPARTMENT_ID, value).apply()

    companion object {
        const val KEY_LOGIN = "key_login"
        const val KEY_PASSWORD = "key_password"
        const val KEY_DEPARTMENT_ID = "key_department_id"
    }
}