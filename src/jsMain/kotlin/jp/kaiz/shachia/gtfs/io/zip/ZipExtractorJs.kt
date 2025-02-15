package jp.kaiz.shachia.gtfs.io.zip

import `<dynamic>`.get
import org.khronos.webgl.Uint8Array
import org.khronos.webgl.get
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ZipExtractorJs : ZipExtractor {
    override suspend fun extract(zipBytes: ByteArray): List<ZipEntry> {
        val unzipped = unzipAsyncSuspend(zipBytes)
        val keys = js("Object.keys(unzipped)") as Array<String>
        val zipEntries = mutableListOf<ZipEntry>()
        for (fileName in keys) {
            val fileData = unzipped[fileName] as Uint8Array
            val byteArray = ByteArray(fileData.length) { i ->
                fileData[i]
            }

            zipEntries.add(ZipEntry(fileName, byteArray))
        }
        return zipEntries
    }
}

fun Uint8Array.toByteArray(): ByteArray {
    val byteArray = ByteArray(this.length)
    for (i in 0 until this.length) {
        byteArray[i] = this[i].toByte()
    }
    return byteArray
}

fun ByteArray.toUint8Array(): Uint8Array {
    return Uint8Array(this.toTypedArray())
}

suspend fun unzipAsyncSuspend(zipData: ByteArray): dynamic = suspendCoroutine { cont ->
    val zipUint8Array: Uint8Array = zipData.toUint8Array()

    unzipAsync(zipUint8Array) { error, unzipped ->
        if (error != null) {
            cont.resumeWithException(RuntimeException("unzip failed: $error"))
        } else {
            cont.resume(unzipped)
        }
    }
}

actual fun createZipExtractor(): ZipExtractor = ZipExtractorJs()