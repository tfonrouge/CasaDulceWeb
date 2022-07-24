package com.fonrouge.remoteScreen.model

import com.fonrouge.fsLib.model.base.BaseModel

@kotlinx.serialization.Serializable
@com.fonrouge.fsLib.Collection("docIdCounter")
class NumIdCounter(
    override val _id: String,
    val docId: Int
) : BaseModel<String>
