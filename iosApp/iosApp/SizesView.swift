//
//  SizesView.swift
//  Gift Packing Calculator
//
//  Created by Денис Сизов on 25.12.2022.
//

import SwiftUI
import shared

struct SizesView: View {
    @State private var width: String = ""
    @State private var height: String = ""
    @State private var depth: String = ""
    
    let calculator = Calculator()
    
    var body: some View {
        NavigationView {
            VStack {
                Text("Укажи размер подарка")
                    .font(.title)
                    .fontWeight(.bold)
                ZStack {
                    Image("gift-icon")
                        .resizable()
                        .frame(width: 200, height: 200)
                        .padding(EdgeInsets(top: 0, leading: 50, bottom: 0, trailing: 0))
                        .foregroundColor(.red)
                    Group {
                        TextField("Высота", text: $height)
                            .foregroundColor(.black)
                            .frame(width: 70, height: 20)
                            .padding()
                            .cornerRadius(15)
                            .border(.black)
                            .padding(EdgeInsets(top: 0, leading: -170, bottom: 0, trailing: 0))
                        
                        TextField("Ширина", text: $width)
                            .foregroundColor(.black)
                            .frame(width: 70, height: 20)
                            .padding()
                            .cornerRadius(15)
                            .border(.black)
                            .padding(EdgeInsets(top: 220, leading: -130, bottom: 0, trailing: 0))
                        
                        TextField("Глубина", text: $depth)
                            .foregroundColor(.black)
                            .frame(width: 70, height: 20)
                            .padding()
                            .cornerRadius(15)
                            .border(.black)
                            .padding(EdgeInsets(top: 220, leading: 230, bottom: 0, trailing: 0))
                    }
                }
                
                NavigationLink (
                    destination: {
                        ResultView(size: calculator.calculate(
                            width: Double(width) ?? 0,
                            height: Double(height) ?? 0,
                            depth: Double(depth) ?? 0
                        ))
                    }, label: {
                        Text("Рассчитать")
                    }
                    
                )
                .padding()
                .frame(width: 300)
                .background(.red)
                .cornerRadius(20)
                .foregroundColor(.white)
                .offset(y: 100)
            }
        }
        .navigationBarHidden(true)
    }
}

struct SizesView_Previews: PreviewProvider {
    static var previews: some View {
        SizesView()
    }
}
