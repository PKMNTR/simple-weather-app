package com.example.pkmntr.greatweatherapp.views;

import android.content.Context;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.pkmntr.greatweatherapp.R;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    Fragment photoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timber.plant(new Timber.DebugTree());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/LeagueSpartan.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        if(savedInstanceState == null)
        {
            Bundle args = new Bundle();
            photoFragment = MainPhotoFragment.newInstance(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,photoFragment).commit();
        }
        /*else
        {
            photoFragment = getSupportFragmentManager().getFragment(savedInstanceState,"myFragment");
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,photoFragment).commit();
        }*/


    }

    @Override
    public void onBackPressed()
    {
        if(getSupportFragmentManager().getBackStackEntryCount()>0)
            getSupportFragmentManager().popBackStack();
        else
        super.onBackPressed();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        //getSupportFragmentManager().putFragment(outState, "myFragment", photoFragment);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
