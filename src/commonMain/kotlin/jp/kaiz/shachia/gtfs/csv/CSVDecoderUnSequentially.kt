package jp.kaiz.shachia.gtfs.csv

import app.softwork.serialization.csv.CSVEncoder
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.StringFormat
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.descriptors.elementDescriptors
import kotlinx.serialization.descriptors.elementNames
import kotlinx.serialization.encoding.AbstractDecoder
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlin.Nothing
import kotlin.jvm.JvmOverloads

@ExperimentalSerializationApi
class CSVDecoder(
    private val header: List<String>,
    private val data: List<List<String>>,
    override val serializersModule: SerializersModule
) : AbstractDecoder() {

    private var index = 0
    private var headerIndex = 0
    private var level = 0
    private var currentRow = 0

    override fun beginStructure(descriptor: SerialDescriptor): CSVDecoder {
        if (descriptor.kind !is StructureKind.LIST) {
            level += 1
        }
        return this
    }

    override fun decodeNotNullMark(): Boolean {
        val value = data[currentRow].getOrNull(headerIndex)
        return !value.isNullOrEmpty()
    }

    override fun endStructure(descriptor: SerialDescriptor) {
        level -= 1
        if (level == 0) {
            currentRow += 1
            index = 0
        }
    }

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        if (this.index == descriptor.elementsCount) return CompositeDecoder.DECODE_DONE
        if (this.currentRow == data.size) return CompositeDecoder.DECODE_DONE

        val serialName = descriptor.getElementName(this.index)
        this.headerIndex = header.indexOf(serialName)
        return this.index
    }

    override fun decodeCollectionSize(descriptor: SerialDescriptor) = data.size

    override fun decodeNull(): Nothing? {
        this.index++
        return null
    }

    override fun decodeBoolean() = decodeString().toBoolean()

    override fun decodeByte() = decodeString().toByte()

    override fun decodeShort() = decodeString().toShort()

    override fun decodeInt() = decodeString().toInt()

    override fun decodeLong() = decodeString().toLong()

    override fun decodeFloat() = decodeString().toFloat()

    override fun decodeDouble() = decodeString().toDouble()

    override fun decodeChar() = decodeString().single()

    override fun decodeString(): String {
        val value = data[currentRow][headerIndex]
        this.index++
        return value
    }

    override fun decodeEnum(enumDescriptor: SerialDescriptor) = enumDescriptor.elementNames.indexOf(decodeString())
}

@ExperimentalSerializationApi
sealed class CSVUnSequentiallyFormat(
    private val separator: String,
    private val lineSeparator: String,
    override val serializersModule: SerializersModule
) : StringFormat {
    private class Custom(
        separator: String,
        lineSeparator: String,
        serializersModule: SerializersModule
    ) : CSVUnSequentiallyFormat(separator, lineSeparator, serializersModule)

    companion object Default : CSVUnSequentiallyFormat(
        separator = ",",
        lineSeparator = "\n",
        serializersModule = EmptySerializersModule()
    ) {
        @JvmOverloads
        operator fun invoke(
            separator: String = ",",
            lineSeparator: String = "\n",
            serializersModule: SerializersModule = EmptySerializersModule()
        ): CSVUnSequentiallyFormat =
            Custom(separator, lineSeparator, serializersModule)
    }

    val bom = "\uFEFF"
    val cr = "\r"

    override fun <T> decodeFromString(deserializer: DeserializationStrategy<T>, string: String): T {
        deserializer.descriptor.checkForLists()
        val lines = string.replace(bom, "").replace(cr, "").split(lineSeparator)
        val data = lines.dropLastWhile { it.isEmpty() }
            .map(String::parseCsvLine)
        return deserializer.deserialize(
            decoder = CSVDecoder(
                header = data[0],
                data = data.drop(1),
                serializersModule = serializersModule
            )
        )
    }

    override fun <T> encodeToString(serializer: SerializationStrategy<T>, value: T): String = buildString {
        serializer.descriptor.checkForLists()
        var afterFirst = false

        serializer.descriptor.flatNames.forEach {
            if (afterFirst) {
                append(separator)
            }
            append(it)
            afterFirst = true
        }

        serializer.serialize(
            encoder = CSVEncoder(this, separator, lineSeparator, serializersModule),
            value = value
        )
    }
}

@ExperimentalSerializationApi
internal val SerialDescriptor.flatNames: Iterator<String>
    get() = iterator {
        names(this@flatNames)
    }

@ExperimentalSerializationApi
private suspend fun SequenceScope<String>.names(s: SerialDescriptor) {
    val count = s.elementsCount
    for (i in 0 until count) {
        val descriptor = s.getElementDescriptor(i)
        if (descriptor.elementsCount == 0 || descriptor.kind == SerialKind.ENUM) {
            yield(s.getElementName(i))
        } else {
            names(descriptor)
        }
    }
}


@OptIn(ExperimentalSerializationApi::class)
internal fun SerialDescriptor.checkForLists() {
    for (descriptor in elementDescriptors) {
        if (descriptor.kind is StructureKind.LIST || descriptor.kind is StructureKind.MAP) {
            error("List or Map are not yet supported")
        }
        descriptor.checkForLists()
    }
}

private fun String.parseCsvLine(delimiter: Char = ',', quoteChar: Char = '"'): List<String> {
    val result = mutableListOf<String>()
    val currentField = StringBuilder()
    var inQuotes = false
    var i = 0

    while (i < this.length) {
        val c = this[i]
        when {
            c == quoteChar -> {
                if (inQuotes && i + 1 < this.length && this[i + 1] == quoteChar) {
                    currentField.append(quoteChar)
                    i++
                } else {
                    inQuotes = !inQuotes
                }
            }

            c == delimiter && !inQuotes -> {
                result.add(currentField.toString())
                currentField.clear()
            }

            else -> {
                currentField.append(c)
            }
        }
        i++
    }
    result.add(currentField.toString())
    return result
}