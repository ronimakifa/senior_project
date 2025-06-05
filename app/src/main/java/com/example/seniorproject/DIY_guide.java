package com.example.seniorproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Fragment that provides a DIY (Do It Yourself) guide for plant care.
 * This fragment can be instantiated with parameters and displays DIY-related information.
 *
 * <p>Use the {@link #newInstance(String, String)} factory method to create a new instance with parameters.</p>
 *
 * @author Roni Zuckerman
 * @version 1.0
 * @since 2024-06-07
 */
public class DIY_guide extends Fragment {

    /** Argument key for the first parameter. */
    private static final String ARG_PARAM1 = "param1";
    /** Argument key for the second parameter. */
    private static final String ARG_PARAM2 = "param2";

    /** The first parameter for fragment initialization. */
    private String mParam1;
    /** The second parameter for fragment initialization. */
    private String mParam2;

    /**
     * Required empty public constructor.
     * Use {@link #newInstance(String, String)} to create an instance with parameters.
     */
    public DIY_guide() {
        // Required empty public constructor
    }

    /**
     * Factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param param1 The first parameter for the fragment.
     * @param param2 The second parameter for the fragment.
     * @return A new instance of fragment DIY_guide.
     * @author Roni Zuckerman
     */
    public static DIY_guide newInstance(String param1, String param2) {
        DIY_guide fragment = new DIY_guide();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called to do initial creation of the fragment.
     * Retrieves parameters from the arguments bundle if available.
     *
     * @param savedInstanceState If the fragment is being re-created from a previous saved state, this is the state.
     * @author Roni Zuckerman
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * Inflates the layout for this fragment and loads a YouTube video in a WebView.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return The View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d_i_y_guide, container, false);

        WebView webView = view.findViewById(R.id.youtubeWebView);
        webView.getSettings().setJavaScriptEnabled(true);
        String videoId = "Fy8kP2EYpcY";
        String html = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoId + "\" frameborder=\"0\" allowfullscreen></iframe>";
        webView.loadData(html, "text/html", "utf-8");

        return view;
    }
}