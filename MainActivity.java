package android.example.infs3634_task_3.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.example.infs3634_task_3.Favorite;
import android.example.infs3634_task_3.Fragment.FavoriteFragment;
import android.example.infs3634_task_3.Fragment.SearchFragment;
import android.example.infs3634_task_3.R;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    static Favorite favorite = new Favorite();
    BottomNavigationView bottomNavigationView;

    //Edit: Example Code From Week 8 Tutorial
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // I want there to be a Fragment in the slot from the start
        Fragment fragment = new SearchFragment();
        swapFragment(fragment);

        bottomNavigationView = findViewById(R.id.bot_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // menuItem = the item on the bottom nav view that was selected
                // The id's here belong to the items in the menu of the BottomnNavigationView
                // The menu is chunked out as bottom_navigation.xml in the res > menu folder

                //switch between two fragments when pressing items on navigation
                if (menuItem.getItemId() == R.id.search_bar) {
                    Fragment fragment = new SearchFragment();
                    swapFragment(fragment);
                    return true;
                } else if (menuItem.getItemId() == R.id.favorite_bar) {
                    Fragment fragment = new FavoriteFragment();
                    swapFragment(fragment);
                    return true;
                }
                return false;
            }
        });
    }

    //Example Code from Week 8 Tutorial Solution without editing
    //The function swapFragment allows activity to switch between fragments

    /**
     * Helper method to change the fragment displayed in the activity. We put this here so we don't
     * have to repeat the code every time we want to saw
     *
     * @param fragment: instance of the fragment to go into the slot
     */
    private void swapFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }

    //Favorite
    public static Favorite getFavorite() {
        return favorite;
    }


}
