package com.kids.teeth.dentista.fragment;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.kids.teeth.dentista.R;

public class EmergencyInProgressFragmentDirections {
  private EmergencyInProgressFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionEmergencyInProgressFragmentToProfileFragment() {
    return new ActionOnlyNavDirections(R.id.action_EmergencyInProgressFragment_to_ProfileFragment);
  }
}
