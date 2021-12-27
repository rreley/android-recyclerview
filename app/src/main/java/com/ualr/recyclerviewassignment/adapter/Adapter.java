package com.ualr.recyclerviewassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ualr.recyclerviewassignment.R;
import com.ualr.recyclerviewassignment.model.Inbox;

import java.util.List;

public class Adapter extends RecyclerView.Adapter {

    private List<Inbox> mItems;
    private Context mContext;

    // TODO 03: We define a new attribute using the created interface
    // TODO 02: We define an interface to communicate the Adapter and the Activity/Fragment
    // TODO 04: We define the set method

    public Adapter(Context context, List<Inbox> items) {
        this.mItems = items;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_item, parent, false);
        RecyclerView.ViewHolder holder = new InboxViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Inbox item = mItems.get(position);
        InboxViewHolder viewHolder = (InboxViewHolder) holder;
        String emailName = item.getFrom();
        char initial = emailName.charAt(0);

        if (item.isSelected()) {
            viewHolder.icon.setText("X");
            viewHolder.InboxHolder.setBackgroundColor(mContext.getResources().getColor(R.color.grey_300));
        } else {
            viewHolder.icon.setText(String.format("%c", initial));
            viewHolder.InboxHolder.setBackgroundColor(mContext.getResources().getColor(android.R.color.white));
        }

        viewHolder.from.setText(emailName);
        viewHolder.email.setText(item.getEmail());
        viewHolder.message.setText(item.getMessage());
        viewHolder.date.setText(item.getDate());
    }

    @Override
    public int getItemCount() {
        return this.mItems.size();
    }

    protected void toggleSelection(int position) {
        this.mItems.get(position).toggleSelection();
        notifyItemChanged(position);
    }

    protected void removeItem(int position) {
        if (position < 0 || position >= this.mItems.size()) {
            return;
        }
        mItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mItems.size());
    }

    public void addItem(Inbox item) {
        mItems.add(0, item);
        notifyItemInserted(0);
    }

    protected class InboxViewHolder extends RecyclerView.ViewHolder {

        protected ImageView InboxCircle;
        protected TextView icon, from, email, message, date;
        protected View InboxHolder;

        public InboxViewHolder(@NonNull View v) {
            super(v);
            InboxCircle = v.findViewById(R.id.inboxCircle);
            icon = v.findViewById(R.id.circleText);
            from = v.findViewById(R.id.emailFrom);
            email = v.findViewById(R.id.emailAddress);
            message = v.findViewById(R.id.emailMessage);
            date = v.findViewById(R.id.emailDate);
            InboxHolder = v.findViewById(R.id.InboxHolder);

            InboxHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleSelection(getLayoutPosition());
                }
            });

            InboxCircle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItems.get(getLayoutPosition()).isSelected()) {
                        removeItem(getLayoutPosition());
                    }
                }
            });
        }
    }
}
