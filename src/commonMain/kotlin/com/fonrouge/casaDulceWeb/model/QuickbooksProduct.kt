package com.fonrouge.casaDulceWeb.model

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
@JsExport
class QuickbooksProduct(
    override val _id: String,
    override var handle: String,
    override var title: String,
    override var bodyHtml: String,
    override var vendor: String,
    override var tags: String,
    override var published: Boolean,
    override var barcode: String,
    override var cost: Double,
    override var price: Double,
    override var weight: Double,
    override var qty: Int,
    override val linked: Boolean?,
    override val updated: Boolean?
) : IProduct<String>
