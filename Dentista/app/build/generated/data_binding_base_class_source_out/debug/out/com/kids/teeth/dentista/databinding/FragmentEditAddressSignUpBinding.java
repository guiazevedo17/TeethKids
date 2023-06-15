// Generated by view binder compiler. Do not edit!
package com.kids.teeth.dentista.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class FragmentEditAddressSignUpBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppCompatImageButton btnCancelEditAddressSignUp;

  @NonNull
  public final AppCompatButton btnConcludeEditAddressSignUp;

  @NonNull
  public final TextInputEditText etCity;

  @NonNull
  public final TextInputEditText etComplement;

  @NonNull
  public final TextInputEditText etName;

  @NonNull
  public final TextInputEditText etNeighborhood;

  @NonNull
  public final TextInputEditText etNumber;

  @NonNull
  public final TextInputEditText etPostalCode;

  @NonNull
  public final TextInputEditText etState;

  @NonNull
  public final TextInputEditText etStreet;

  @NonNull
  public final TextInputLayout ilCity;

  @NonNull
  public final TextInputLayout ilComplement;

  @NonNull
  public final TextInputLayout ilName;

  @NonNull
  public final TextInputLayout ilNeighborhood;

  @NonNull
  public final TextInputLayout ilNumber;

  @NonNull
  public final TextInputLayout ilPostalCode;

  @NonNull
  public final TextInputLayout ilState;

  @NonNull
  public final TextInputLayout ilStreet;

  @NonNull
  public final AppCompatTextView tbEditAddressProfile;

  private FragmentEditAddressSignUpBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppCompatImageButton btnCancelEditAddressSignUp,
      @NonNull AppCompatButton btnConcludeEditAddressSignUp, @NonNull TextInputEditText etCity,
      @NonNull TextInputEditText etComplement, @NonNull TextInputEditText etName,
      @NonNull TextInputEditText etNeighborhood, @NonNull TextInputEditText etNumber,
      @NonNull TextInputEditText etPostalCode, @NonNull TextInputEditText etState,
      @NonNull TextInputEditText etStreet, @NonNull TextInputLayout ilCity,
      @NonNull TextInputLayout ilComplement, @NonNull TextInputLayout ilName,
      @NonNull TextInputLayout ilNeighborhood, @NonNull TextInputLayout ilNumber,
      @NonNull TextInputLayout ilPostalCode, @NonNull TextInputLayout ilState,
      @NonNull TextInputLayout ilStreet, @NonNull AppCompatTextView tbEditAddressProfile) {
    this.rootView = rootView;
    this.btnCancelEditAddressSignUp = btnCancelEditAddressSignUp;
    this.btnConcludeEditAddressSignUp = btnConcludeEditAddressSignUp;
    this.etCity = etCity;
    this.etComplement = etComplement;
    this.etName = etName;
    this.etNeighborhood = etNeighborhood;
    this.etNumber = etNumber;
    this.etPostalCode = etPostalCode;
    this.etState = etState;
    this.etStreet = etStreet;
    this.ilCity = ilCity;
    this.ilComplement = ilComplement;
    this.ilName = ilName;
    this.ilNeighborhood = ilNeighborhood;
    this.ilNumber = ilNumber;
    this.ilPostalCode = ilPostalCode;
    this.ilState = ilState;
    this.ilStreet = ilStreet;
    this.tbEditAddressProfile = tbEditAddressProfile;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentEditAddressSignUpBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentEditAddressSignUpBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_edit_address_sign_up, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentEditAddressSignUpBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnCancelEditAddressSignUp;
      AppCompatImageButton btnCancelEditAddressSignUp = ViewBindings.findChildViewById(rootView, id);
      if (btnCancelEditAddressSignUp == null) {
        break missingId;
      }

      id = R.id.btnConcludeEditAddressSignUp;
      AppCompatButton btnConcludeEditAddressSignUp = ViewBindings.findChildViewById(rootView, id);
      if (btnConcludeEditAddressSignUp == null) {
        break missingId;
      }

      id = R.id.etCity;
      TextInputEditText etCity = ViewBindings.findChildViewById(rootView, id);
      if (etCity == null) {
        break missingId;
      }

      id = R.id.etComplement;
      TextInputEditText etComplement = ViewBindings.findChildViewById(rootView, id);
      if (etComplement == null) {
        break missingId;
      }

      id = R.id.etName;
      TextInputEditText etName = ViewBindings.findChildViewById(rootView, id);
      if (etName == null) {
        break missingId;
      }

      id = R.id.etNeighborhood;
      TextInputEditText etNeighborhood = ViewBindings.findChildViewById(rootView, id);
      if (etNeighborhood == null) {
        break missingId;
      }

      id = R.id.etNumber;
      TextInputEditText etNumber = ViewBindings.findChildViewById(rootView, id);
      if (etNumber == null) {
        break missingId;
      }

      id = R.id.etPostalCode;
      TextInputEditText etPostalCode = ViewBindings.findChildViewById(rootView, id);
      if (etPostalCode == null) {
        break missingId;
      }

      id = R.id.etState;
      TextInputEditText etState = ViewBindings.findChildViewById(rootView, id);
      if (etState == null) {
        break missingId;
      }

      id = R.id.etStreet;
      TextInputEditText etStreet = ViewBindings.findChildViewById(rootView, id);
      if (etStreet == null) {
        break missingId;
      }

      id = R.id.ilCity;
      TextInputLayout ilCity = ViewBindings.findChildViewById(rootView, id);
      if (ilCity == null) {
        break missingId;
      }

      id = R.id.ilComplement;
      TextInputLayout ilComplement = ViewBindings.findChildViewById(rootView, id);
      if (ilComplement == null) {
        break missingId;
      }

      id = R.id.ilName;
      TextInputLayout ilName = ViewBindings.findChildViewById(rootView, id);
      if (ilName == null) {
        break missingId;
      }

      id = R.id.ilNeighborhood;
      TextInputLayout ilNeighborhood = ViewBindings.findChildViewById(rootView, id);
      if (ilNeighborhood == null) {
        break missingId;
      }

      id = R.id.ilNumber;
      TextInputLayout ilNumber = ViewBindings.findChildViewById(rootView, id);
      if (ilNumber == null) {
        break missingId;
      }

      id = R.id.ilPostalCode;
      TextInputLayout ilPostalCode = ViewBindings.findChildViewById(rootView, id);
      if (ilPostalCode == null) {
        break missingId;
      }

      id = R.id.ilState;
      TextInputLayout ilState = ViewBindings.findChildViewById(rootView, id);
      if (ilState == null) {
        break missingId;
      }

      id = R.id.ilStreet;
      TextInputLayout ilStreet = ViewBindings.findChildViewById(rootView, id);
      if (ilStreet == null) {
        break missingId;
      }

      id = R.id.tbEditAddressProfile;
      AppCompatTextView tbEditAddressProfile = ViewBindings.findChildViewById(rootView, id);
      if (tbEditAddressProfile == null) {
        break missingId;
      }

      return new FragmentEditAddressSignUpBinding((ConstraintLayout) rootView,
          btnCancelEditAddressSignUp, btnConcludeEditAddressSignUp, etCity, etComplement, etName,
          etNeighborhood, etNumber, etPostalCode, etState, etStreet, ilCity, ilComplement, ilName,
          ilNeighborhood, ilNumber, ilPostalCode, ilState, ilStreet, tbEditAddressProfile);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
