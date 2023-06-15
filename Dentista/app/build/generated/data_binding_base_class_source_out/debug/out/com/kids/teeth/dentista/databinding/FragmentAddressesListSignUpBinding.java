// Generated by view binder compiler. Do not edit!
package com.kids.teeth.dentista.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kids.teeth.dentista.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentAddressesListSignUpBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppCompatImageButton btnBackAddressesListSignUp;

  @NonNull
  public final FloatingActionButton fabAddAddress;

  @NonNull
  public final RecyclerView rvAddressList;

  @NonNull
  public final AppCompatTextView tbAddressesListSignUp;

  @NonNull
  public final AppCompatTextView tvSubtitleEmptyAddresses;

  @NonNull
  public final AppCompatTextView tvTitleEmptyAddresses;

  private FragmentAddressesListSignUpBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppCompatImageButton btnBackAddressesListSignUp,
      @NonNull FloatingActionButton fabAddAddress, @NonNull RecyclerView rvAddressList,
      @NonNull AppCompatTextView tbAddressesListSignUp,
      @NonNull AppCompatTextView tvSubtitleEmptyAddresses,
      @NonNull AppCompatTextView tvTitleEmptyAddresses) {
    this.rootView = rootView;
    this.btnBackAddressesListSignUp = btnBackAddressesListSignUp;
    this.fabAddAddress = fabAddAddress;
    this.rvAddressList = rvAddressList;
    this.tbAddressesListSignUp = tbAddressesListSignUp;
    this.tvSubtitleEmptyAddresses = tvSubtitleEmptyAddresses;
    this.tvTitleEmptyAddresses = tvTitleEmptyAddresses;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentAddressesListSignUpBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentAddressesListSignUpBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_addresses_list_sign_up, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentAddressesListSignUpBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnBackAddressesListSignUp;
      AppCompatImageButton btnBackAddressesListSignUp = ViewBindings.findChildViewById(rootView, id);
      if (btnBackAddressesListSignUp == null) {
        break missingId;
      }

      id = R.id.fabAddAddress;
      FloatingActionButton fabAddAddress = ViewBindings.findChildViewById(rootView, id);
      if (fabAddAddress == null) {
        break missingId;
      }

      id = R.id.rvAddressList;
      RecyclerView rvAddressList = ViewBindings.findChildViewById(rootView, id);
      if (rvAddressList == null) {
        break missingId;
      }

      id = R.id.tbAddressesListSignUp;
      AppCompatTextView tbAddressesListSignUp = ViewBindings.findChildViewById(rootView, id);
      if (tbAddressesListSignUp == null) {
        break missingId;
      }

      id = R.id.tvSubtitleEmptyAddresses;
      AppCompatTextView tvSubtitleEmptyAddresses = ViewBindings.findChildViewById(rootView, id);
      if (tvSubtitleEmptyAddresses == null) {
        break missingId;
      }

      id = R.id.tvTitleEmptyAddresses;
      AppCompatTextView tvTitleEmptyAddresses = ViewBindings.findChildViewById(rootView, id);
      if (tvTitleEmptyAddresses == null) {
        break missingId;
      }

      return new FragmentAddressesListSignUpBinding((ConstraintLayout) rootView,
          btnBackAddressesListSignUp, fabAddAddress, rvAddressList, tbAddressesListSignUp,
          tvSubtitleEmptyAddresses, tvTitleEmptyAddresses);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
