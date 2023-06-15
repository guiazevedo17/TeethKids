// Generated by view binder compiler. Do not edit!
package com.kids.teeth.dentista.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.kids.teeth.dentista.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentEmergencyDetailBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppCompatButton btnAcceptEmergency;

  @NonNull
  public final AppCompatImageButton btnBackEmergencyDetail;

  @NonNull
  public final AppCompatButton btnDeclineEmergency;

  @NonNull
  public final AppCompatImageView ivFirstPicture;

  @NonNull
  public final AppCompatImageView ivSecondPicture;

  @NonNull
  public final AppCompatImageView ivThirdPicture;

  @NonNull
  public final ProgressBar pbLoading;

  @NonNull
  public final ScrollView svPictures;

  @NonNull
  public final AppCompatTextView tbEmergencyDetail;

  @NonNull
  public final AppCompatTextView tvEmergencyDate;

  @NonNull
  public final AppCompatTextView tvFirstPicture;

  @NonNull
  public final AppCompatTextView tvRequesterName;

  @NonNull
  public final AppCompatTextView tvSecondPicture;

  @NonNull
  public final AppCompatTextView tvThirdPicture;

  private FragmentEmergencyDetailBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppCompatButton btnAcceptEmergency,
      @NonNull AppCompatImageButton btnBackEmergencyDetail,
      @NonNull AppCompatButton btnDeclineEmergency, @NonNull AppCompatImageView ivFirstPicture,
      @NonNull AppCompatImageView ivSecondPicture, @NonNull AppCompatImageView ivThirdPicture,
      @NonNull ProgressBar pbLoading, @NonNull ScrollView svPictures,
      @NonNull AppCompatTextView tbEmergencyDetail, @NonNull AppCompatTextView tvEmergencyDate,
      @NonNull AppCompatTextView tvFirstPicture, @NonNull AppCompatTextView tvRequesterName,
      @NonNull AppCompatTextView tvSecondPicture, @NonNull AppCompatTextView tvThirdPicture) {
    this.rootView = rootView;
    this.btnAcceptEmergency = btnAcceptEmergency;
    this.btnBackEmergencyDetail = btnBackEmergencyDetail;
    this.btnDeclineEmergency = btnDeclineEmergency;
    this.ivFirstPicture = ivFirstPicture;
    this.ivSecondPicture = ivSecondPicture;
    this.ivThirdPicture = ivThirdPicture;
    this.pbLoading = pbLoading;
    this.svPictures = svPictures;
    this.tbEmergencyDetail = tbEmergencyDetail;
    this.tvEmergencyDate = tvEmergencyDate;
    this.tvFirstPicture = tvFirstPicture;
    this.tvRequesterName = tvRequesterName;
    this.tvSecondPicture = tvSecondPicture;
    this.tvThirdPicture = tvThirdPicture;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentEmergencyDetailBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentEmergencyDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_emergency_detail, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentEmergencyDetailBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnAcceptEmergency;
      AppCompatButton btnAcceptEmergency = ViewBindings.findChildViewById(rootView, id);
      if (btnAcceptEmergency == null) {
        break missingId;
      }

      id = R.id.btnBackEmergencyDetail;
      AppCompatImageButton btnBackEmergencyDetail = ViewBindings.findChildViewById(rootView, id);
      if (btnBackEmergencyDetail == null) {
        break missingId;
      }

      id = R.id.btnDeclineEmergency;
      AppCompatButton btnDeclineEmergency = ViewBindings.findChildViewById(rootView, id);
      if (btnDeclineEmergency == null) {
        break missingId;
      }

      id = R.id.ivFirstPicture;
      AppCompatImageView ivFirstPicture = ViewBindings.findChildViewById(rootView, id);
      if (ivFirstPicture == null) {
        break missingId;
      }

      id = R.id.ivSecondPicture;
      AppCompatImageView ivSecondPicture = ViewBindings.findChildViewById(rootView, id);
      if (ivSecondPicture == null) {
        break missingId;
      }

      id = R.id.ivThirdPicture;
      AppCompatImageView ivThirdPicture = ViewBindings.findChildViewById(rootView, id);
      if (ivThirdPicture == null) {
        break missingId;
      }

      id = R.id.pbLoading;
      ProgressBar pbLoading = ViewBindings.findChildViewById(rootView, id);
      if (pbLoading == null) {
        break missingId;
      }

      id = R.id.svPictures;
      ScrollView svPictures = ViewBindings.findChildViewById(rootView, id);
      if (svPictures == null) {
        break missingId;
      }

      id = R.id.tbEmergencyDetail;
      AppCompatTextView tbEmergencyDetail = ViewBindings.findChildViewById(rootView, id);
      if (tbEmergencyDetail == null) {
        break missingId;
      }

      id = R.id.tvEmergencyDate;
      AppCompatTextView tvEmergencyDate = ViewBindings.findChildViewById(rootView, id);
      if (tvEmergencyDate == null) {
        break missingId;
      }

      id = R.id.tvFirstPicture;
      AppCompatTextView tvFirstPicture = ViewBindings.findChildViewById(rootView, id);
      if (tvFirstPicture == null) {
        break missingId;
      }

      id = R.id.tvRequesterName;
      AppCompatTextView tvRequesterName = ViewBindings.findChildViewById(rootView, id);
      if (tvRequesterName == null) {
        break missingId;
      }

      id = R.id.tvSecondPicture;
      AppCompatTextView tvSecondPicture = ViewBindings.findChildViewById(rootView, id);
      if (tvSecondPicture == null) {
        break missingId;
      }

      id = R.id.tvThirdPicture;
      AppCompatTextView tvThirdPicture = ViewBindings.findChildViewById(rootView, id);
      if (tvThirdPicture == null) {
        break missingId;
      }

      return new FragmentEmergencyDetailBinding((ConstraintLayout) rootView, btnAcceptEmergency,
          btnBackEmergencyDetail, btnDeclineEmergency, ivFirstPicture, ivSecondPicture,
          ivThirdPicture, pbLoading, svPictures, tbEmergencyDetail, tvEmergencyDate, tvFirstPicture,
          tvRequesterName, tvSecondPicture, tvThirdPicture);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
