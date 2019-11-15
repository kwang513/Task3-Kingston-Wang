package android.example.infs3634_task_3.Activity;

import android.content.Intent;
import android.example.infs3634_task_3.Model.Cat;
import android.example.infs3634_task_3.Model.CatImage;
import android.example.infs3634_task_3.Favorite;
import android.example.infs3634_task_3.R;
import android.example.infs3634_task_3.database.AppDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public class CatDetailActivity extends AppCompatActivity {
    ConstraintLayout catCL;
    TextView name;
    ImageView catImage;
    ImageView favoriteButton;
    TextView description;
    TextView weight;
    TextView temperament;
    TextView origin;
    TextView lifespan;
    TextView wikiLink;
    TextView dogFriendly;
    boolean isFav = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_detail);

        //Assign and Link values from xml file
        catCL = findViewById(R.id.cl_all);
        name = catCL.findViewById(R.id.name);
        catImage = catCL.findViewById(R.id.catImage);
        favoriteButton = catCL.findViewById(R.id.favoriteBtn);
        description = catCL.findViewById(R.id.description);
        weight = catCL.findViewById(R.id.weight);
        temperament = catCL.findViewById(R.id.temperament);
        origin = catCL.findViewById(R.id.origin);
        lifespan = catCL.findViewById(R.id.lifespan);
        wikiLink = catCL.findViewById(R.id.wikiLink);
        dogFriendly = catCL.findViewById(R.id.dog_friendly);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        final AppDatabase db = AppDatabase.getInstance(this.getApplicationContext());
        final Cat cat = db.catDao().findCatById(id);

        name.setText(cat.getName());
        description.setText(cat.getDescription());
        //int value to String
        weight.setText(String.valueOf(cat.getWeight()));
        temperament.setText(cat.getTemperament());
        origin.setText(cat.getOrigin());
        lifespan.setText(cat.getLifeSpan());
        wikiLink.setText(cat.getWikipediaUrl());
        //int value to String
        dogFriendly.setText(String.valueOf(cat.getDogFriendly()));


        //Favorite Button Function
        if (MainActivity.getFavorite().isCatFav(cat)) {
            isFav = true;
            favoriteButton.setImageResource(R.drawable.ic_star1);
        } else {
            isFav = false;
            favoriteButton.setImageResource(R.drawable.ic_star2);
        }

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isFav) {
                    isFav = false;
                    favoriteButton.setImageResource(R.drawable.ic_star2);
                    MainActivity.getFavorite().addFavCat(cat);
                } else {
                    isFav = true;
                    favoriteButton.setImageResource(R.drawable.ic_star1);
                    MainActivity.getFavorite().removeFavCat(cat);
                }
            }

        });

        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://api.thecatapi.com/v1/images/search?breed_id=" + cat.getId();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                CatImage[] catImages = gson.fromJson(response, CatImage[].class);
                String imageUrl = catImages[0].getUrl();
                Glide.with(getApplicationContext()).load(imageUrl).into(catImage);
                requestQueue.stop();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "The request failed: " //+ error.getMessage()
                        , Toast.LENGTH_SHORT).show();
                requestQueue.stop();
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener,
                errorListener);

        // This line simply adds the request to the queue. It's not necessarily going to be executed
        // immediately (there could possibly be other requests in the queue).
        // Also, because requests to the internet take time, we cannot guarantee that the response
        // will be received right away.
        // We are NOT GUARANTEED to have a response after this line.
        // That is the purpose of the onResponse method in the response listener.
        requestQueue.add(stringRequest);


//        @Override
//        public void onFetchFailure(String msg) {
//            RequestQueueService.cancelProgressDialog();
//            RequestQueueService.showAlert("", msg, CatDetailActivity.this);
//        }
//
//        @Override
//        public void onFetchStart() {
//            RequestQueueService.showProgressDialog(CatDetailActivity.this);
//        }
    }

}
