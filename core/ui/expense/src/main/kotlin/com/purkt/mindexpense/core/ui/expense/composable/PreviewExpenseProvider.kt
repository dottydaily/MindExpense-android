package com.purkt.mindexpense.core.ui.expense.composable

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.purkt.mindexpense.core.data.common.toLocalDateTimeOrThrowError
import com.purkt.mindexpense.core.data.expense.model.Expense

internal class PreviewExpenseProvider: PreviewParameterProvider<Expense> {
    override val values: Sequence<Expense>
        get() = sequenceOf(*mockExpenses.toTypedArray())
}

internal val mockExpenses = listOf(
    Expense(
        localId = 1,
        ownerUserId = 1,
        title = "Dinner",
        recipient = "KFC",
        note = "Dinner with friends",
        amount = 100.0,
        imageUrl = "",
        paidAt = "2025-03-24T00:00:00".toLocalDateTimeOrThrowError(),
        createdAt = "2025-03-24T00:00:00".toLocalDateTimeOrThrowError(),
        updatedAt = "2025-03-24T00:00:00".toLocalDateTimeOrThrowError(),
    ),
    Expense(
        localId = 2,
        ownerUserId = 1,
        title = "Coffee",
        recipient = "Starbucks",
        note = "Coffee before work",
        amount = 80.0,
        imageUrl = "",
        paidAt = "2025-03-04T10:00:00".toLocalDateTimeOrThrowError(),
        createdAt = "2025-03-24T10:00:00".toLocalDateTimeOrThrowError(),
        updatedAt = "2025-03-24T10:00:00".toLocalDateTimeOrThrowError(),
    ),
    Expense(
        localId = 3,
        ownerUserId = 1,
        title = "Lunch",
        recipient = "McDonald",
        note = "Lunch fast food",
        amount = 150.0,
        imageUrl = "",
        paidAt = "2025-03-02T05:12:00".toLocalDateTimeOrThrowError(),
        createdAt = "2025-03-25T05:12:00".toLocalDateTimeOrThrowError(),
        updatedAt = "2025-03-25T05:12:00".toLocalDateTimeOrThrowError(),
    ),
    Expense(
        localId = 4,
        ownerUserId = 1,
        title = "Car fuel",
        recipient = "Shell",
        note = "Fill car tank",
        amount = 500.0,
        imageUrl = "",
        paidAt = "2025-03-26T12:40:00".toLocalDateTimeOrThrowError(),
        createdAt = "2025-03-26T12:40:00".toLocalDateTimeOrThrowError(),
        updatedAt = "2025-03-26T12:40:00".toLocalDateTimeOrThrowError()
    )
)