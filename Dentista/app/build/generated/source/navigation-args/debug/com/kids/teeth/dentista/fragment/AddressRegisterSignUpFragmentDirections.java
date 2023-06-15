package com.kids.teeth.dentista.fragment;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.kids.teeth.dentista.R;

public class AddressRegisterSignUpFragmentDirections {
  private AddressRegisterSignUpFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionAddressRegisterSignUpFragmentToAddressesListSignUpFragment() {
    return new ActionOnlyNavDirections(R.id.action_AddressRegisterSignUpFragment_to_AddressesListSignUpFragment);
  }
}
