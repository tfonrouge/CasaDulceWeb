package com.fonrouge.remoteScreen.database

import com.fonrouge.fsLib.mongoDb.CTableDb
import com.fonrouge.fsLib.mongoDb.LookupBuilder
import com.fonrouge.remoteScreen.model.CustomerItm
import com.fonrouge.remoteScreen.model.CustomerOrderHdr
import com.fonrouge.remoteScreen.model.DocumentWithNumId
import kotlinx.coroutines.runBlocking

val CustomerOrderHdrDb = object : CTableDb<CustomerOrderHdr, String>(
    klass = CustomerOrderHdr::class
) {
    override val lookupFun: (() -> List<LookupBuilder<CustomerOrderHdr, *, *, *>>) = {
        listOf(
            LookupBuilder(
                cTableDb = CustomerItmDb::class,
                localField = CustomerOrderHdr::customerItm_id,
                foreignField = CustomerItm::_id,
                resultProperty = CustomerOrderHdr::customerItm
            )
        )
    }

    init {
        runBlocking {
            coroutineColl.ensureUniqueIndex(
                CustomerOrderHdr::numId
            )
        }
    }
}

suspend fun getNextNumId(cTableDb: CTableDb<out DocumentWithNumId<String>, String>): Int {
    return cTableDb.coroutineColl.find()
        .descendingSort(DocumentWithNumId<*>::numId)
        .limit(1)
        .first()?.let { it.numId + 1 } ?: 1
}
