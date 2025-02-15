package jp.kaiz.shachia.gtfs.io.zip

import java.util.zip.ZipInputStream
import java.util.zip.ZipEntry as JavaZipEntry

class ZipExtractorJvm : ZipExtractor {
    override suspend fun extract(zipBytes: ByteArray): List<ZipEntry> {
        val zis = ZipInputStream(zipBytes.inputStream())
        val zipEntries = zis.map {
            val fileName = it.name

            ZipEntry(
                fileName = fileName,
                data = zis.readBytes()
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
