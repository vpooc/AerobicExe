package com.vpooc.aerobicexe.fragment;

import com.vpooc.aerobicexe.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SportFragment 
extends Fragment{
View view;
	@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {

		try {
			view=inflater.inflate(R.layout.fragment_sport, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
}
}
