package com.example.notekmm.data.di

import com.example.notekmm.data.local.DatabaseDriverFactory
import com.example.notekmm.data.note.SqlDelightDataSource
import com.example.notekmm.database.NoteDatabase
import com.example.notekmm.domain.note.NoteDataSource

class DatabaseModule {
    private val factory by lazy { DatabaseDriverFactory() }
    val noteDataSource: NoteDataSource by lazy {
        SqlDelightDataSource(NoteDatabase(factory.createDriver()))
    }
}
