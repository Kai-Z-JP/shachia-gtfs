package jp.kaiz.shachia.gtfs.io.zip

class ZipExtractorJvm : ZipExtractor {
    override suspend fun extract(zipBytes: ByteArray): List<ZipEntry> {
        val zipReader = ZipReader(zipBytes)
        return zipReader.readEntries()
    }
}

actual fun createZipExtractor(): ZipExtractor = ZipExtractorJvm()
