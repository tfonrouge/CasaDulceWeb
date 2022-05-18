package com.fonrouge.remoteScreen

@kotlinx.serialization.Serializable
class Product(
    var id: Int = 0,
    override var code: String,
    override var description: String,
    override var unit: String,
) : IProduct
