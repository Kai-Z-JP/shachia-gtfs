package jp.kaiz.shachia.gtfs.io.zip

object ZipUtils {
    private val extractor: ZipExtractor = createZipExtractor()

    suspend fun extractZip(zipBytes: ByteArray): List<ZipEntry> = extractor.extract(zipBytes)
}
