package com.vroad.app.berry.ui.common;

import androidx.annotation.Nullable;

import com.vroad.app.basic.common.ApplicationInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Authentication result : success (user details) or error message.
 */
@Getter
@AllArgsConstructor
public class OperationResult {
  private boolean success;
  @Nullable
  private String message;

  public OperationResult(boolean success) {
    this.success = success;
  }

  public OperationResult(boolean success, int messageResourceId) {
    this.success = success;
    this.message = ApplicationInfo.getContext().getResources().getString(messageResourceId);
  }
}