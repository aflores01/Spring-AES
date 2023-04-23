package com.reach.aes.controller

import com.reach.aes.common.Environment
import com.reach.aes.service.AESService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping( value = ["/aes"])
class CryptController {

    @Autowired
    private lateinit var aesService: AESService

    @PostMapping(value = ["/crypt"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun cryptJson(
        @RequestBody body: String
    ): String {
        return aesService.cipher(body, Environment.secretKey)
    }

    @PostMapping(value = ["/decrypt"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun decryptJson(
        @RequestBody body: String
    ): String {
        return aesService.decrypt(body, Environment.secretKey)
    }
}