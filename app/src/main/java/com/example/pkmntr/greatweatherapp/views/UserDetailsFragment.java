package com.example.pkmntr.greatweatherapp.views;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pkmntr.greatweatherapp.R;
import com.example.pkmntr.greatweatherapp.models.PhotosModels.Photos;
import com.squareup.picasso.Picasso;

import java.util.logging.StreamHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailsFragment extends Fragment {

    String username;
    String name;
    String portfolio_url;
    String user_url;
    String image_url;
    String bio = null;

    @BindView(R.id.iv_profile)
    ImageView ivPhotoProfile;
    @BindView(R.id.tv_photo_username)
    TextView tvPhotoUsername;
    @BindView(R.id.tv_photo_bio)
    TextView tvPhotoBio;
    @BindView(R.id.tv_photo_name)
    TextView tvPhotoName;
    @BindView(R.id.tv_photo_portfolio_link)
    TextView tvPortfolioLink;



    public UserDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Activity activity = getActivity();
        View rootview = inflater.inflate(R.layout.fragment_user_details, container, false);
        ButterKnife.bind(this, rootview);

        Context context = ivPhotoProfile.getContext();
        Picasso.with(context)
                .load(image_url)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(ivPhotoProfile);

        tvPhotoUsername.setText(username);
        tvPhotoName.setText(name);
        tvPortfolioLink.setText(portfolio_url);
        if(bio != null)
        {
            tvPhotoBio.setText(bio);
        }
        else
        {
            tvPhotoBio.setVisibility(View.INVISIBLE);
        }


        return rootview;
    }

    public static UserDetailsFragment newInstance(Bundle args) {
        UserDetailsFragment fragment = new UserDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!= null)
        {
            if(getArguments().containsKey("user"))
            {
                username = getArguments().getString("user");
            }
            if(getArguments().containsKey("image"))
            {
                image_url = getArguments().getString("image");
            }
            if(getArguments().containsKey("name"))
            {
                name = getArguments().getString("name");
            }
            if(getArguments().containsKey("user_link"))
            {
                user_url = getArguments().getString("user_link");
            }
            if(getArguments().containsKey("portfolio"))
            {
                portfolio_url = getArguments().getString("portfolio");
            }
            if(getArguments().containsKey("bio"))
            {
                bio = getArguments().getString("bio");
            }
        }
    }

    @OnClick (R.id.iv_profile)
    public void showProfileWebsiteOnClick() {
        if(user_url != null)
        {
            Uri uri = Uri.parse(user_url);
            startActivity(new Intent(Intent.ACTION_VIEW,uri));
        }
    }

    @OnClick(R.id.tv_photo_portfolio_link)
    public void showPortfolioWebsiteOnClick()
        {
            if(portfolio_url != null)
            {
                Uri uri = Uri.parse(portfolio_url);
                startActivity(new Intent((Intent.ACTION_VIEW),uri));
            }
        }


}
