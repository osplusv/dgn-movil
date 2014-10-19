package com.oracle.dgnmovil.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.oracle.dgnmovil.app.R;
import com.oracle.dgnmovil.app.adapter.ReporteItemAdapter;
import com.oracle.dgnmovil.app.model.Reporte;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by osvaldo on 10/19/14.
 */
public class ReportTabFragment extends Fragment {
    private final int LIMIT = 40;
    private ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reportes_tab, container, false);

        ArrayList<Reporte> reportes = new ArrayList<Reporte>();
        final ReporteItemAdapter adapter = new ReporteItemAdapter(getActivity(), reportes);

        mListView = (ListView) rootView.findViewById(android.R.id.list);
        TextView emptyText = (TextView) rootView.findViewById(android.R.id.empty);
        mListView.setEmptyView(emptyText);
        mListView.setAdapter(adapter);

        Firebase postsRef = new Firebase("https://dgn.firebaseio.com/reportes");
        Query postsQuery = postsRef.limit(LIMIT);

        postsQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                Reporte r = new Reporte();
                r.setProducto((String) newPost.get("producto"));
                r.setComentario((String) newPost.get("comentario"));
                r.setImg((String) newPost.get("imagen"));

                adapter.insert(r, 0);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return rootView;
    }
}
