package component

import util.Calculator
import util.Consumer

class ResultComponent(val paperSize: Calculator.PaperSize, private val output: Consumer<Output>) {

    fun onNewGift() = output(Output.NewGift)

    sealed class Output {
        data object NewGift : Output()
    }
}