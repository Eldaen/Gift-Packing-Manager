//
//  ResultView.swift
//  Gift Packing Calculator
//
//  Created by Денис Сизов on 25.12.2022.
//

import SwiftUI
import shared

struct ResultView: View {
    let size: Calculator.PaperSize
    
    var body: some View {
        NavigationView {
            VStack {
                Text("Вот нужные размеры")
                    .font(.title)
                    .fontWeight(.bold)
                    .padding()
                    .padding(EdgeInsets(top: 0, leading: 0, bottom: 50, trailing: 0))
                
                VStack {
                    HStack(alignment: .center) {
                        Text("Высота")
                        Spacer()
                        Text(String(format: "%.1f", size.height))
                    }
                    
                    HStack {
                        Text("Ширина")
                        Spacer()
                        Text(String(format: "%.1f", size.width))
                    }
                }
                .padding()
                .frame(width: 200)
                .overlay(
                    RoundedRectangle(cornerRadius: 16)
                        .stroke(.red, lineWidth: 2)
                )
                
                NavigationLink (
                    destination: {
                        SizesView()
                    }, label: {
                        Text("Новый подарок")
                            .padding()
                            .frame(width: 300)
                            .background(.red)
                            .cornerRadius(20)
                            .foregroundColor(.white)
                            .padding(EdgeInsets(top: 50, leading: 0, bottom: 0, trailing: 0))
                    }
                    
                )
            }
        }
        .navigationBarHidden(true)
    }
}

struct ResultView_Previews: PreviewProvider {
    static var previews: some View {
        ResultView(size: Calculator.PaperSize(width: 50, height: 100))
    }
}
