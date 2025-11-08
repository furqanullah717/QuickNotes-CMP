package com.codewithfk.quickernotes.notes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.TableInfo
import com.codewithfk.quickernotes.model.Note
import com.codewithfk.quickernotes.utils.DateUtils
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import quickernotes.composeapp.generated.resources.Res
import quickernotes.composeapp.generated.resources.delete
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Composable
fun ListNotesScreen(list: List<Note>, onDelete: (Note) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(list) {
            NoteItem(note = it, onDelete)
        }
    }
}

fun getRandomColor(): Color {
    return Color((155..255).random(), (155..255).random(), (155..255).random())
}

@OptIn(ExperimentalTime::class)
@Composable
fun NoteItem(note: Note, onDelete: (Note) -> Unit) {
    Column(
        modifier = Modifier.padding(8.dp).fillMaxWidth().clip(RoundedCornerShape(8.dp))
            .background(getRandomColor()).padding(16.dp)

    ) {
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Text(text = note.title, fontSize = 22.sp, color = Black, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.size(8.dp))
            Image(
                painterResource(Res.drawable.delete),
                contentDescription = null,
                modifier = Modifier.size(48.dp).clickable {
                    onDelete.invoke(note)
                }.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.size(8.dp))
        Text(text = note.description, fontSize = 16.sp, color = Black)
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = DateUtils.formatDate(Instant.parse(note.updatedAt), "dd MMM, yyyy - hh:mm a"),
            fontSize = 12.sp,
            color = Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )
    }
}

@Composable
@Preview
fun PreviewLisT() {
    ListNotesScreen(
        listOf(
            Note(
                title = "This is the first note that i want to show to the users.",
                description = "Description",
                userId = 1.toString()
            ), Note(
                title = "This is the first note that i want to show to the users.",
                description = "Description",
                userId = 1.toString()
            ), Note(
                title = "This is the first note that i want to show to the users.",
                description = "Description",
                userId = 1.toString()
            )
        ),
        {}
    )

}