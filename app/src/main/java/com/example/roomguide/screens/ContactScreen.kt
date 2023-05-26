package com.example.roomguide.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomguide.Contact
import com.example.roomguide.ContactEvent
import com.example.roomguide.ContactsState
import com.example.roomguide.SortType
import com.example.roomguide.components.AddContactDialog

@Composable
fun ContactScreen(
    state: ContactsState,
    onEvent: (ContactEvent) -> Unit
) {
    Scaffold(
        backgroundColor = Color.DarkGray,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(ContactEvent.ShowDialog)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add contact"
                )
            }
        },
    ) { _ ->
        if (state.isAddingContact) {
            AddContactDialog(state = state, onEvent = onEvent)
        }
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SortType.values().map { sortType ->
                        Row(
                            modifier = Modifier.clickable {
                                onEvent(ContactEvent.SortContacts(sortType))
                            },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = state.sortType == sortType,
                                onClick = {
                                    onEvent(ContactEvent.SortContacts(sortType))
                                })
                            Text(text = sortType.name)
                        }
                    }
                }
            }
            items(state.contacts) { contact ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${contact.firstName} ${contact.lastName}",
                            fontSize = 20.sp
                        )
                        Text(text = contact.phoneNumber, fontSize = 12.sp)
                    }
                    IconButton(
                        onClick = {
                            onEvent(ContactEvent.DeleteContact(contact))
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete contact"
                        )
                    }
                }
            }
        }
    }
}