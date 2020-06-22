package com.accentrs.iofferbh.interfaces;

import androidx.fragment.app.Fragment;

public interface SwitchFragmentsCallback {
    public void switchFragment(Fragment fragment, int fragID, Object extras);
    public void switchFragmentAddToBackStack(Fragment fragment, int fragID, Object extras, boolean addToBackStack);
    public void popBackStack(boolean popBackStack);
    public void whichFragment(Fragment fragment);
}
