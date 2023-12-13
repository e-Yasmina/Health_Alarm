package com.example.health_alarm_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Add_Medicine extends AppCompatActivity {

    Uri selectedImageUri;
    Button upload;
    int SELECT_PICTURE = 200;
    ImageView photo;
    String photoURL;
    Button add;
    String name;
    String type;
    int quantity;
    int dose;
    String frequency;
    int  remaining_amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        upload=findViewById(R.id.uploadImg);
        photo=findViewById(R.id.photo_holder);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        //Medicine Attribus



        add=findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
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

                    medicine.setName(name);
                    medicine.setPhoto("Not available");
                    if (photoURL != null && !photoURL.isEmpty()) {
                        medicine.setPhoto(photoURL);
                    }
                    medicine.setType(type);
                    medicine.setQuantity(quantity);
                    medicine.setDose(dose);
                    medicine.setFrequency(frequency);
                    medicine.setRemaining_amount(quantity);

                    medicine.setId(null);

                    FirestoreUtils.add("Medicines", medicine,
                            documentReference -> {
                                // The ID should be set in the medicine object at this point
                                String documentId = medicine.getId();
                                Toast.makeText(getApplicationContext(), "Medicine added successfully with ID: "+medicine.getId(),Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Add_Medicine.this, Medicines_Portal.class);
                                startActivity(intent);
                                finish();
                                Log.d("FirestoreUtils", "Medicine added successfully with ID: " + documentId);
                            },
                            e -> Log.e("FirestoreUtils", "Error adding medicine: " + e.getMessage()));

                }

            }
        });



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
                if (null != selectedImageUri) {
                    photo.setImageURI(selectedImageUri);
                    photoURL=selectedImageUri.toString();
                }
            }
        }
    }
}