package com.fonrouge.casaDulceWeb

import kotlin.js.JsExport

@JsExport
interface IBase<T> {
    val _id: T
}
