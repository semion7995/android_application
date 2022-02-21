package com.example.homefragmentexample.repository;

import com.example.homefragmentexample.R;
import com.example.homefragmentexample.helpers.Strategy;

import java.util.ArrayList;
public class StrategyRepository {

    public static final ArrayList<Strategy> BOX_STRATEGY = new ArrayList<>();

    static {
        CREATE_BOX_STRATEGY();
    }
    private static  ArrayList<Strategy> CREATE_BOX_STRATEGY(){
        BOX_STRATEGY.add(new Strategy(R.drawable.strategy_flat, "Strategy Flat","0","0"));
        BOX_STRATEGY.add(new Strategy(R.drawable.strategy_two, "Catching up strategy","1","0"));
        BOX_STRATEGY.add(new Strategy(R.drawable.strategy_three, "Fixed Interest Strategy","2","0"));
        BOX_STRATEGY.add(new Strategy(R.drawable.strategy_four, "Martingale Method","3","0"));
        BOX_STRATEGY.add(new Strategy(R.drawable.strategy_five, "Strategy method of anti-Martingale","4","0"));
        BOX_STRATEGY.add(new Strategy(R.drawable.strategy_six, "D'Alembert's strategy","5","0"));
        return BOX_STRATEGY;
    }

}
