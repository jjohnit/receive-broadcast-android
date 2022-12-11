package com.jjasan2.application_2.attractions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jjasan2.application_2.ListViewModel;
import com.jjasan2.application_2.R;

public class AttractionDetailsFragment extends Fragment {

    private ListViewModel viewModel;
    private WebView webView;

    public AttractionDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attraction_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Web view for displaying the website
        webView = view.findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());

        // Observe the live data to open the corresponding webview
        viewModel = new ViewModelProvider(requireActivity()).get(ListViewModel.class);
        viewModel.getSelectedItem().observe(getViewLifecycleOwner(), item -> {
            if(0 <= item && item < AttractionsActivity.attractions.length)
                webView.loadUrl(getResources().getStringArray(R.array.attraction_links)[item]);
        });
    }

//    public void changeUrl(int position){
//        webView.loadUrl(requireActivity().getResources().getStringArray(R.array.attraction_links)[position]);
//    }
}