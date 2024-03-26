package com.vroad.app.libui.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

import androidx.annotation.NonNull;

import com.elvishew.xlog.XLog;

public class NetworkMonitor extends ConnectivityManager.NetworkCallback {
  @Override
  public void onAvailable(@NonNull Network network) {
    XLog.i("-- network(%s) available --", network);
    super.onAvailable(network);
  }

  @Override
  public void onLost(@NonNull Network network) {
    XLog.i("-- network(%s) lost --", network);
    super.onLost(network);
  }

  @Override
  public void onLosing(@NonNull Network network, int maxMsToLive) {
    XLog.i("-- network(%s) losing", network);
    super.onLosing(network, maxMsToLive);
  }

  @Override
  public void onUnavailable() {
    XLog.i("-- network unavailable");
    super.onUnavailable();
  }

  @Override
  public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
    XLog.i("-- network(%s) capabilities(%s) changed --", network, networkCapabilities);
    super.onCapabilitiesChanged(network, networkCapabilities);
  }

  @Override
  public void onBlockedStatusChanged(@NonNull Network network, boolean blocked) {
    XLog.i("-- network(%s) status changed --", network);
    super.onBlockedStatusChanged(network, blocked);
  }

  @Override
  public void onLinkPropertiesChanged(@NonNull Network network, @NonNull LinkProperties linkProperties) {
    XLog.i("-- network(%s) link properties changed --", network);
    super.onLinkPropertiesChanged(network, linkProperties);
  }

  public static void start(Context context) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    connectivityManager.requestNetwork(
      new NetworkRequest.Builder().build(),
      new NetworkMonitor()
    );
  }
}
