package com.irontec.beaconreference.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.irontec.beaconreference.R;

import org.altbeacon.beacon.BeaconManager;

/**
 * Created by axier on 8/7/15.
 */
public class BluethootUtils {

    public static void verifyBluetoothWithDialog(Context context) {
        try {
            if (!BeaconManager.getInstanceForApplication(context).checkAvailability()) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.BeaconsDialog);
                builder.setTitle("Bluetooth not enabled");
                builder.setMessage("Please enable bluetooth in settings and restart this application.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.show();
            }
        } catch (RuntimeException e) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.BeaconsDialog);
            builder.setTitle("Bluetooth LE not available");
            builder.setMessage("Sorry, this device does not support Bluetooth LE.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.show();
        }

    }

    public static Boolean verifyBluetooth(Context context) {
        try {
            return BeaconManager.getInstanceForApplication(context).checkAvailability();
        } catch (RuntimeException e) {
            return false;
        }
    }
}
