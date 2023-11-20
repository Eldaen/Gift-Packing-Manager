package component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import util.Calculator
import util.Consumer

class RootComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()

    private val stack = childStack(
        source = navigation,
        initialConfiguration = Configuration.SizeConfig,
        handleBackButton = true,
        childFactory = ::createChild
    )

    val childStack: Value<ChildStack<*, Child>> = stack

    private fun createChild(configuration: Configuration, componentContext: ComponentContext): Child =
        when (configuration) {
            is Configuration.SizeConfig -> Child.SizeView(
                SizeComponent(
                    componentContext = componentContext,
                    output = Consumer(::onSizeComponentOutput)
                )
            )

            is Configuration.ResultConfig -> Child.ResultView(
                ResultComponent(
                    configuration.paperSize,
                    output = Consumer(::onResultComponentOutput)
                )
            )
        }


    private fun onSizeComponentOutput(output: SizeComponent.Output) {
        when (output) {
            is SizeComponent.Output.Calculate -> navigation.replaceCurrent(Configuration.ResultConfig(output.paperSize))
        }
    }
    
    private fun onResultComponentOutput(output: ResultComponent.Output) {
        when (output) {
            is ResultComponent.Output.NewGift -> navigation.replaceCurrent(Configuration.SizeConfig)
        }
    }

    private sealed class Configuration : Parcelable {
        @Parcelize
        data object SizeConfig : Configuration()

        @Parcelize
        data class ResultConfig(val paperSize: Calculator.PaperSize) : Configuration()
    }

    sealed class Child {
        data class SizeView(val component: SizeComponent) : Child()
        data class ResultView(val component: ResultComponent) : Child()
    }
}