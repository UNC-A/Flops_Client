//// Assuming Response.Message has a timestamp property
//data class Message(
//    val message: String,
//    val timestamp: Long
//)
//
//// Combine sentMessages and messageList into a single list
//val combinedMessages = mutableListOf<Message>()
//
//sentMessages.forEach { message ->
//    combinedMessages.add(Message(message.message, message.timestamp))
//}
//
//messageList.forEach { received ->
//    combinedMessages.add(Message(received.message, received.timestamp))
//}
//
//// Sort the combined list by timestamp
//combinedMessages.sortBy { it.timestamp }
//
//// Display the combined and sorted list in your UI
//LazyColumn(
//modifier = Modifier.fillMaxWidth(),
//verticalArrangement = Arrangement.Bottom
//) {
//
//}
