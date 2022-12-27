package com.sp.navigationbartest;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.content.Context;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.ChipGroup;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class DetailsFragment extends Fragment {

    private EditText userName;
    /*private RadioGroup restaurantTypes;
    private Button buttonSave;
    private EditText restaurantAddress;
    private EditText restaurantTel;*/
    private EditText age;
    private EditText location;
    private RadioGroup gender;
    private RadioGroup competitiveness;
    //private ChipGroup gamegenre;
    private RadioGroup gamegenre;
    private RadioGroup platform;
    //private Switch pc;
   // private Switch console;
    //private Switch mobile;
    private ImageButton buttonSave;
    private int genrecount;


    private ImageView imageView;
    private Button button;
    int SELECT_PICTURE = 200;

    private String image;
    private byte[] bytesImage;

    private TextView locationn = null;
    private GPSTracker gpsTracker;
    private double latitude = 0.0d;
    private double longitude = 0.0d;

    private RestaurantHelper helper = null;
    public  String restaurantID = null;
    public DetailsFragment(){
        //Required Empty Public Constructor
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        restaurantID = null;
        helper = new RestaurantHelper(getContext());
        getParentFragmentManager().setFragmentResultListener("listToDetailKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult( String key ,  Bundle bundle) {
                restaurantID = bundle.getString("id");
                if (restaurantID != null){
                    load();
                } else{
                    clear();
                }
                /*userName.setText(bundle.getString("username"));
                age.setText(bundle.getString("age"));
                location.setText(bundle.getString("location"));
                bundle.putString("image", image);

                String competitiveType = bundle.getString("compete");
                if(competitiveType.equals("Low")){
                    competitiveness.check(R.id.low);
                }else if(competitiveType.equals("Low-Mid")){
                    competitiveness.check(R.id.low_mid);
                }else if(competitiveType.equals("Mid")){
                    competitiveness.check(R.id.mid);
                }else if(competitiveType.equals("Mid-High")){
                    competitiveness.check(R.id.mid_high);
                }else if(competitiveType.equals("High")){
                    competitiveness.check(R.id.high);
                }

                String genderType = bundle.getString("gender");
                if (genderType.equals("Male")) {
                    gender.check(R.id.male);
                } else if (genderType.equals("Female")) {
                    gender.check(R.id.female);
                }

                String platformType = bundle.getString("platform");
                if(platformType.equals("PC")){
                    platform.check(R.id.pc);
                } else if(platformType.equals("Console")){
                    platform.check(R.id.console);
                } else if(platformType.equals("Mobile")){
                    platform.check(R.id.mobile);
                }

                String genreType = bundle.getString("genre");
                if(platformType.equals("Sandbox")) {
                    gamegenre.check(R.id.sandbox);
                } else if(platformType.equals("RTS")) {
                    gamegenre.check(R.id.rts);
                } else if(platformType.equals("Shooters")) {
                    gamegenre.check(R.id.shooters);
                } else if(platformType.equals("MOBA")) {
                    gamegenre.check(R.id.moba);
                } else if(platformType.equals("Simulation")) {
                    gamegenre.check(R.id.simulation);
                } else if(platformType.equals("Fighting Games")) {
                    gamegenre.check(R.id.fighting);
                } else if(platformType.equals("Adventure")) {
                    gamegenre.check(R.id.adventure);
                } else if(platformType.equals("RPG")) {
                    gamegenre.check(R.id.rpg);
                } else if(platformType.equals("Rhythm Games")) {
                    gamegenre.check(R.id.rhythm);
                }*/




            }

        });
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        helper.close();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        userName= view.findViewById(R.id.user_name);
        age= view.findViewById(R.id.age);
        buttonSave= view.findViewById(R.id.button_save);
        buttonSave.setOnClickListener(onSave);
        location= view.findViewById(R.id.location);
        gender= view.findViewById(R.id.gender);
        competitiveness=view.findViewById(R.id.competitiveness);
        //Platform
        platform = view.findViewById(R.id.platform);
        gamegenre=view.findViewById(R.id.genre);

        imageView =view.findViewById(R.id.imageView);
        button = view.findViewById(R.id.button);
        //button.setOnClickListener(rise ->mGetContet.launch("image/*"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        //locationn = view.findViewById(R.id.locationn);

        //gpsTracker = new GPSTracker(getContext());




        return  view;
    }

    private void imageChooser(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap;
                        try {
                            selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                                    ((RestaurantList)getActivity()).getContentResolver(), selectedImageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        imageView.setImageBitmap(selectedImageBitmap);
                    }
                }
            });

    ActivityResultLauncher<String> mGetContet = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if(result != null){
                        imageView.setImageURI(result);
                        image =result.toString();
                    }
                }
            });


    public void clear(){
        userName.setText("");
        age.setText("");
        location.setText("");
        gender.clearCheck();
        competitiveness.clearCheck();
        platform.clearCheck();
        gamegenre.clearCheck();
        //imageView.setImageDrawable(null);
        //image = null;
    }


    private void load(){
        Cursor c = helper.getById(restaurantID);
        c.moveToFirst();
        userName.setText(helper.getuserName(c));
        age.setText(helper.getuserAge(c));
        location.setText(helper.getuserLocation(c));
        /*latitude = helper.getLatitude(c);
        longitude = helper.getLongitude(c);
        locationn.setText(String.valueOf(latitude)+ " , " + String.valueOf(longitude));*/



        /*byte[] iage = helper.getuserImage(c);
        Bitmap bitmap = BitmapFactory.decodeByteArray(iage, 0 , iage.length);
        imageView.setImageBitmap(bitmap);

        //Uri myUri = Uri.parse(helper.getuserImage(c));
        //imageView.setImageURI(myUri);
        Bitmap bitmaps = BitmapFactory.decodeFile(image);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmaps.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        bytesImage = byteArrayOutputStream.toByteArray();*/




        if(helper.getuserGender(c).equals("Male")){
            gender.check(R.id.male);
        } else if(helper.getuserGender(c).equals("Female")){
            gender.check(R.id.female);
        }

        if(helper.getuserComp(c).equals("Low")){
            competitiveness.check(R.id.low);
        } else if(helper.getuserComp(c).equals("Low-Mid")){
            competitiveness.check(R.id.low_mid);
        } else if(helper.getuserComp(c).equals("Mid")){
            competitiveness.check(R.id.mid);
        } else if(helper.getuserComp(c).equals("Mid-High")){
            competitiveness.check(R.id.mid_high);
        } else if(helper.getuserComp(c).equals("High")){
            competitiveness.check(R.id.high);
        }

        if(helper.getuserPlatform(c).equals("PC")){
            platform.check(R.id.pc);
        }else if(helper.getuserPlatform(c).equals("Console")) {
            platform.check(R.id.console);
        }else if(helper.getuserPlatform(c).equals("Mobile")) {
            platform.check(R.id.mobile);
        }

        if(helper.getuserGenre(c).equals("Sandbox")){
            gamegenre.check(R.id.sandbox);
        }else if(helper.getuserGenre(c).equals("RTS")) {
            gamegenre.check(R.id.rts);
        }else if(helper.getuserGenre(c).equals("Shooters")) {
            gamegenre.check(R.id.shooters);
        }else if(helper.getuserGenre(c).equals("MOBA")) {
            gamegenre.check(R.id.moba);
        }else if(helper.getuserGenre(c).equals("Simulation")) {
            gamegenre.check(R.id.simulation);
        }else if(helper.getuserGenre(c).equals("Fighting Games")) {
            gamegenre.check(R.id.fighting);
        }else if(helper.getuserGenre(c).equals("Adventure")) {
            gamegenre.check(R.id.adventure);
        }else if(helper.getuserGenre(c).equals("RPG")) {
            gamegenre.check(R.id.rpg);
        }else if(helper.getuserGenre(c).equals("Rhythm Games")) {
            gamegenre.check(R.id.rhythm);
        }


        }




    public View.OnClickListener onSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String nameStr = userName.getText().toString();
            String ageStr = age.getText().toString();
            String locationStr = location.getText().toString();
            String genderType = "";
            String competitiveType = "";
            String platformtype = "";
            String genreType ="";




            switch(gender.getCheckedRadioButtonId()){
                case R.id.male:
                    genderType = "Male";
                    break;
                case R.id.female:
                    genderType = "Female";
                    break;
            }

            switch (competitiveness.getCheckedRadioButtonId()) {
                case R.id.low:
                    competitiveType = "Low";
                    break;

                case R.id.low_mid:
                    competitiveType = "Low-Mid";
                    break;

                case R.id.mid:
                    competitiveType = "Mid";
                    break;

                case R.id.mid_high:
                    competitiveType = "Mid-High";
                    break;

                case R.id.high:
                    competitiveType = "High";
                    break;
            }

            switch (platform.getCheckedRadioButtonId()) {
                case R.id.pc:
                    platformtype = "PC";
                    break;

                case R.id.console:
                    platformtype = "Console";
                    break;

                case R.id.mobile:
                    platformtype = "Mobile";
                    break;
            }

            switch (gamegenre.getCheckedRadioButtonId()) {
                case R.id.sandbox:
                    genreType = "Sandbox";
                    break;

                case R.id.rts:
                    genreType = "RTS";
                    break;

                case R.id.shooters:
                    genreType = "Shooters";
                    break;

                case R.id.moba:
                    genreType = "MOBA";
                    break;

                case R.id.simulation:
                    genreType = "Simulation";
                    break;

                case R.id.fighting:
                    genreType = "Fighting Games";
                    break;

                case R.id.adventure:
                    genreType = "Adventure";
                    break;

                case R.id.rpg:
                    genreType = "RPG";
                    break;

                case R.id.rhythm:
                    genreType = "Rhythm Games";
                    break;
            }





            /*Bundle bundle = new Bundle();
            bundle.putString("username", nameStr);
            bundle.putString("age", ageStr);
            bundle.putString("location", locationStr);
            bundle.putString("gender", genderType);
            bundle.putString("compete", competitiveType);
            bundle.putString("Image", image);
            bundle.putString("platform",platformtype);
            bundle.putString("genre",genreType);*/

            if(restaurantID == null ){
                helper.insert(nameStr,ageStr,locationStr,genderType, competitiveType, platformtype, genreType);
            } else {
                helper.update(restaurantID, nameStr, ageStr, locationStr, genderType , competitiveType, platformtype, genreType);
            }

            //getParentFragmentManager().setFragmentResult("detailToListKey", bundle);
            BottomNavigationView nav = (BottomNavigationView)getActivity().findViewById(R.id.bottomNavigationView);
            nav.setSelectedItemId(R.id.restlist);
        }
    };
}