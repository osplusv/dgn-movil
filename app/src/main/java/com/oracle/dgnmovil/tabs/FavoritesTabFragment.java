package com.oracle.dgnmovil.tabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.oracle.dgnmovil.app.R;
import com.oracle.dgnmovil.app.adapter.ItemAdapter;
import com.oracle.dgnmovil.app.model.Item;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class FavoritesTabFragment extends Fragment {
    private int mVal;
    private String[] mData;

    public FavoritesTabFragment() {
    }

    public static FavoritesTabFragment newInstance(int num) {
        FavoritesTabFragment f = new FavoritesTabFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVal = getArguments().getInt("num");
        if (mVal == 1) {
            mData = new String[] {
                    "Fav 1",
                    "Fav 2",
                    "Fav 3",
                    "Fav 4",
                    "Fav 5",
                    "Fav 6",
                    "Fav 7",
                    "Fav 4",
                    "Fav 5",
                    "Fav 6"
            };
        } else {
            mData = new String[] {
                    "Rec 1",
                    "Rec 2",
                    "Rec 3",
                    "Rec 4",
                    "Rec 5",
                    "Rec 6",
                    "Rec 7"
            };
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favorites_tab, container, false);

        ArrayList<Item> items = populateItems();
        ItemAdapter adapter = new ItemAdapter(getActivity(), items);

        ListView listView = (ListView) rootView.findViewById(android.R.id.list);
        TextView emptyText = (TextView)rootView.findViewById(android.R.id.empty);

        listView.setEmptyView(emptyText);
        listView.setAdapter(adapter);

        return rootView;
    }

    public ArrayList<Item> populateItems() {
        ArrayList<Item> items = new ArrayList<Item>();

        Item i = new Item();
        i.setClave("NOM-003-STPS-1999");
        i.setTitulo("Este es el titulo de la norma");
        i.setFecha("17/12/2014");

        items.add(i);
        items.add(i);
        items.add(i);
        items.add(i);
        items.add(i);

        return items;
    }

}
