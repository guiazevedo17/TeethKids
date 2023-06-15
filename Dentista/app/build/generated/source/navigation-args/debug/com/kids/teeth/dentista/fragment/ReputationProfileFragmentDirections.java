package com.kids.teeth.dentista.fragment;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.kids.teeth.dentista.R;

public class ReputationProfileFragmentDirections {
  private ReputationProfileFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionReputationProfileFragmentToProfileFragment() {
    return new ActionOnlyNavDirections(R.id.action_ReputationProfileFragment_to_ProfileFragment);
  }

  @NonNull
  public static NavDirections actionReputationProfileFragmentToFeedbacksListFragment() {
    return new ActionOnlyNavDirections(R.id.action_ReputationProfileFragment_to_FeedbacksListFragment);
  }
}
