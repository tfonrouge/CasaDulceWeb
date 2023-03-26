package com.fonrouge.casaDulceWeb.model

import com.fonrouge.fsLib.model.base.BaseDoc
import kotlin.js.JsExport

@JsExport
interface DocumentWithNumId<T : Any> : BaseDoc<T> {
    val numId: Int
}
