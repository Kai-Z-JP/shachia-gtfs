package jp.kaiz.shachia.gtfs.io.zip;

import kotlinx.serialization.Serializable

@Serializable
data class ZipEntry(
    val fileName: String,
    val data: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ZipEntry

        if (fileName != other.fileName) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = fileName.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }
}