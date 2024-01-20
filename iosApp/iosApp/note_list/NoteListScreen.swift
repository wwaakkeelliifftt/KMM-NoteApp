//
//  NoteListScreen.swift
//  iosApp
//
//  Created by mm_wakelift on 10.01.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteListScreen: View {
    // todo: rebuild for inject @nds, but want to check before
    private var noteDataSource: NoteDataSource
    @StateObject var viewModel = NoteListViewModel(noteDataSource: nil)
    
    @State var isNoteSelected = false
    @State var selectedNoteId: Int64? = nil
    
    init(noteDataSource: NoteDataSource) {
        self.noteDataSource = noteDataSource
    }
    
    var body: some View {
        VStack {
            ZStack {
                NavigationLink(
                    destination: NoteDetailScreen(noteDataSource: self.noteDataSource, noteId: selectedNoteId),
                    isActive: $isNoteSelected
                ) {
                    EmptyView()
                }.hidden()
                
                HideableSearchBarTextField<NoteDetailScreen>(
                    onSearchToggled: {
                        viewModel.toggleIsSearchActive()
                    }, destinationProvider: {
                        NoteDetailScreen(noteDataSource: noteDataSource, noteId: nil)
                    }, isSearchActive: viewModel.isSearchActive,
                    searchText: $viewModel.searchText
                )
                .frame(maxWidth: .infinity, minHeight: 40)
                .padding()
                
                if !viewModel.isSearchActive {
                    Text("All Notes")
                        .font(.title2)
                }
            }
            
            List {
                ForEach(viewModel.filteredNotes, id: \.self.id) { note in
                    Button(action: {
                        isNoteSelected = true
                        selectedNoteId = note.id?.int64Value
                    }) {
                        NoteItem(note: note, onDeleteClick: { viewModel.deleteNoteById(id: note.id?.int64Value) })
                    }
                }
            }
            .onAppear {
                viewModel.loadNotes()
            }
            .listStyle(.plain)
            .listRowSeparator(.hidden)
            
        }
        .onAppear {
            viewModel.setNoteDataSource(noteDataSource: noteDataSource)
        }
    }
}

struct NoteListScreen_Previews: PreviewProvider {
    static var previews: some View {
        EmptyView()
    }
}
