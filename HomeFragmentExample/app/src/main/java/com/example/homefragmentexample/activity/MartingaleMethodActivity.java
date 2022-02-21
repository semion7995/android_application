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

public class MartingaleMethodActivity extends AppCompatActivity {

    Strategy strategy;
    FavDB favDB;
    Button favBtn4;
    TextView title_edit_text4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_martinagle_method);

        title_edit_text4 = (TextView)findViewById(R.id.description4);

        title_edit_text4.setMovementMethod(new ScrollingMovementMethod());

        strategy = StrategyRepository.BOX_STRATEGY.get(3);

        favDB = new FavDB(this);


        favBtn4 = (Button) findViewById(R.id.favBtn4);
        if (strategy.getFavStatus().equals("1")){
            favBtn4.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
        }
        favBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeClick(strategy, favBtn4);// likeCountTextView); - parameter view favorites count view
            }
        });
    }

    // like click
    private void likeClick (Strategy strategy, Button favBtn4){

        if (strategy.getFavStatus().equals("0")) {
            strategy.setFavStatus("1");
            favDB.insertIntoTheDatabase(strategy.getTitle(), strategy.getImageResourse(),
                    strategy.getKey_id(), strategy.getFavStatus());
            favBtn4.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
            favBtn4.setSelected(true);

        } else if (strategy.getFavStatus().equals("1")) {
            strategy.setFavStatus("0");
            favDB.remove_fav(strategy.getKey_id());
            favBtn4.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp);
            favBtn4.setSelected(false);
        }
    }

}