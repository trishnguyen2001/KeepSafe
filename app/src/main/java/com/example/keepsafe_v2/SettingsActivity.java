package com.example.keepsafe_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "settingsActivity";

    private PINStorage mPINStorage;
    private Switch pinSwitch;
    private Button btnSavePIN, btnBack;
    private EditText input;
    private boolean validSetPin;
    private boolean hasPIN;
    private final int[] VALID_INPUT = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mPINStorage = new PINStorage(this);
        pinSwitch = findViewById(R.id.switch_pin);
        btnSavePIN = findViewById(R.id.button_savePIN);
        btnBack = findViewById(R.id.button_BACKSETTING);
        input = findViewById(R.id.editText_editPIN);
        validSetPin = false;
        hasPIN = !mPINStorage.isEmpty();

        //INITIALIZES DISPLAY
        if (hasPIN) {
            pinSwitch.setText("ON");
            pinSwitch.setChecked(true);
            btnSavePIN.setVisibility(View.VISIBLE);
            input.setVisibility(View.VISIBLE);
            input.setHint("    *    *    *    *    ");
        } else {
            pinSwitch.setText("OFF");
            pinSwitch.setChecked(false);
            btnSavePIN.setVisibility(View.INVISIBLE);
            input.setVisibility(View.INVISIBLE);
        }

        //PIN SWITCH
        pinSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                //updates display after switch is toggled
                if (checked) {
                    //do stuff when Switch is ON
                    pinSwitch.setText("ON");
                    pinSwitch.setChecked(true);
                    btnSavePIN.setVisibility(View.VISIBLE);
                    input.setVisibility(View.VISIBLE);
                    toastMessage("INPUT 4-DIGIT PIN");
                } else {
                    //do stuff when Switch if OFF
                    pinSwitch.setText("OFF");
                    pinSwitch.setChecked(false);
                    btnSavePIN.setVisibility(View.INVISIBLE);
                    input.setVisibility(View.INVISIBLE);

                    //PIN option off ==> clear PIN database
                    mPINStorage.clearDatabase("pinTable");

                    //get data and append to list
                    Cursor pinData = mPINStorage.getData();
                    if (!(pinData.getCount() > 0)) {
                        Log.d(TAG, "cleared PINStorage");
                    }
                }
            }
        });

        //SAVE_PIN BUTTON
        btnSavePIN.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                //runs in bg and won't lag app
                Thread t = new Thread() {
                    public void run() {
                        //receive input PIN as string and create new PIN obj
                        String sPIN = input.getText().toString();
                        Log.d(TAG, "inputted PIN: " + sPIN);
                        validSetPin = validPin(sPIN);                           //returns true if inputPin is valid and can be set
                        if (validSetPin) {
                            final boolean added = mPINStorage.newPin(sPIN);
                            if (added) {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        toastMessage("PIN SUCCESSFULLY SET");
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        toastMessage("PIN COULDN'T BE SET");
                                    }
                                });
                            }

                            //checks to see if PIN has been added
                            boolean checkAdd = !mPINStorage.isEmpty();
                            Log.d(TAG, "Add PIN status: " + checkAdd);
                        } else {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    toastMessage("PLEASE ENTER A VALID, 4-DIGIT PIN");
                                }
                            });
                            input.setText("");  //resets input box
                        }
                    }
                };
                t.start();
            }
        });

        //BACK BUTTON
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if valid pin set OR pin disabled OR already has pin --> allowed to go back to list
                if (validSetPin && pinSwitch.isChecked() || !pinSwitch.isChecked() || hasPIN) {
                    openList();
                } else {
                    toastMessage("PLEASE ENTER A VALID PIN OR TURN THE PIN OPTION OFF");
                }
            }
        });
    }

    //changes screens --> ACTIVITY_LIST
    public void openList() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    //checks if inputted PIN is in a valid format
    private boolean validPin(String inputPin) {
        //checks input is only 4 digits
        int length = inputPin.length();
        if (length != 4) {
            toastMessage("4 DIGITS ONLY");
            return false;
        }

        //checks if input is a digit 0-9
        int validCounter = 0;
        for (int j = 0; j < length; j++) {
            //gets each char from inputPin and turns into int
            String subInput = inputPin.substring(j, j + 1);
            int p = Integer.parseInt(subInput);
            boolean validDigit = false;
            for (int i = 0; i < VALID_INPUT.length; i++) {
                //parses VALID_INPUT and checks each digit against input p
                if (p == VALID_INPUT[i]) {
                    validDigit = true;
                }
            }
            //returns false if input p != anything in VALID_INPUT
            if (!validDigit) {
                toastMessage("DIGITS 0-9 ONLY");
                return false;
            }
            //continue checking the rest of the inputs if current p is valid digit
            else {
                validCounter++;
            }
        }
        //must have all 4 inputs be valid digits
        return validCounter == 4;
    }

    //toast message
    private void toastMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}