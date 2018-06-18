package com.example.akash.zivameproductfeature;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private boolean isTypeChecked;
    private FragmentManager mFragmentManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getFragmentManager();
        if(savedInstanceState == null) {
            mFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, ProductFeatureFragment.newInstance(), "productFeatureFragment")
                    .commit();
        }
    }

    public boolean isTypePreferenceChecked() {
        return isTypeChecked;
    }

    public void setTypePreferenceChecked(boolean typePrefChecked) {
        isTypeChecked = typePrefChecked;
    }
}
