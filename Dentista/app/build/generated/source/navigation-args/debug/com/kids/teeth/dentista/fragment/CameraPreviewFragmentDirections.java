package com.kids.teeth.dentista.fragment;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.kids.teeth.dentista.R;

public class CameraPreviewFragmentDirections {
  private CameraPreviewFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionCameraPreviewFragmentToSignUpFragment() {
    return new ActionOnlyNavDirections(R.id.action_CameraPreviewFragment_to_SignUpFragment);
  }
}
