package com.codewithfk.quickernotes.data.remote

import com.codewithfk.quickernotes.model.AuthRequest
import com.codewithfk.quickernotes.model.AuthResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

expect val BASE_URL: String

class ApiService(private val client: HttpClient) {

    private val AUTH = "$BASE_URL/auth"
    private val SIGN_IN_ENDPOINT = "$AUTH/login"
    private val SIGN_UP_ENDPOINT = "$AUTH/signup"

    suspend fun signIn(request: AuthRequest): Result<AuthResponse> {
        return try {
            val response = client.post(SIGN_IN_ENDPOINT) {
                setBody(request)
            }
            if (response.status == HttpStatusCode.OK) {
                Result.success(response.body() as AuthResponse)
            } else {
                Result.failure(Exception("Something went wrong"))
            }
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    suspend fun signUp(request: AuthRequest): Result<AuthResponse> {
        return try {
            val response = client.post(SIGN_UP_ENDPOINT) {
                setBody(request)
            }
            if (response.status == HttpStatusCode.Created) {
                Result.success(response.body() as AuthResponse)
            } else {
                Result.failure(Exception("Something went wrong"))
            }
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

}