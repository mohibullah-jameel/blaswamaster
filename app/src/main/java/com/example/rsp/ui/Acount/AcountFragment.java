package com.example.rsp.ui.Acount;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.rsp.R;

public class AcountFragment extends Fragment {

    private AcountViewModel acountViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        acountViewModel =
                ViewModelProviders.of(this).get(AcountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_Account, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        acountViewModel.getText().observe((LifecycleOwner) getContext(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}