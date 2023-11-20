import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    private var component: RootComponent

    init(_ component: RootComponent) {
        self.component = component
    }
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(root: component)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}