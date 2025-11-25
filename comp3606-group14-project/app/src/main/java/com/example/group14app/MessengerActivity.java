// In MessengerActivity.java
package com.example.group14app;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MessengerActivity extends AppCompatActivity {

    private RecyclerView messagesRecyclerView;
    private EditText messageInputEditText;
    private FloatingActionButton sendButton;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;

    String phoneNumber = "8687609064";
    String message = "Placeholder Message";

    // --- Timer fields ---
    private CountDownTimer responseTimer;
    private long timeLeftMs = 15_000;
    private boolean timeUp = false;
    private TextView timerTextView;




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

        timerTextView = findViewById(R.id.timer_textview);
        startResponseTimer();

        // Set click listener for the send button
//        sendButton.setOnClickListener(v -> handleSendMessage());

        // --- Send button logic ---
        sendButton.setOnClickListener(v -> {
            if (timeUp) {
                Toast.makeText(this, "Time expired — cannot send SMS.", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            if (responseTimer != null) responseTimer.cancel();
            handleSendMessage();


        });
    }

    private void setupRecyclerView() {
        messageList = new ArrayList<>();
        // Add some dummy data for testing
        messageList.add(new Message("Attempted Login on Remote System 14", false)); // Received
        messageList.add(new Message("Please provide authentication code:", false)); // Received


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

            // 6. Send SMS message
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
            } else {
                try{
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber, null, newMessage.getText(), null, null);
                    Toast.makeText(this, "Message Sent (via SMS)", Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    e.printStackTrace(); //Prints stack trace to Logcat
                    Toast.makeText(this, "Error: Failed to send message", Toast.LENGTH_SHORT).show();
                }
            }



            FileManager.saveMessageList(this, messageList);
            Toast.makeText(this, "Saving all messages to file..", Toast.LENGTH_SHORT).show();
        }
    }

    // --- Timer method ---
    private void startResponseTimer() {
        timerTextView.setText("15s left");

        responseTimer = new CountDownTimer(timeLeftMs, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                timerTextView.setText(seconds + "s left");
            }

            @Override
            public void onFinish() {
                timeUp = true;
                timerTextView.setText("Time up!");
                messageInputEditText.setTextColor(Color.RED);
            }
        }.start();
    }





    // This method is called by the onClick attribute in the XML
    public void onBackClicked(View view) {
        // Finishes the current activity and returns to the previous one

        FileManager.saveMessageList(this, messageList);
        Toast.makeText(this, "Saving all messages to file..", Toast.LENGTH_SHORT).show();
        finish();
    }


}
