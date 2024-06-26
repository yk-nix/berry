package com.vroad.app.berry.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.vroad.app.berry.R;
import com.vroad.app.berry.databinding.ActivityHomeBinding;
import com.vroad.app.berry.ui.login.LoginActivity;
import com.vroad.app.libui.base.BasicActivity;
import com.vroad.app.libui.base.BasicViewModelFactory;

import lombok.Getter;

public class HomeActivity extends BasicActivity<ActivityHomeBinding, HomeViewModel> {
  private AppBarConfiguration appBarConfiguration;
  private NavController navController;
  @Getter
  private ViewGroup.LayoutParams toolbarTitleLayoutParams;
  private BottomSheetDialog bottomSheetDialog;

  public HomeActivity() {
    super(true, BasicViewModelFactory.class);
  }

  @Override
  public void init(@Nullable Bundle savedInstanceState) {
    super.init(savedInstanceState);
    if (!viewModel.isLoggedIn()) {
      startActivity(new Intent(this, LoginActivity.class));
    }
    appBarConfiguration = new AppBarConfiguration.Builder().build();
    navController = ((NavHostFragment) binding.navHostFragmentActivityHome.getFragment()).getNavController();
    NavigationUI.setupWithNavController(binding.navView, navController);
    resetNavigationViewItemSelectedListener();
    registerLogoutResultObserver();
  }

  @Override
  public void release() {
  }

  private void resetNavigationViewItemSelectedListener() {
    binding.navView.setOnItemSelectedListener(menuItem -> NavigationUI.onNavDestinationSelected(menuItem, navController));
  }

  private void registerLogoutResultObserver() {
    viewModel.getLogoutResultState().observe(this, loginResult -> {
      if (loginResult == null)
        return;
      if (!loginResult.isSuccess()) {
        showLogoutFailed(loginResult.getMessage());
      } else {
//        viewModel.getLogoutResultState().setValue(null);
        setResult(Activity.RESULT_OK);
        closeBottomSheetDialog();
        finish();
        startActivity(getIntent());
      }
    });
  }

  private void showLogoutFailed(String message) {
    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
  }

  private void closeBottomSheetDialog() {
    if (bottomSheetDialog != null)
      bottomSheetDialog.cancel();
  }

  @Override
  public boolean onSupportNavigateUp() {
    return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
  }

  public void onClickLogoutConfirmed(View view) {
    viewModel.logout();
  }

  @SuppressLint("ResourceType")
  public void logout(View view) {
    if (bottomSheetDialog == null) {
      bottomSheetDialog = new BottomSheetDialog(this);
      bottomSheetDialog.setContentView(R.layout.bottom_sheet_logout);
    }
    bottomSheetDialog.show();
  }

  public void onClickLogoutCancel(View view) {
    if (bottomSheetDialog != null) {
      bottomSheetDialog.cancel();
    }
  }
}