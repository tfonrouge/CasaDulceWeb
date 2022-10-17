package com.fonrouge.remoteScreen.database

import com.fonrouge.fsLib.mongoDb.CTableDb
import com.fonrouge.fsLib.mongoDb.LookupPipelineBuilder
import com.fonrouge.fsLib.mongoDb.lookupField
import com.fonrouge.remoteScreen.model.CustomerItm
import com.fonrouge.remoteScreen.model.DeliveryOrderHdr
import com.fonrouge.remoteScreen.model.DeliveryOrderItm
import kotlinx.coroutines.runBlocking

val DeliveryOrderHdrDb = object : CTableDb<DeliveryOrderHdr, String>(
    klass = DeliveryOrderHdr::class
) {
    override val lookupFun: (() -> List<LookupPipelineBuilder<DeliveryOrderHdr, *, *>>)? = {
        listOf(
            lookupField(
                cTableDb = CustomerItmDb::class,
                localField = DeliveryOrderHdr::customerItm_id,
                foreignField = CustomerItm::_id,
                resultField = DeliveryOrderHdr::customerItm
            )
        )
    }

    init {
        runBlocking {
            coroutineColl.ensureUniqueIndex(
                DeliveryOrderHdr::customerOrderItm_id
            )
        }
    }
}

//suspend fun getNextNumId(cTableDb: CTableDb<out DocumentWithNumId<String>, String>): Int {
//    return cTableDb.coroutineColl.find()
//        .descendingSort(DocumentWithNumId<*>::numId)
//        .limit(1)
//        .first()?.let { it.numId + 1 } ?: 1
//}

