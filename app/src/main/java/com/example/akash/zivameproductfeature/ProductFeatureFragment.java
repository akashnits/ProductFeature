package com.example.akash.zivameproductfeature;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.akash.zivameproductfeature.adapter.TypeAdapter;
import com.example.akash.zivameproductfeature.model.Value;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFeatureFragment extends android.app.Fragment implements TypeAdapter.ClickHandler {


    @BindView(R.id.product_feature)
    TextView productFeature;
    @BindView(R.id.ivClose)
    ImageView ivClose;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.tvLearnMore)
    TextView tvLearnMore;
    @BindView(R.id.llDescription)
    LinearLayout llDescription;
    @BindView(R.id.tvGeneral)
    TextView tvGeneral;
    @BindView(R.id.llProductFeature)
    LinearLayout llProductFeature;
    Unbinder unbinder;
    @BindView(R.id.rvType)
    RecyclerView rvType;
    @BindView(R.id.cvDescription)
    CardView cvDescription;


    private int mCheckedPosition;
    private MainActivity mMainActivity;
    private Context mContext;
    private TypeAdapter mTypeAdapter;
    private ArrayList<String> nameList;
    private ArrayList<String> descriptionList;

    public ProductFeatureFragment() {
        // Required empty public constructor
    }

    public static ProductFeatureFragment newInstance() {
        return new ProductFeatureFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        init();

        mTypeAdapter = new TypeAdapter(mContext, getFragmentManager(), nameList, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_feature, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvDescription.setVisibility(View.GONE);
                mTypeAdapter.clearLastCheckedPosition(mCheckedPosition);
                mMainActivity.setTypePreferenceChecked(false);
                mCheckedPosition= -1;
                mTypeAdapter.setmPositionLastChecked(-1);
            }
        });

        GridLayoutManager gl = new GridLayoutManager(mContext, 3);
        rvType.setLayoutManager(gl);
        mTypeAdapter.setmRecyclerView(rvType);


        rvType.setAdapter(mTypeAdapter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivity = (MainActivity) getActivity();
        mContext = context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public int getmCheckedPosition() {
        return mCheckedPosition;
    }

    public void setmCheckedPosition(int mCheckedPosition) {
        this.mCheckedPosition = mCheckedPosition;
    }

    public MainActivity getmMainActivity() {
        return mMainActivity;
    }

    public void setmMainActivity(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity;
    }


    @Override
    public void onTypeClicked(int position) {
        if (position == -1) {
            cvDescription.setVisibility(View.GONE);
        } else {
            cvDescription.setVisibility(View.VISIBLE);
            //set the description text
            tvDescription.setText(descriptionList.get(position));
        }
    }

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("Features.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void init(){
        Gson gson= new Gson();

        String jsonString= loadJSONFromAsset(mContext);
        List<Value> values= Arrays.asList(gson.fromJson(jsonString, Value[].class));

        nameList = new ArrayList<>();
        descriptionList= new ArrayList<>();
        for(int i=0; i< values.size(); i++){
            nameList.add(values.get(i).getName());
            descriptionList.add(values.get(i).getDescription());
        }

    }
}
