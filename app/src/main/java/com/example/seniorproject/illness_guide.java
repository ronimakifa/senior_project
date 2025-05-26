package com.example.seniorproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment that provides a guide for plant illnesses.
 * This fragment can be instantiated with parameters and displays illness-related information.
 *
 * <p>Use the {@link #newInstance(String, String)} factory method to create a new instance with parameters.</p>
 *
 * @author Roni Zuckerman
 * @version 1.0
 *
 */
public class illness_guide extends Fragment {

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
    public illness_guide() {
        // Required empty public constructor
    }

    /**
     * Factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param param1 The first parameter for the fragment.
     * @param param2 The second parameter for the fragment.
     * @return A new instance of fragment illness_guide.
     */
    public static illness_guide newInstance(String param1, String param2) {
        illness_guide fragment = new illness_guide();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called to do initial creation of the fragment.
     *
     * @param savedInstanceState If the fragment is being re-created from a previous saved state, this is the state.
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
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return The View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_illness_guide, container, false);
    }
}