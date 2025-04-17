package com.example.marketing.exception

open class EssentialVariableNotSetException(
    override val message: String = "Essential Variable not set...",
    open val viewmodelName: String,
): BusinessException(message)