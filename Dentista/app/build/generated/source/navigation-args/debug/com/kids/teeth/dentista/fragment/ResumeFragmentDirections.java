package com.kids.teeth.dentista.fragment;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.kids.teeth.dentista.R;

public class ResumeFragmentDirections {
  private ResumeFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionResumeFragmentToSignUpFragment() {
    return new ActionOnlyNavDirections(R.id.action_ResumeFragment_to_SignUpFragment);
  }
}
