package jp.kaiz.shachia.gtfs.io.zip

import java.util.zip.Inflater
import java.util.zip.InflaterInputStream

actual fun decompressDeflate(data: ByteArray): ByteArray {
    val inflater = Inflater(true)
    val inflaterInputStream = InflaterInputStream(data.inputStream(), inflater)
    return inflaterInputStream.readAllBytes()
}