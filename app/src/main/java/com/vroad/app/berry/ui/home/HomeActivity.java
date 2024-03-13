package com.vroad.app.berry.ui.home;

import static androidx.navigation.ui.NavigationUI.onNavDestinationSelected;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.elvishew.xlog.XLog;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;
import com.vroad.app.basic.common.BasicActivityWithViewModelFactory;
import com.vroad.app.basic.utils.ReflectionUtil;
import com.vroad.app.berry.R;
import com.vroad.app.berry.data.repository.LoginRepository;
import com.vroad.app.berry.databinding.ActivityHomeBinding;
import com.vroad.app.berry.ui.login.LoginActivity;
import com.vroad.app.libui.utils.UtilsUI;

import java.util.Objects;
import java.util.Optional;

import lombok.Getter;

public class HomeActivity extends BasicActivityWithViewModelFactory<ActivityHomeBinding, HomeViewModel, HomeViewModelFactory> {
  private AppBarConfiguration appBarConfiguration;
  private NavController navController;
  @Getter
  private ViewGroup.LayoutParams toolbarTitleLayoutParams;
  private BottomSheetDialog bottomSheetDialog;

  @Override
  protected void init() {
//    setSupportActionBar(binding.toolbar.getRoot());
    if (!LoginRepository.getInstance(getApplicationContext()).isLoggedIn())
      startActivity(new Intent(this, LoginActivity.class));
//    appBarConfiguration = new AppBarConfiguration.Builder(
//        R.id.tasks_fragment, R.id.dashboard_fragment, R.id.settings_fragment).build();
    navController = ((NavHostFragment) binding.navHostFragmentActivityHome.getFragment()).getNavController();
//    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    NavigationUI.setupWithNavController(binding.navView, navController);
    resetNavigationViewItemSelectedListener();
//    UtilsUI.centerToolbarTitle(binding.toolbar.getRoot());
    registerLogoutResultObserver();
  }

  private void resetNavigationViewItemSelectedListener() {
    binding.navView.setOnItemSelectedListener(menuItem -> {
//      if (menuItem.getItemId() == R.id.settings_fragment) {
//        UtilsUI.setStatusBarColor(this, getResources().getColor(R.color.white, null));
//      } else {
//        UtilsUI.setStatusBarColor(this, getResources().getColor(R.color.gray_white, null));
//      }
      return NavigationUI.onNavDestinationSelected(menuItem, navController);
    });
  }

  private void registerLogoutResultObserver() {
    viewModel.getLogoutResultState().observe(this, loginResult -> {
      if (loginResult == null)
        return;
      if (!loginResult.isSuccess()) {
        showLogoutFailed(loginResult.getMessage());
      } else {
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