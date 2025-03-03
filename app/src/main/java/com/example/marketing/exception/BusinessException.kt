package com.example.marketing.exception

open class BusinessException(
    override val message: String
): RuntimeException(message = message)