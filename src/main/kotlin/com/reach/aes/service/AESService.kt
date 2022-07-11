package com.reach.aes.service

import com.reach.aes.common.Constants
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.util.encoders.Base64
import org.springframework.stereotype.Service
import java.security.Security
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

@Service
class AESService {

    fun cipher(data: String, secret: String): String {
        Security.addProvider(BouncyCastleProvider())
        data.toByteArray(charset(Constants.UTF8)).also { dataBytes ->
            synchronized(Cipher::class.java) {
                Cipher.getInstance(Constants.PADDING).also { cipher ->
                    cipher.init(
                        Cipher.ENCRYPT_MODE,
                        SecretKeySpec(
                            secret.toByteArray(charset(Constants.UTF8)),
                            Constants.AES
                        )
                    )
                    ByteArray(cipher.getOutputSize(dataBytes.size)).also { cipherData ->
                        cipher.doFinal(cipherData, cipher.update(
                            dataBytes, 0, dataBytes.size,
                            cipherData, 0
                        ))
                        return String(
                            Base64.encode(cipherData)
                        )
                    }
                }
            }
        }
    }

    fun decrypt(data: String, secret: String): String {
        Security.addProvider(BouncyCastleProvider())
        Base64.decode(data.trim { it <= ' ' }.toByteArray(charset(Constants.UTF8))).also { input ->
            synchronized(Cipher::class.java) {
                Cipher.getInstance(Constants.PADDING).also { cipher ->
                    cipher.init(
                        Cipher.DECRYPT_MODE,
                        SecretKeySpec(
                            secret.toByteArray(charset(Constants.UTF8)),
                            Constants.AES
                        )
                    )
                    ByteArray(cipher.getOutputSize(input.size)).also { rawData ->
                        cipher.doFinal(rawData, cipher.update(
                            input, 0, input.size,
                            rawData, 0
                        ))
                        return String(rawData).trim { it <= ' ' }
                    }
                }
            }
        }
    }
}