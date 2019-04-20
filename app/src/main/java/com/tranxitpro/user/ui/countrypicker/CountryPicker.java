package com.tranxitpro.user.ui.countrypicker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.tranxitpro.user.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CountryPicker extends DialogFragment {

    private CountryListAdapter adapter;
    private List<Country> countriesList = new ArrayList<>();
    private List<Country> selectedCountriesList = new ArrayList<>();
    private CountryPickerListener listener;

    public CountryPicker() {
        setCountriesList(Country.getAllCountries());
    }

    public static CountryPicker newInstance(String dialogTitle) {
        CountryPicker picker = new CountryPicker();
        Bundle bundle = new Bundle();
        bundle.putString("dialogTitle", dialogTitle);
        picker.setArguments(bundle);
        return picker;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.country_picker, null);
        Bundle args = getArguments();
        if (args != null) {
            String dialogTitle = args.getString("dialogTitle");
            getDialog().setTitle(dialogTitle);
            int width = getResources().getDimensionPixelSize(R.dimen._300sdp);
            int height = getResources().getDimensionPixelSize(R.dimen._400sdp);
            getDialog().getWindow().setLayout(width, height);
        }
        EditText searchEditText = view.findViewById(R.id.country_code_picker_search);
        ListView countryListView = view.findViewById(R.id.country_code_picker_listview);

        selectedCountriesList = new ArrayList<>(countriesList.size());
        selectedCountriesList.addAll(countriesList);

        adapter = new CountryListAdapter(getActivity(), selectedCountriesList);
        countryListView.setAdapter(adapter);

        countryListView.setOnItemClickListener((parent, view1, position, id) -> {
            if (listener != null) {
                Country country = selectedCountriesList.get(position);
                listener.onSelectCountry(country.getName(), country.getCode(), country.getDialCode(),
                        country.getFlag());
            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                search(s.toString());
            }
        });

        return view;
    }

    public void setListener(CountryPickerListener listener) {
        this.listener = listener;
    }

    @SuppressLint("DefaultLocale")
    private void search(String text) {
        selectedCountriesList.clear();
        for (Country country : countriesList)
            if (country.getName().toLowerCase(Locale.ENGLISH).contains(text.toLowerCase()))
                selectedCountriesList.add(country);
        adapter.notifyDataSetChanged();
    }

    public void setCountriesList(List<Country> newCountries) {
        this.countriesList.clear();
        this.countriesList.addAll(newCountries);
    }

}
