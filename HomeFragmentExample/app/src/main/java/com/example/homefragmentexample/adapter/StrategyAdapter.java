package com.example.homefragmentexample.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.homefragmentexample.helpers.Strategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;

public class StrategyAdapter extends RecyclerView.Adapter<StrategyAdapter.ViewHolder>{

//    private ItemClickListener rvClickListener;

    private ArrayList<Strategy> strategies;
    private Context context;
    private FavDB favDB;

    public StrategyAdapter(ArrayList<Strategy> strategies, Context context) {
        this.strategies = strategies;
        this.context = context;
//        this.rvClickListener = rvClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        favDB = new FavDB(context);
        //create table on first
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            createTableOnFirstStart();
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,
                parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StrategyAdapter.ViewHolder holder, int position) {
        final Strategy strategy = strategies.get(position);

        readCursorData(strategy, holder);
        holder.imageView.setImageResource(strategy.getImageResourse());
        holder.titleTextView.setText(strategy.getTitle());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                rvClickListener.onItemClick(strategy);
//            }
//        });
    }



    @Override
    public int getItemCount() {
        return strategies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        ImageView imageView;
        TextView titleTextView;
//        TextView likeCountTextView;
        Button favBtn;
        Button open_full_info;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.strategy_foto);
            titleTextView = itemView.findViewById(R.id.strategy_name);
            favBtn = itemView.findViewById(R.id.favBtn);
//            likeCountTextView = itemView.findViewById(R.id.likeCountTextView);
            open_full_info = itemView.findViewById(R.id.btn_full_information);

            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Strategy strategy = strategies.get(position);
                    likeClick(strategy, favBtn);// likeCountTextView); - parameter view favorites count view
                }
            });


            //add to fav btn
           itemView.findViewById(R.id.btn_full_information).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                  if (titleTextView.getText().toString().equals("Strategy Flat")){
                      Intent intent = new Intent(itemView.getContext(), FlatFullInfoActivity.class);
                      context.startActivity(intent);
                  }
                  if (titleTextView.getText().toString().equals("Catching up strategy")){
                      Intent intent = new Intent(itemView.getContext(), CatchingUpStrategyActivity.class);
                      context.startActivity(intent);
                  }
                  if (titleTextView.getText().toString().equals("Fixed Interest Strategy")){
                      Intent intent = new Intent(itemView.getContext(), FixedInterestStrategyActivity.class);
                      context.startActivity(intent);
                  }
                  if (titleTextView.getText().toString().equals("Martingale Method")){
                      Intent intent = new Intent(itemView.getContext(), MartingaleMethodActivity.class);
                      context.startActivity(intent);
                  }
                  if (titleTextView.getText().toString().equals("Strategy method of anti-Martingale")){
                      Intent intent = new Intent(itemView.getContext(), AntiMartinagleActivity.class);
                      context.startActivity(intent);
                  }
                  if (titleTextView.getText().toString().equals("D'Alembert's strategy")){
                      Intent intent = new Intent(itemView.getContext(), DalembertStrategyActivity.class);
                      context.startActivity(intent);
                  }
               }
           });
//            favBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    Strategy strategy = strategies.get(position);
//                    likeClick(strategy, favBtn, likeCountTextView);
//
//                }
//            });
//            itemView.setOnClickListener(this);
        }
//
//        @Override
//        public void onClick(View view) {
//            rvClickListener.onItemClick(view, getAdapterPosition());
//        }
    }

    private void createTableOnFirstStart() {
        favDB.insertEmpty();

        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    private void readCursorData(Strategy strategy, ViewHolder viewHolder) {
        Cursor cursor = favDB.read_one_data(strategy.getKey_id());
        SQLiteDatabase db = favDB.getReadableDatabase();
        try {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String item_fav_status = cursor.getString(cursor.getColumnIndex(FavDB.FAVORITE_STATUS));
                strategy.setFavStatus(item_fav_status);

                //check fav status
                if (item_fav_status != null && item_fav_status.equals("1")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
//                    viewHolder.likeCountTextView.setText("1");

                } else if (item_fav_status != null && item_fav_status.equals("0")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp);

                }
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }

    }

    // like click
    private void likeClick (Strategy strategy, Button favBtn){ //, final TextView textLike) {
        DatabaseReference refLike = FirebaseDatabase.getInstance().getReference().child("likes");
        final DatabaseReference upvotesRefLike = refLike.child(strategy.getKey_id());

        if (strategy.getFavStatus().equals("0")) {

            strategy.setFavStatus("1");
            favDB.insertIntoTheDatabase(strategy.getTitle(), strategy.getImageResourse(),
                    strategy.getKey_id(), strategy.getFavStatus());

            favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp);

            favBtn.setSelected(true);

            upvotesRefLike.runTransaction(new Transaction.Handler() {
                @NonNull
                @Override
                public Transaction.Result doTransaction(@NonNull final MutableData mutableData) {
                    try {
                        Integer currentValue = mutableData.getValue(Integer.class);
                        if (currentValue == null) {
                            mutableData.setValue(1);
                        } else {
                            mutableData.setValue(currentValue + 1);
//                            new Handler(Looper.getMainLooper()).post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    textLike.setText(mutableData.getValue().toString());
//                                }
//                            });
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



        } else if (strategy.getFavStatus().equals("1")) {
            strategy.setFavStatus("0");
            favDB.remove_fav(strategy.getKey_id());
            favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp);
            favBtn.setSelected(false);

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
//                            new Handler(Looper.getMainLooper()).post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    textLike.setText(mutableData.getValue().toString());
//                                }
//                            });
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
    }
//    public interface ItemClickListener {
//        void onItemClick(Strategy strategy);
//    }
}
