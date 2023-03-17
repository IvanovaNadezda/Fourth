package com.example.fourth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.fourth.databinding.Screen1Binding;

public class FragmentScreen1 extends Fragment {
    Screen1Binding binding;
    public FragmentScreen1()
    {
        super(R.layout.screen1); // Располагаемся на первом экране
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = Screen1Binding.inflate(inflater, container, false);

        // Переход на второй экран FragmentScreen2
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().
                        setReorderingAllowed(true);
                fragmentTransaction.replace(R.id.fragment_view, new FragmentScreen2());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        // Переход на третий экран FragmentScreen3
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().
                        setReorderingAllowed(true);
                fragmentTransaction.replace(R.id.fragment_view, new FragmentScreen3());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return binding.getRoot();
    }
}
