package com.greenvenom.networking.domain

abstract class Error {
    open val message: String = ""
    open val errorType: ErrorType? = null
}