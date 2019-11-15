package android.example.infs3634_task_3;

import android.example.infs3634_task_3.Model.Cat;

import java.util.ArrayList;
import java.util.HashMap;

public class Favorite {
    private static HashMap<String, Cat> favCats;

    public Favorite() {
        favCats = new HashMap<>();
    }

    public ArrayList<Cat> getFavCats() {
        return new ArrayList(favCats.values());
    }

    //check if the fav cat is null or not
    public boolean isCatFav(Cat cat) {
        if (favCats.get(cat.getId()) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void addFavCat(Cat cat) {
        favCats.put(cat.getId(), cat);
    }

    public void removeFavCat(Cat cat) {
        favCats.remove(cat.getId());
    }
}
