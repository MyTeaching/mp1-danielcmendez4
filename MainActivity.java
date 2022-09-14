package com.example.tipscalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText b; // b = bill
    private TextView getTip1_tv, getTip2_tv, getTip3_tv, getTipx_tv, total1_tv, total2_tv, total3_tv, totalx_tv, Valseek;
    private SeekBar sB; // sB = SeekBar; t = tip; ttl = total
    private double amount_bill, t1, t2, t3, tx, ttl1, ttl2, ttl3, ttlx, txper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //when seekbar is 18 we should put a regular value
        txper = 0;

        // create any components
        b = findViewById(R.id.bill);
        getTip1_tv = findViewById(R.id.tip1);
        getTip2_tv = findViewById(R.id.tip2);
        getTip3_tv = findViewById(R.id.tip3);
        getTipx_tv = findViewById(R.id.tipx);
        total1_tv = findViewById(R.id.total1);
        total2_tv = findViewById(R.id.total2);
        total3_tv = findViewById(R.id.total3);
        totalx_tv = findViewById(R.id.totalx);
        sB = findViewById(R.id.seekBar);
        Valseek = findViewById(R.id.seekval);

        // When text is changed we shall handle it
        b.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence cS, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence cS, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable cS) {
                // to obtain the bill
                try {
                    amount_bill = Double.parseDouble(b.getText().toString());
                } catch (Exception e) {
                    
                }
                
                // convert all total values and the tip 
                
                updateUI();
            }
        });

        sB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar sB, int i, boolean b) {
                txper = i; 
                
                // convert UI for seekBAr of change 
                updateSeekUI(); 
            }

            @Override
            public void onStartTrackingTouch(SeekBar sB) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar sB) {

            }
        });
    }

    private void updateSeekUI() {
        Valseek.setText(txper + "%"); // to configure the seekVal

        // to upgrade the total and a tip
        tx = amount_bill * txper / 100;
        ttlx = amount_bill + tx;
        getTipx_tv.setText(ttlx + "");
        totalx_tv.setText(ttlx + "");
    }

    private void updateUI() {
        // calculation about the tips
        t1 = Math.round(10 * amount_bill) / 100;
        t2 = Math.round(15 * amount_bill) / 100;
        t3 = Math.round(20 * amount_bill) / 100;
        tx = Math.round(txper * amount_bill) / 100;
        getTip1_tv.setText(t1 + "");
        getTip2_tv.setText(t2 + "");
        getTip3_tv.setText(t3 + "");
        getTipx_tv.setText(tx + "");

        // calculation for the total
        t1 = amount_bill + t1;
        t2 = amount_bill + t2;
        t3 = amount_bill + t3;
        tx = amount_bill + tx;
        total1_tv.setText(t1 + "");
        total2_tv.setText(t2 + "");
        total3_tv.setText(t3 + "");
        totalx_tv.setText(tx + "");
    }
}