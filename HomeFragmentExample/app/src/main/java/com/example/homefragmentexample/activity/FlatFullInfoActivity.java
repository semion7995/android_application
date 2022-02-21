package com.example.homefragmentexample.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;

import com.example.homefragmentexample.R;
import com.example.homefragmentexample.helpers.FavDB;
import com.example.homefragmentexample.helpers.Strategy;
import com.example.homefragmentexample.repository.StrategyRepository;

public class FlatFullInfoActivity extends AppCompatActivity {
    Strategy strategy;
    FavDB favDB;
    Button favBtn;
    TextView title_edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_full_info);


        title_edit_text = (TextView)findViewById(R.id.description);

        title_edit_text.setMovementMethod(new ScrollingMovementMethod());

        favDB = new FavDB(this);
        strategy = StrategyRepository.BOX_STRATEGY.get(0);

        favBtn = (Button) findViewById(R.id.favBtn);
        if (strategy.getFavStatus().equals("1")){
            favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
        }

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeClick(strategy, favBtn);// likeCountTextView); - parameter view favorites count view
            }
        });
    }

    // like click
    private void likeClick (Strategy strategy, Button favBtn){ //, final TextView textLike) {
        if (strategy.getFavStatus().equals("0")) {
            strategy.setFavStatus("1");
            favDB.insertIntoTheDatabase(strategy.getTitle(), strategy.getImageResourse(),
                    strategy.getKey_id(), strategy.getFavStatus());
            favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
            favBtn.setSelected(true);
        } else if (strategy.getFavStatus().equals("1")) {
            strategy.setFavStatus("0");
            favDB.remove_fav(strategy.getKey_id());
            favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp);
            favBtn.setSelected(false);

        }
    }
}