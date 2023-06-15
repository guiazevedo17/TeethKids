package com.kids.teeth.dentista.fragment;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.kids.teeth.dentista.R;

public class SignInFragmentDirections {
  private SignInFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionSignInFragmentToProfileFragment() {
    return new ActionOnlyNavDirections(R.id.action_SignInFragment_to_ProfileFragment);
  }

  @NonNull
  public static NavDirections actionSignInFragmentToSignUpFragment() {
    return new ActionOnlyNavDirections(R.id.action_SignInFragment_to_SignUpFragment);
  }
}
