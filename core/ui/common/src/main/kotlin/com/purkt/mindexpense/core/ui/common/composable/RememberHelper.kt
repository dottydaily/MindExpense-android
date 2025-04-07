package com.purkt.mindexpense.core.ui.common.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberListener(listener: () -> Unit) = remember { listener }

@Composable
fun<Param: Any, Result: Any> rememberListenerParams(listener: (Param) -> Result) = remember { listener }