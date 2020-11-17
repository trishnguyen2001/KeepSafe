package com.example.keepsafe_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    private EditText eTitle, eUser, ePw;
    private String title, user, pw;
    private Node entry;
    private DatabaseHelper mDatabaseHelper;
    private Button btnBack, btnConfirmAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        eTitle = findViewById(R.id.editText_TITLE);
        eUser = findViewById(R.id.editText_USER);
        ePw = findViewById(R.id.editText_PW);
        mDatabaseHelper = new DatabaseHelper(this);
        btnBack = findViewById(R.id.button_BACKADD);
        btnConfirmAdd = findViewById(R.id.button_CONFIRMADD);

        //BACK BUTTON
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openList();
            }
        });

        //ADD BUTTON
        btnConfirmAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checks if all text fields are filled out
                if ((eTitle.length() != 0) && (eUser.length() != 0) && (ePw.length() != 0)) {
                    //gets inputs
                    title = eTitle.getText().toString();
                    user = eUser.getText().toString();
                    pw = ePw.getText().toString();

                    //create new node w/ input --> add to db
                    entry = new Node(title, user, pw);
                    addData(entry);

                    //resets hints
                    eTitle.setHint("TITLE");
                    eUser.setHint("USERNAME");
                    ePw.setHint("PASSWORD");

                    //clears all text fields
                    eTitle.setText("");
                    eUser.setText("");
                    ePw.setText("");

                    //after adding, go back to list
                    openList();
                }
                //if empty text field
                else {
                    toastMessage("PLEASE FILL OUT ALL TEXT FIELDS");
                }
            }
        });
    }

    //adds new node to db
    public void addData(Node newEntry) {
        //insert and save success to bool
        boolean insertData = mDatabaseHelper.addData(newEntry);

        //display success
        if (insertData) {
            toastMessage("ENTRY SUCCESSFULLY CREATED");
        } else {
            toastMessage("ERROR CREATING ENTRY");
        }
    }

    //creates new toast message
    private void toastMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    //changes screens --> ACTIVITY_LIST
    public void openList() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

}