package com.example.notekmm.android.note_list

import com.example.notekmm.domain.note.Note

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false
)