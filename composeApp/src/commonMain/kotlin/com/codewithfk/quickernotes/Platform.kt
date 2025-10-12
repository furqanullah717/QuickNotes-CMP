package com.codewithfk.quickernotes

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform