package com.example.mentalhealthapp;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

public class ProfileEditActivity extends AppCompatActivity {

    public Button btnSave;
    public TextView nText;
    public EditText nName;
    public EditText nBio;
    public EditText nPassword;
    public ImageView image;
    private static final int PICK_IMAGE =1;
    Uri imageuri;
   // public String getString;
    public  String Name,Bio, Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        btnSave = (Button)findViewById(R.id.btnProfileSave);

        nText = (TextView ) findViewById(R.id.id_edit_text);
        nName = (EditText ) findViewById(R.id.tvNameProfile);
        nBio = (EditText ) findViewById(R.id.tvBioProfile);
        nPassword =(EditText ) findViewById(R.id.et_password_cp);
        image=findViewById(R.id.iv_cp);

        nName.setText(getIntent().getStringExtra("Name"));
        nBio.setText(getIntent().getStringExtra("Bio"));

        ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() ==RESULT_OK)
                        {
                            Toast.makeText(ProfileEditActivity.this,"Image Result Ok",
                                    Toast.LENGTH_SHORT).show();
                            imageuri = result.getData().getData();
                            try{
                                Toast.makeText(ProfileEditActivity.this,imageuri.toString(),
                                        Toast.LENGTH_SHORT).show();
                                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageuri);
                                image.setImageBitmap(bitmap);

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(gallery,"Select Picture"),PICK_IMAGE);
                launchSomeActivity.launch(Intent.createChooser(gallery,"Select Picture"));
            }
        });
        //profileImage=(ImageView )findViewById(R.id.iv_cp);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //open gallery
                //Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(openGalleryIntent,1000);
                Name= nName.getText().toString();
                Bio=nBio.getText().toString();
//                Text=nPassword.getText().toString();
//
                Toast.makeText(ProfileEditActivity.this,Name+" "+Bio,Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(ProfileEditActivity.this,ProfileActivity.class);
                intent.putExtra("Name",Name);
                intent.putExtra("Bio",Bio);
//                intent.putExtra("Text",Text);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }

        });

    }
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//
//    }


//       @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==1000){
//            if(resultCode== Activity.RESULT_OK){
//                Uri imageUri =data.getData();
//                profileImage.setImageURI(imageUri);
//            }
//        }
//    }
}