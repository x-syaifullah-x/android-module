@file:JvmName("InputStreamKtx")

package id.xxx.module.data.ktx

import java.io.ByteArrayOutputStream
import java.io.InputStream

fun InputStream.toByteArrayOutputStream(): ByteArrayOutputStream {
    val byteArrayOutputStream = ByteArrayOutputStream()
    val buffer = ByteArray(1024)
    var length: Int
    while (this.read(buffer).also { length = it } != -1) {
        byteArrayOutputStream.write(buffer, 0, length)
    }
    this.close()
    return byteArrayOutputStream
}