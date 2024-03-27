package com.vroad.app.berry.ui.dashboard;

import static com.baidu.location.LocationClientOption.LocationMode.Hight_Accuracy;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.vroad.app.berry.R;
import com.vroad.app.berry.data.pojo.Device;
import com.vroad.app.berry.databinding.FragmentDashboardBinding;
import com.vroad.app.libui.base.AbstractApplication;
import com.vroad.app.libui.base.BasicFragment;
import com.vroad.app.libui.utils.ImageUtils;
import com.vroad.app.libui.utils.UtilsUI;

import java.util.List;

public class DashboardFragment extends BasicFragment<FragmentDashboardBinding, DashboardViewModel> {
  private LocationClient locationClient;
  private MapView mapView;
  private BaiduMap map;
  private boolean initialized;
  private Bitmap locationMarkerIcon;

  public DashboardFragment() {
    super(false, R.layout.fragment_dashboard);
  }

  @Override
  public void init(@Nullable Bundle savedInstanceState) {
    super.init(savedInstanceState);
    UtilsUI.centerToolbarTitle(binding.toolbar);
    viewModel.getDevices().observe(getViewLifecycleOwner(), deviceObserver);
    viewModel.loadDevices();
    initMap();
  }

  @Override
  public void release() {
    viewModel.getDevices().removeObserver(deviceObserver);
  }

  private void initMap() {
    locationMarkerIcon = ImageUtils.decodeBitmapFromResource(getContext(), R.drawable.ic_location_on_24dp, Color.RED);
    MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(13.0f).build());
    mapView = binding.mapview;
    map = mapView.getMap();
//    map.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
    map.setMyLocationEnabled(true);
    map.setMapStatus(mapStatusUpdate);
    map.setOnMarkerClickListener(onMarkerClickListener);
//    startLocationClient();
    locateTo(30.667179, 104.072474);
  }

  private void startLocationClient() {
    locationClient = new LocationClient(getContext());
    LocationClientOption option = new LocationClientOption();
    option.setOpenGps(true);
    option.setScanSpan(1000);
    option.setIsNeedAddress(true);
    option.setLocationMode(Hight_Accuracy);
    locationClient.setLocOption(option);
    locationClient.registerLocationListener(locationListener);
    locationClient.start();
  }

  private final Observer<List<Device>> deviceObserver = devices -> devices.forEach(device -> {
    Bundle bundle = new Bundle();
    bundle.putSerializable(Device.TAG, device);
    addMarker(device.getLatitude(), device.getLongitude(), bundle);
  });

  private final BDAbstractLocationListener locationListener = new BDAbstractLocationListener() {
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
      if (bdLocation == null || mapView == null || (
          bdLocation.getLocType() != BDLocation.TypeGpsLocation &&
              bdLocation.getLocType() != BDLocation.TypeNetWorkLocation))
        return;
      if (!initialized) {
        locateTo(bdLocation.getLatitude(), bdLocation.getLongitude());
        initialized = true;
      }
    }
  };

  private final BaiduMap.OnMarkerClickListener onMarkerClickListener = (marker) -> {
    showDeviceInfo((Device) marker.getExtraInfo().getSerializable(Device.TAG));
    return false;
  };

  private void showDeviceInfo(Device device) {
    Context context = getContext();
    if (context != null) {
      Dialog dialog = new Dialog(context, R.style.Theme_Berry_Dialog);
      View root = View.inflate(context, R.layout.layout_dialog_device_info, null);
      initDeviceInfoDialogView(root, device);
      dialog.setContentView(root);
      dialog.setCanceledOnTouchOutside(true);
      Window dialogWindow = dialog.getWindow();
      if (dialogWindow != null) {
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(layoutParams);
      }
      dialog.show();
    }
  }

  private void initDeviceInfoDialogView(View root, Device device) {
    ((TextView) root.findViewById(R.id.device_id)).setText(device.getDeviceId());
    ((TextView) root.findViewById(R.id.device_ip)).setText(device.getIp());
  }

  private void locateTo(double latitude, double longitude) {
    LatLng ll = new LatLng(latitude, longitude);
    MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
    map.animateMapStatus(update);
  }

  private void addMarker(double latitude, double longitude, Bundle extraInfo) {
    if (locationMarkerIcon != null) {
      LatLng point = new LatLng(latitude, longitude);
      BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(locationMarkerIcon);
      OverlayOptions option = new MarkerOptions()
          .position(point)
          .extraInfo(extraInfo)
          .icon(bitmap);
      map.addOverlay(option);
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    if (mapView != null)
      mapView.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    if (mapView != null)
      mapView.onPause();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (locationClient != null)
      locationClient.stop();
    if (map != null)
      map.setMyLocationEnabled(false);
    if (mapView != null)
      mapView.onDestroy();
  }

  @Override
  public AbstractApplication getAbstractApplication() {
    return (AbstractApplication) requireActivity().getApplication();
  }
}