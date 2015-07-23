package com.irontec.beaconreference;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.irontec.beaconreference.beacon.BeaconsDefinition;
import com.irontec.beaconreference.utils.BluethootUtils;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

/**
 * 
 * @author dyoung
 * @author Matt Tyler
 */
public class MonitoringActivity extends AppCompatActivity implements BeaconConsumer {

	protected static final String TAG = MonitoringActivity.class.getSimpleName();

    private BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);;
	private TextView mBlueBeaconTv, mWhiteBeaconTv, mBlackBeaconTv, mPirateBeaconTv;
	private ImageView mPirateImg, mWhiteImg, mBlackImg, mBlueImg;
	private int blueCounter = 0, whiteCounter = 0, blackCounter = 0, pirateCounter = 0;
	private int MAX_TIMES_NOT_APPEARING = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monitoring);

		mPirateImg = (ImageView) findViewById(R.id.pirateAvatar);
		mWhiteImg = (ImageView) findViewById(R.id.whiteAvatar);
		mBlueImg = (ImageView) findViewById(R.id.blueAvatar);
		mBlackImg = (ImageView) findViewById(R.id.blackAvatar);

		mBlueBeaconTv = (TextView) findViewById(R.id.blueBeaconTv);
		mBlackBeaconTv = (TextView) findViewById(R.id.blackBeaconTv);
		mWhiteBeaconTv = (TextView) findViewById(R.id.whiteBeaconTv);
		mPirateBeaconTv = (TextView) findViewById(R.id.pirateBeaconTv);

		if (BluethootUtils.verifyBluetooth(this)) {
			beaconManager.bind(this);
		} else {
			BluethootUtils.verifyBluetoothWithDialog(this);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		beaconManager.unbind(this);
	}

    @Override
    public void onResume() {
        super.onResume();
		if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(false);
        ((BeaconApplication) this.getApplicationContext()).setMonitoringActivity(this);
    }

    @Override
    public void onPause() {
        super.onPause();
		if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(true);
        ((BeaconApplication) this.getApplicationContext()).setMonitoringActivity(null);
    }

	@Override
	public void onBeaconServiceConnect() {
		beaconManager.setRangeNotifier(new RangeNotifier() {
			@Override
			public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
				blueCounter++;
				blackCounter++;
				whiteCounter++;
				pirateCounter++;
				if (beacons.size() > 0) {
					for (Beacon beacon : beacons) {
                        if (beacon == null) {
                            continue;
                        }
						if (BeaconsDefinition.BLUE_ID1.equalsIgnoreCase(beacon.getId1().toString())) {
							changeBlueTextViewContent(String.format("El beacon AZUL se encuentra a %f metros", beacon.getDistance()));
							blueCounter = 0;
						} else if (BeaconsDefinition.WHITE_ID1.equalsIgnoreCase(beacon.getId1().toString())) {
							changeWhiteTextViewContent(String.format("El beacon BLANCO se encuentra a %f metros", beacon.getDistance()));
							whiteCounter = 0;
						} else if (BeaconsDefinition.BLACK_ID1.equalsIgnoreCase(beacon.getId1().toString())) {
							changeBlackTextViewContent(String.format("El beacon NEGRO se encuentra a %f metros", beacon.getDistance()));
							blackCounter = 0;
						} else {
							showPirateBeacon(
									String.format("¡¡¡Hay un beacon PIRATA a %f metros!!! \n Uuid: %s \n Major %s y Minor %s",
									beacon.getDistance(), beacon.getId1(), beacon.getId2(), beacon.getId3()));
							pirateCounter = 0;
						}
					}
					if (blueCounter > MAX_TIMES_NOT_APPEARING) {
						runOnUiThread(new Runnable() {
							public void run() {
								if (mBlueImg != null && mBlueBeaconTv != null) {
									mBlueImg.setVisibility(View.INVISIBLE);
									mBlueBeaconTv.setText("");
								}
							}
						});
					}
					if (whiteCounter > MAX_TIMES_NOT_APPEARING) {
						runOnUiThread(new Runnable() {
							public void run() {
								if (mWhiteImg != null && mWhiteBeaconTv != null) {
									mWhiteImg.setVisibility(View.INVISIBLE);
									mWhiteBeaconTv.setText("");
								}
							}
						});
					}
					if (blackCounter > MAX_TIMES_NOT_APPEARING ) {
						runOnUiThread(new Runnable() {
							public void run() {
								if (mBlackImg != null && mBlackBeaconTv != null) {
									mBlackImg.setVisibility(View.INVISIBLE);
									mBlackBeaconTv.setText("");
								}
							}
						});
					}
					if (pirateCounter > MAX_TIMES_NOT_APPEARING) {
						runOnUiThread(new Runnable() {
							public void run() {
								if (mPirateImg != null && mPirateBeaconTv != null) {
									mPirateImg.setVisibility(View.INVISIBLE);
									mPirateBeaconTv.setText("");
								}
							}
						});
					}
				}
			}

		});

		try {
			beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
		} catch (RemoteException e) { e.printStackTrace(); }
	}

	public void showPirateBeacon(final String line) {
		runOnUiThread(new Runnable() {
			public void run() {
				if (mPirateImg != null) {
					mPirateImg.setVisibility(View.VISIBLE);
				}
				if (mPirateBeaconTv != null) {
					mPirateBeaconTv.setText(line);
				}
			}
		});
	}

	public void changeBlueTextViewContent(final String line) {
		runOnUiThread(new Runnable() {
			public void run() {
				if (mBlueImg != null) {
					mBlueImg.setVisibility(View.VISIBLE);
				}
				if (mBlueBeaconTv != null) {
					mBlueBeaconTv.setText(line);
				}
			}
		});
	}

	public void changeWhiteTextViewContent(final String line) {
		runOnUiThread(new Runnable() {
			public void run() {
				if (mWhiteImg != null) {
					mWhiteImg.setVisibility(View.VISIBLE);
				}
				if (mWhiteBeaconTv != null) {
					mWhiteBeaconTv.setText(line);
				}
			}
		});
	}

	public void changeBlackTextViewContent(final String line) {
		runOnUiThread(new Runnable() {
			public void run() {
				if (mBlackImg != null) {
					mBlackImg.setVisibility(View.VISIBLE);
				}
				if (mBlackBeaconTv != null) {
					mBlackBeaconTv.setText(line);
				}
			}
		});
	}

}
