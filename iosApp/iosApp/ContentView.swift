import UIKit
import SwiftUI
import ComposeApp

struct ContentView: View {
    @State
    private var componentHolder = ComponentHolder {
        RootComponent(
                componentContext: $0
            )
    }
    
    var body: some View {
        ComposeView(componentHolder.component)
            .onAppear { LifecycleRegistryExtKt.resume(self.componentHolder.lifecycle) }
            .onDisappear { LifecycleRegistryExtKt.stop(self.componentHolder.lifecycle) }
    }
}
