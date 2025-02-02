package com.greenvenom.networking.domain

abstract class NetworkError {
    open val message: String = ""
    open val errorType: ErrorType? = null
}