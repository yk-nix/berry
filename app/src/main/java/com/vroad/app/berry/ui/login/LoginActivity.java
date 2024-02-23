package com.vroad.app.berry.ui.login;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModelProvider;

import com.vroad.app.basic.BasicActivity;
import com.vroad.app.berry.databinding.ActivityLoginBinding;

public class LoginActivity extends BasicActivity<ActivityLoginBinding> {

  private LoginViewModel loginViewModel;

  public LoginActivity() {
    super(true);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory()).get(LoginViewModel.class);

    loginViewModel.getLoginFormState().observe(this, loginFormState -> {
      if (loginFormState == null)
        return;
      binding.login.setEnabled(loginFormState.isDataValid());
      if (loginFormState.getUsernameError() != null)
        binding.username.setError(getString(loginFormState.getUsernameError()));
      if (loginFormState.getPasswordError() != null)
        binding.password.setError(getString(loginFormState.getPasswordError()));
    });

    loginViewModel.getLoginResult().observe(this, loginResult -> {
      if (loginResult == null)
        return;
      binding.loading.setVisibility(View.GONE);
      if (loginResult.getError() != null)
        showLoginFailed(loginResult.getError());
      if (loginResult.getSuccess() != null) {
        setResult(Activity.RESULT_OK);
        finish();
      }
    });

    TextWatcher textWatcher = new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override
      public void afterTextChanged(Editable s) {
        loginViewModel.loginDataChanged(
            binding.username.getText().toString(),
            binding.password.getText().toString()
        );
      }
    };
    binding.username.addTextChangedListener(textWatcher);
    binding.password.addTextChangedListener(textWatcher);
    binding.password.setOnEditorActionListener((v, actionId, event) -> {
      if (actionId == EditorInfo.IME_ACTION_DONE) {
        login();
      }
      return false;
    });
  }

  private void login() {
    binding.loading.setVisibility(View.VISIBLE);
    loginViewModel.login(
        binding.username.getText().toString(),
        binding.password.getText().toString());
  }

  public void onClickLoginButton(View view) {
    login();
  }

  private void showLoginFailed(@StringRes Integer errorString) {
    Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
  }
}