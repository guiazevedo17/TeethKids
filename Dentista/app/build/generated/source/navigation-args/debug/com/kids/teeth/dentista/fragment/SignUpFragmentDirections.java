package com.kids.teeth.dentista.fragment;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.kids.teeth.dentista.R;

public class SignUpFragmentDirections {
  private SignUpFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionSignUpFragmentToSignInFragment() {
    return new ActionOnlyNavDirections(R.id.action_SignUpFragment_to_SignInFragment);
  }

  @NonNull
  public static NavDirections actionSignUpFragmentToAddressesListSignUpFragment() {
    return new ActionOnlyNavDirections(R.id.action_SignUpFragment_to_AddressesListSignUpFragment);
  }

  @NonNull
  public static NavDirections actionSignUpFragmentToResumeFragment() {
    return new ActionOnlyNavDirections(R.id.action_SignUpFragment_to_ResumeFragment);
  }

  @NonNull
  public static NavDirections actionSignUpFragmentToCameraPreviewFragment() {
    return new ActionOnlyNavDirections(R.id.action_SignUpFragment_to_CameraPreviewFragment);
  }
}
