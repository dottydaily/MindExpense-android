package com.purkt.mindexpense.core.ui.common.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.purkt.mindexpense.core.ui.common.R
import com.purkt.mindexpense.core.ui.common.theme.MindExpenseTheme

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    maxLength: Int? = null,
    labelHint: String = "",
    isError: Boolean,
    errorText: String = "",
    minLines: Int = 1,
    maxLines: Int = Int.MAX_VALUE,
    focusRequester: FocusRequester? = null,
    previousFocusRequester: FocusRequester? = null,
    nextFocusRequester: FocusRequester? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(text = value)
        )
    }

    LaunchedEffect(value) {
        if (textFieldValue.text == value) return@LaunchedEffect

        val shiftLeft = textFieldValue.text.length - textFieldValue.selection.start
        val newSelection = TextRange(start = value.length - shiftLeft, end = value.length - shiftLeft)
        val newTextFieldValue = TextFieldValue(
            text = value,
            selection = newSelection,
        )
        textFieldValue = newTextFieldValue
    }

    Column(
        modifier = modifier.width(IntrinsicSize.Max),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacer_s)),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
        ) {
            AnimatedVisibility(
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.spacer_s)),
                visible = isError && errorText.isNotBlank(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
            ) {
                Text(
                    style = MaterialTheme.typography.labelLarge,
                    text = errorText,
                    color = MaterialTheme.colorScheme.error,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                style = MaterialTheme.typography.labelLarge,
                text = if (maxLength != null) {
                    "${value.length}/$maxLength"
                } else "${value.length}",
                color = if (maxLength != null && value.length > maxLength) {
                    MaterialTheme.colorScheme.error
                } else Color.Unspecified,
            )
        }

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .let { if (focusRequester != null) it.focusRequester(focusRequester) else it }
                .let { currentModifier ->
                    if (previousFocusRequester != null || nextFocusRequester != null) {
                        currentModifier.focusProperties {
                            if (previousFocusRequester != null) previous = previousFocusRequester
                            if (nextFocusRequester != null) next = nextFocusRequester
                        }
                    } else currentModifier
                },
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
                onValueChange.invoke(it.text)
            },
            label = { Text(text = labelHint) },
            isError = isError,
            minLines = minLines,
            maxLines = maxLines,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
        )
    }
}

@MindExpensePreview
@Composable
private fun PreviewInputField(
    @PreviewParameter(PreviewInputFieldProvider::class) info: PreviewInputFieldInfo,
) {
    MindExpenseTheme {
        Surface {
            var value by rememberSaveable { mutableStateOf(info.text) }

            InputField(
                value = value,
                onValueChange = { value = it },
                labelHint = "Title",
                maxLength = info.maxLength,
                isError = info.isError,
                errorText = info.errorText,
                minLines = info.minLines,
                maxLines = info.maxLines,
            )
        }
    }
}

private data class PreviewInputFieldInfo(
    val text: String = "",
    val labelHint: String = "",
    val maxLength: Int? = null,
    val isError: Boolean = false,
    val errorText: String = "",
    val minLines: Int = 1,
    val maxLines: Int = Int.MAX_VALUE,
)

private class PreviewInputFieldProvider: PreviewParameterProvider<PreviewInputFieldInfo> {
    override val values: Sequence<PreviewInputFieldInfo>
        get() = sequenceOf(
            PreviewInputFieldInfo(labelHint = "Title"),
            PreviewInputFieldInfo(text = "Hello", labelHint = "Title", maxLength = 30),
            PreviewInputFieldInfo(text = "Hello", labelHint = "Title", minLines = 4),
            PreviewInputFieldInfo(text = "Hello", labelHint = "Title", maxLength = 30, isError = true, errorText = "Error Text")
        )
}