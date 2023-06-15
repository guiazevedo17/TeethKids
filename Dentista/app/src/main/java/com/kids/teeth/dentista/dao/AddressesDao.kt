package com.kids.teeth.dentista.dao

import com.kids.teeth.dentista.model.Address

object AddressesDao {
    private val addresses = mutableListOf<Address>()

    fun add(address: Address){
        addresses.add(address)
    }

    fun searchAll(): List<Address> {
        return addresses
    }

    fun clearAll() {
        addresses.clear()
    }
}