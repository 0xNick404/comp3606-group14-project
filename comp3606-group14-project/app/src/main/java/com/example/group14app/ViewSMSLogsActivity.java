package com.example.group14app;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewSMSLogsActivity extends AppCompatActivity {
    private RecyclerView messagesRecyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> savedMessageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sms_logs);

        messagesRecyclerView = findViewById(R.id.messages_recyclerview);
        savedMessageList = FileManager.readMessageList(this);
        Toast.makeText(this, "Reading from file..", Toast.LENGTH_SHORT).show();

        if(savedMessageList == null || savedMessageList.isEmpty()){
            Toast.makeText(this, "No saved messages found.", Toast.LENGTH_SHORT).show();
            return;
        }

        messageAdapter = new MessageAdapter(savedMessageList);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messagesRecyclerView.setAdapter(messageAdapter);
//
        messagesRecyclerView.scrollToPosition(savedMessageList.size() - 1);
    }


    public void onBackClicked(View view) {
        // Finishes the current activity and returns to the previous one
        finish();
    }



}