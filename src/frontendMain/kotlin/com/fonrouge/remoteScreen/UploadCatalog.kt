package com.fonrouge.remoteScreen

import io.kvision.form.upload.upload
import io.kvision.modal.Modal
import io.kvision.modal.ModalSize

class UploadCatalog : Modal("Upload Product Catalog", size = ModalSize.XLARGE) {

    init {
        upload(
            uploadUrl = "http://localhost:8000/${apiServicesUrl}/$uploadProductCatalog",
            multiple = false,
            label = "Upload excel file"
        ) {

        }
    }
}
