package com.sloths.speedy.shortsounds;

/**
 * Created by joel on 4/25/2015.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private String[] mDataSet;
    private Context context;
    private RVListener listener;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param trackNames String[] containing the data to populate views to be used by RecyclerView.
     */
    public RecyclerViewAdapter(String[] trackNames, RecyclerViewFragment rvf) {
        mDataSet = trackNames;
        this.context = rvf.getActivity();
        listener = rvf;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.track_view, viewGroup, false);
        // Define click listener for the ViewHolder's View.
        ViewHolder vh = new ViewHolder(v, viewGroup);

        return vh;
    }

    public String[] getTrackNames() {
        return mDataSet;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.setTitleView(position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.length;
    }


    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView vTitle;
        private final LinearLayout controller;
//        private final ListView effectsList;
        private boolean trackExpanded;
        private final ViewGroup viewGroup;

        public ViewHolder(View v, ViewGroup viewGroup) {
            super(v);
            v.setOnClickListener(new TrackItemClickListener());
            vTitle = (TextView) v.findViewById(R.id.track_title);
            controller = (LinearLayout) v.findViewById(R.id.track_child);
            controller.setVisibility(View.GONE);
            this.viewGroup = viewGroup;

            // Populate effects in the effects list the track keeps
            // TODO: Link the effects to the real ones in the database
            // Currently populating a fake effects list
//            List<ShortSoundTrackEffect> effects = getEffects();
//
//            effectsList = (ListView) v.findViewById(R.id.effects_list_b);
//            EffectsListAdapter effectsAdapter = new EffectsListAdapter(context, effects);
//            effectsList.setAdapter(effectsAdapter);
            trackExpanded = false;

        }

        public void setTitleView(int position) {
            vTitle.setText(mDataSet[position]);
        }

        private List<ShortSoundTrackEffect> getEffects() {
            List<ShortSoundTrackEffect> retList = new ArrayList<ShortSoundTrackEffect>();
            ShortSoundTrackEffect effect1 = new EqEffect();
            ShortSoundTrackEffect effect2 = new ReverbEffect();
            retList.add(effect1);
            retList.add(effect2);
            return retList;
        }

        /* The click listener for ListView in the navigation drawer */
        private class TrackItemClickListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(v, getPosition());
                if (!trackExpanded) {
                    // Expand track
                    controller.setVisibility(View.VISIBLE);
                    trackExpanded = true;
                } else {
                    controller.setVisibility(View.GONE);
                    trackExpanded = false;
                }
            }
        }
    }

    public interface RVListener {
        public void onItemClicked(View v, int position);
    }
}