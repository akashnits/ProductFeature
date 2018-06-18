package com.example.akash.zivameproductfeature.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.example.akash.zivameproductfeature.ProductFeatureFragment;
import com.example.akash.zivameproductfeature.R;

import java.util.ArrayList;
import java.util.List;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.TypeViewHolder> {



    private List<String> mList;
    private Context mContext;
    private int mPositionLastChecked;
    private RecyclerView mRecyclerView;
    private ProductFeatureFragment mProductFeatureFragment;
    private ClickHandler mClickHandler;

    public interface ClickHandler{
        public void onTypeClicked(int position);
    }


    public TypeAdapter(Context mContext, FragmentManager fm, ArrayList<String> typeList, ClickHandler clickHandler) {
        this.mContext = mContext;
        this.mProductFeatureFragment= ((ProductFeatureFragment) fm.findFragmentByTag("productFeatureFragment"));
        this.mPositionLastChecked= mProductFeatureFragment.getmCheckedPosition();
        this.mList= typeList;
        this.mClickHandler= clickHandler;

    }



    @NonNull
    @Override
    public TypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_type, parent, false);
        return new TypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeViewHolder holder, int position) {
        holder.ctvType.setText(mList.get(position));

        if(((position + 1) % 3 == 0 ) || (mList.size()-1 == position))
            holder.divider.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    public void setmRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    class TypeViewHolder extends RecyclerView.ViewHolder {
        CheckedTextView ctvType;
        View divider;

        public TypeViewHolder(View itemView) {
            super(itemView);
            ctvType= (CheckedTextView) itemView.findViewById(R.id.ctvType);
            divider= (View) itemView.findViewById(R.id.divider);

            ctvType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //wherever the click be, set the last checked position false if it's there
                    if(mProductFeatureFragment.getmMainActivity().isTypePreferenceChecked()) {
                        TypeViewHolder viewHolder = (TypeViewHolder)
                                mRecyclerView.findViewHolderForAdapterPosition(mPositionLastChecked);
                        viewHolder.ctvType.setSelected(false);
                        viewHolder.ctvType.setTextColor(Color.parseColor("#9370DB"));

                        //close the dialog
                        mClickHandler.onTypeClicked(-1);
                    }

                    //Clicked on  somewhere else or no checks
                    if (!mProductFeatureFragment.getmMainActivity().isTypePreferenceChecked()
                            || getAdapterPosition() != mPositionLastChecked) {
                        CheckedTextView ctv = (CheckedTextView) v;
                        ctv.setTextColor(Color.parseColor("#F08080"));
                        mPositionLastChecked = getAdapterPosition();
                        mProductFeatureFragment.setmCheckedPosition(mPositionLastChecked);
                        mProductFeatureFragment.getmMainActivity().setTypePreferenceChecked(true);
                        ctv.toggle();
                        //show the dialog
                        mClickHandler.onTypeClicked(getAdapterPosition());
                    }else {
                        mProductFeatureFragment.getmMainActivity().setTypePreferenceChecked(false);
                    }
                }
            });
        }
    }

    public int getmPositionLastChecked() {
        return mPositionLastChecked;
    }

    public void setmPositionLastChecked(int mPositionLastChecked) {
        this.mPositionLastChecked = mPositionLastChecked;
    }

    public void clearLastCheckedPosition(int position){
        TypeViewHolder viewHolder= (TypeViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
        viewHolder.ctvType.setTextColor(Color.parseColor("#9370DB"));
    }
}
