import SwiftUI
import Shared

struct SizesView: View {
    @State private var width = ""
    @State private var height = ""
    @State private var depth = ""
    @State private var result: Calculator.PaperSize?
    @FocusState private var focusedField: String?

    private let calculator = Calculator()
    private var calculatedSize: Calculator.PaperSize? {
        calculator.calculateOrNull(width: width, height: height, depth: depth)
    }

    var body: some View {
        Group {
            if let result {
                ResultView(size: result) {
                    self.result = nil
                    width = ""
                    height = ""
                    depth = ""
                }
            } else {
                GeometryReader { geometry in
                    ScrollView {
                        VStack(spacing: 0) {
                            Text("Укажи размер подарка")
                                .font(.system(size: 28, weight: .bold))
                                .multilineTextAlignment(.center)
                                .padding(.bottom, 28)

                            Image("gift-icon")
                                .resizable()
                                .renderingMode(.template)
                                .scaledToFit()
                                .foregroundStyle(.red)
                                .frame(width: 200, height: 200)
                                .accessibilityHidden(true)
                                .padding(.bottom, 28)

                            HStack(spacing: 12) {
                                dimensionField("Высота", id: "height", text: $height)
                                dimensionField("Ширина", id: "width", text: $width)
                                dimensionField("Глубина", id: "depth", text: $depth)
                            }
                            Text("Размеры в сантиметрах")
                                .font(.system(size: 14))
                                .foregroundStyle(.secondary)
                                .padding(.top, 16)

                            Button("Рассчитать") {
                                focusedField = nil
                                result = calculatedSize
                            }
                            .buttonStyle(GiftButtonStyle())
                            .opacity(calculatedSize == nil ? 0.45 : 1)
                            .disabled(calculatedSize == nil)
                            .accessibilityIdentifier("calculate")
                            .padding(.top, 48)
                        }
                        .frame(maxWidth: 348)
                        .padding(24)
                        .frame(maxWidth: .infinity, minHeight: geometry.size.height)
                    }
                    .scrollDismissesKeyboard(.interactively)
                }
                .toolbar {
                    ToolbarItemGroup(placement: .keyboard) {
                        Spacer()
                        Button("Готово") { focusedField = nil }
                    }
                }
            }
        }
        .background(Color(uiColor: .systemBackground))
    }

    private func dimensionField(_ label: String, id: String, text: Binding<String>) -> some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(label)
                .font(.system(size: 14))
                .foregroundStyle(.secondary)
            TextField("0", text: text)
                .font(.system(size: 17))
                .keyboardType(.decimalPad)
                .focused($focusedField, equals: id)
                .padding(.horizontal, 12)
                .frame(height: 52)
                .overlay(RoundedRectangle(cornerRadius: 12).stroke(Color.primary.opacity(0.3)))
                .accessibilityLabel(label)
                .accessibilityIdentifier(id)
        }
        .frame(maxWidth: .infinity)
    }
}

#Preview {
    SizesView()
}
