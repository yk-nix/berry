package com.vroad.app.berry.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.elvishew.xlog.XLog;
import com.vroad.app.basic.common.BasicActivityWithViewModel;
import com.vroad.app.berry.R;
import com.vroad.app.berry.data.repository.LoginRepository;
import com.vroad.app.berry.databinding.ActivityHomeBinding;
import com.vroad.app.berry.ui.login.LoginActivity;

public class HomeActivity extends BasicActivityWithViewModel<ActivityHomeBinding, HomeViewModel> {
  private AppBarConfiguration appBarConfiguration;
  private NavController navController;

  @Override
  protected void init() {
    XLog.i("---view model: %s", viewModel);
    if (!LoginRepository.getInstance(getApplicationContext()).isLoggedIn())
      startActivity(new Intent(this, LoginActivity.class));
    appBarConfiguration = new AppBarConfiguration.Builder(
        R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications).build();
    navController = ((NavHostFragment) binding.navHostFragmentActivityHome.getFragment()).getNavController();
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    NavigationUI.setupWithNavController(binding.navView, navController);
  }

  @Override
  public boolean onSupportNavigateUp() {
    return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
  }
}