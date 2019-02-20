package com.snehal.homework1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    boolean switchStatus = true;
    double bac;
    double weight = 0;
    int progVal = 0;
    double r;
    double a;
    boolean clicked = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mActionBarToolbar;
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle(R.string.title);
        mActionBarToolbar.setNavigationIcon(R.mipmap.wineglass);
        mActionBarToolbar.setBackgroundColor(getResources().getColor(R.color.colorBlack));

        final Button btnSave = (Button) findViewById(R.id.btnSave);
        final Button btnAddDrink = (Button)findViewById(R.id.btnAddDrink);
        Button reset = (Button)findViewById(R.id.btnReset);

        final EditText w = (EditText) findViewById(R.id.editTextWeight);
        final Switch switchGender = (Switch) findViewById(R.id.switchGender);
        final RadioGroup ad = (RadioGroup)findViewById(R.id.radioGroup);
        final RadioButton size1= (RadioButton) findViewById(R.id.radioBtnSize1);
        final RadioButton size2= (RadioButton) findViewById(R.id.radioBtn2);
        final RadioButton size3= (RadioButton) findViewById(R.id.radioBtnSize3);
        final SeekBar seekBarAlcPer = (SeekBar)findViewById(R.id.simpleSeekBar);
        final TextView bacResult = (TextView) findViewById(R.id.textBac);
        final TextView bacResultStatus = (TextView) findViewById(R.id.textStatus);
        final ProgressBar prog = (ProgressBar) findViewById(R.id.progressBar);
        final TextView alcPer = (TextView) findViewById(R.id.textViewAlcPer);
        final TextView yourStatus = (TextView)findViewById(R.id.textViewStatusVal);
        final TextView alcPerVal = (TextView) findViewById(R.id.textViewAlcPer);



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              clicked = true;

                //Chacking for empty input for weight
                if(TextUtils.isEmpty(w.getText())) {
                    w.setError("Enter the weight in lb.");
                }
                else if(Double.valueOf(w.getText().toString()) == 0){
                    w.setError("Weight can't be zero");
                }
                else {
                    double prevWeight = weight;
                    double prevR = r;
                    weight = Double.valueOf(w.getText().toString());
                    if (switchGender.isChecked()) {
                        r = 0.68;
                    } else {
                        r = 0.55;
                    }

                    if(prevWeight != weight || prevR != r)
                    {
                        bac = 0;
                    }

                }

            }
        });

        seekBarAlcPer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                i = ((int)Math.round(i));
                seekBar.setProgress(i);
                alcPer.setText(Integer.toString(seekBar.getProgress()) + "%");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Reset
                size1.setChecked(true);
                size2.setChecked(false);
                size3.setChecked(false);
                seekBarAlcPer.setProgress(5);
                w.setText("");
                w.setError(null);
                switchStatus = true;
                switchGender.setChecked(true);
                bac =0.00;
                bacResult.setText("BAC LEVEL: 0.00");
                yourStatus.setText("You're safe");
                yourStatus.setBackgroundColor(getResources().getColor(R.color.yourStatusGreen));
                prog.setProgress(progVal);
                btnAddDrink.setEnabled(true);
                btnSave.setEnabled(true);
                clicked=false;

            }
        });


        btnAddDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(w.getText()) ) {
                    w.setError("Enter the weight in lb.");
                }
                else if (clicked){
                    if(!TextUtils.isEmpty(w.getText()) ){
                        weight = Double.valueOf(w.getText().toString());

                        if(weight == 0){
                            w.setError("Weight can't be zero");
                        }
                        else{
                            if(size1.isChecked()){
                                a = 1 * seekBarAlcPer.getProgress();
                            }
                            else if (size2.isChecked()){
                                a = 5 * seekBarAlcPer.getProgress();
                            }
                            else {
                                a = 12 * seekBarAlcPer.getProgress();
                            }

                            if (switchGender.isChecked()) {
                                r = 0.68;
                            } else {
                                r = 0.55;
                            }

                            bac +=a * 6.24/ (weight * r *100);

                            double bacProg = (bac/0.25) * 100;
                            bacProg = Math.round(bacProg);

                            if (bac < 25) {
                                prog.setProgress((int) bacProg);
                            } else {
                                prog.setMax((int) bacProg);
                            }

                            double bacRes = bac * 100;
                            bacRes = Math.round(bacRes);
                            bacRes = bacRes / 100;

                            if(bacRes == 0.0){

                                bacResult.setText("BAC LEVEL: 0.00");
                            }
                            else {
                                bacResult.setText("BAC LEVEL: " + Double.toString(bacRes));
                            }
                            btnAddDrink.setEnabled(true);
                            btnSave.setEnabled(true);

                            if (bac >= 0.25) {

                                btnAddDrink.setEnabled(false);
                                btnSave.setEnabled(false);

                                Toast.makeText(MainActivity.this, "No more drinks for you", Toast.LENGTH_LONG).show();
                                bacResultStatus.setText("Your status:");
                            }
                            if (bac <= 0.08) {

                                bacResultStatus.setText("Your status:");
                                yourStatus.setText("You're safe");
                                yourStatus.setBackgroundColor(getResources().getColor(R.color.yourStatusGreen));
                            } else if (bac == 0.08 || bac < 0.20) {

                                bacResultStatus.setText("Your status:");
                                yourStatus.setText("Be Careful");
                                yourStatus.setBackgroundColor(getResources().getColor(R.color.yourStatusOrange));
                            } else if (bac >= 0.20 || bac < 0.25) {

                                bacResultStatus.setText("Your status:");
                                yourStatus.setText("Over the limit!");
                                yourStatus.setBackgroundColor(getResources().getColor(R.color.yourStatusRed));
                            }

                        }
                    }


                }
                else{
                    Toast.makeText(MainActivity.this,"Please click save button",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
