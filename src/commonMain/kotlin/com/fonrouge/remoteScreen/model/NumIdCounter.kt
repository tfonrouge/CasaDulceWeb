package com.fonrouge.remoteScreen.model

import com.fonrouge.fsLib.annotations.Collection
import com.fonrouge.fsLib.model.base.BaseModel

@kotlinx.serialization.Serializable
@Collection("docIdCounter")
class NumIdCounter(
    override val _id: String,
    val docId: Int
) : BaseModel<String>
