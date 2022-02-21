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

public class CatchingUpStrategyActivity extends AppCompatActivity {


    Strategy strategy;
    FavDB favDB;
    Button favBtn2;


    TextView title_edit_text2;
       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catching_up_strategy);


        title_edit_text2 = (TextView)findViewById(R.id.description2);

        title_edit_text2.setMovementMethod(new ScrollingMovementMethod());

        strategy = StrategyRepository.BOX_STRATEGY.get(1);

        favDB = new FavDB(this);



        favBtn2 = (Button) findViewById(R.id.favBtn2);

        if (strategy.getFavStatus().equals("1")){
            favBtn2.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
        }
        favBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeClick(strategy, favBtn2);// likeCountTextView); - parameter view favorites count view
            }
        });
    }

    // like click
    private void likeClick (Strategy strategy, Button favBtn2){ //, final TextView textLike) {
        if (strategy.getFavStatus().equals("0")) {
            strategy.setFavStatus("1");
            favDB.insertIntoTheDatabase(strategy.getTitle(), strategy.getImageResourse(),
                    strategy.getKey_id(), strategy.getFavStatus());
            favBtn2.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
            favBtn2.setSelected(true);
        } else if (strategy.getFavStatus().equals("1")) {
            strategy.setFavStatus("0");
            favDB.remove_fav(strategy.getKey_id());
            favBtn2.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp);
            favBtn2.setSelected(false);
        }
    }
}