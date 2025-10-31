package com.codewithfk.quickernotes.data.remote

import com.codewithfk.quickernotes.model.AuthRequest
import com.codewithfk.quickernotes.model.AuthResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

expect val BASE_URL_EMULATOR: String
const val BASE_URL = "http://localhost:8080"

class ApiService(val client: HttpClient) {
    private val ACTIVE_URL = BASE_URL_EMULATOR
    private val LOGIN_ENDPOINT = "${ACTIVE_URL}/auth/login"
    private val SIGNUP_ENDPOINT = "${ACTIVE_URL}/auth/signup"

    suspend fun login(request: AuthRequest): Result<AuthResponse> {
        return try {
            val response = client.post(urlString = LOGIN_ENDPOINT) {
                setBody(request)
            }
            if (response.status == HttpStatusCode.OK) {
                Result.success(response.body() as AuthResponse)
            } else {
                Result.failure(Exception("Failed to load data"))
            }

        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    suspend fun signup(request: AuthRequest): Result<AuthResponse> {
        return try {
            val response = client.post(urlString = SIGNUP_ENDPOINT) {
                setBody(request)
            }
            if (response.status == HttpStatusCode.Created) {
                Result.success(response.body() as AuthResponse)
            } else {
                Result.failure(Exception("Failed to load data"))
            }

        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}