package com.example.notekmm.android.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notekmm.android.util.SSH
import com.example.notekmm.domain.note.Note
import com.example.notekmm.domain.note.NoteDataSource
import com.example.notekmm.domain.time.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val noteTitle = savedStateHandle.getStateFlow(SSH.NOTE_TITLE, "")
    private val noteContent = savedStateHandle.getStateFlow(SSH.NOTE_CONTENT, "")
    private val isNoteTitleFocused = savedStateHandle.getStateFlow(SSH.IS_NOTE_TITLE_FOCUSED, false)
    private val isNoteContentFocused = savedStateHandle.getStateFlow(SSH.IS_NOTE_CONTENT_FOCUSED, false)
    private val noteColor = savedStateHandle.getStateFlow(SSH.NOTE_COLOR, Note.generateRandomColor())

    val state = combine(noteTitle, noteContent, isNoteTitleFocused, isNoteContentFocused, noteColor)
    { title, content, isTitleFocused, isContentFocused, noteColor ->
        NoteDetailState(
            noteTitle = title,
            isNoteTitleHintVisible =  title.isEmpty() && !isTitleFocused,
            noteContent = content,
            isNoteContentHintVisible = content.isEmpty() && !isContentFocused,
            noteColor = noteColor
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteDetailState())

    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved get() = _hasNoteBeenSaved.asStateFlow()

    private var existingNoteId: Long? = null

    init {
        savedStateHandle.get<Long>(SSH.EXISTING_NOTE_ID)?.let { noteId ->
            if (noteId == -1L) return@let
            existingNoteId = noteId

            viewModelScope.launch {
                noteDataSource.getNoteById(id = existingNoteId!!)?.let { note ->
                    savedStateHandle[SSH.NOTE_TITLE] = note.title
                    savedStateHandle[SSH.NOTE_CONTENT] = note.content
                    savedStateHandle[SSH.NOTE_COLOR] = note.colorHex
                }
            }
        }
    }

    fun onNoteTitleChanged(text: String) {
        savedStateHandle[SSH.NOTE_TITLE] = text
    }

    fun onNoteContentChanged(text: String) {
        savedStateHandle[SSH.NOTE_CONTENT] = text
    }

    fun isNoteTitleFocusChanged(isFocused: Boolean) {
        savedStateHandle[SSH.IS_NOTE_TITLE_FOCUSED] = isFocused
    }

    fun isNoteContentFocusChanged(isFocused: Boolean) {
        savedStateHandle[SSH.IS_NOTE_CONTENT_FOCUSED] = isFocused
    }

    fun saveNote() {
        viewModelScope.launch {
            noteDataSource.insertNote(
                Note(
                    id = existingNoteId,
                    title = state.value.noteTitle,
                    content = state.value.noteContent,
                    colorHex = state.value.noteColor,
                    created = DateTimeUtil.now()
                )
            )
            _hasNoteBeenSaved.value = true
        }
    }

}