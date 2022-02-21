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

public class AntiMartinagleActivity extends AppCompatActivity {

    Strategy strategy;
    FavDB favDB;
    Button favBtn5;
    TextView title_edit_text5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anti_martinagle);

        title_edit_text5 = (TextView) findViewById(R.id.description5);

        title_edit_text5.setMovementMethod(new ScrollingMovementMethod());

        strategy = StrategyRepository.BOX_STRATEGY.get(4);

        favDB = new FavDB(this);



        favBtn5 = (Button) findViewById(R.id.favBtn5);
        if (strategy.getFavStatus().equals("1")){
            favBtn5.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
        }
            favBtn5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    likeClick(strategy, favBtn5);// likeCountTextView); - parameter view favorites count view
                }
            });
        }

        private void likeClick (Strategy strategy, Button favBtn5){

            if (strategy.getFavStatus().equals("0")) {
                strategy.setFavStatus("1");
                favDB.insertIntoTheDatabase(strategy.getTitle(), strategy.getImageResourse(),
                        strategy.getKey_id(), strategy.getFavStatus());
                favBtn5.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
                favBtn5.setSelected(true);
            } else if (strategy.getFavStatus().equals("1")) {
                strategy.setFavStatus("0");
                favDB.remove_fav(strategy.getKey_id());
                favBtn5.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp);
                favBtn5.setSelected(false);
            }

    }
}
