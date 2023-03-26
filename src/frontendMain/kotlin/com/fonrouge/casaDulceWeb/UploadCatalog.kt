package com.fonrouge.casaDulceWeb

import io.kvision.form.upload.upload
import io.kvision.modal.Dialog
import io.kvision.modal.ModalSize

class UploadCatalog(catalogType: CatalogType) :
    Dialog<Boolean>(
        caption = "Upload $catalogType Catalog",
        size = ModalSize.XLARGE
    ) {

    init {
        upload(
//            uploadUrl = "/kv/upload/$catalogType",
            multiple = false,
            label = "Upload excel file"
        )
    }
}
