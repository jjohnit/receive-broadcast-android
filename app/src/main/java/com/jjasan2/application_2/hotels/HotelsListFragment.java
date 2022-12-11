package com.jjasan2.application_2.hotels;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jjasan2.application_2.ListViewModel;
import com.jjasan2.application_2.R;


public class HotelsListFragment extends ListFragment {

    private ListViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.hotels,
                R.layout.list_item);
        setListAdapter(arrayAdapter);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hotels_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ListViewModel.class);
        // To highlight the last selected option after orientation change
        if(savedInstanceState != null){
            getListView().setItemChecked(savedInstanceState.getInt("listPosition"), true);
        }
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        getListView().setItemChecked(position, true);
        viewModel.selectItem(position);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(viewModel.getSelectedItem().getValue() != null)
            outState.putInt("listPosition", viewModel.getSelectedItem().getValue());
    }
}