package co.railgun.spica.ui.component

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillNode
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.platform.LocalAutofillTree
import androidx.compose.ui.platform.debugInspectorInfo

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.autofill(
    autofillTypes: List<AutofillType>,
    boundingBox: Rect? = null,
    onFill: (String) -> Unit,
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "autofill"
        properties["autofillTypes"] = autofillTypes
        properties["boundingBox"] = boundingBox
        properties["onFill"] = onFill
    },
) {
    val autofill = LocalAutofill.current
    val autofillTree = LocalAutofillTree.current
    val autofillNode = AutofillNode(
        autofillTypes = autofillTypes,
        boundingBox = boundingBox,
        onFill = onFill,
    )
    autofillTree += autofillNode
    Modifier
        .onGloballyPositioned { autofillNode.boundingBox = it.boundsInWindow() }
        .onFocusChanged { focusState ->
            autofill?.run {
                when {
                    focusState.isFocused -> autofill.requestAutofillForNode(autofillNode)
                    else -> autofill.cancelAutofillForNode(autofillNode)
                }
            }
        }
}
