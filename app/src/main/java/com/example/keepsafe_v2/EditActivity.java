package com.example.keepsafe_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    private Button btnSave, btnCancel, btnDelete;
    private EditText editTitle, editUser, editPw;
    private DatabaseHelper mDatabaseHelper;
    private String title, user, pw;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        btnSave = findViewById(R.id.button_SAVE);
        btnCancel = findViewById(R.id.button_CANCEL);
        btnDelete = findViewById(R.id.button_DELETE);
        editTitle = findViewById(R.id.editText_EDITTITLE);
        editUser = findViewById(R.id.editText_EDITUSER);
        editPw = findViewById(R.id.editText_EDITPW);
        mDatabaseHelper = new DatabaseHelper(this);

        //get intent extras from ListActivity
        Intent receivedIntent = getIntent();

        //get itemID, title, itemUser, itemPw
        id = receivedIntent.getIntExtra("id", -1);
        title = receivedIntent.getStringExtra("title");
        user = receivedIntent.getStringExtra("user");
        pw = receivedIntent.getStringExtra("pw");

        //set text to show current title, user, pw
        editTitle.setText(title);
        editUser.setText(user);
        editPw.setText(pw);

        //DELETE BUTTON
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //deletes entry from database
                mDatabaseHelper.deleteTitle(id, title);
                mDatabaseHelper.deleteUser(id, user);
                mDatabaseHelper.deletePw(id, user);

                //clears text fields
                editTitle.setText("");
                editUser.setText("");
                editPw.setText("");

                //toast
                toastMessage("ENTRY DELETED");

                openList();
            }
        });

        //SAVE BUTTON
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gets new values and updates entry
                String newTitle = editTitle.getText().toString();
                String newUser = editUser.getText().toString();
                String newPw = editPw.getText().toString();

                //checks if all text fields aren't null
                if (!newTitle.equals("") || !newUser.equals("") || !newPw.equals("")) {
                    mDatabaseHelper.updateTitle(newTitle, id, title);
                    mDatabaseHelper.updateUser(newUser, id, user);
                    mDatabaseHelper.updatePw(newPw, id, pw);

                    openList();
                } else {
                    toastMessage("PLEASE FILL OUT ALL TEXT FIELDS");
                }
            }
        });

        //CANCEL BUTTON
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //goes back to list screen
                openList();
            }
        });
    }

    //changes screens --> ACTIVITY_LIST
    public void openList() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    //toast message
    private void toastMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}