package com.example.adamhurwitz.hackingenvironment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class AsyncTaskFragment extends Fragment {

    private ArrayList<DoodleData> doodleDataList = new ArrayList<>();
    private GridViewAsyncAdapter gridViewAdapter;

    /**
     * Empty constructor for the AsyncTaskFragment() class.
     */
    public AsyncTaskFragment() {
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grid_recentview_layout, container, false);

        gridViewAdapter = new GridViewAsyncAdapter(getActivity(), R.layout.grid_recentitem_layout,
                doodleDataList);

        // Get a reference to the grid view layout and attach the adapter to it.
        GridView gridView = (GridView) view.findViewById(R.id.grid_recentview_layout);
        gridView.setAdapter(gridViewAdapter);

        // Create Toast
        // gridView.setOnItemClickListener(new OnItem... [auto-completes])
        /*gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            // parent = parent view, view = grid_item view, position = grid_item position
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // removed and replaced w/ Explicit Intent
                Toast.makeText(getActivity(), doodleDataList.get(position).getTitle(),
                        Toast.LENGTH_SHORT).show();
            }
        });*/

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            // parent is parent view, view is grid_item view, position is grid_item position
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(),
                        com.example.adamhurwitz.hackingenvironment.DetailActivity.class);

                /*String message = movieObjects.get(position).getTitle();
                 intent.putExtra(EXTRA_MESSAGE, message);*/

                // intent.putExtra(EXTRA_MESSAGE, movieObjects.get(position));

                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        doodleDataList.clear();
        gridViewAdapter.notifyDataSetChanged();
        getDoodleData();
    }

    private void getDoodleData() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        // Make sure that the device is actually connected to the internet before trying to get data
        // about the Google doodles.
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            FetchDoodleDataTask doodleTask = new FetchDoodleDataTask(gridViewAdapter,
                    doodleDataList);
            doodleTask.execute("release_date.desc");
        }
    }
}