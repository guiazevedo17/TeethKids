package com.kids.teeth.dentista.fragment;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.kids.teeth.dentista.R;

public class ProfileFragmentDirections {
  private ProfileFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionProfileFragmentToSignInFragment() {
    return new ActionOnlyNavDirections(R.id.action_ProfileFragment_to_SignInFragment);
  }

  @NonNull
  public static NavDirections actionProfileFragmentToEditProfileFragment() {
    return new ActionOnlyNavDirections(R.id.action_ProfileFragment_to_EditProfileFragment);
  }

  @NonNull
  public static NavDirections actionProfileFragmentToAddressesListProfileFragment() {
    return new ActionOnlyNavDirections(R.id.action_ProfileFragment_to_AddressesListProfileFragment);
  }

  @NonNull
  public static NavDirections actionProfileFragmentToEmergenciesListFragment() {
    return new ActionOnlyNavDirections(R.id.action_ProfileFragment_to_EmergenciesListFragment);
  }

  @NonNull
  public static NavDirections actionProfileFragmentToReputationFragment() {
    return new ActionOnlyNavDirections(R.id.action_ProfileFragment_to_ReputationFragment);
  }

  @NonNull
  public static NavDirections actionProfileFragmentToMapsFragment() {
    return new ActionOnlyNavDirections(R.id.action_ProfileFragment_to_MapsFragment);
  }
}
