package com.minhle.myrecylerview;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views

public class ContactsAdapter extends  RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    // Store a member variable for the contacts
    private List<Contact> mContacts;
    // Pass in the contact array into the constructor
    public ContactsAdapter(List<Contact> contacts) {mContacts = contacts;}
    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_contact, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ContactsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Contact contact = mContacts.get(position);
        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(contact.getName());
        Button button = viewHolder.messageButton;
        button.setText(contact.isOnline() ? "Message" : "Offline");
        button.setEnabled(contact.isOnline());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {return mContacts.size();}

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable for any view that will be set as you render a row
        public TextView nameTextView;
        public Button messageButton;
        private Context context;

        // We also create a constructor that accepts the entire item row and does the view lookups to find each subview
        public ViewHolder(Context context, View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            this.context = context;
            nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
            messageButton = (Button) itemView.findViewById(R.id.message_button);
            // Attach a click listener to the entire row view
            itemView.setOnClickListener(this);
        }

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            // gets item position
            if (position != RecyclerView.NO_POSITION) {
                // Check if an item was deleted, but the user clicked it before the UI removed it
                // We can access the data within the views
                String txt = mContacts.get(position).getName();
                Toast.makeText(context, txt, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
