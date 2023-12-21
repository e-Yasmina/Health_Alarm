package com.example.health_alarm_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ManageMedicine extends AppCompatActivity {
    String medicineId;
    int SELECT_PICTURE = 200;
    Uri selectedImageUri;
    Button modify;
    Button delete;
    Button alert;
    ImageView photo;
    boolean  isInjectionsChecked ;
    boolean isEyeDropsChecked ;
    boolean isOralPillsChecked ;
    String photoURL;
    String name;
    String type;
    int quantity;
    int dose;
    String frequency;
    int  remaining_amount;
    Button upload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_medicine);
        Intent intent = getIntent();
        photo=findViewById(R.id.photo_holder);
        upload=findViewById(R.id.uploadImg);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        delete=findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirestoreUtils.delete("Medicines", medicineId,
                        documentReference -> {

                            Toast.makeText(getApplicationContext(), "You have deleted your medicine successfully",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ManageMedicine.this, Medicines_Portal.class);
                            startActivity(intent);
                            finish();
                            Log.d("FirestoreUtils", "Medicine deleted successfully " );
                        },
                        e -> Log.e("FirestoreUtils", "Error deleting medicine: " + e.getMessage()));
            }
        });

        modify=findViewById(R.id.modify);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout nameLayout = findViewById(R.id.name);
                TextInputEditText nameEditText = nameLayout.findViewById(R.id.nametext);
                name = nameEditText.getText().toString().trim();
                CheckBox injectionsCheckbox = findViewById(R.id.injections);
                CheckBox eyeDropsCheckbox = findViewById(R.id.eye_drops);
                CheckBox oralPillsCheckbox = findViewById(R.id.oral_pills);

                boolean isInjectionsChecked = injectionsCheckbox.isChecked();
                boolean isEyeDropsChecked = eyeDropsCheckbox.isChecked();
                boolean isOralPillsChecked = oralPillsCheckbox.isChecked();
                if(isInjectionsChecked) {
                    type = "injections";
                }else if(isEyeDropsChecked){
                    type= "eye drops";
                }else{
                    type="oral pills";
                }
                TextInputLayout qLayout = findViewById(R.id.quantity);
                TextInputEditText qEditText = qLayout.findViewById(R.id.quantitytext);
                String quantityStr= qEditText.getText().toString().trim();
                //quantity = Integer.parseInt(quantityStr);


                TextInputLayout dLayout = findViewById(R.id.dose);
                TextInputEditText dEditText = dLayout.findViewById(R.id.dosetext);
                String doseStr= dEditText.getText().toString().trim();
                //dose = Integer.parseInt(doseStr);

                TextInputLayout fLayout = findViewById(R.id.frequency);
                TextInputEditText fEditText = fLayout.findViewById(R.id.frequencytext);
                frequency= fEditText.getText().toString().trim();


                if(name.isEmpty() || type.isEmpty() || quantityStr.isEmpty() || doseStr.isEmpty() || frequency.isEmpty() ){
                    Toast.makeText(getApplicationContext(), "Please fill all information needed"+name+type+quantityStr+doseStr+frequency,Toast.LENGTH_LONG).show();

                }else{
                    quantity = Integer.parseInt(quantityStr);
                    dose = Integer.parseInt(doseStr);

                    Medicine medicine = new Medicine();
                    medicine.setId(medicineId);
                    medicine.setName(name);

                    if (selectedImageUri != null) {
                        try {
                            Bitmap bitmap = uriToBitmap(selectedImageUri);
                            Uri newUri = saveBitmapToInternalStorage(bitmap);
                            photo.setImageURI(newUri);
                            photoURL = newUri.toString();
                            medicine.setPhoto(photoURL);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (!"Not available".equals(medicine.getPhoto())) {
                        // Only set the photo to "Not available" if it was not available before
                        medicine.setPhoto("Not available");
                    }
                    medicine.setType(type);
                    medicine.setQuantity(quantity);
                    medicine.setDose(dose);
                    medicine.setFrequency(frequency);
                    medicine.setRemaining_amount(quantity);

                    FirestoreUtils.update("Medicines", medicineId,medicine,
                            documentReference -> {

                                Toast.makeText(getApplicationContext(), "You have modify your medicine successfully",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ManageMedicine.this, Medicines_Portal.class);
                                startActivity(intent);
                                finish();
                                Log.d("FirestoreUtils", "Medicine added successfully with ID: " );
                            },
                            e -> Log.e("FirestoreUtils", "Error adding medicine: " + e.getMessage()));

                }

            }
        });
        alert=findViewById(R.id.set_alert);
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageMedicine.this, Reminders.class);
                // Pass the id value to the next activity
                intent.putExtra("MEDICINE_ID", medicineId);
                //intent.putExtra("Photo",photo);
                // Start the activity
                startActivity(intent);


            }
        });

        if (intent != null) {
            medicineId = intent.getStringExtra("MEDICINE_ID");
            //Toast.makeText(this, medicineId, Toast.LENGTH_SHORT).show();
            TextInputLayout nameLayout = findViewById(R.id.name);
            TextInputEditText nameEditText = nameLayout.findViewById(R.id.nametext);

            photo=findViewById(R.id.photo_holder);

            CheckBox injectionsCheckbox = findViewById(R.id.injections);
            CheckBox eyeDropsCheckbox = findViewById(R.id.eye_drops);
            CheckBox oralPillsCheckbox = findViewById(R.id.oral_pills);

            isInjectionsChecked = injectionsCheckbox.isChecked();
            isEyeDropsChecked = eyeDropsCheckbox.isChecked();
            isOralPillsChecked = oralPillsCheckbox.isChecked();

            TextInputLayout qLayout = findViewById(R.id.quantity);
            TextInputEditText qEditText = qLayout.findViewById(R.id.quantitytext);

            TextInputLayout dLayout = findViewById(R.id.dose);
            TextInputEditText dEditText = dLayout.findViewById(R.id.dosetext);

            TextInputLayout fLayout = findViewById(R.id.frequency);
            TextInputEditText fEditText = fLayout.findViewById(R.id.frequencytext);
            FirestoreUtils.readById("Medicines", medicineId, Medicine.class, new OnSuccessListener<Medicine>() {
                @Override
                public void onSuccess(Medicine medicine) {
                    if (medicine != null) {
                        nameEditText.setText(medicine.getName());
                        if (medicine.getPhoto() != "Not available") {
                            Glide.with(ManageMedicine.this)
                                    .load(medicine.getPhoto())
                                    .into(photo);
                            photo.setMaxHeight(150);
                        }
                        if ("injections".equals(medicine.getType().trim())) {
                            isInjectionsChecked = true;
                        } else if ("eye drops".equals(medicine.getType().trim())) {
                            isEyeDropsChecked = true;
                        } else {
                            isOralPillsChecked = true;
                        }
                        // After setting other values, check the checkboxes
                        if (isInjectionsChecked) {
                            injectionsCheckbox.setChecked(true);
                        } else if (isEyeDropsChecked) {
                            eyeDropsCheckbox.setChecked(true);
                        } else if (isOralPillsChecked) {
                            oralPillsCheckbox.setChecked(true);
                        }


                        String sq= String.valueOf(medicine.getQuantity());
                        qEditText.setText(sq);
                        String sd= String.valueOf(medicine.getDose());
                        dEditText.setText(sd);
                        fEditText.setText(medicine.getFrequency());

                    } else {
                        Toast.makeText(ManageMedicine.this, "Medicine not found ", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.print(e);
                }
            });

        }

    }
    void imageChooser() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    try {
                        Bitmap bitmap = uriToBitmap(selectedImageUri);
                        Uri newUri = saveBitmapToInternalStorage(bitmap);
                        photo.setImageURI(newUri);
                        photoURL = newUri.toString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private Bitmap uriToBitmap(Uri uri) throws IOException {
        return MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
    }

    private Uri saveBitmapToInternalStorage(Bitmap bitmap) {
        ContextWrapper wrapper = new ContextWrapper(getApplicationContext());

        File file = wrapper.getDir("images", MODE_PRIVATE);
        String filename = "Image_" + System.currentTimeMillis() + ".jpg";
        File imagePath = new File(file, filename);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Uri.parse(imagePath.getAbsolutePath());
    }
}
