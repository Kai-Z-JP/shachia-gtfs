package jp.kaiz.shachia.gtfs.io.zip

class ByteReader(private val data: ByteArray) {
    private var position: Int = 0

    fun readIntLE(): Int {
        if (position + 4 > data.size) throw IndexOutOfBoundsException("Cannot read Int")
        val value = (data[position].toInt() and 0xFF) or
                ((data[position + 1].toInt() and 0xFF) shl 8) or
                ((data[position + 2].toInt() and 0xFF) shl 16) or
                ((data[position + 3].toInt() and 0xFF) shl 24)
        position += 4
        return value
    }

    fun readShortLE(): Short {
        if (position + 2 > data.size) throw IndexOutOfBoundsException("Cannot read Short")
        val value = (data[position].toInt() and 0xFF) or ((data[position + 1].toInt() and 0xFF) shl 8)
        position += 2
        return value.toShort()
    }

    fun readBytes(length: Int): ByteArray {
        if (position + length > data.size) throw IndexOutOfBoundsException("Cannot read Bytes")
        val bytes = data.copyOfRange(position, position + length)
        position += length
        return bytes
    }

    fun hasMore(): Boolean = position < data.size
}