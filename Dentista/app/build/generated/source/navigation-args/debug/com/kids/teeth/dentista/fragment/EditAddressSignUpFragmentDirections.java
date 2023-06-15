package com.kids.teeth.dentista.fragment;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.kids.teeth.dentista.R;

public class EditAddressSignUpFragmentDirections {
  private EditAddressSignUpFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionEditAddressSignUpFragmentToAddressesListSignUpFragment() {
    return new ActionOnlyNavDirections(R.id.action_EditAddressSignUpFragment_to_AddressesListSignUpFragment);
  }
}
