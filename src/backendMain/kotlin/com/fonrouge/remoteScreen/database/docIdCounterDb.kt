package com.fonrouge.remoteScreen.database

import com.fonrouge.fsLib.mongoDb.CTableDb
import com.fonrouge.fsLib.mongoDb.mongoDbCollection
import com.fonrouge.remoteScreen.model.DocumentWithNumId
import com.fonrouge.remoteScreen.model.NumIdCounter
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.UpdateOptions
import org.litote.kmongo.eq
import org.litote.kmongo.inc
import org.litote.kmongo.set
import org.litote.kmongo.setTo

val numIdCounterDb = mongoDbCollection<NumIdCounter>()

suspend fun getNextNumId(cTableDb: CTableDb<out DocumentWithNumId<*>>): Int {
    val _id = cTableDb.collection.namespace.collectionName
    val numIdCounter = numIdCounterDb.collection.findOneAndUpdate(
        filter = NumIdCounter::_id eq _id,
        update = inc(NumIdCounter::docId, 1),
        options = FindOneAndUpdateOptions().upsert(false)
    )
    val numId: Int = numIdCounter?.docId?.let { it + 1 } ?: (cTableDb.collection.find()
        .descendingSort(DocumentWithNumId<*>::numId)
        .limit(1)
        .first()?.let { it.numId + 1 } ?: 1)
    if (numIdCounter == null) {
        numIdCounterDb.collection.updateOne(
            filter = NumIdCounter::_id eq _id,
            update = set(NumIdCounter::docId setTo numId),
            options = UpdateOptions().upsert(true)
        )
    }
    return numId
}
