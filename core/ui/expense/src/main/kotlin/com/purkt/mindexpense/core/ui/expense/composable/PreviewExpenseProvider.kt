package com.purkt.mindexpense.core.ui.expense.composable

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.purkt.mindexpense.core.data.common.toLocalDateTimeOrThrowError
import com.purkt.mindexpense.core.data.expense.model.Expense

internal class PreviewExpenseProvider: PreviewParameterProvider<Expense> {
    override val values: Sequence<Expense>
        get() = sequenceOf(*mockExpenses.toTypedArray())
}

internal val mockExpenseAmountBeforeResized = Expense(
    localId = 2,
    ownerUserId = 1,
    title = "Coffee",
    recipient = "Starbucks",
    note = "Coffee before work",
    amount = 800_000.02,
    imageUrl = "",
    paidAt = "2025-03-04T10:00:00".toLocalDateTimeOrThrowError(),
    createdAt = "2025-03-24T10:00:00".toLocalDateTimeOrThrowError(),
    updatedAt = "2025-03-24T10:00:00".toLocalDateTimeOrThrowError(),
)

internal val mockExpenseBiggestAmountResized = Expense(
    localId = 4,
    ownerUserId = 1,
    title = "Car fuel",
    recipient = "Shell",
    note = "Fill car tank",
    amount = 999_999_999.99,
    imageUrl = "",
    paidAt = "2025-03-26T12:40:00".toLocalDateTimeOrThrowError(),
    createdAt = "2025-03-26T12:40:00".toLocalDateTimeOrThrowError(),
    updatedAt = "2025-03-26T12:40:00".toLocalDateTimeOrThrowError()
)

internal val mockExpenses = listOf(
    Expense(
        localId = 1,
        ownerUserId = 1,
        title = "Dinner",
        recipient = "KFC",
        note = "Dinner with friends",
        amount = 899.0,
        imageUrl = "",
        paidAt = "2025-03-24T00:00:00".toLocalDateTimeOrThrowError(),
        createdAt = "2025-03-24T00:00:00".toLocalDateTimeOrThrowError(),
        updatedAt = "2025-03-24T00:00:00".toLocalDateTimeOrThrowError(),
    ),
    Expense(
        localId = 4,
        ownerUserId = 1,
        title = "Lunch",
        recipient = "Oisho Buffet",
        note = "Eat with family",
        amount = 3_500.75,
        imageUrl = "",
        paidAt = "2025-03-26T12:40:00".toLocalDateTimeOrThrowError(),
        createdAt = "2025-03-26T12:40:00".toLocalDateTimeOrThrowError(),
        updatedAt = "2025-03-26T12:40:00".toLocalDateTimeOrThrowError()
    ),
    mockExpenseAmountBeforeResized,
    mockExpenseBiggestAmountResized,
)