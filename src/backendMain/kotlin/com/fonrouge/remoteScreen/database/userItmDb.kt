package com.fonrouge.remoteScreen.database

import com.fonrouge.fsLib.mongoDb.CTableDb
import org.litote.kmongo.Id

val UserItmDb = object : CTableDb<UserItm, Id<UserItm>>(
    klass = UserItm::class
) {}
