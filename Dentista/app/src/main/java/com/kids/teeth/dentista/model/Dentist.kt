package com.kids.teeth.dentista.model

import com.google.firebase.firestore.DocumentId

class Dentist(

    @DocumentId
    val id: String? = null,
    val uid: String? = null,

    val nome: String? = null,

    )
