package com.vroad.app.berry.ui.login;

import androidx.annotation.Nullable;

class LoginFormState {
  @Nullable
  private final Integer usernameError;
  @Nullable
  private final Integer passwordError;
  private final boolean isDataValid;

  LoginFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
    this.usernameError = usernameError;
    this.passwordError = passwordError;
    this.isDataValid = false;
  }

  LoginFormState(boolean isDataValid) {
    this.usernameError = null;
    this.passwordError = null;
    this.isDataValid = isDataValid;
  }

  @Nullable
  Integer getUsernameError() {
    return usernameError;
  }

  @Nullable
  Integer getPasswordError() {
    return passwordError;
  }

  boolean isDataValid() {
    return isDataValid;
  }
}