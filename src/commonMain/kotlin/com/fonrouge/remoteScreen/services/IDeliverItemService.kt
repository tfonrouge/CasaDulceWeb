package com.fonrouge.remoteScreen.services

import io.kvision.remote.RemoteOption

interface IDeliverItemService {

    suspend fun selectDeliverItem(
        search: String?,
        initial: String?,
        state: String?
    ): List<RemoteOption>
}
