package com.example.group14app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeDashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_dashboard);
    }

    public void onOpenMessengerClicked(View view) {
        Intent intent = new Intent(HomeDashboardActivity.this, MessengerActivity.class);
        startActivity(intent);

        Toast.makeText(this, "Opening Messenger...", Toast.LENGTH_SHORT).show();
    }

    public void onViewLogsClicked(View view) {
        Intent intent = new Intent(HomeDashboardActivity.this, ViewSMSLogsActivity.class);
        startActivity(intent);

        Toast.makeText(this, "Opening Logs...", Toast.LENGTH_SHORT).show();
    }

    public void onExitClicked(View view) {
        finishAffinity();
        Toast.makeText(this, "Exiting App...", Toast.LENGTH_SHORT).show();
    }
}
