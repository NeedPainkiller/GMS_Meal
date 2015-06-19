package com.gms.gms_meal.Rate_Package;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.andexert.library.RippleView;
import com.gms.gms_meal.R;
import com.gms.gms_meal.lib.FontAwesomeText;

/**
 * Created by kam6376 on 2015-06-19.
 */
public class RateDialog extends Dialog implements RippleView.OnRippleCompleteListener {
    RippleView rippleFuck, rippleShit, rippleSoso, rippleGood, rippleWell;


    Context context;

    public RateDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.d_vote_rate);

        rippleFuck = (RippleView) findViewById(R.id.rateRippleFuck);
        rippleFuck.setOnRippleCompleteListener(this);
        rippleShit = (RippleView) findViewById(R.id.rateRippleShit);
        rippleShit.setOnRippleCompleteListener(this);
        rippleSoso = (RippleView) findViewById(R.id.rateRippleSoso);
        rippleSoso.setOnRippleCompleteListener(this);
        rippleGood = (RippleView) findViewById(R.id.rateRippleGood);
        rippleGood.setOnRippleCompleteListener(this);
        rippleWell = (RippleView) findViewById(R.id.rateRippleWell);
        rippleWell.setOnRippleCompleteListener(this);



    }

    @Override
    public void onComplete(RippleView rippleView) {

        switch (rippleView.getId()) {
            case R.id.rateRippleFuck:
                dismiss();
                break;
            case R.id.rateRippleShit:
                dismiss();
                break;
            case R.id.rateRippleSoso:
                dismiss();
                break;
            case R.id.rateRippleGood:
                dismiss();
                break;
            case R.id.rateRippleWell:
                dismiss();
                break;

        }

    }
}
