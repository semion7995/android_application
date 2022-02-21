package com.example.homefragmentexample.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homefragmentexample.R;
import com.example.homefragmentexample.activity.AntiMartinagleActivity;
import com.example.homefragmentexample.activity.CatchingUpStrategyActivity;
import com.example.homefragmentexample.activity.DalembertStrategyActivity;
import com.example.homefragmentexample.activity.FixedInterestStrategyActivity;
import com.example.homefragmentexample.activity.FlatFullInfoActivity;
import com.example.homefragmentexample.activity.MartingaleMethodActivity;
import com.example.homefragmentexample.helpers.FavDB;
import com.example.homefragmentexample.helpers.FavItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {

private Context context;
private List<FavItem> favItemList;
private FavDB favDB;
private DatabaseReference refLike;

public FavAdapter(Context context, List<FavItem> favItemList) {
        this.context = context;
        this.favItemList = favItemList;
        }

@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_item,
        parent, false);
        favDB = new FavDB(context);
        return new ViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.favTextView.setText(favItemList.get(position).getItem_title());
        holder.favImageView.setImageResource(favItemList.get(position).getItem_image());
        }

@Override
public int getItemCount() {
        return favItemList.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView favTextView;
    Button favBtn;
    ImageView favImageView;
    Button btn_full_information_fav_item;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        favTextView = itemView.findViewById(R.id.favTextView);
        favBtn = itemView.findViewById(R.id.favBtn);
        favImageView = itemView.findViewById(R.id.favImageView);

        refLike = FirebaseDatabase.getInstance().getReference().child("likes");

        btn_full_information_fav_item = itemView.findViewById(R.id.btn_full_information_fav_item);
        btn_full_information_fav_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favTextView.getText().toString().equals("Strategy Flat")){
                    Intent intent = new Intent(itemView.getContext(), FlatFullInfoActivity.class);
                    context.startActivity(intent);
                }
                if (favTextView.getText().toString().equals("Catching up strategy")){
                    Intent intent = new Intent(itemView.getContext(), CatchingUpStrategyActivity.class);
                    context.startActivity(intent);
                }
                if (favTextView.getText().toString().equals("Fixed Interest Strategy")){
                    Intent intent = new Intent(itemView.getContext(), FixedInterestStrategyActivity.class);
                    context.startActivity(intent);
                }
                if (favTextView.getText().toString().equals("Martingale Method")){
                    Intent intent = new Intent(itemView.getContext(), MartingaleMethodActivity.class);
                    context.startActivity(intent);
                }
                if (favTextView.getText().toString().equals("Strategy method of anti-Martingale")){
                    Intent intent = new Intent(itemView.getContext(), AntiMartinagleActivity.class);
                    context.startActivity(intent);
                }
                if (favTextView.getText().toString().equals("D'Alembert's strategy")){
                    Intent intent = new Intent(itemView.getContext(), DalembertStrategyActivity.class);
                    context.startActivity(intent);
                }
            }
        });
        /**
         *  add btn_full_info
         */

        //remove from fav after click
        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                final FavItem favItem = favItemList.get(position);
                final DatabaseReference upvotesRefLike = refLike.child(favItemList.get(position).getKey_id());
                favDB.remove_fav(favItem.getKey_id());
                removeItem(position);

                upvotesRefLike.runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull final MutableData mutableData) {
                        try {
                            Integer currentValue = mutableData.getValue(Integer.class);
                            if (currentValue == null) {
                                mutableData.setValue(1);
                            } else {
                                mutableData.setValue(currentValue - 1);
                            }
                        } catch (Exception e) {
                            throw e;
                        }
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                        System.out.println("Transaction completed");
                    }
                });
            }
        });
    }
}

    private void removeItem(int position) {
        favItemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,favItemList.size());
    }
}
