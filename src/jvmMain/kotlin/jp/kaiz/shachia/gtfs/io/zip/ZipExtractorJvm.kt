package jp.kaiz.shachia.gtfs.io.zip

import java.util.zip.ZipInputStream
import java.util.zip.ZipEntry as JavaZipEntry

class ZipExtractorJvm : ZipExtractor {
    override suspend fun extract(zipBytes: ByteArray): List<ZipEntry> {
        val zis = ZipInputStream(zipBytes.inputStream())
        val zipEntries = zis.map {
            val fileName = it.name
            val compressedSize = it.compressedSize
            val uncompressedSize = it.size
            val compressionMethod = it.method

            val data = zis.readBytes()

            ZipEntry(
                fileName = fileName,
                compressedSize = compressedSize,
                uncompressedSize = uncompressedSize,
                compressionMethod = compressionMethod,
                data = data
            )
        }
        return zipEntries
    }

    fun <R> ZipInputStream.map(transform: (JavaZipEntry) -> R): List<R> {
        val entries = mutableListOf<R>()
        var entry = nextEntry
        while (entry != null) {
            val r = transform(entry)
            entries.add(r)
            entry = nextEntry
        }

        return entries
    }
}

actual fun createZipExtractor(): ZipExtractor = ZipExtractorJvm()
