// In MessengerActivity.java
package com.example.group14app;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MessengerActivity extends AppCompatActivity {

    private RecyclerView messagesRecyclerView;
    private EditText messageInputEditText;
    private FloatingActionButton sendButton;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        // Initialize views
        messagesRecyclerView = findViewById(R.id.messages_recyclerview);
        messageInputEditText = findViewById(R.id.message_input_edittext);
        sendButton = findViewById(R.id.send_button);
        TextView contactNameTextView = findViewById(R.id.contact_name_textview);

        // Set the contact name (you would get this from an Intent in a real app)
        contactNameTextView.setText("Manjaro Security Limited");

        // Setup RecyclerView
        setupRecyclerView();

        // Set click listener for the send button
        sendButton.setOnClickListener(v -> handleSendMessage());
    }

    private void setupRecyclerView() {
        messageList = new ArrayList<>();
        // Add some dummy data for testing
        messageList.add(new Message("Hello!", false)); // Received
        messageList.add(new Message("Hi there! How are you?", true)); // Sent
        messageList.add(new Message("I'm good, thanks! This is a test.", false)); // Received

        messageAdapter = new MessageAdapter(messageList);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messagesRecyclerView.setAdapter(messageAdapter);

        // Scroll to the bottom to show the latest message
        messagesRecyclerView.scrollToPosition(messageList.size() - 1);
    }

    private void handleSendMessage() {
        String messageText = messageInputEditText.getText().toString().trim();
        if (!messageText.isEmpty()) {
            // 1. Create a new message object
            Message newMessage = new Message(messageText, true); // True because the user is sending it

            // 2. Add the message to the list
            messageList.add(newMessage);

            // 3. Notify the adapter that a new item has been added
            messageAdapter.notifyItemInserted(messageList.size() - 1);

            // 4. Scroll the RecyclerView to the bottom to show the new message
            messagesRecyclerView.scrollToPosition(messageList.size() - 1);

            // 5. Clear the input field
            messageInputEditText.setText("");

            // TODO: Add actual SMS sending logic here using SmsManager
            // Example: sendSms(contactPhoneNumber, messageText);
            Toast.makeText(this, "Message Sent (UI only)", Toast.LENGTH_SHORT).show();
        }
    }

    // This method is called by the onClick attribute in the XML
    public void onBackClicked(View view) {
        // Finishes the current activity and returns to the previous one
        finish();
    }
}
