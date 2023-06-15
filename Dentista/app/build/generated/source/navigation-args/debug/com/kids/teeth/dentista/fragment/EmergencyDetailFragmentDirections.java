package com.kids.teeth.dentista.fragment;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.kids.teeth.dentista.R;

public class EmergencyDetailFragmentDirections {
  private EmergencyDetailFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionEmergencyDetailFragmentToEmergenciesListFragment() {
    return new ActionOnlyNavDirections(R.id.action_EmergencyDetailFragment_to_EmergenciesListFragment);
  }
}
