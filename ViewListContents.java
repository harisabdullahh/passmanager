package com.example.passwordmanager;

import android.app.AppComponentFactory;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewListContents extends AppCompatActivity {

    databaseHelper myDB;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);

        ClipboardManager clipboard = (ClipboardManager)
                getSystemService(Context.CLIPBOARD_SERVICE);

        ListView listView = (ListView) findViewById(R.id.listView);
        myDB = new databaseHelper(this);

        ArrayList<String> theList = new ArrayList<>();
        Cursor data = myDB.getListContents();
        String clickedItem = new String();

        if (data.getCount() == 0) {
            Toast.makeText(ViewListContents.this, "The DB is empty", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                theList.add(data.getString(1));
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
                listView.setAdapter(listAdapter);
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String clickedItem = (String) arg0.getItemAtPosition(arg2);
                ClipData clip = ClipData.newPlainText("Password Copied", clickedItem);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(ViewListContents.this, "Password Copied", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
