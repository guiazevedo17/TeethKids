package com.kids.teeth.dentista.fragment;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.kids.teeth.dentista.R;

public class AddressesListProfileFragmentDirections {
  private AddressesListProfileFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionAddressesListProfileFragmentToProfileFragment() {
    return new ActionOnlyNavDirections(R.id.action_AddressesListProfileFragment_to_ProfileFragment);
  }

  @NonNull
  public static NavDirections actionAddressesListProfileFragmentToAddressRegisterProfileFragment() {
    return new ActionOnlyNavDirections(R.id.action_AddressesListProfileFragment_to_AddressRegisterProfileFragment);
  }

  @NonNull
  public static NavDirections actionAddressesListProfileFragmentToEditAddressProfileFragment() {
    return new ActionOnlyNavDirections(R.id.action_AddressesListProfileFragment_to_EditAddressProfileFragment);
  }
}
