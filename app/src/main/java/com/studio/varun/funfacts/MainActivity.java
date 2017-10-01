package com.studio.varun.funfacts;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout input;
    RelativeLayout output;
    RelativeLayout relativeLayout;
    Button showFactButton;
    TextView factLabel;
    MultiStateToggleButton categoryBtn,randomBtn;
    EditText numOrDate;
    Button submitBtn;
    String categoryArr[]={"trivia","math","date","year"};
    int globadIdx=0,globalVal=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Declare our view variables and assign them the views from the layout file
        input = (RelativeLayout) findViewById(R.id.inputLayout);
        output = (RelativeLayout) findViewById(R.id.outputLayout);
        numOrDate = (EditText) findViewById(R.id.inputNumberOrDate);
        submitBtn = (Button) findViewById(R.id.submitButton);

        input.setVisibility(View.VISIBLE);
        output.setVisibility(View.INVISIBLE);

        factLabel = (TextView) findViewById(R.id.factTextView);
        showFactButton = (Button) findViewById(R.id.showFactButton);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

        categoryBtn = (MultiStateToggleButton) findViewById(R.id.mstb_multi_id);
        randomBtn = (MultiStateToggleButton) findViewById(R.id.mstb_random);

        randomBtn.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                //showToast(value+"");
                globalVal=value;
                if(value == 0)//random
                {
                    numOrDate.setVisibility(View.INVISIBLE);
                }
                else if(value == 1)//input
                {
                    numOrDate.setVisibility(View.VISIBLE);
                }
            }
        });
        categoryBtn.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
                //Log.d(TAG, "Position: " + position);
                //showToast(position+"");
                globadIdx = position;
                if(position == 2)//date
                {
                    numOrDate.setInputType(InputType.TYPE_CLASS_DATETIME);
                    numOrDate.setHint("mm/dd");
                }
                else
                {
                    numOrDate.setInputType(InputType.TYPE_CLASS_NUMBER);
                    numOrDate.setHint("number");
                }

            }
        });

        showFactButton.setOnClickListener(this);
        submitBtn.setOnClickListener(this);



    }

    private void showToast(String text) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.showFactButton:
                showInput();
                break;
            case R.id.submitButton:
                showFact();
                break;

        }
    }

    private void showInput() {
        int color = ColorWheel.getColor();
        relativeLayout.setBackgroundColor(color);
        showFactButton.setTextColor(color);

        randomBtn.setColors(Color.WHITE,color);
        categoryBtn.setColors(Color.WHITE,color);
        submitBtn.setTextColor(color);

        input.setVisibility(View.VISIBLE);
        output.setVisibility(View.INVISIBLE);
    }

    private void showFact() {
        if(numOrDate.getText().toString().isEmpty())
        {

            if(globalVal == 1)//input
            {
                showToast("No input");
                return;
            }

        }

        Retrofit r = new Retrofit.Builder()
                .baseUrl("http://numbersapi.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IFactRequest f = r.create(IFactRequest.class);

        Call<String> call = null;
        if(globalVal == 0)
            call = f.retrieveFact("/random/"+categoryArr[globadIdx]);
        else if(globalVal == 1)
            call = f.retrieveFact("/"+numOrDate.getText().toString()+"/"+categoryArr[globadIdx]);


        if(call != null)
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                {
                    factLabel.setText(response.body());
                    showOutput();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }

    private void showOutput() {
        int color = ColorWheel.getColor();
        relativeLayout.setBackgroundColor(color);
        showFactButton.setTextColor(color);
        input.setVisibility(View.INVISIBLE);
        output.setVisibility(View.VISIBLE);
    }


}


