package com.juileebhombe.upstoxassignment.data.model

import kotlinx.serialization.Serializable


@Serializable
data class Error(val code: Int? = null, val message: String? = null)
