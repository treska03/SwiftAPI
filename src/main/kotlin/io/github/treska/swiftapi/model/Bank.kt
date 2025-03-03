package io.github.treska.swiftapi.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
data class Bank(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0,
    val address: String,
    val bankName: String,
    @ManyToOne val country: Country,
    val isHeadquarter: Boolean,
    val swiftCode: String
)
