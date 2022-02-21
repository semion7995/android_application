package com.example.homefragmentexample.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.homefragmentexample.R;
import com.example.homefragmentexample.helpers.FavDB;
import com.example.homefragmentexample.helpers.Strategy;
import com.example.homefragmentexample.repository.StrategyRepository;

public class DalembertStrategyActivity extends AppCompatActivity {

    Strategy strategy;
    FavDB favDB;
    Button favBtn6;
    TextView title_edit_text6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dalembert_strategy);
        title_edit_text6 = (TextView)findViewById(R.id.description6);
        title_edit_text6.setMovementMethod(new ScrollingMovementMethod());
        favDB = new FavDB(this);
        strategy = StrategyRepository.BOX_STRATEGY.get(5);
        favBtn6 = (Button) findViewById(R.id.favBtn6);
        if (strategy.getFavStatus().equals("1")){
            favBtn6.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
        }

        favBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeClick(strategy, favBtn6);// likeCountTextView); - parameter view favorites count view
            }
        });
    }

    // like click
    private void likeClick (Strategy strategy, Button favBtn6){ //, final TextView textLike) {
        if (strategy.getFavStatus().equals("0")) {
            strategy.setFavStatus("1");
            favDB.insertIntoTheDatabase(strategy.getTitle(), strategy.getImageResourse(),
                    strategy.getKey_id(), strategy.getFavStatus());
            favBtn6.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
            favBtn6.setSelected(true);
        } else if (strategy.getFavStatus().equals("1")) {
            strategy.setFavStatus("0");
            favDB.remove_fav(strategy.getKey_id());
            favBtn6.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp);
            favBtn6.setSelected(false);
        }
    }
}