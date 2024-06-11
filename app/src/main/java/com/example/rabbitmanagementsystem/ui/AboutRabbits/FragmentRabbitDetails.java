package com.example.rabbitmanagementsystem.ui.AboutRabbits;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.rabbitmanagementsystem.R;
import com.example.rabbitmanagementsystem.databinding.FragmentRabbitDetailsBinding;


public class FragmentRabbitDetails extends Fragment {
   Button btnViewMore;

    private FragmentRabbitDetailsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= FragmentRabbitDetailsBinding.inflate(inflater,container,false);
        View root=binding.getRoot();
      btnViewMore=root.findViewById(R.id.btnMore);



       btnViewMore.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String url="https://www.agrifarming.in/rabbit-farming#:~:text=Rabbits%20can%20be%20raised%20in,like%20dogs%20or%20cats%2C%20etc.";
               Intent intent = new Intent(Intent.ACTION_VIEW);
               intent.setData(Uri.parse(url));
               startActivity(intent);
           }
       });




        return root;

    }


}