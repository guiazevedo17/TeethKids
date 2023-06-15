// Generated by view binder compiler. Do not edit!
package com.kids.teeth.dentista.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kids.teeth.dentista.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentEditProfileBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppCompatImageButton btnCancelEditProfile;

  @NonNull
  public final AppCompatButton btnConcludeEditProfile;

  @NonNull
  public final TextInputEditText etConfPasswordEditProfile;

  @NonNull
  public final TextInputEditText etEmailEditProfile;

  @NonNull
  public final TextInputEditText etNameEditProfile;

  @NonNull
  public final TextInputEditText etPasswordEditProfile;

  @NonNull
  public final TextInputEditText etPhoneEditProfile;

  @NonNull
  public final TextInputEditText etResumeEditProfile;

  @NonNull
  public final TextInputLayout ilConfPasswordEditProfile;

  @NonNull
  public final TextInputLayout ilEmailEditProfile;

  @NonNull
  public final TextInputLayout ilNameEditProfile;

  @NonNull
  public final TextInputLayout ilPasswordEditProfile;

  @NonNull
  public final TextInputLayout ilPhoneEditProfile;

  @NonNull
  public final TextInputLayout ilResumeEditProfile;

  @NonNull
  public final AppCompatImageButton ivProfilePictureEditProfile;

  @NonNull
  public final ScrollView svEditProfile;

  @NonNull
  public final AppCompatTextView tbEditProfile;

  private FragmentEditProfileBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppCompatImageButton btnCancelEditProfile,
      @NonNull AppCompatButton btnConcludeEditProfile,
      @NonNull TextInputEditText etConfPasswordEditProfile,
      @NonNull TextInputEditText etEmailEditProfile, @NonNull TextInputEditText etNameEditProfile,
      @NonNull TextInputEditText etPasswordEditProfile,
      @NonNull TextInputEditText etPhoneEditProfile, @NonNull TextInputEditText etResumeEditProfile,
      @NonNull TextInputLayout ilConfPasswordEditProfile,
      @NonNull TextInputLayout ilEmailEditProfile, @NonNull TextInputLayout ilNameEditProfile,
      @NonNull TextInputLayout ilPasswordEditProfile, @NonNull TextInputLayout ilPhoneEditProfile,
      @NonNull TextInputLayout ilResumeEditProfile,
      @NonNull AppCompatImageButton ivProfilePictureEditProfile, @NonNull ScrollView svEditProfile,
      @NonNull AppCompatTextView tbEditProfile) {
    this.rootView = rootView;
    this.btnCancelEditProfile = btnCancelEditProfile;
    this.btnConcludeEditProfile = btnConcludeEditProfile;
    this.etConfPasswordEditProfile = etConfPasswordEditProfile;
    this.etEmailEditProfile = etEmailEditProfile;
    this.etNameEditProfile = etNameEditProfile;
    this.etPasswordEditProfile = etPasswordEditProfile;
    this.etPhoneEditProfile = etPhoneEditProfile;
    this.etResumeEditProfile = etResumeEditProfile;
    this.ilConfPasswordEditProfile = ilConfPasswordEditProfile;
    this.ilEmailEditProfile = ilEmailEditProfile;
    this.ilNameEditProfile = ilNameEditProfile;
    this.ilPasswordEditProfile = ilPasswordEditProfile;
    this.ilPhoneEditProfile = ilPhoneEditProfile;
    this.ilResumeEditProfile = ilResumeEditProfile;
    this.ivProfilePictureEditProfile = ivProfilePictureEditProfile;
    this.svEditProfile = svEditProfile;
    this.tbEditProfile = tbEditProfile;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentEditProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentEditProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_edit_profile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentEditProfileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnCancelEditProfile;
      AppCompatImageButton btnCancelEditProfile = ViewBindings.findChildViewById(rootView, id);
      if (btnCancelEditProfile == null) {
        break missingId;
      }

      id = R.id.btnConcludeEditProfile;
      AppCompatButton btnConcludeEditProfile = ViewBindings.findChildViewById(rootView, id);
      if (btnConcludeEditProfile == null) {
        break missingId;
      }

      id = R.id.etConfPasswordEditProfile;
      TextInputEditText etConfPasswordEditProfile = ViewBindings.findChildViewById(rootView, id);
      if (etConfPasswordEditProfile == null) {
        break missingId;
      }

      id = R.id.etEmailEditProfile;
      TextInputEditText etEmailEditProfile = ViewBindings.findChildViewById(rootView, id);
      if (etEmailEditProfile == null) {
        break missingId;
      }

      id = R.id.etNameEditProfile;
      TextInputEditText etNameEditProfile = ViewBindings.findChildViewById(rootView, id);
      if (etNameEditProfile == null) {
        break missingId;
      }

      id = R.id.etPasswordEditProfile;
      TextInputEditText etPasswordEditProfile = ViewBindings.findChildViewById(rootView, id);
      if (etPasswordEditProfile == null) {
        break missingId;
      }

      id = R.id.etPhoneEditProfile;
      TextInputEditText etPhoneEditProfile = ViewBindings.findChildViewById(rootView, id);
      if (etPhoneEditProfile == null) {
        break missingId;
      }

      id = R.id.etResumeEditProfile;
      TextInputEditText etResumeEditProfile = ViewBindings.findChildViewById(rootView, id);
      if (etResumeEditProfile == null) {
        break missingId;
      }

      id = R.id.ilConfPasswordEditProfile;
      TextInputLayout ilConfPasswordEditProfile = ViewBindings.findChildViewById(rootView, id);
      if (ilConfPasswordEditProfile == null) {
        break missingId;
      }

      id = R.id.ilEmailEditProfile;
      TextInputLayout ilEmailEditProfile = ViewBindings.findChildViewById(rootView, id);
      if (ilEmailEditProfile == null) {
        break missingId;
      }

      id = R.id.ilNameEditProfile;
      TextInputLayout ilNameEditProfile = ViewBindings.findChildViewById(rootView, id);
      if (ilNameEditProfile == null) {
        break missingId;
      }

      id = R.id.ilPasswordEditProfile;
      TextInputLayout ilPasswordEditProfile = ViewBindings.findChildViewById(rootView, id);
      if (ilPasswordEditProfile == null) {
        break missingId;
      }

      id = R.id.ilPhoneEditProfile;
      TextInputLayout ilPhoneEditProfile = ViewBindings.findChildViewById(rootView, id);
      if (ilPhoneEditProfile == null) {
        break missingId;
      }

      id = R.id.ilResumeEditProfile;
      TextInputLayout ilResumeEditProfile = ViewBindings.findChildViewById(rootView, id);
      if (ilResumeEditProfile == null) {
        break missingId;
      }

      id = R.id.ivProfilePictureEditProfile;
      AppCompatImageButton ivProfilePictureEditProfile = ViewBindings.findChildViewById(rootView, id);
      if (ivProfilePictureEditProfile == null) {
        break missingId;
      }

      id = R.id.svEditProfile;
      ScrollView svEditProfile = ViewBindings.findChildViewById(rootView, id);
      if (svEditProfile == null) {
        break missingId;
      }

      id = R.id.tbEditProfile;
      AppCompatTextView tbEditProfile = ViewBindings.findChildViewById(rootView, id);
      if (tbEditProfile == null) {
        break missingId;
      }

      return new FragmentEditProfileBinding((ConstraintLayout) rootView, btnCancelEditProfile,
          btnConcludeEditProfile, etConfPasswordEditProfile, etEmailEditProfile, etNameEditProfile,
          etPasswordEditProfile, etPhoneEditProfile, etResumeEditProfile, ilConfPasswordEditProfile,
          ilEmailEditProfile, ilNameEditProfile, ilPasswordEditProfile, ilPhoneEditProfile,
          ilResumeEditProfile, ivProfilePictureEditProfile, svEditProfile, tbEditProfile);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
