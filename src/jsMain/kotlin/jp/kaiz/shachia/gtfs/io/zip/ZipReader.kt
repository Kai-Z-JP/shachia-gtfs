package jp.kaiz.shachia.gtfs.io.zip

import org.khronos.webgl.Uint8Array
import org.khronos.webgl.get


actual fun decompressDeflate(data: ByteArray): ByteArray {
    val uint8Array = Uint8Array(
        data.toTypedArray()
    )
    val inflated = try {
        val output = Pako.inflate(uint8Array, js("{ raw: true }"))
        output.unsafeCast<Uint8Array>()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
    return inflated?.asByteArray() ?: byteArrayOf()
}

// ヘルパー関数: Uint8ArrayからByteArrayへの変換
fun Uint8Array.asByteArray(): ByteArray {
    val byteArray = ByteArray(this.length)
    for (i in 0 until this.length) {
        byteArray[i] = this[i].toByte()
    }
    return byteArray
}

@JsModule("pako")
@JsNonModule
external object Pako {
    fun inflate(input: dynamic, options: dynamic = definedExternally): dynamic
}