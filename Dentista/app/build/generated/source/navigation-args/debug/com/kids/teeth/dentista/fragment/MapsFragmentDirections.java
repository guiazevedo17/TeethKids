package com.kids.teeth.dentista.fragment;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.kids.teeth.dentista.R;

public class MapsFragmentDirections {
  private MapsFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionMapFragmentToEmergencyInProgress() {
    return new ActionOnlyNavDirections(R.id.action_MapFragment_to_EmergencyInProgress);
  }
}
