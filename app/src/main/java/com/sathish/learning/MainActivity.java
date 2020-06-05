package com.sathish.learning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sathish.learning.util.ContactListAdapter;
import com.sathish.learning.util.ContactsDetails;
import com.sathish.learning.util.DBHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText name;
    private EditText phoneNumber;
    private AppCompatButton addButton;
    private DBHelper dbHelper;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.name);
        phoneNumber = (EditText) findViewById(R.id.phone);
        addButton = (AppCompatButton) findViewById(R.id.addButton);
        dbHelper = new DBHelper(MainActivity.this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclearview);
        displayData();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString() != null && name.getText().toString() != "" && phoneNumber.getText().toString() != null && phoneNumber.getText().toString() != "") {
                    boolean result = dbHelper.saveContacts(name.getText().toString(), phoneNumber.getText().toString());
                    if (result) {
                        Toast.makeText(getApplicationContext(), "Contact saved", Toast.LENGTH_LONG).show();
                        displayData();
                    } else {
                        Toast.makeText(getApplicationContext(), "Contact not saved", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter data", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void displayData() {
        List<ContactsDetails> contactsDetailsList = dbHelper.getAllCotacts();
        Log.i("MainActivity", "userDatas-->" + contactsDetailsList);
        ContactListAdapter adapter = new ContactListAdapter(contactsDetailsList, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
