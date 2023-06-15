package com.kids.teeth.dentista.fragment;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.kids.teeth.dentista.R;

public class AddressRegisterProfileFragmentDirections {
  private AddressRegisterProfileFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionAddressRegisterProfileFragmentToAddressesListProfileFragment() {
    return new ActionOnlyNavDirections(R.id.action_AddressRegisterProfileFragment_to_AddressesListProfileFragment);
  }
}
