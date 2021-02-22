package com.example.keepsafe_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class ListActivity extends AppCompatActivity {

    private static final String TAG = "DatabaseHelper";

    private DatabaseHelper mDatabaseHelper;
    private PINStorage mPINStorage;
    private ListView mListView;
    private EntryListAdapter adapter;
    private ImageView btnLogout, btnAdd, btnSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mListView = findViewById(R.id.list_ENTRIES);
        mDatabaseHelper = new DatabaseHelper(this);
        btnLogout = findViewById(R.id.button_LOGOUT);
        btnSetting = findViewById(R.id.button_SETTINGS);
        btnAdd = findViewById(R.id.button_ADD);
        mPINStorage = new PINStorage(this);

        //set visibility logout button
        boolean hasPIN = !mPINStorage.isEmpty();
        if (hasPIN) {
            btnLogout.setVisibility(View.VISIBLE);
        } else {
            btnLogout.setVisibility(View.INVISIBLE);
        }

        //displays entry list
        populateListView();

        //SETTINGS BUTTON
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings();
            }
        });

        //ADD BUTTON
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAdd();
            }
        });

        //LOGOUT BUTTON
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }
        });

    }

    //toast message
    private void toastMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    //changes screens --> ACTIVITY_SETTINGS
    public void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    //changes screens --> ACTIVITY_ADD
    public void openAdd() {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    //changes screens --> ACTIVITY_LOGIN
    public void openLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    //displays ListView
    public void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the List View");

        //get data and append to list
        Cursor data = mDatabaseHelper.getData();
        ArrayList<Node> listData = new ArrayList<>();

        while (data.moveToNext()) {
            //get value from database in column 1
            //add to listData
            String title = data.getString(1);    //gets title
            String user = data.getString(2);    //gets title
            String pw = data.getString(3);    //gets title

            Node n = new Node(title, user, pw);
            listData.add(n);
            Log.d(TAG, title + " " + user + " " + pw);
        }

        Node[] listArray = new Node[listData.size()];
        for (int i = 0; i < listArray.length; i++) {
            listArray[i] = listData.get(i);
        }
        Arrays.sort(listArray);

        //create list adapter and set adapter
        adapter = new EntryListAdapter(this, R.layout.adapter_view_layout, listArray);
        mListView.setAdapter(adapter);

        //SEARCH BAR
        EditText listFilter = findViewById((R.id.editText_searchBar));
        listFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (ListActivity.this).adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        //sets onItemClickListener to ListView ==> items in list clickable and editable
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Node entry = (Node) adapterView.getItemAtPosition(i);
                String title = entry.getTitle();
                Log.d(TAG, "onItemClick: You Clicked on " + title);

                //returns id associated w/ entry
                Cursor idData = mDatabaseHelper.getItemID(title);
                int itemID = -1;
                while (idData.moveToNext()) {
                    itemID = idData.getInt(0);
                }
                Log.d(TAG, "getItem: id: " + itemID);
                //returns user associated w/ entry
                Cursor userData = mDatabaseHelper.getItemUser(title);
                String itemUser = "null";
                while (userData.moveToNext()) {
                    itemUser = userData.getString(0);
                }
                Log.d(TAG, "getItem: user: " + itemUser);
                //returns pw associated w/ entry
                Cursor pwData = mDatabaseHelper.getItemPw(title);
                String itemPw = "null";
                while (pwData.moveToNext()) {
                    itemPw = pwData.getString(0);
                }
                Log.d(TAG, "getItem: pw: " + itemPw);

                //starts edit screen
                if (itemID > -1) {
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent editScreenIntent = new Intent(ListActivity.this, EditActivity.class);
                    editScreenIntent.putExtra("id", itemID);
                    editScreenIntent.putExtra("title", title);
                    editScreenIntent.putExtra("user", itemUser);
                    editScreenIntent.putExtra("pw", itemPw);
                    startActivity(editScreenIntent);
                } else {
                    toastMessage("No ID associated w/ that title");
                }
            }
        });

    }
}