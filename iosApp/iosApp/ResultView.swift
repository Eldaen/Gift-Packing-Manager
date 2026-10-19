import SwiftUI
import Shared

struct ResultView: View {
    let size: Calculator.PaperSize
    let onNewGift: () -> Void

    var body: some View {
        GeometryReader { geometry in
            ScrollView {
                VStack(spacing: 0) {
                    Text("Вот нужные размеры")
                        .font(.system(size: 28, weight: .bold))
                        .multilineTextAlignment(.center)
                        .padding(.bottom, 16)
                    Text("Размер бумаги в сантиметрах")
                        .font(.system(size: 14))
                        .foregroundStyle(.secondary)
                        .padding(.bottom, 34)

                    VStack(spacing: 16) {
                        resultRow("Высота", value: size.height, id: "paperHeight")
                        resultRow("Ширина", value: size.width, id: "paperWidth")
                    }
                    .padding(20)
                    .frame(width: 240)
                    .overlay(RoundedRectangle(cornerRadius: 16).stroke(.red, lineWidth: 2))

                    Button("Новый подарок", action: onNewGift)
                        .buttonStyle(GiftButtonStyle())
                        .accessibilityIdentifier("newGift")
                        .padding(.top, 50)
                }
                .frame(maxWidth: 348)
                .padding(24)
                .frame(maxWidth: .infinity, minHeight: geometry.size.height)
            }
        }
    }

    private func resultRow(_ label: String, value: Double, id: String) -> some View {
        HStack {
            Text(label)
            Spacer()
            Text(value, format: .number.precision(.fractionLength(1)))
                .fontWeight(.semibold)
                .accessibilityIdentifier(id)
        }
        .font(.system(size: 17))
    }
}

#Preview {
    ResultView(size: Calculator.PaperSize(width: 33, height: 26.6667), onNewGift: {})
}
