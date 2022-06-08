package com.fonrouge.remoteScreen

import kotlin.js.JsExport

@JsExport
interface IBase<T> {
    val _id: T
}
