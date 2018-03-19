package com.example.kashishgupta.hackathon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;

public class RegisterationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);

        // Inflate the layout for this fragment
        final TextView tvgov = (TextView) findViewById(R.id.textView2);
        final Button bgov = (Button) findViewById(R.id.button);
        final TextView tvInd = (TextView) findViewById(R.id.textView2);
        final Button bInd = (Button) findViewById(R.id.button2);
        final TextView tvstu = (TextView) findViewById(R.id.textView4);
        final Button bstu = (Button) findViewById(R.id.button3);



        spinner.setItems("Government Employee", "Industrialist", "Student");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {


            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                switch (position) {
                    case 0:
                        tvInd.setVisibility(View.INVISIBLE);

                        bInd.setVisibility(View.INVISIBLE);

                        tvgov.setVisibility(View.VISIBLE);

                        bgov.setVisibility(View.VISIBLE);
                        tvstu.setVisibility(View.INVISIBLE);

                        bstu.setVisibility(View.INVISIBLE);

                        break;
                    case 1:
                        tvstu.setVisibility(View.INVISIBLE);

                        bstu.setVisibility(View.INVISIBLE);
                        tvInd.setVisibility(View.VISIBLE);

                        bInd.setVisibility(View.VISIBLE);
                        tvgov.setVisibility(View.INVISIBLE);

                        bgov.setVisibility(View.INVISIBLE);

                        break;
                    case 2:
                        tvgov.setVisibility(View.INVISIBLE);

                        bgov.setVisibility(View.INVISIBLE);
                        tvstu.setVisibility(View.VISIBLE);
                        tvInd.setVisibility(View.INVISIBLE);

                        bInd.setVisibility(View.INVISIBLE);
                        bstu.setVisibility(View.VISIBLE);

                        break;
                }
               // Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

            }    });}}
