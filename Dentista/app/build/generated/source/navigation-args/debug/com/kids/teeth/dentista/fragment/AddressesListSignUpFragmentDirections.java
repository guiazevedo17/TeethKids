package com.kids.teeth.dentista.fragment;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.kids.teeth.dentista.R;

public class AddressesListSignUpFragmentDirections {
  private AddressesListSignUpFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionAddressesListSignUpFragmentToSignUpFragment() {
    return new ActionOnlyNavDirections(R.id.action_AddressesListSignUpFragment_to_SignUpFragment);
  }

  @NonNull
  public static NavDirections actionAddressesListSignUpFragmentToAddressRegisterSignUpFragment() {
    return new ActionOnlyNavDirections(R.id.action_AddressesListSignUpFragment_to_AddressRegisterSignUpFragment);
  }

  @NonNull
  public static NavDirections actionAddressesListSignUpFragmentToEditAddressSignUpFragment() {
    return new ActionOnlyNavDirections(R.id.action_AddressesListSignUpFragment_to_EditAddressSignUpFragment);
  }
}
