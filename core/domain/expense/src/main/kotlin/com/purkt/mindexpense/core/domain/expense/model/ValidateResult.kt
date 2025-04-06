package com.purkt.mindexpense.core.domain.expense.model

data class ValidateAmountResult(
    val isError: Boolean = false,
    val validatedAmount: Double,
    val displayAmount: String,
)