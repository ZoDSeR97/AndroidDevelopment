package com.minhle.myrecylerview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Contact> contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Lookup the recyclerview in activity layout
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvContacts.addItemDecoration(itemDecoration);
        // Initialize contacts
        contacts = Contact.createContactsList(20);
        // Create adapter passing in the sample user data
        ContactsAdapter adapter = new ContactsAdapter(contacts);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));

        Button addContact = findViewById(R.id.addContact);
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize contacts
                contacts.addAll(Contact.createContactsList(1));
                // Create adapter passing in the sample user data
                ContactsAdapter adapter = new ContactsAdapter(contacts);
                // Attach the adapter to the recyclerview to populate items
                rvContacts.setAdapter(adapter);
                // Set layout manager to position the items
                rvContacts.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
        });

        // That's all!
    }
}