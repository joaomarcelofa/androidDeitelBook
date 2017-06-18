package joao.com.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class TipCalculator extends AppCompatActivity {

    // Instance variables

    private static final String BILL_TOTAL = "BILL_TOTAL"; //Usado como constante de estado
    private static final String CUSTOM_PERCENT = "CUSTOM_PERCENT";

    private double currentBillTotal;
    private int currentCustomPercent;
    private EditText tip10EditText;
    private EditText total10EditText;
    private EditText tip15EditText;
    private EditText total15EditText;
    private EditText tip20EditText;
    private EditText total20EditText;
    private TextView tipCustomTextView;
    private EditText tipCustomEditText;
    private EditText totalCustomEditText;
    private EditText billEditText;

    private SeekBar customSeekBar;
    private OnSeekBarChangeListener customSeekBarListener = new OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            currentCustomPercent = seekBar.getProgress();
            updateCustom();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {}
    };

    private TextWatcher billEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            try
            {
                currentBillTotal = Double.parseDouble(s.toString());
            }

            catch(NumberFormatException ex)
            {
                currentBillTotal = 0.0;
            }

            updateStandard();
            updateCustom();
        }

        @Override
        public void afterTextChanged(Editable s)
        {}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Verifica se o aplciativo começou ou está sendo reexecutado
        if (savedInstanceState == null) {
            currentBillTotal = 0.0;
            currentCustomPercent = 20;
        } else {
            currentBillTotal = savedInstanceState.getDouble(BILL_TOTAL);
            currentCustomPercent = savedInstanceState.getInt(CUSTOM_PERCENT);
        }

        bindReferences();

    }

    private void bindReferences() {
        // Obtendo os componentes EditText
        tip10EditText = (EditText) findViewById(R.id.tip10EditText);
        total10EditText = (EditText) findViewById(R.id.total10EditText);
        tip15EditText = (EditText) findViewById(R.id.tip15EditText);
        total15EditText = (EditText) findViewById(R.id.total15EditText);
        tip20EditText = (EditText) findViewById(R.id.tip20EditText);
        total20EditText = (EditText) findViewById(R.id.total20EditText);

        tipCustomTextView = (TextView) findViewById(R.id.tipCustomTextView);

        tipCustomEditText = (EditText) findViewById(R.id.tipCustomEditText);
        totalCustomEditText = (EditText) findViewById(R.id.totalCustomEditText);

        billEditText = (EditText) findViewById(R.id.billEditText);
        billEditText.addTextChangedListener(billEditTextWatcher);
        billEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) v;
                String s = editText.getText().toString();
                if(s.equals("0.00")) {
                    editText.setText("");
                }
            }
        });

        customSeekBar = (SeekBar) findViewById(R.id.customSeekBar);
        customSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
    }


    private void updateStandard()
    {
        double tenPercentTip    = currentBillTotal * 0.1;
        double tenPercentTotal  = currentBillTotal + tenPercentTip;
        tip10EditText.setText(String.format("%.02f", tenPercentTip));
        total10EditText.setText(String.format("%.02f", tenPercentTotal));

        double fifteenPercentTip    = currentBillTotal * .15;
        double fifteenPercentTotal  = currentBillTotal + fifteenPercentTip;
        tip15EditText.setText(String.format("%.02f", fifteenPercentTip));
        total15EditText.setText(String.format("%.02f", fifteenPercentTotal));

        double twelvePercentTip     = currentBillTotal * 0.2;
        double twelvePercentTotal   = currentBillTotal + twelvePercentTip;
        tip20EditText.setText(String.format("%.02f",twelvePercentTip));
        total20EditText.setText(String.format("%.02f",twelvePercentTotal));
    }

    private void updateCustom(){
        tipCustomTextView.setText(currentCustomPercent + "%");

        double customTipAmount      = currentBillTotal * currentCustomPercent * .01;
        double customTotalAmount    = currentBillTotal + customTipAmount;
        tipCustomEditText.setText(String.format("%.02f",customTipAmount));
        totalCustomEditText.setText(String.format("%.02f",customTotalAmount));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putDouble(BILL_TOTAL,currentBillTotal);
        outState.putInt(CUSTOM_PERCENT,currentCustomPercent);
    }

}
