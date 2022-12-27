package com.sp.navigationbartest;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private RecyclerView list;
    private Cursor model = null;
    //private List<Restaurant> model = new ArrayList<>();

    private RestaurantAdapter adapter = null;
    private RestaurantHelper helper = null;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //adapter= new RestaurantAdapter(model);
        helper = new RestaurantHelper(getContext());
        model = helper.getAll();
        adapter = new RestaurantAdapter(getContext(),model);
        /*getParentFragmentManager().setFragmentResultListener("detailToListKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult( String key,  Bundle bundle) {
                Restaurant r1 = new Restaurant();
                r1.setUserName(bundle.getString("username"));
                r1.setUserAge(bundle.getString("age"));
                r1.setUserLocation(bundle.getString("location"));
                r1.setUserGender(bundle.getString("gender"));
                r1.setUserComp(bundle.getString("compete"));
                r1.setUserImage(bundle.getString("Image"));
                r1.setUserPlatform(bundle.getString("platform"));
                r1.setUserGenre(bundle.getString("genre"));
                model.add(r1);
            }*/
       // });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        list = view.findViewById(R.id.restaurants);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        list.setHasFixedSize(true);
        list.setLayoutManager(layoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        //list.setAdapter(adapter);
        if(model != null){
            model.close();
        }
        model = helper.getAll();
        adapter.swapCursor(model);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        helper.close();
    }

    public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantHolder>{
        private Context context;
        private RestaurantHelper helper = null;
        private Cursor cursor;
        RestaurantAdapter(Context context, Cursor cursor){
            this.context = context;
            this.cursor = cursor;
            helper = new RestaurantHelper(context);
        }
        public void swapCursor(Cursor newcursor){
            Cursor oldCurser = this.cursor;
            this.cursor = newcursor;
            oldCurser.close();
        }
        //private List<Restaurant> itemslist;
        //RestaurantAdapter(List<Restaurant> mItemList) {this.itemslist = mItemList;}


        @Override
        public RestaurantAdapter.RestaurantHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
            return new RestaurantHolder(view);
        }
        @Override
        public void onBindViewHolder(RestaurantAdapter.RestaurantHolder holder, final int position){

            if(!cursor.moveToPosition(position)){
                return;
            }

            holder.username.setText(helper.getuserName(cursor));
            holder.age.setText(helper.getuserAge(cursor));
            holder.location.setText(helper.getuserLocation(cursor));
            holder.gender.setText(helper.getuserGender(cursor));
            holder.competitive.setText(helper.getuserComp(cursor));
            holder.platform.setText(helper.getuserPlatform(cursor));
            holder.genre.setText(helper.getuserGenre(cursor));

            /*byte[] image = helper.getuserImage(cursor);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0 , image.length);
            holder.imageView.setImageBitmap(bitmap);*/


            //holder.competitive.setText(helper.getuserComp(cursor));
            //final Restaurant r = itemslist.get(position);
            /*holder.username.setText(r.getUserName());
            holder.age.setText(r.getUserAge());
            holder.gender.setText(r.getUserGender());
            holder.location.setText(r.getUserLocation());
            holder.competitive.setText(r.getUserComp());
            Uri myUri = Uri.parse(r.getUserImage());
            holder.imageView.setImageURI(myUri);
            holder.platform.setText(r.getUserPlatform());
            holder.genre.setText(r.getUserGenre());*/

           /* if(r.getUserImage().equals("Male")){
                holder.icon.setImageResource(R.drawable.ball_red);
            } else if(r.getUserImage().equals("Female")) {
                holder.icon.setImageResource(R.drawable.ball_yellow);
            } else{
                holder.icon.setImageResource(R.drawable.ball_green);
            }*/
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    /*Bundle bundle = new Bundle();

                    bundle.putString("username", r.getUserName());
                    bundle.putString("age", r.getUserAge());
                    bundle.putString("location", r.getUserLocation());
                    bundle.putString("gender", r.getUserGender());
                    bundle.putString("compete",r.getUserComp());
                    bundle.putString("Image",r.getUserImage());
                    bundle.putString("platform",r.getUserPlatform());
                    bundle.putString("genre",r.getUserGenre());*/

                    int position = holder.getAdapterPosition();
                    cursor.moveToPosition(position);
                    String recordID = helper.getID(cursor);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", recordID);

                    getParentFragmentManager().setFragmentResult("listToDetailKey", bundle);
                    BottomNavigationView nav = (BottomNavigationView)getActivity().findViewById(R.id.bottomNavigationView);
                    nav.setSelectedItemId(R.id.restdetail);

                }
            });
        }
        @Override

        public int getItemCount() {return cursor.getCount();}

        class RestaurantHolder extends RecyclerView.ViewHolder{
            private TextView username = null;
            private TextView age = null;
            private TextView location = null;
            private ImageView icon = null;
            private TextView gender = null;
            private TextView competitive = null;
            private ImageView imageView = null;
            private String image = null;
            private TextView platform = null;
            private TextView genre = null;
            public RestaurantHolder(View itemView){
                super(itemView);
                username = itemView.findViewById(R.id.userName);
                age = itemView.findViewById(R.id.userAge);
                //icon = itemView.findViewById(R.id.icon);
                gender = itemView.findViewById(R.id.userGender);
                location = itemView.findViewById(R.id.userLocation);
                competitive = itemView.findViewById(R.id.userComp);
                imageView = itemView.findViewById(R.id.icon);
                platform = itemView.findViewById(R.id.userPlatform);
                genre = itemView.findViewById(R.id.userGenre);

            }
        }
    }

}