package com.example.notekmm.data.note

import com.example.notekmm.database.NoteDatabase
import com.example.notekmm.domain.note.Note
import com.example.notekmm.domain.note.NoteDataSource
import com.example.notekmm.domain.time.DateTimeUtil

class SqlDelightDataSource(val db: NoteDatabase): NoteDataSource {
    private val queries = db.noteQueries

    override suspend fun insertNote(note: Note) {
        queries.insertNote(
            id = note.id,
            title = note.title,
            content = note.content,
            colorHex = note.colorHex,
            created = DateTimeUtil.toEpochMille(note.created)
        )
    }

    override suspend fun deleteNoteById(id: Long) {
        queries.deleteNoteById(id = id)
    }

    override suspend fun getNoteById(id: Long): Note? {
        return queries.getNoteById(id = id)
            .executeAsOneOrNull()
            ?.toNote()
    }

    override suspend fun getAllNotes(): List<Note> {
        return queries.getAllNotes()
            .executeAsList()
            .map { it.toNote() }
    }

}