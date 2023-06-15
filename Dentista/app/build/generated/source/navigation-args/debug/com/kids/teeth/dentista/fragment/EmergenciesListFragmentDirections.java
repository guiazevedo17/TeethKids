package com.kids.teeth.dentista.fragment;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.kids.teeth.dentista.R;

public class EmergenciesListFragmentDirections {
  private EmergenciesListFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionEmergenciesListFragmentToProfileFragment() {
    return new ActionOnlyNavDirections(R.id.action_EmergenciesListFragment_to_ProfileFragment);
  }

  @NonNull
  public static NavDirections actionEmergenciesListFragmentToEmergencyDetailFragment() {
    return new ActionOnlyNavDirections(R.id.action_EmergenciesListFragment_to_EmergencyDetailFragment);
  }
}
