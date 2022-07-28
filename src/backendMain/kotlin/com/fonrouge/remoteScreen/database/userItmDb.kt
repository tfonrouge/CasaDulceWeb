package com.fonrouge.remoteScreen.database

import com.fonrouge.fsLib.mongoDb.mongoDbCollection
import org.litote.kmongo.Id

val userItmDb = mongoDbCollection<UserItm, Id<UserItm>>()
