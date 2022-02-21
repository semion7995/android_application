package com.example.homefragmentexample.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homefragmentexample.R;
import com.example.homefragmentexample.adapter.StrategyAdapter;
import com.example.homefragmentexample.helpers.Strategy;
import com.example.homefragmentexample.repository.StrategyRepository;

import java.util.ArrayList;

public class HomeFragment extends Fragment  {
//    private ArrayList<Strategy> strategies = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_fragment_home, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(new StrategyAdapter(strategies, getActivity()));
        recyclerView.setAdapter(new StrategyAdapter(StrategyRepository.BOX_STRATEGY,getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        strategies = StrategyRepository.CREATE_BOX_STRATEGY();


        return root;
    }

//    @Override
//    public void onItemClick(Strategy strategy) {
//        Fragment fragment = DetailFragment.newInstance(strategy.getTitle());
//
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.nav_host_fragment, fragment, "detail_fragment");
//        transaction.addToBackStack(null);
//        transaction.commit();
//
//    }
}
