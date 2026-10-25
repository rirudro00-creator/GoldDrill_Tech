package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ChatConversation(
    val id: Int,
    val sellerName: String,
    val lastMessage: String,
    val time: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    val conversations = remember {
        listOf(
            ChatConversation(1, "Ahmed Ould Sidi", "Yes, the CTX 3030 is still available in Nouakchott.", "10:30 AM"),
            ChatConversation(2, "Oumar Ba", "Can you deliver the jackhammer to Akjoujt?", "Yesterday"),
            ChatConversation(3, "Mauritania Mining Supplies", "Send us your location for the wash plant quote.", "2 days ago")
        )
    }

    var activeChat by remember { mutableStateOf<ChatConversation?>(null) }
    var inputMessage by remember { mutableStateOf("") }
    val chatMessages = remember { mutableStateListOf("Hello there, I am interested!.") }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        TopAppBar(
            title = {
                Text(
                    text = activeChat?.sellerName ?: "Messages",
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                if (activeChat != null) {
                    TextButton(onClick = { activeChat = null }) {
                        Text("< Back", color = Color.Black)
                    }
                }
            }
        )

        if (activeChat == null) {
            // List of Conversations
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(conversations) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { activeChat = item }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(Color(0xFF0D9488), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = item.sellerName.take(1),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(item.sellerName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text(item.time, color = Color.Gray, fontSize = 11.sp)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(item.lastMessage, color = Color.Gray, fontSize = 12.sp, maxLines = 1)
                        }
                    }
                    Divider(color = Color(0xFFF1F5F9))
                }
            }
        } else {
            // Individual Chat Thread Window
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.weight(1f).padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(chatMessages) { msg ->
                        Box(
                            modifier = Modifier
                                .align(Alignment.End)
                                .background(Color(0xFF0D9488), RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Text(msg, color = Color.White, fontSize = 13.sp)
                        }
                    }
                }

                // Chat Input Box
                Row(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = inputMessage,
                        onValueChange = { inputMessage = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Write a message...") },
                        shape = RoundedCornerShape(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (inputMessage.isNotBlank()) {
                                chatMessages.add(inputMessage)
                                inputMessage = ""
                            }
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D9488))
                    ) {
                        Text("Send")
                    }
                }
            }
        }
    }
}