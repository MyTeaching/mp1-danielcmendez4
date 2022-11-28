package com.dcm.mp1;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
public class MainActivity extends AppCompatActivity implements TextWatcher, SeekBar.OnSeekBarChangeListener, AdapterView.OnItemSelectedListener {

    public static final String TAG = "MainActivity";

    private TextView tvPer; // tvPer means textViewPercent
    private SeekBar amountPer;
    private TextView amountTip;
    private TextView amountTtl; //ttl = Total
    private TextView ttlPerPersonTv;
    private double t; //t = tip
    private double ttl;
    private double ttlPerPerson;

    // for the spinner and adapter
    private Spinner spn; // spn = spinner
    private ArrayAdapter<CharSequence> adt; //adt = adapter
    private int sizeParty = 1;

    private RadioButton numRound;
    private RadioButton tRound;
    private RadioButton ttlRound;

    private double amountBill = 0.0;
    private double per = .20; // per = percent

    private static final NumberFormat currFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat perFormat = NumberFormat.getPercentInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate method:");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectToViews(); // to create a method

        amountPer.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener)this);

        amountTip.setText(currFormat.format(0));
        amountTtl.setText(currFormat.format(0));

        if (spn != null) {
            spn.setOnItemSelectedListener(this);
            spn.setAdapter(adt);
        }

        Log.d(TAG, "done for the methods of onCreate");
    }

    // the view
    private void connectToViews() {
        Log.d(TAG, "inside the method of connectToViews:");
        EditText amountTextBillEdit = (EditText)findViewById(R.id.amountTextBillEdit);
        amountTextBillEdit.addTextChangedListener((TextWatcher) this);
        tvPer = (TextView)findViewById(R.id.percentTextView);
        amountPer = (SeekBar)findViewById(R.id.amountPercent);
        //Tv means TextView
        findViewById(R.id.tipTextView);
        amountTip = (TextView)findViewById(R.id.amountTip);
        findViewById(R.id.totalTextView);
        amountTtl = (TextView)findViewById(R.id.amountTotal);
        spn = (Spinner) findViewById(R.id.number_people_spinner);
        adt = ArrayAdapter.createFromResource(this,R.array.label_spn, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item);
        // by doing both buttons and a radio group
        findViewById(R.id.group_radio);
        numRound = (RadioButton)findViewById(R.id.none_radio);
        tRound = (RadioButton)findViewById(R.id.tipRadio);
        ttlRound = (RadioButton)findViewById(R.id.totalRadio);
        ttlPerPersonTv = (TextView)findViewById(R.id.totalPerPerson_textView);
        Log.d(TAG, "connectToViews for method Finished");
    }

    @Override
    public void onTextChanged(CharSequence cS, int i, int i1, int i2) {
        Log.d(TAG, "onTextChanged method:=" + cS);

        try {
            amountBill = Double.parseDouble(cS.toString());
        }
        catch (Exception e) {
            amountBill = 0;
        }
        amountTtl.setText(currFormat.format(amountBill));
        calculate();

        Log.d(TAG, "onTextChanged method ended:");
    }

    @Override
    public void afterTextChanged(Editable editable) {

        Log.d(TAG, "afterTextChanged:");
    }

    @Override
    public void beforeTextChanged(CharSequence cS, int i, int i1, int i2) {
    }

    @Override
    public void onProgressChanged(SeekBar sB, int progress, boolean b) {
        sB.setProgress(progress);
        per = progress / 100.0;
        amountPer.setProgress(progress);
        calculate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar sB) {

        tvPer.setText(perFormat.format(per));
    }

    @Override
    public void onStopTrackingTouch(SeekBar sB) {

        calculate();
    }

    private void calculate() {
        Log.d(TAG, "calculate method:");

        if (numRound.isChecked()) { //"none" will show to correct bill, total, and tip and rounding will be finished
            t = amountBill * per;
            ttl = amountBill * t;
            ttlPerPerson = ttl / sizeParty;
        } else if (tRound.isChecked()) {
            t = amountBill * per;
            t = Math.ceil(t); //rounded to the nearest tip
            ttl = t + amountBill;
            ttlPerPerson = ttl / sizeParty;
        } else if (ttlRound.isChecked()) {
            t = amountBill * per;
            ttl = t + amountBill;
            ttl = Math.ceil(ttl); // rounding up to the nearest number for total
            ttlPerPerson = ttl / sizeParty;
        }

        setCalculate();

        Log.d(TAG, "The end of calculate Method: ");
    }

    private void setCalculate() {
        tvPer.setText(perFormat.format(per));
        amountTip.setText(currFormat.format(t));
        amountTtl.setText(currFormat.format(ttl));
        ttlPerPersonTv.setText(currFormat.format(ttlPerPerson));
    }

    @Override
    public void onItemSelected(AdapterView<?> av, View v, int i, long l) {
        sizeParty = Integer.parseInt(av.getItemAtPosition(i).toString());
        calculate();
    }

    @Override
    public void onNothingSelected(AdapterView<?> aV) {

    }

}