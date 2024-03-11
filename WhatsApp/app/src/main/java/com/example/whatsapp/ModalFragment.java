package com.example.whatsapp;

import androidx.fragment.app.Fragment;

public class ModalFragment {
    private Fragment fragment;
    private String title;

    public ModalFragment(Fragment fragment, String title) {
        this.fragment = fragment;
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public String getTitle() {
        return title;
    }
}
