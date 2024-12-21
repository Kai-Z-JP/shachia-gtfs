package jp.kaiz.shachia.gtfs.io.zip

import org.khronos.webgl.Uint8Array
import kotlin.js.Promise
import kotlin.js.RegExp

@JsModule("jszip")
@JsNonModule
external class JSZip {
    fun loadAsync(data: dynamic, options: dynamic = definedExternally): Promise<JSZip>

    fun file(name: String): JSZipObject?
    fun file(name: RegExp): Array<JSZipObject>

    val files: Map<String, JSZipObject>
}

external interface JSZipObject {
    val name: String
    val dir: Boolean
    fun async(`type`: String): Promise<Uint8Array>

}
