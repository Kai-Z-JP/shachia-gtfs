package jp.kaiz.shachia.gtfs.io.zip

import kotlinx.coroutines.await
import org.khronos.webgl.Uint8Array
import org.khronos.webgl.get

class ZipExtractorJs : ZipExtractor {
    override suspend fun extract(zipBytes: ByteArray): List<ZipEntry> {
        val jsZip = JSZip()
        val loadedZip = jsZip.loadAsync(zipBytes.toTypedArray()).await()
        val entries = mutableListOf<ZipEntry>()

        val files = loadedZip.files
        val fileNames = js("Object").keys(files) as Array<String>

        for (fileName in fileNames) {
            val file = loadedZip.file(fileName)
            if (file != null && !file.dir) {
                val data: Uint8Array = file.async("uint8array").await()
                entries.add(
                    ZipEntry(
                        fileName = fileName,
                        uncompressedSize = data.length.toLong(),
                        compressedSize = data.length.toLong(),
                        compressionMethod = 8,
                        data = data.toByteArray()
                    )
                )
            }
        }

        return entries
    }
}

fun Uint8Array.toByteArray(): ByteArray {
    val byteArray = ByteArray(this.length)
    for (i in 0 until this.length) {
        byteArray[i] = this[i].toByte()
    }
    return byteArray
}


actual fun createZipExtractor(): ZipExtractor = ZipExtractorJs()