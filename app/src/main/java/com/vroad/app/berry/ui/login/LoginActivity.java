package com.vroad.app.berry.ui.login;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.vroad.app.basic.common.BasicActivityWithViewModelFactory;
import com.vroad.app.berry.databinding.ActivityLoginBinding;

public class LoginActivity extends BasicActivityWithViewModelFactory<ActivityLoginBinding, LoginViewModel, LoginViewModelFactory> {
  public LoginActivity() {
    super(false);
  }

  @Override
  protected void init() {
    registerLoginFormStateObserver();
    registerLoginResultObserver();
    binding.username.addTextChangedListener(textWatcher);
    binding.password.addTextChangedListener(textWatcher);
    binding.password.setOnEditorActionListener((v, actionId, event) -> {
      if (actionId == EditorInfo.IME_ACTION_DONE) {
        login();
      }
      return false;
    });
  }

  private void registerLoginFormStateObserver() {
    viewModel.getLoginFormState().observe(this, loginFormState -> {
      if (loginFormState == null)
        return;
      binding.login.setEnabled(loginFormState.isDataValid());
      if (loginFormState.getUsernameError() != null)
        binding.username.setError(getString(loginFormState.getUsernameError()));
      if (loginFormState.getPasswordError() != null)
        binding.password.setError(getString(loginFormState.getPasswordError()));
    });
  }

  private void registerLoginResultObserver() {
    viewModel.getLoginResult().observe(this, loginResult -> {
      if (loginResult == null)
        return;
      binding.loading.setVisibility(View.GONE);
      if (!loginResult.isSuccess()) {
        showLoginFailed(loginResult.getMessage());
      } else {
        setResult(Activity.RESULT_OK);
        finish();
      }
    });
  }

  private final TextWatcher textWatcher = new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
      viewModel.loginDataChanged(
          binding.username.getText().toString(),
          binding.password.getText().toString()
      );
    }
  };

  private void login() {
    binding.loading.setVisibility(View.VISIBLE);
    viewModel.login(
        binding.username.getText().toString(),
        binding.password.getText().toString()
    );
  }

  public void onClickLoginButton(View view) {
    login();
  }

  private void showLoginFailed(String message) {
    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
  }
}