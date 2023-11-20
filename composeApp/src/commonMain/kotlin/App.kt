import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import component.RootComponent
import ui.ResultUi
import ui.SizeUi

@Composable
fun App(root: RootComponent) {
    MaterialTheme {
        Children(
            stack = root.childStack
        ) {
            when (val child = it.instance) {
                is RootComponent.Child.ResultView -> ResultUi(child.component)
                is RootComponent.Child.SizeView -> SizeUi(child.component)
            }
        }
    }
}