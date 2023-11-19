package com.example.sc.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.intellij.lang.annotations.Identifier

@Entity
data class Product(
    val name: String,
    val description: String?,
    val price: Int,
    @PrimaryKey val id: Int? = null
)
