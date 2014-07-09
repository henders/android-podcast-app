package com.example.podcastplayer;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class AddRssDialogFragment extends DialogFragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_rss_dialog, container, false);
        View urlView = v.findViewById(R.id.editUrl);
        String url = ((EditText)urlView).toString();
        Log.i("RSS dialog", "RSS url: " + url);
        return v;
    }
}
