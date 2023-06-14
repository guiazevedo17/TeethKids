package com.kids.teeth.dentista.model

import com.google.firebase.firestore.DocumentId

data class Address(
    var dentistId: String? = null,
    val name: String? = null,
    val code: String? = null,
    val street: String? = null,
    val number: String? = null,
    val complement: String? = null,
    val neighborhood: String? = null,
    val city: String? = null,
    val state: String? = null
    )