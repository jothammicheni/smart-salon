package com.example.witxsalon.ui.appointments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.witxsalon.databinding.FragmentAppointmentsBinding;
public class Appointments extends Fragment {

    private AppointmentsViewModel mViewModel;

    public static Appointments newInstance() {
        return new Appointments();
    }
    private FragmentAppointmentsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AppointmentsViewModel appointmentsViewModel =
                new ViewModelProvider(this.requireActivity()).get(AppointmentsViewModel.class);

        binding = FragmentAppointmentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.tvApppointments;
        appointmentsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}