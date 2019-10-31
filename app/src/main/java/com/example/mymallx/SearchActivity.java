package com.example.mymallx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity {

    androidx.appcompat.widget.SearchView searchView;
    TextView productNotFound;
    RecyclerView resultRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = findViewById(R.id.searchViewId);
        productNotFound = findViewById(R.id.porductNotFoundID);
        resultRecyclerView = findViewById(R.id.resultProductId);

        resultRecyclerView.setVisibility(View.VISIBLE);
        productNotFound.setVisibility(View.GONE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        resultRecyclerView.setLayoutManager(linearLayoutManager);

        final List<WishListModel> list = new ArrayList<>();
        final List<String >ids=new ArrayList<>();
        final Adapter adapter = new Adapter(list, false);
        adapter.setFromeSearch(true);
        resultRecyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                list.clear();
                ids.clear();

                final String[] tags = query.toLowerCase().split(" ");
                for (final String tag : tags) {
                    tag.trim();
                    FirebaseFirestore.getInstance().collection("PRODUCTS").whereArrayContains("tags", tag)
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments())
                                {
                                    WishListModel model=new WishListModel(documentSnapshot.getId(), (String) documentSnapshot.get("product_img_" + 1),
                                            (String) documentSnapshot.get("product_title"),
                                            (Long) documentSnapshot.get("free_coupen"),
                                            (String) documentSnapshot.get("average_rating"),
                                            (String) documentSnapshot.get("total_rating"),
                                            (String) documentSnapshot.get("product_price"),
                                            (String) documentSnapshot.get("cutted_price"),
                                            (Boolean) documentSnapshot.get("COD"));
                                    model.setTags((ArrayList<String>) documentSnapshot.get("tags"));
                                    if (!ids.contains(model.getProductId()))//when search the product //if product one time searching the it stope serching
                                    {
                                        list.add(model);
                                        ids.add(model.getProductId());
                                    }
                                }
                                if (tag.equals(tags[tags.length-1]))
                                {
                                    if (list.size()==0)
                                    {
                                        productNotFound.setVisibility(View.VISIBLE);
                                        resultRecyclerView.setVisibility(View.GONE);
                                    }
                                    else
                                    {
                                        productNotFound.setVisibility(View.GONE);
                                        resultRecyclerView.setVisibility(View.VISIBLE);
                                        adapter.getFilter().filter(query);
                                    }
                                }
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }

    class Adapter extends WishListAdapter implements Filterable {

       private List<WishListModel>origenalList;
        public Adapter(List<WishListModel> wishListModelList, Boolean wish) {
            super(wishListModelList, wish);
            origenalList=wishListModelList;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    //filter logic
                    FilterResults filterResults=new FilterResults();
                     List<WishListModel>filterList=new ArrayList<>();
                    final String[] tags = constraint.toString().toLowerCase().split(" ");
                    for (WishListModel model:origenalList)
                    {
                        ArrayList<String >presenrTags=new ArrayList<>();
                        for (String tag:tags)
                        {
                            if (model.getTags().contains(tag))
                            {
                                presenrTags.add(tag);
                            }
                        }
                        model.setTags(presenrTags);
                    }
                    for (int i=tags.length;i>0;i--)
                    {
                        for (WishListModel model:origenalList)
                        {
                            if (model.getTags().size()==i)
                            {
                                filterList.add(model);
                            }
                        }
                    }
                    filterResults.values=filterList;
                    filterResults.count=filterList.size();
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results.count>0)
                    {
                        setWishListModelList((List<WishListModel>) results.values);
                    }
                    notifyDataSetChanged();
                }
            };
        }
    }
}
