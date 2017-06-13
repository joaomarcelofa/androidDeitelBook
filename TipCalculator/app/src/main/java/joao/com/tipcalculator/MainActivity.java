package joao.com.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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

    private SeekBar customSeekBar;
    private OnSeekBarChangeListener customSeekBarListener;

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

        customSeekBar = (SeekBar) findViewById(R.id.customSeekBar);

        customSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
    }
}
