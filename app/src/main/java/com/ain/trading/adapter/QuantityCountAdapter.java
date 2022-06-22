package com.ain.trading.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ain.trading.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class QuantityCountAdapter extends RecyclerView.Adapter<QuantityCountAdapter.PickerViewHolder> {
    private Context context;
    private int highlghtPosition = -1;
    private boolean scrollInitiated = false;
    private List<String> quantityCountArrayList;
    BottomSheetDialog bottomSheetDialog;
    String selected_listValue;

    public QuantityCountAdapter(Context context, List<String> quantityCountArrayList, BottomSheetDialog bottomSheetDialog) {
        this.context = context;
        this.quantityCountArrayList = quantityCountArrayList;
        this.bottomSheetDialog = bottomSheetDialog;
    }

    @Override
    public PickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.quantity_item, parent, false);
        return new PickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PickerViewHolder holder, final int position) {
        holder.countTextView.setText(String.valueOf(quantityCountArrayList.get(position)));
        System.out.println("quantityCountArrayList.get(position).toString() = " + quantityCountArrayList.get(position));

        if(highlghtPosition==position) {
            //holder.itemView.setBackgroundColor(Color.parseColor("#000000"));
            holder.countTextView.setTextColor(Color.parseColor("#F44336"));
        }
        else
        {
            holder.countTextView.setTextColor(Color.parseColor("#000000"));
        }
        holder.countTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_listValue = holder.countTextView.getText().toString();
                highlghtPosition=position;
                notifyDataSetChanged();


            }
        });



    }

    @Override
    public int getItemCount() {
        return quantityCountArrayList.size();
    }

    class PickerViewHolder extends RecyclerView.ViewHolder {
        private TextView countTextView;

        PickerViewHolder(View itemView) {
            super(itemView);
            countTextView = itemView.findViewById(R.id.countTextView);
        }
    }

    public void updateRecyclerView(int highlightPosition) {
        this.highlghtPosition = highlightPosition;
        this.scrollInitiated = true;
        notifyDataSetChanged();
    }

    public String getQuantity() {
        return selected_listValue;
    }
}

