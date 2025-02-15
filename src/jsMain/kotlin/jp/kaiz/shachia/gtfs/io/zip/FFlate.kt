@file:JsModule("fflate")
@file:JsNonModule

package jp.kaiz.shachia.gtfs.io.zip

import org.khronos.webgl.Uint8Array


/**
 * fflate の unzipSync 関数
 *
 * @param data 解凍する Zip のバイナリデータ(Uint8Array)
 * @return {"ファイル名": Uint8Array, ...} の連想配列 (JSオブジェクト) が返る
 */
@JsName("unzipSync")
external fun unzipSync(data: Uint8Array, options: dynamic = definedExternally): dynamic

@JsName("unzip")
external fun unzipAsync(
    data: Uint8Array,
    callback: (error: dynamic, unzipped: dynamic) -> Unit
)
