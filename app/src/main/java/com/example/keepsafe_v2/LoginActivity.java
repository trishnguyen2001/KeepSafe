package com.example.keepsafe_v2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private int[] inputPIN;
    private int index;
    private PINStorage mPINStorage;
    private TextView inputBar;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnLogin;
    ImageView btnDel;

    private static final String TAG = "loginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputBar = findViewById(R.id.editText_inputBar);
        inputBar.setText("    ");
        inputPIN = new int[4];
        index = 0;
        mPINStorage = new PINStorage(this);
        btn1 = findViewById(R.id.input_1);
        btn2 = findViewById(R.id.input_2);
        btn3 = findViewById(R.id.input_3);
        btn4 = findViewById(R.id.input_4);
        btn5 = findViewById(R.id.input_5);
        btn6 = findViewById(R.id.input_6);
        btn7 = findViewById(R.id.input_7);
        btn8 = findViewById(R.id.input_8);
        btn9 = findViewById(R.id.input_9);
        btn0 = findViewById(R.id.input_0);
        btnDel = findViewById(R.id.input_DEL);
        btnLogin = findViewById(R.id.button_LOGIN);

        //1-BUTTON
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFull(inputPIN)) {
                    inputPIN[index] = 1;
                    index++;
                    inputBar.append("*    ");
                } else {
                    index--;
                    inputPIN[index] = 1;
                }
            }
        });

        //2-BUTTON
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFull(inputPIN)) {
                    inputPIN[index] = 2;
                    index++;
                    inputBar.append("*    ");
                } else {
                    index--;
                    inputPIN[index] = 2;
                }
            }
        });

        //3-BUTTON
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFull(inputPIN)) {
                    inputPIN[index] = 3;
                    index++;
                    inputBar.append("*    ");
                } else {
                    index--;
                    inputPIN[index] = 3;
                }
            }
        });

        //4-BUTTON
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFull(inputPIN)) {
                    inputPIN[index] = 4;
                    index++;
                    inputBar.append("*    ");
                } else {
                    index--;
                    inputPIN[index] = 4;
                }
            }
        });

        //5-BUTTON
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFull(inputPIN)) {
                    inputPIN[index] = 5;
                    index++;
                    inputBar.append("*    ");
                } else {
                    index--;
                    inputPIN[index] = 5;
                }
            }
        });

        //6-BUTTON
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFull(inputPIN)) {
                    inputPIN[index] = 6;
                    index++;
                    inputBar.append("*    ");
                } else {
                    index--;
                    inputPIN[index] = 6;
                }
            }
        });

        //7-BUTTON
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFull(inputPIN)) {
                    inputPIN[index] = 7;
                    index++;
                    inputBar.append("*    ");
                } else {
                    index--;
                    inputPIN[index] = 7;
                }
            }
        });

        //8-BUTTON
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFull(inputPIN)) {
                    inputPIN[index] = 8;
                    index++;
                    inputBar.append("*    ");
                } else {
                    index--;
                    inputPIN[index] = 8;
                }
            }
        });

        //9-BUTTON
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFull(inputPIN)) {
                    inputPIN[index] = 9;
                    index++;
                    inputBar.append("*    ");
                } else {
                    index--;
                    inputPIN[index] = 9;
                }
            }
        });

        //0-BUTTON
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFull(inputPIN)) {
                    inputPIN[index] = 0;
                    index++;
                    inputBar.append("*    ");
                } else {
                    index--;
                    inputPIN[index] = 0;
                }
            }
        });

        //DELETE BUTTON
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index--;            //decrements index so future inputPIN[] entries
                // will be overridden @ those indexes

                if (index > 4) {
                    try {
                        throw new Exception("illegal index");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //delete "*"
                if (index == 0) {
                    inputBar.setText("    ");
                } else if (index == 1) {
                    inputBar.setText("    *    ");
                } else if (index == 2) {
                    inputBar.setText("    *    *    ");
                } else {
                    inputBar.setText("    *    *    *    ");
                }
            }
        });

        //LOGIN BUTTON
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = false;

                //parse input int[] into String pin
                String input = "";
                for (int i : inputPIN) {
                    input = input + i;
                }
                Log.d(TAG, "INPUT PIN: " + input);

                //returns PIN
                Cursor pinData = mPINStorage.getData();
                String currentPIN = "temp";                     //temp pin before retrieval
                pinData.moveToLast();
                String current = pinData.getString(1);

                //ensures most recent PIN is used
                ArrayList<String> pinList = new ArrayList<>();
                pinList.add(current);
                int pinIndex = pinList.size() - 1;
                currentPIN = pinList.get(pinIndex);
                Log.d(TAG, "CURRENT PIN: " + currentPIN);

                //input = saved pin?
                if (input.equals(currentPIN)) {
                    isValid = true;
                }

                //if correct pin, then let user in to entry list
                if (isValid) {
                    openList();
                } else {
                    toastMessage("INCORRECT PIN");
                    inputPIN = new int[4];  //clears input for user to retry
                    index = 0;              //resets index
                    inputBar.setText("  "); //resets input bar
                }
            }
        });
    }

    //checks if input int[] is full
    public boolean isFull(int[] a) {
        return a.length > 4;
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
