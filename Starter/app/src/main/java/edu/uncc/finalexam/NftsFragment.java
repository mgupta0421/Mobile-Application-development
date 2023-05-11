package edu.uncc.finalexam;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import edu.uncc.finalexam.databinding.FragmentNftsBinding;
import edu.uncc.finalexam.databinding.NftRowItemBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NftsFragment extends Fragment {
    public NftsFragment() {
        // Required empty public constructor
    }

    FragmentNftsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNftsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    private OkHttpClient client;

    ArrayList<Nft> nftList;
    ArrayList<String> favImages;



    NftAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        client = new OkHttpClient();
        favImages = new ArrayList<>();
        nftList = new ArrayList<>();
        getActivity().setTitle("NFT");
        getSearchResults();
  // CALL THE get nft method
        adapter = new NftAdapter();
        binding.recyclerViewNfts.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewNfts.setHasFixedSize(true);
        binding.recyclerViewNfts.setAdapter(adapter);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(FirebaseAuth.getInstance().getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                favImages.clear();
                for(QueryDocumentSnapshot doc: value)
                {
                    favImages.add((String)doc.get("id"));
                }
                adapter.notifyDataSetChanged();
            }
        });

        binding.buttonNftSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comparator<Nft> sortnfts = new Comparator<Nft>() {
                    @Override
                    public int compare(Nft image1, Nft image2) {
                        return image1.getName().compareTo(image2.getName());
                    }
                };
                Collections.sort(nftList, sortnfts);

                adapter.notifyDataSetChanged();
            }
        });

        binding.buttonCollectionSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comparator<Nft> sortcollection = new Comparator<Nft>() {
                    @Override
                    public int compare(Nft image1, Nft image2) {
                        return image1.getCollectionname().compareTo(image2.getCollectionname());
                    }
                };
                Collections.sort(nftList, sortcollection);

                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_logout){
            mListener.logout();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getSearchResults(){
        String url = "https://www.theappsdr.com/api/nfts-assets";

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if(response.isSuccessful())
                {
                    nftList.clear();
                    ResponseBody responseBody = response.body();
                    String temp = responseBody.string();
                    try {
                        JSONObject jsonObject = new JSONObject(temp);
                        JSONArray jsonArray = jsonObject.getJSONArray("assets");
                        for(int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject resultObject = jsonArray.getJSONObject(i);
                            JSONObject nftobjects = resultObject.getJSONObject("nft");
                            JSONObject collectionobject = resultObject.getJSONObject("collection");
                            Nft image = new Nft();
                            image.setId(nftobjects.getString("id"));
                            image.setName(nftobjects.getString("name"));
                            image.setImage(nftobjects.getString("image_thumbnail_url"));
                            image.setCollectionname(collectionobject.getString("name"));
                            image.setCollectionimage(collectionobject.getString("banner_image_url"));

                            nftList.add(image);
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });


    }

    class NftAdapter extends RecyclerView.Adapter<NftAdapter.NftViewHolder>
    {
        @NonNull
        @Override
        public NftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new NftViewHolder(NftRowItemBinding.inflate(getLayoutInflater(),parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull NftViewHolder holder, int position) {
            Nft image = nftList.get(position);
            holder.setUpUI(image);

        }

        @Override
        public int getItemCount() {
            return nftList.size();
        }

        class NftViewHolder extends RecyclerView.ViewHolder{

            NftRowItemBinding mBinding;
            Nft mImage;
            public NftViewHolder(NftRowItemBinding vhBinding)
            {
                super(vhBinding.getRoot());
                mBinding = vhBinding;

                mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      //  mListener.goToPhotoDetail(mImage);
                    }
                });

                mBinding.imageViewAddRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       if(favImages.contains(mImage.id))
                        {
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection(FirebaseAuth.getInstance().getUid()).document(mImage.getId()).delete().addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getActivity(), "Removed", Toast.LENGTH_SHORT).show();
                                  //  notifyDataSetChanged();
                                }
                            });
                        }
                        else {
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("id", mImage.getId());
                            map.put("name", mImage.getName());
                            map.put("image", mImage.getImage());
                            map.put("collectionname", mImage.getCollectionname());
                            map.put("collectionimage", mImage.getCollectionimage());
                            db.collection(FirebaseAuth.getInstance().getUid()).document(mImage.getId()).set(map).addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getActivity(), "Added", Toast.LENGTH_SHORT).show();
                                 //   adapter.notifyDataSetChanged();
                                }
                            });

                        }
                    }
                });

            }

            void setUpUI(Nft image)
            {
                this.mImage = image;
                Picasso.get().load(image.getImage()).into(mBinding.imageViewNftIcon);
                mBinding.textViewNftName.setText(mImage.name);
                Picasso.get().load(image.getCollectionimage()).into(mBinding.imageViewCollectionBanner);
                mBinding.textViewCollectionName.setText(mImage.collectionname);

                if(favImages.contains(mImage.getId()))
                {
                    mBinding.imageViewAddRemove.setImageResource(R.drawable.ic_minus);
                }
                else{
                mBinding.imageViewAddRemove.setImageResource(R.drawable.ic_plus);
            }
            }
        }
    }

    NftsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (NftsListener) context;
    }

    interface NftsListener {
        void logout();
    }
}