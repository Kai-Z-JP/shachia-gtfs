package jp.kaiz.shachia.gtfs.io.zip;

data class ZipEntry(
    val fileName: String,
    val uncompressedSize: Long,
    val compressedSize: Long,
    val compressionMethod: Int,
    val data: ByteArray
)