package com.kids.teeth.dentista.fragment;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.kids.teeth.dentista.R;

public class EditProfileFragmentDirections {
  private EditProfileFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionEditProfileFragmentToProfileFragment() {
    return new ActionOnlyNavDirections(R.id.action_EditProfileFragment_to_ProfileFragment);
  }
}
