package com.example.health_alarm_v2;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private static List<Medicine> itemList;


    public CustomAdapter(List<Medicine> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Medicine currentItem = itemList.get(position);

        holder.itemText.setText(currentItem.getName());
        holder.id.setText(currentItem.getId());
//        if (currentItem.getPhoto() == "Not available" ) {
//            holder.itemImage.setImageResource(R.drawable.img_3);
//
//        } else {
//            Glide.with(holder.itemView).load(currentItem.getPhoto()).into(holder.itemImage);
//        }
        if (currentItem.getPhoto() != null && !currentItem.getPhoto().equals("Not available")) {
            //Uri imageUri = Uri.parse();
            Uri imageUri = Uri.fromFile(new File(currentItem.getPhoto()));
            Glide.with(holder.itemView)
                    .load(imageUri)
                    //.error(R.drawable.img_3) // Set your default image resource in case of error
                    .into(holder.itemImage);
        } else {
            holder.itemImage.setImageResource(R.drawable.img_3); // Set your default image resource
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemText;
        EditText id;
        RelativeLayout b;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemText = itemView.findViewById(R.id.itemText);
            id=itemView.findViewById(R.id.id);
            b=itemView.findViewById(R.id.button);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Medicine clickedItem = itemList.get(position);

                        // Get the id value
                        String medicineId = clickedItem.getId();

                        // Create an Intent to start the ManageMedicine activity
                        Intent intent = new Intent(itemView.getContext(), ManageMedicine.class);

                        // Pass the id value to the next activity
                        intent.putExtra("MEDICINE_ID", medicineId);

                        // Start the activity
                        itemView.getContext().startActivity(intent);

                    }
                }
            });

        }

    }
}

