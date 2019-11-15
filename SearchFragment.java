package android.example.infs3634_task_3.Fragment;

import android.example.infs3634_task_3.Model.Cat;
import android.example.infs3634_task_3.database.AppDatabase;
import android.example.infs3634_task_3.CatAdapter;
import android.example.infs3634_task_3.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private EditText editText;
    private ImageButton imageButton;

    public SearchFragment() {
        //empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.rv_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        editText = view.findViewById(R.id.search_text);
        imageButton = view.findViewById(R.id.search_image);

        final CatAdapter catAdapter = new CatAdapter();

        // Start Volley stuff
        // 1. Create request queue.
        // 2. Create response listener and error listener
        // 3. Create Request object using url, response listener, error listener.
        // 4. Put Request object into request queue.

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        final AppDatabase db = AppDatabase.getInstance(this.getContext());
        // To get the url, you need to read the documentation of the API.
        // Figure out what data you want, and they should tell you how to get the correct URL.
        // Also in this case, I've put my API key in an XML file (secrets.xml).
        // This helps me easily reuse the API key by calling getString.
        // It also makes it easy for me to hide the API key if I want to share my code
        // (I can just ignore the secrets.xml file instead of having to go into all the files and delete it.)
        // You will need to go into the file and put in your own API key first.
        String url = "https://api.thecatapi.com/v1/breeds";
//        String url = "https://api.thecatapi.com/v1/breeds/search?q="+getString(R.string.cat_api_key);

        // Response.Listener<String>. We define what to do after a response is received.
        // Response.Listener means that it is referring to the inner class Listener, which has been
        // defined inside another class Response.
        // The <String> part corresponds to the type of the response.
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // This method, onResponse, is executed after a response is received for your request.
                // Logically, this means it is the only place where you are guarantee that a response
                // has been received.
                // So, you must write all the code that depends on having a response here.
                // For example, converting the response to objects using Gson.

                Gson gson = new Gson();
                Cat[] allCatsResponse = gson.fromJson(response, Cat[].class);
//                BestsellerList bestsellerList = bestsellerListResponse.getResults();

                List<Cat> cats = Arrays.asList(allCatsResponse);
                System.out.println(cats);

                // I save my results to the database so I can retrieve it later in my other activities.
//                AppDatabase db = AppDatabase.getInstance(getContext());

//                final AppDatabase db = AppDatabase.getInstance(getContext());
                // Keep in mind that this insertAll query will be blocking the main thread, so the
                // program will be stuck at this line of code until the query finishes.
                db.catDao().insertCats(cats);

                // After inserting, I want to get what's in the database now.
//                List<Book> listBooksFromDatabase = db.bookDao().getAll();

                // Convert list to arraylist
//                ArrayList<Book> booksFromDatabase = new ArrayList<Book>(listBooksFromDatabase);

                catAdapter.setData(db.catDao().getAllCatsSorted());
                recyclerView.setAdapter(catAdapter);

                // It is a good idea to include this line after we are done with the requestQueue.
                // It just closes the queue.
                requestQueue.stop();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Plan B when request failed
                catAdapter.setData(db.catDao().getAllCatsSorted());
                recyclerView.setAdapter(catAdapter);

                Toast.makeText(getContext(), "The request failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
        setOnClick(imageButton, catAdapter);
        return view;
    }

    private void setOnClick(final ImageButton searchButton, final CatAdapter catAdapter) {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                String url = "https://api.thecatapi.com/v1/breeds/search?q=" + editText.getText();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Cat[] filteredCatsResponse = gson.fromJson(response, Cat[].class);
                        List<Cat> catsFiltered = Arrays.asList(filteredCatsResponse);
                        catAdapter.setData(catsFiltered);
                        recyclerView.setAdapter(catAdapter);
                        requestQueue.stop();
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "The request failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        requestQueue.stop();
                    }
                };

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener,
                        errorListener);
                requestQueue.add(stringRequest);
            }
        });
    }

}
