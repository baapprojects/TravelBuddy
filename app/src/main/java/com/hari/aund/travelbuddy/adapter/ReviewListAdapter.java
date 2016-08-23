package com.hari.aund.travelbuddy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hari.aund.travelbuddy.R;
import com.hari.aund.travelbuddy.data.ReviewDetail;

import java.util.ArrayList;

/**
 * Created by Hari Nivas Kumar R P on 8/20/2016.
 */

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder> {

    private static final String LOG_TAG = ReviewListAdapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<ReviewDetail> mReviewDetailsArrayList;

    public ReviewListAdapter(Context context,
                             ArrayList<ReviewDetail> reviewDetailsArrayList) {
        mContext = context;
        mReviewDetailsArrayList = reviewDetailsArrayList;
    }

    @Override
    public ReviewListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.recycler_view_reviews, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReviewListAdapter.ViewHolder viewHolder, int position) {
        ReviewDetail reviewDetail = mReviewDetailsArrayList.get(position);

        viewHolder.reviewerName.setText(reviewDetail.getReviewerName());
        viewHolder.reviewContent.setText(reviewDetail.getContent());
        viewHolder.reviewerRating.setRating(reviewDetail.getRatingValue());
    }

    @Override
    public int getItemCount() {
        return mReviewDetailsArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView reviewerName, reviewContent;
        RatingBar reviewerRating;

        public ViewHolder(View itemView) {
            super(itemView);
            reviewerName = (TextView) itemView.findViewById(R.id.reviewer_name);
            reviewContent = (TextView) itemView.findViewById(R.id.review_content);
            reviewerRating = (RatingBar) itemView.findViewById(R.id.reviewer_rating);
        }
    }
}
