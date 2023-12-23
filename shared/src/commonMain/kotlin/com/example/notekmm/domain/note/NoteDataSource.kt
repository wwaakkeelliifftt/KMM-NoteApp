package com.example.notekmm.domain.note

interface NoteDataSource {
    suspend fun insertNote(note: Note)
    suspend fun deleteNoteById(id: Long)
    suspend fun getNoteById(id: Long): Note?
    suspend fun getAllNotes(): List<Note>
}

