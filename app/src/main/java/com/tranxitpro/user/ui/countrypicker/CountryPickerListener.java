package com.tranxitpro.user.ui.countrypicker;

/**
 * Created by mukesh on 25/04/16.
 */
public interface CountryPickerListener {
    void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID);
}
