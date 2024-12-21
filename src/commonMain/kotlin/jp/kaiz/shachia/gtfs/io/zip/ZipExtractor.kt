package jp.kaiz.shachia.gtfs.io.zip

interface ZipExtractor {
    suspend fun extract(zipBytes: ByteArray): List<ZipEntry>
}

expect fun createZipExtractor(): ZipExtractor