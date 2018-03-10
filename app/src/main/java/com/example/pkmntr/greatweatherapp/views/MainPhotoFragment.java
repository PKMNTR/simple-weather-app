package com.example.pkmntr.greatweatherapp.views;


import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pkmntr.greatweatherapp.R;
import com.example.pkmntr.greatweatherapp.models.PhotosModels.Photos;
import com.example.pkmntr.greatweatherapp.models.PhotosModels.Urls;
import com.example.pkmntr.greatweatherapp.models.PhotosModels.User;
import com.example.pkmntr.greatweatherapp.models.WeatherModels.Current;
import com.example.pkmntr.greatweatherapp.rest.PhotosApiService;
import com.example.pkmntr.greatweatherapp.rest.WeatherApiService;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPhotoFragment extends Fragment {

    @BindView(R.id.iv_photo_main) ImageView mImageView;
    @BindView(R.id.tv_error_photo_main) TextView tvErrorPhoto;
    @BindView(R.id.tv_error_weather_main) TextView tvErrorWeather;
    @BindView(R.id.pb_loading_indicator_image) ProgressBar pbLoadingImage;
    @BindView(R.id.pb_loading_indicator_weather) ProgressBar pbLoadingWeather;
    @BindView(R.id.tv_weather_city) TextView tvWeatherCity;
    @BindView(R.id.tv_weather_phrase) TextView tvWeatherPrase;
    @BindView(R.id.tv_weather_temperature) TextView tvWeatherTemperature;
    @BindView(R.id.tv_weather_c) TextView tvWeatherC;

    private static final String BASE_URL = "https://api.unsplash.com";
    private static final String BASE_URL_WEATHER = "http://api.wunderground.com/api/";
    private static Retrofit retrofit = null;
    private static Retrofit retrofit_weather = null;
    private final static String API_KEY = "YOUR_API_KEY";
    private final static String API_KEY_WEATHER = "YOUR_API_KEY";

    public Photos photos = null;
    public Current weather = null;

    String user;
    String image;
    String name;
    String user_link;
    String portfolio;
    String bio;
    String photo;
    String ciudad;
    String frase;
    String temperatura;


    public MainPhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Activity activity = getActivity();
        View rootview = inflater.inflate(R.layout.fragment_main_photo, container, false);
        ButterKnife.bind(this, rootview);

        return rootview;
    }


    public static MainPhotoFragment newInstance(Bundle args) {
        MainPhotoFragment fragment = new MainPhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
        {
        }
    }

    private void getWeatherData(Call<Current> call_weather)
    {
        call_weather.enqueue(new Callback<Current>() {
            @Override
            public void onResponse(Call<Current> call, Response<Current> response) {
                Timber.i(response.body().getCurrentObservation().getDisplayLocation().getCity());

                pbLoadingWeather.setVisibility(View.INVISIBLE);
                tvWeatherCity.setVisibility(View.VISIBLE);
                tvWeatherPrase.setVisibility(View.VISIBLE);
                tvWeatherTemperature.setVisibility(View.VISIBLE);
                tvWeatherC.setVisibility(View.VISIBLE);

                Current clima = response.body();

                tvWeatherCity.setText(clima.getCurrentObservation().getDisplayLocation().getCity());
                tvWeatherPrase.setText(clima.getCurrentObservation().getWeather());
                tvWeatherTemperature.setText(clima.getCurrentObservation().getTempC().toString());

                weather = clima;

                }

            @Override
            public void onFailure(Call<Current> call, Throwable t) {
                pbLoadingWeather.setVisibility(View.INVISIBLE);
                tvErrorWeather.setVisibility(View.VISIBLE);

            }
        });


    }

    private void getPhotoData(Call<Photos> call)
    {
        call.enqueue(new Callback<Photos>() {
            @Override
            public void onResponse(Call<Photos> call, Response<Photos> response) {

                pbLoadingImage.setVisibility(View.INVISIBLE);
                mImageView.setVisibility(View.VISIBLE);

                Photos photo = response.body();
                Timber.i(photo.getUrls().getRegular());
                Context context = mImageView.getContext();
                String photoUrl = photo.getUrls().getRegular();
                Picasso.with(context)
                        .load(photoUrl)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(mImageView);
                photos = response.body();
            }

            @Override
            public void onFailure(Call<Photos> call, Throwable t) {
                pbLoadingImage.setVisibility(View.INVISIBLE);
                tvErrorPhoto.setVisibility(View.VISIBLE);
            }
        });

    }

    @OnClick(R.id.iv_photo_main)
    public void viewImageDetail()
    {
        if(photos != null)
        {
            Bundle args = new Bundle();
            args.putString("user", photos.getUser().getUsername());
            args.putString("image", photos.getUser().getProfileImage().getLarge());
            args.putString("name",photos.getUser().getName());
            args.putString("user_link",photos.getUser().getLinks().getHtml());
            args.putString("portfolio",photos.getUser().getPortfolioUrl());
            if(photos.getUser().getBio() != null)
                args.putString("bio",photos.getUser().getBio());
            Timber.i(photos.getUser().getUsername());

            UserDetailsFragment userDetailsFragment = UserDetailsFragment.newInstance(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.main_fragment_container,userDetailsFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (user != null) {
            Bundle args = new Bundle();
            args.putString("user", user);
            args.putString("image", image);
            args.putString("name", name);
            args.putString("user_link",user_link);
            args.putString("portfolio", portfolio);
            if(bio != null)
                args.putString("bio",photos.getUser().getBio());

            UserDetailsFragment userDetailsFragment = UserDetailsFragment.newInstance(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.main_fragment_container,userDetailsFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            Context context = getContext();
            Toast em_photo = Toast.makeText(context, "No hay datos que mostrar", Toast.LENGTH_SHORT);
            em_photo.show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(photos != null)
        {
            outState.putString("user", photos.getUser().getUsername());
            outState.putString("image", photos.getUser().getProfileImage().getLarge());
            outState.putString("name",photos.getUser().getName());
            outState.putString("user_link",photos.getUser().getLinks().getHtml());
            outState.putString("portfolio",photos.getUser().getPortfolioUrl());
            if(photos.getUser().getBio() != null)
                outState.putString("bio",photos.getUser().getBio());
            outState.putString("photo",photos.getUrls().getRegular());
        }
        if(weather != null)
        {
            outState.putString("ciudad", weather.getCurrentObservation().getDisplayLocation().getCity());
            outState.putString("frase", weather.getCurrentObservation().getWeather());
            outState.putString("temperatura", weather.getCurrentObservation().getTempC().toString());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null)
        {
            restorePreviousState(savedInstanceState);
        }
        else
        {
            String lat = String.valueOf(19.2) ;
            String longitud = String.valueOf(-99.2);


            //Declaracion de retrofit para clima
            if(retrofit_weather == null)
            {
                retrofit_weather = new Retrofit.Builder()
                        .baseUrl(BASE_URL_WEATHER)
                        .addConverterFactory(GsonConverterFactory.create()).build();
            }

            WeatherApiService weatherApiService = retrofit_weather.create(WeatherApiService.class);
            Call<Current> call_weather = weatherApiService.getCurrentCoditions(API_KEY_WEATHER, lat, longitud);


            // Declaracion de retrofit para imagen
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }

            PhotosApiService photosApiService = retrofit.create(PhotosApiService.class);
            Call<Photos> call = photosApiService.getRandomPhoto(API_KEY);

            //Llamada a datos de clima
            getWeatherData(call_weather);

            //Llamada a datos de imagen
            getPhotoData(call);
        }
    }

    public void restorePreviousState(@Nullable Bundle savedInstanceState)
    {
        savedInstanceState.getString("user", user);
        savedInstanceState.getString("image", image);
        savedInstanceState.getString("name",name);
        savedInstanceState.getString("user_link",user_link);
        savedInstanceState.getString("portfolio",portfolio);
        savedInstanceState.getString("bio",bio);
        savedInstanceState.getString("photo",photo);
        savedInstanceState.getString("ciudad", ciudad);
        savedInstanceState.getString("frase", frase);
        savedInstanceState.getString("temperatura", temperatura);


        pbLoadingImage.setVisibility(View.INVISIBLE);
        mImageView.setVisibility(View.VISIBLE);
        pbLoadingWeather.setVisibility(View.INVISIBLE);
        tvWeatherCity.setVisibility(View.VISIBLE);
        tvWeatherPrase.setVisibility(View.VISIBLE);
        tvWeatherTemperature.setVisibility(View.VISIBLE);


        Context context = mImageView.getContext();
        Picasso.with(context)
                .load(photo)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(mImageView);

        tvWeatherCity.setText(ciudad);
        tvWeatherPrase.setText(frase);
        tvWeatherTemperature.setText(temperatura);
    }
}
