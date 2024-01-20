//
//  NoteItem.swift
//  iosApp
//
//  Created by mm_wakelift on 16.01.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteItem: View {

    var note: Note
    var onDeleteClick: () -> Void
    
    
    var body: some View {
        VStack(alignment: .leading) {
            HStack {
                Text(note.title)
                    .font(.title3)
                    .fontWeight(.semibold)
                Spacer()
                Button(action: onDeleteClick) {
                    Image(systemName: "xmark").foregroundColor(.black)
                }
            }.padding(.bottom, -1)
            
            Text(note.content)
                .fontWeight(.light)
                .padding(.bottom, -1)
            HStack {
                Spacer()
                Text(DateTimeUtil().formatNoteDate(dateTime: note.created))
                    .font(.footnote)
                    .fontWeight(.light)
            }
        }
        .padding()
        .background(Color(hex: note.colorHex))
        .clipShape(RoundedRectangle(cornerRadius: 5.0))
        
        
        
    }
}

struct NoteItem_Previews: PreviewProvider {
    static var previews: some View {
        NoteItem(
            note: Note(id: nil, title: "Example note", content: "Note content",
                       colorHex: 0xff3194, created: DateTimeUtil().now()),
            onDeleteClick: { }
        )
    }
}
