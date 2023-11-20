package component

import com.arkivanov.decompose.ComponentContext
import util.Calculator
import util.Consumer

class SizeComponent(componentContext: ComponentContext, private val output: Consumer<Output>) {

    fun onCalculate(width: Double, height: Double, depth: Double) {
        val paperSize = Calculator.calculate(width, height, depth)
        output(Output.Calculate(paperSize))
    }

    sealed class Output {
        data class Calculate(val paperSize: Calculator.PaperSize) : Output()
    }
}

