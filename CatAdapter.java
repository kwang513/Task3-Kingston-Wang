package android.example.infs3634_task_3;

import android.content.Context;
import android.content.Intent;
import android.example.infs3634_task_3.Activity.CatDetailActivity;
import android.example.infs3634_task_3.Model.Cat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

// We need to give a type in angle brackets <> when we extend RecyclerView.Adapter
// It's saying that we want an adapter that adapts to CatViewHolder (our custom ViewHolder)
public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatViewHolder> {
    // class variable that holds the data that we want to adapt
    private Context mContext;
    private List<Cat> catsToAdapt;

    public void setData(Context mContext, List<Cat> catsToAdapt) {
        // This is basically a Setter that we use to give data to the adapter
        this.mContext = mContext;
        this.catsToAdapt = catsToAdapt;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // First create a View from the layout file. It'll probably be a ViewGroup like
        // ConstraintLayout that contains more Views inside it.
        // This view now represents your entire one item.
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.cat_list,parent,false);

        // Then create an instance of your custom ViewHolder with the View you got from inflating
        // the layout.
        CatViewHolder catViewHolder = new CatViewHolder(view);
        return catViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        final Cat catAtPosition = catsToAdapt.get(position);

        // Defined own method "bind" in the CatViewHolder
        // class, and all the assignment and setup is done in there
        holder.bind(catAtPosition);
    }

    //Edit from BookAdapter in Week 8 tutorial
    @Override
    public int getItemCount() {
        return catsToAdapt.size();
    }

    public void setData(List<Cat> allCatsSorted) {
    }

    public static class CatViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView nameTextView;
//        public boolean isBookmarked = false;

        // This constructor is used in onCreateViewHolder
        public CatViewHolder(View v) {
            super(v);  // runs the constructor for the ViewHolder superclass
            view = v;
            nameTextView = v.findViewById(R.id.catName);

            //favorite button function
//            favImageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (isBookmarked) {
//                        favImageView.setImageResource(android.R.drawable.star_on);
//                    } else {
//                        favImageView.setImageResource(android.R.drawable.star_off);
//                    }
//                    isBookmarked = !isBookmarked;
//                }
//            });
        }

        // See comment in onBindViewHolder
        public void bind(final Cat cat) {
            nameTextView.setText(cat.getName());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();

                    Intent intent = new Intent(context, CatDetailActivity.class);
                    intent.putExtra("id", cat.getId());
                    context.startActivity(intent);
                }
            });

//            String imageUrl = cat.getWikipediaUrl();
//            Glide.with(view.getContext()).load(imageUrl).into(catImageView);

        }
    }
}


