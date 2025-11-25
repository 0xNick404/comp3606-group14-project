package com.example.group14app;

import android.content.Context;
import android.util.Log;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class FileManager {
    private static final String FILENAME = "messageList.obj"; // Use a different extension for object files
    private static final String TAG = "FileManager";

    public static void saveMessageList(Context context, List<Message> messages){
        try (FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos)){
            for (Object obj : messages) {
                Log.d("DEBUG", "Saving object type: " + obj.getClass().getName());
            }
            oos.writeObject(messages); //Writes the message object
            Log.d(TAG, "Message object saved successfully.");
        } catch (Exception e){
            Log.e(TAG, "Failed to save message object", e);
        }
    }

    public static List<Message> readMessageList(Context context){
        try(FileInputStream fis = context.openFileInput(FILENAME);
        ObjectInputStream ois = new ObjectInputStream(fis)){
            List<Message> messages = (List<Message>) ois.readObject(); //Read the object
            Log.d(TAG, "Message object read successfully.");
            return messages;
        } catch (Exception e) {
           Log.e(TAG, "Failed to read message object", e);
           return null;
        }
    }
}
