package com.oracle.dgnmovil.tabs;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oracle.dgnmovil.app.R;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class FavoritesTabFragment extends Fragment {


    public FavoritesTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites_tab, container, false);
    }


}
