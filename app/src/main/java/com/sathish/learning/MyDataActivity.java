package com.sathish.learning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.sathish.learning.util.ContactViewAdapter;
import com.sathish.learning.util.ContactsDetails;
import com.sathish.learning.util.DBHelper;

import java.util.List;

public class MyDataActivity extends AppCompatActivity {
    private RecyclerView recyclearview;
    private DBHelper dbHelper;
    List<ContactsDetails> contactsDetailsList;
    ContactViewAdapter viewAdapter;
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_data);
        dbHelper = new DBHelper(this);
        recyclearview = (RecyclerView) findViewById(R.id.recyclearview);
        populateData();
        initAdapter();
        initScrollListener();
    }

    private void populateData() {
        contactsDetailsList = dbHelper.getAllCotactsWithblaer(1);
    }

    private void initAdapter() {
        viewAdapter = new ContactViewAdapter(contactsDetailsList, this);
        recyclearview.setLayoutManager(new LinearLayoutManager(this));
        recyclearview.setAdapter(viewAdapter);
    }

    private void initScrollListener() {
        recyclearview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == contactsDetailsList.size() - 1) {
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });


    }

    private void loadMore() {
        contactsDetailsList.add(null);
        recyclearview.post(new Runnable() {
            public void run() {
                viewAdapter.notifyItemInserted(contactsDetailsList.size() - 1);
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                contactsDetailsList.remove(contactsDetailsList.size() - 1);
                int scrollPosition = contactsDetailsList.size();
                viewAdapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 10;
                List<ContactsDetails> contactsDetailsListDetails = dbHelper.getAllCotactsWithblaer(currentSize);
                for (int i = 0; i < contactsDetailsListDetails.size(); i++) {
                    contactsDetailsList.add(contactsDetailsListDetails.get(i));
                }

                Log.i("TAG", "run: " + contactsDetailsList);
                viewAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);


    }
}
