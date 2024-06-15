package com.example.vacancies.ui.Users;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vacancies.Prevalent.Prevalent;
import com.example.vacancies.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

//    private CircleImageView profileImageView;
    private EditText fullNameEditText, userPhoneEditText, addressEditText;
    private TextView saveTextButton,  closeTextBtn;
    //private Uri imageUri;
    private String checker = "";
    private StorageReference storageProfilePictureRef;
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        profileImageView = (CircleImageView) findViewById(R.id.settings_account_image);
        fullNameEditText = (EditText) findViewById(R.id.settings_fullname);
        userPhoneEditText = (EditText) findViewById(R.id.settings_phone);
        addressEditText = (EditText) findViewById(R.id.settings_address);
        saveTextButton = (TextView) findViewById(R.id.save_settings_tv);
        closeTextBtn = (TextView) findViewById(R.id.close_settings_tv);
        storageProfilePictureRef = FirebaseStorage.getInstance().getReference().child("Profile pictures");

        userInfoDisplay(fullNameEditText, userPhoneEditText,addressEditText);

        closeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(SettingsActivity.this, HomeActivity.class);
                startActivity(loginIntent);
            }
        });
        saveTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (checker.equals("clicked"))
                {
                    userInfoSaved();
                }
                else
                {
                    updateOnlyUserInfo();
                }
            }
        });

//        profileImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                checker = "clicked";
//
//                CropImage.activity(imageUri)
//                        .setAspectRatio(1, 1)
//                        .start(SettingsActivity.this);
//            }
//        });
    }

    private void userInfoDisplay(final EditText fullNameEditText,final EditText userPhoneEditText,final EditText addressEditText) {
        String phone = Prevalent.currentOnlineUser.getPhone();
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(phone);

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.child("image").exists())
                    {
                        String image = dataSnapshot.child("image").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String phone = dataSnapshot.child("phone").getValue().toString();
                        String address = dataSnapshot.child("address").getValue().toString();

                        //Picasso.get().load(image).into(profileImageView);
                        fullNameEditText.setText(name);
                        userPhoneEditText.setText(phone);
                        addressEditText.setText(address);
                    }

                    if (dataSnapshot.child("address").exists())
                    {
                        String name = dataSnapshot.child("name").getValue().toString();
                        String phone = dataSnapshot.child("phone").getValue().toString();
                        String address = dataSnapshot.child("address").getValue().toString();

                        fullNameEditText.setText(name);
                        userPhoneEditText.setText(phone);
                        addressEditText.setText(address);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE  &&  resultCode==RESULT_OK  &&  data!=null)
//        {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            imageUri = result.getUri();
//
//            profileImageView.setImageURI(imageUri);
//        }
//        else
//        {
//            Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
//
//            startActivity(new Intent(SettingsActivity.this, SettingsActivity.class));
//            finish();
//        }
//    }


    private void userInfoSaved() {
        if (TextUtils.isEmpty(fullNameEditText.getText().toString()))
        {
            Toast.makeText(this, "Заполните имя.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(addressEditText.getText().toString()))
        {
            Toast.makeText(this, "Заполните о себе", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(userPhoneEditText.getText().toString()))
        {
            Toast.makeText(this, "Заполните номер", Toast.LENGTH_SHORT).show();
        }
//        else if(checker.equals("clicked"))
//        {
//            uploadImage();
//        }
    }

//    private void uploadImage() {
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Обновляемся..");
//        progressDialog.setMessage("Пожалуйста, подождите");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
//
//        if (imageUri != null)
//        {
//            final StorageReference fileRef = storageProfilePictureRef
//                    .child(Prevalent.currentOnlineUser.getPhone() + ".WebP");
//
//            uploadTask = fileRef.putFile(imageUri);
//
//            uploadTask.continueWithTask(new Continuation() {
//                        @Override
//                        public Object then(@NonNull Task task) throws Exception
//                        {
//                            if (!task.isSuccessful())
//                            {
//                                throw task.getException();
//                            }
//
//                            return fileRef.getDownloadUrl();
//                        }
//                    })
//                    .addOnCompleteListener(new OnCompleteListener() {
//                        @Override
//                        public void onComplete(@NonNull Task task)
//                        {
//                            if (task.isSuccessful())
//                            {
//
//                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
//
//                                HashMap<String, Object> userMap = new HashMap<>();
//                                userMap. put("name", fullNameEditText.getText().toString());
//                                userMap. put("aboutUs", addressEditText.getText().toString());
//                                userMap. put("phoneOrder", userPhoneEditText.getText().toString());
//                                ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);
//
//                                progressDialog.dismiss();
//
//                                startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
//                                Toast.makeText(SettingsActivity.this, "Информация успешно сохранена", Toast.LENGTH_SHORT).show();
//                                finish();
//                            }
//                            else
//                            {
//                                progressDialog.dismiss();
//                                Toast.makeText(SettingsActivity.this, "Error.", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//        }
//        else
//        {
//            Toast.makeText(this, "Изображение не выбрано.", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void updateOnlyUserInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

        HashMap<String, Object> userMap = new HashMap<>();
        userMap. put("name", fullNameEditText.getText().toString());
        userMap. put("address", addressEditText.getText().toString());
        userMap. put("phoneOrder", userPhoneEditText.getText().toString());
        ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);

        startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
        Toast.makeText(SettingsActivity.this, "Успешно сохранено", Toast.LENGTH_SHORT).show();
        finish();
    }


}