package com.oracle.dgnmovil.tabs;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.oracle.dgnmovil.app.R;
import com.oracle.dgnmovil.app.adapter.FavoritoAdapter;
import com.oracle.dgnmovil.app.model.Favorito;
import com.oracle.dgnmovil.util.DbUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class FavoritesTabFragment extends Fragment {
    private ListView mListView;

    public FavoritesTabFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorites_tab, container, false);

        ArrayList<Favorito> favoritos = new ArrayList<Favorito>();
        final FavoritoAdapter adapter = new FavoritoAdapter(getActivity(), favoritos);

        mListView = (ListView) rootView.findViewById(android.R.id.list);
        TextView emptyText = (TextView) rootView.findViewById(android.R.id.empty);
        mListView.setEmptyView(emptyText);
        mListView.setAdapter(adapter);

        List<Favorito> l = loadFavorites();
        Collections.reverse(l);
        adapter.addAll(l);
        adapter.notifyDataSetChanged();

        return rootView;
    }

    public ArrayList<Favorito> loadFavorites() {
        DbUtil dbUtil = new DbUtil(getActivity());

        // Just for testing
        // dbUtil.setNormaPreference(1, 0);

        return dbUtil.getFavorites();
    }
}
