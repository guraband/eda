package eda.delivery.repository

import eda.delivery.entity.Address
import org.springframework.data.jpa.repository.JpaRepository

interface AddressRepository : JpaRepository<Address, Long> {
    fun findAllByUserId(userId: Long): List<Address>
}