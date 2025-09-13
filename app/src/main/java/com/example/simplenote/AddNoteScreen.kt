package com.example.simplenote

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simplenote.buyingsomethingfilledwithadditionalinfo.BuyingSomethingFilledWithAdditionalInfo
import com.example.simplenote.home0notes.Home0Notes

/**
 * This composable was generated from the UI Package 'home_0_notes'.
 * Generated code; do not edit directly
 */
@Composable
fun AddNoteScreen(deleteNote: () -> Unit) {
    Box() {
        BuyingSomethingFilledWithAdditionalInfo(Modifier.fillMaxSize())
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp)
                .size(64.dp)
                .clickable() { deleteNote() }
        )
    }
}