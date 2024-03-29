package com.fonrouge.casaDulceWeb.model

import com.fonrouge.fsLib.model.base.BaseDoc
import kotlin.js.JsExport

@JsExport
interface IProduct<T : Any> : BaseDoc<T> {
    var handle: String
    var title: String
    var bodyHtml: String
    var vendor: String
    var tags: String
    var published: Boolean
    var barcode: String
    var cost: Double
    var price: Double
    var weight: Double
    var qty: Int
    val linked: Boolean?
    val updated: Boolean?
}