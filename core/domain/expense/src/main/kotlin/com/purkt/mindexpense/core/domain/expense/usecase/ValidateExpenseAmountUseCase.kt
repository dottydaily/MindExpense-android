package com.purkt.mindexpense.core.domain.expense.usecase

import com.purkt.mindexpense.core.domain.expense.model.ValidateAmountResult
import com.purkt.mindexpense.core.logging.AppLogger
import java.text.DecimalFormat

interface ValidateExpenseAmountUseCase {
    fun execute(
        input: String,
        fallBackDisplayAmount: String,
        fallbackValidatedAmount: Double,
    ): ValidateAmountResult
}

internal class ValidateExpenseAmountUseCaseImpl : ValidateExpenseAmountUseCase {

    private val numberPattern = Regex("^[1-9]*[0-9]\\.?[0-9]{0,2}$")
    private val minimumAmountExclusive = 0
    private val maximumAmountInclusive = 999_999_999.99

    override fun execute(
        input: String,
        fallBackDisplayAmount: String,
        fallbackValidatedAmount: Double,
    ): ValidateAmountResult {
        return try {
            if (input == "") {
                val validatedAmount = 0.0
                return ValidateAmountResult(
                    isError = validatedAmount.isInvalidRange(),
                    validatedAmount = validatedAmount,
                    displayAmount = input,
                )
            }

            val amountNoComma = input.replace(",", "")

            // Check if valid amount string
            if (!amountNoComma.matches(numberPattern)) {
                throw Exception("Invalid amount format")
            }

            // Format with comma
            val wholeNumberFormatter = DecimalFormat("#,##0")
            if (amountNoComma.contains(".")) {
                val splitValue = amountNoComma.split(".")
                val wholeNumber = splitValue.getOrNull(0)
                    ?.let { if (it.isEmpty()) 0 else it.toInt() }
                    ?: 0
                val fractionNumber = splitValue.getOrNull(1).orEmpty()

                val amountStringToBeParsed = "$wholeNumber.${fractionNumber.ifEmpty { 0 }}"
                val validatedAmount = wholeNumberFormatter.parse(amountStringToBeParsed)
                    ?.toDouble()
                    ?: 0.0

                val formattedWholeNumber = wholeNumberFormatter.format(wholeNumber)
                val displayAmount = "$formattedWholeNumber.$fractionNumber"

                ValidateAmountResult(
                    isError = validatedAmount.isInvalidRange(),
                    validatedAmount = validatedAmount,
                    displayAmount = displayAmount,
                )
            } else {
                val validatedAmount = wholeNumberFormatter.parse(amountNoComma)?.toDouble() ?: 0.0
                val displayAmount = wholeNumberFormatter.format(amountNoComma.toInt())
                ValidateAmountResult(
                    isError = validatedAmount.isInvalidRange(),
                    validatedAmount = validatedAmount,
                    displayAmount = displayAmount,
                )
            }
        } catch (e: Throwable) {
            AppLogger.e(e)
            ValidateAmountResult(
                isError = fallbackValidatedAmount.isInvalidRange(),
                validatedAmount = fallbackValidatedAmount,
                displayAmount = fallBackDisplayAmount,
            )
        }
    }

    private fun Double.isInvalidRange(): Boolean {
        return this <= minimumAmountExclusive || this > maximumAmountInclusive
    }
}