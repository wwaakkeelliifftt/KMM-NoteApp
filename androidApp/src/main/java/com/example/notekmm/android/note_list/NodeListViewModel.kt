package com.example.notekmm.android.note_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notekmm.android.util.SSH
import com.example.notekmm.domain.note.Note
import com.example.notekmm.domain.note.NoteDataSource
import com.example.notekmm.domain.note.SearchNotes
import com.example.notekmm.domain.time.DateTimeUtil
import com.example.notekmm.presenation.RedPinkHex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NodeListViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val searchNotes = SearchNotes()
    private val notes = savedStateHandle.getStateFlow(SSH.NOTES, emptyList<Note>())
    private val searchText = savedStateHandle.getStateFlow(SSH.SEARCH_TEXT, "")
    private val isSearchActive = savedStateHandle.getStateFlow(SSH.IS_SEARCH_ACTIVE, false)

    val state = combine(notes, searchText, isSearchActive) { notes, searchText, isSearchActive ->
        NoteListState(
            notes = searchNotes.execute(notes, searchText),
            searchText = searchText,
            isSearchActive = isSearchActive
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteListState())

    init {
        // todo: remove demo
        viewModelScope.launch {
            (1..10).forEach {
                noteDataSource.insertNote(
                    Note(
                        id = null,
                        title = "title $it",
                        content = "content $it: blablabla",
                        colorHex = Note.generateRandomColor(),
                        created = DateTimeUtil.now()
                    )
                )
            }
        }
    }

    fun loadNotes() {
        viewModelScope.launch {
            savedStateHandle[SSH.NOTES] = noteDataSource.getAllNotes()
        }
    }

    fun onTextSearchChange(text: String) {
        savedStateHandle[SSH.SEARCH_TEXT] = text
    }

    fun onToggleSearch() {
        savedStateHandle[SSH.IS_SEARCH_ACTIVE] = !isSearchActive.value
        if (!isSearchActive.value) {
            savedStateHandle[SSH.SEARCH_TEXT] = ""
        }
    }

    fun deleteNoteById(id: Long) {
        viewModelScope.launch {
            noteDataSource.deleteNoteById(id = id)
            loadNotes()
        }
    }

}