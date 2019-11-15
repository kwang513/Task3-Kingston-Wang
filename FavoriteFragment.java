package android.example.infs3634_task_3.Fragment;

import android.example.infs3634_task_3.Activity.MainActivity;
import android.example.infs3634_task_3.CatAdapter;
import android.example.infs3634_task_3.Model.Cat;
import android.example.infs3634_task_3.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {
    RecyclerView recyclerView;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = view.findViewById(R.id.rv_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<Cat> favCats = MainActivity.getFavorite().getFavCats();
        final CatAdapter catAdapter = new CatAdapter();
        catAdapter.setData(favCats);
        recyclerView.setAdapter(catAdapter);
        return view;
    }


}
