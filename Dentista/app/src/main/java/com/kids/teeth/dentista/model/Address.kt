package com.kids.teeth.dentista.model

import com.google.firebase.firestore.DocumentId

data class Address(
    /*
    @DocumentId
    var id: String? = null,
    var uid: String? = null,
     */
    var name: String? = null,
    var code: String? = null,
    var street: String? = null,
    var number: String? = null,
    var complement: String? = null,
    var neighborhood: String? = null,
    var city: String? = null,
    var state: String? = null
    )