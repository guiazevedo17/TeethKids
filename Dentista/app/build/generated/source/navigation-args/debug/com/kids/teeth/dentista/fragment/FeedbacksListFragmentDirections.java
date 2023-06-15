package com.kids.teeth.dentista.fragment;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.kids.teeth.dentista.R;

public class FeedbacksListFragmentDirections {
  private FeedbacksListFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionFeedbacksListFragmentToReputationProfileFragment() {
    return new ActionOnlyNavDirections(R.id.action_FeedbacksListFragment_to_ReputationProfileFragment);
  }
}
