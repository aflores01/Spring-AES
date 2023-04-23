package com.reach.aes.common

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration


@Configuration("Environment")
class Environment {

    companion object {
        lateinit var secretKey: String
    }

    @Value("\${aes.secret.key}")
    fun setSecretKey(key: String) {
        secretKey = key
    }
}