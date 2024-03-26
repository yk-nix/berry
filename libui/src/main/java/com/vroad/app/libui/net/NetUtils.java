package com.vroad.app.libui.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

public class NetUtils {

  public static NetworkCapabilities getActiveNetworkCapabilities(Context context) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    return connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
  }

  public static NetworkCapabilities getNetworkCapabilities(Context context, Network network) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    return connectivityManager.getNetworkCapabilities(network);
  }

  public static boolean isInternetAvailable(Context context) {
    return getActiveNetworkCapabilities(context).hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
  }

  public static boolean isInternetAvailable(Context context, Network network) {
    return getNetworkCapabilities(context, network).hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
  }

  public static boolean isWifiAvailable(Context context) {
    return getActiveNetworkCapabilities(context).hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
  }

  public static boolean isWifiAvailable(Context context, Network network) {
    return getNetworkCapabilities(context, network).hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
  }
  public static boolean isCellularAvailable(Context context) {
    return getActiveNetworkCapabilities(context).hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
  }

  public static boolean isCellularAvailable(Context context, Network network) {
    return getNetworkCapabilities(context, network).hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
  }
}
