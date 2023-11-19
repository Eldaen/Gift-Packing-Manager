package com.example.giftpackinghelper

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform