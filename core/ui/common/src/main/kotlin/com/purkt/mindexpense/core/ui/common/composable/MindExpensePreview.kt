package com.purkt.mindexpense.core.ui.common.composable

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(group = "Light", name = "Light-English")
@Preview(group = "Dark", name = "Dark-English", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(group = "Light", name = "Light-Thai", locale = "th")
@Preview(group = "Dark", name = "Dark-Thai", locale = "th", uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class MindExpensePreview

@Preview(group = "Light", name = "Light-English")
@Preview(group = "Dark", name = "Dark-English", uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class MindExpensePreviewOnlyLightDark

@Preview(group = "Preview-FontScaling", name = "1.) FontScale - 50%", fontScale = 0.5f)
@Preview(group = "Preview-FontScaling", name = "2.) FontScale - 75%", fontScale = 0.75f)
@Preview(group = "Preview-FontScaling", name = "3.) FontScale - 100%", fontScale = 1f)
@Preview(group = "Preview-FontScaling", name = "4.) FontScale - 125%", fontScale = 1.25f)
@Preview(group = "Preview-FontScaling", name = "5.) FontScale - 150%", fontScale = 1.5f)
@Preview(group = "Preview-FontScaling", name = "6.) FontScale - 175%", fontScale = 1.75f)
@Preview(group = "Preview-FontScaling", name = "7.) FontScale - 200%", fontScale = 2f)
annotation class MindExpensePreviewAllScales
