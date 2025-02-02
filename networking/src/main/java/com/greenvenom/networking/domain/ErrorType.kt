package com.greenvenom.networking.domain

enum class ErrorType {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    SERIALIZATION_ERROR,
    SERVER_ERROR,
    UNKNOWN_ERROR
}