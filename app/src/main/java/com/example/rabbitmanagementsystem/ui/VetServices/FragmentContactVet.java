package com.example.rabbitmanagementsystem.ui.VetServices;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.rabbitmanagementsystem.databinding.ActivityDisplayUsersBinding;
public class ContactVet extends Fragment {

    private ContactVetViewModel mViewModel;

    public static ContactVet newInstance() {
        return new ContactVet();
    }
    private ActivityDisplayUsersBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ContactVetViewModel appointmentsViewModel =
                new ViewModelProvider(this.requireActivity()).get(ContactVetViewModel.class);
        binding = ActivityDisplayUsersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

       // final TextView textView = binding.tvApppointments;
       // appointmentsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}