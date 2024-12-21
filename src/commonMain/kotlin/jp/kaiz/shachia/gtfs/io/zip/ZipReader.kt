package jp.kaiz.shachia.gtfs.io.zip

class ZipReader(private val bytes: ByteArray) {
    fun readEntries(): List<ZipEntry> {
        val reader = ByteReader(bytes)
        val entries = mutableListOf<ZipEntry>()

        while (reader.hasMore()) {
            val signature = reader.readIntLE()
            if (signature != 0x04034b50) {
                break
            }

            val versionNeeded = reader.readShortLE()
            val flags = reader.readShortLE()
            val compressionMethod = reader.readShortLE().toInt()
            val lastModTime = reader.readShortLE()
            val lastModDate = reader.readShortLE()
            val crc32 = reader.readIntLE()
            val compressedSize = reader.readIntLE().toLong()
            val uncompressedSize = reader.readIntLE().toLong()
            val fileNameLength = reader.readShortLE().toInt()
            val extraFieldLength = reader.readShortLE().toInt()

            val fileNameBytes = reader.readBytes(fileNameLength)
            val extraField = reader.readBytes(extraFieldLength)
            val fileName = fileNameBytes.decodeToString()

            val data = reader.readBytes(compressedSize.toInt())

            val decompressedData = decompressData(compressionMethod, data)

            entries.add(
                ZipEntry(
                    fileName = fileName,
                    uncompressedSize = uncompressedSize,
                    compressedSize = compressedSize,
                    compressionMethod = compressionMethod,
                    data = decompressedData
                )
            )
        }

        return entries
    }

    private fun decompressData(compressionMethod: Int, data: ByteArray) = when (compressionMethod) {
        0 -> data
        8 -> decompressDeflate(data) // Deflate
        else -> throw UnsupportedOperationException("Unsupported compression method: $compressionMethod")
    }
}

expect fun decompressDeflate(data: ByteArray): ByteArray