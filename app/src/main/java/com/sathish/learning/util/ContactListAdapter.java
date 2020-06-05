package com.sathish.learning.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sathish.learning.MainActivity;
import com.sathish.learning.R;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {
    private List<ContactsDetails> contactsDetails;
    private MainActivity activity;

    public ContactListAdapter(List<ContactsDetails> contactsDetails, MainActivity activity) {
        this.contactsDetails = contactsDetails;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactsDetails contactsDetail = contactsDetails.get(position);
        holder.phoneNumber.setText(contactsDetail.getPhoneNumber());
        holder.name.setText(contactsDetail.getName());
    }

    @Override
    public int getItemCount() {
        return contactsDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView phoneNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameview);
            phoneNumber = itemView.findViewById(R.id.phoneView);
        }
    }
}
