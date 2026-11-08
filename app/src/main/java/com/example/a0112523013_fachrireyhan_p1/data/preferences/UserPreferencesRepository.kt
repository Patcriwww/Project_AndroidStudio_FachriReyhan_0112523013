package com.example.a0112523013_fachrireyhan_p1.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/** Delegate tunggal di top-level: tidak membuat DataStore berulang di Composable. */
private val Context.studentSettingsDataStore by preferencesDataStore(name = "student_settings")

class UserPreferencesRepository(context: Context) {
    private val applicationContext = context.applicationContext

    private object Keys {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val SORT_ORDER = stringPreferencesKey("sort_order")
    }

    val settings: Flow<UserSettings> = applicationContext.studentSettingsDataStore.data
        .catch { error ->
            if (error is IOException) emit(emptyPreferences()) else throw error
        }.map { prefs ->
            UserSettings(
                darkMode = prefs[Keys.DARK_MODE] ?: false,
                sortOrder = StudentSortOrder.entries.firstOrNull {
                    it.name == prefs[Keys.SORT_ORDER]
                } ?: StudentSortOrder.ID_ASC
            )
        }

    suspend fun setDarkMode(enabled: Boolean) {
        applicationContext.studentSettingsDataStore.edit { it[Keys.DARK_MODE] = enabled }
    }

    suspend fun setSortOrder(order: StudentSortOrder) {
        applicationContext.studentSettingsDataStore.edit { it[Keys.SORT_ORDER] = order.name }
    }
}
