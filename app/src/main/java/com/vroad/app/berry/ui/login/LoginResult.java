package com.vroad.app.berry.ui.login;

import androidx.annotation.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Authentication result : success (user details) or error message.
 */
@Getter
@AllArgsConstructor
class LoginResult {
  private boolean success;
  @Nullable
  private String message;
}