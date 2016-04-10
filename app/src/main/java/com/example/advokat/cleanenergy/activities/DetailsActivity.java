package com.example.advokat.cleanenergy.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.advokat.cleanenergy.R;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final LinearLayout fixedCosts = (LinearLayout) findViewById(R.id.fixed_costs);
        final LinearLayout volatileCosts = (LinearLayout) findViewById(R.id.volatile_costs);

        final Switch changeCosts = (Switch) findViewById(R.id.switch_cost);

        if ((changeCosts != null) && (volatileCosts != null) && (fixedCosts != null)) {
            changeCosts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (changeCosts.isChecked()) {
                        volatileCosts.setVisibility(View.GONE);
                    }
                    if (!changeCosts.isChecked()) {
                        fixedCosts.setVisibility(View.GONE);
                        volatileCosts.setVisibility(View.VISIBLE);
                    }
                }
            });
        }


    }
}
