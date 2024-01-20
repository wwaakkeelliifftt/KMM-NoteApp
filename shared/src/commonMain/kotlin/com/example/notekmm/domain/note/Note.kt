package com.example.notekmm.domain.note

import com.example.notekmm.presenation.BabyBlueHex
import com.example.notekmm.presenation.LightGreenHex
import com.example.notekmm.presenation.RedOrangeHex
import com.example.notekmm.presenation.RedPinkHex
import com.example.notekmm.presenation.VioletHex
import kotlinx.datetime.LocalDateTime

data class Note(
    val id: Long?,
    val title: String,
    val content:String,
    val colorHex: Long,
    val created: LocalDateTime
) {
    companion object {
        private val colors = listOf(RedOrangeHex, RedPinkHex, BabyBlueHex, VioletHex, LightGreenHex)

        fun generateRandomColor(): Long = colors.random()
    }
}