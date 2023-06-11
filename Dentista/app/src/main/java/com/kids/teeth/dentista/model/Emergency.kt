package com.kids.teeth.dentista.model

import java.io.Serializable
import java.util.Date

class Emergency(
    val name: String? = null,
    val phone: String? = null,
    val date: String? = null,
    val images: List<String>? = null
    ) : Serializable{
}

