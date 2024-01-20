//
//  HideableSearchBarTextField.swift
//  iosApp
//
//  Created by mm_wakelift on 14.01.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct HideableSearchBarTextField<Destination: View>: View {
    
    var onSearchToggled: () -> Void
    var destinationProvider: () -> Destination
    var isSearchActive: Bool
    @Binding var searchText: String
    
    var body: some View {
        HStack {
            TextField("Search...", text: $searchText)
                .textFieldStyle(.roundedBorder)
                .opacity(isSearchActive ? 1 : 0)
            if !isSearchActive {
                Spacer()
            }
            Button(action: onSearchToggled) {
                Image(systemName: isSearchActive ? "xmark" : "magnifyingglass")
                    .foregroundColor(.black)
            }
            NavigationLink(destination: destinationProvider()) {
                Image(systemName: "plus")
                    .foregroundColor(.black)
            }
        }
        
    }
}

struct HideableSearchBarTextField_Previews: PreviewProvider {
    static var previews: some View {
        HideableSearchBarTextField(
            onSearchToggled: { }, destinationProvider: { EmptyView() },
            isSearchActive: true, searchText: .constant("Some text for preview")
        )
    }
}
