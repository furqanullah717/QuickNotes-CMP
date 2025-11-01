package com.codewithfk.quickernotes.data.cache

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStoreManager(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val REFRESH_KEY = stringPreferencesKey("refresh")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
    }


    suspend fun storeToken(token: String) {
        storeString(
            TOKEN_KEY, token
        )
    }

    suspend fun storeEmail(email: String) {
        storeString(
            EMAIL_KEY, email
        )
    }

    suspend fun storeRefreshToken(refreshToken: String) {
        storeString(REFRESH_KEY, refreshToken)
    }

    suspend fun storeUserId(userId: String) {
        storeString(USER_ID_KEY, userId)
    }

    suspend fun getToken(): String? {
        return getString(TOKEN_KEY)
    }

    suspend fun getEmail(): String? {
        return getString(EMAIL_KEY)
    }

    suspend fun getRefreshToken(): String? {
        return getString(REFRESH_KEY)
    }

    suspend fun getUserId(): String? {
        return getString(USER_ID_KEY)
    }

    suspend fun getString(key: Preferences.Key<String>): String? {
        return dataStore.data.map { it[key] }.first()
    }


    suspend fun storeString(key: Preferences.Key<String>, value: String) {
        dataStore.updateData { preferences ->
            preferences.toMutablePreferences().apply {
                this[key] = value
            }
        }
    }

}