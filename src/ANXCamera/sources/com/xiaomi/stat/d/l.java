package com.xiaomi.stat.d;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.MiuiSettings;
import android.text.TextUtils;
import com.xiaomi.stat.I;
import java.util.Iterator;

public class l {

    /* renamed from: a  reason: collision with root package name */
    private static final String f508a = "NetWorkStateUtil";

    /* renamed from: b  reason: collision with root package name */
    private static final int f509b = 16;

    /* renamed from: c  reason: collision with root package name */
    private static final int f510c = 17;

    /* renamed from: d  reason: collision with root package name */
    private static final int f511d = 18;

    /* renamed from: e  reason: collision with root package name */
    private static final int f512e = 19;

    /* renamed from: f  reason: collision with root package name */
    private static final String f513f = "2G";
    private static final String g = "3G";
    private static final String h = "4G";
    private static final String i = "WIFI";
    private static final String j = "ETHERNET";
    private static final String k = "UNKNOWN";
    private static final String l = "NOT_CONNECTED";

    private static class a {
        private a() {
        }

        public static String a(Context context) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return l.l;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                return l.l;
            }
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
            NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(9);
            if (networkInfo == null || !networkInfo.isConnected()) {
                return (networkInfo2 == null || !networkInfo2.isConnected()) ? l.k : l.j;
            }
            return l.i + b(context);
        }

        private static boolean a(int i) {
            return i > 4900 && i < 5900;
        }

        private static String b(Context context) {
            try {
                if (Build.VERSION.SDK_INT >= 22) {
                    int frequency = ((WifiManager) context.getSystemService(MiuiSettings.System.WIFI_SHARE)).getConnectionInfo().getFrequency();
                    if (!a(frequency)) {
                        if (!b(frequency)) {
                            return "";
                        }
                        return l.f513f;
                    }
                } else {
                    char c2 = 65535;
                    WifiManager wifiManager = (WifiManager) context.getSystemService(MiuiSettings.System.WIFI_SHARE);
                    String ssid = wifiManager.getConnectionInfo().getSSID();
                    String substring = ssid.substring(1, ssid.length() - 1);
                    if (ssid != null && ssid.length() > 2) {
                        Iterator<ScanResult> it = wifiManager.getScanResults().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            ScanResult next = it.next();
                            if (next.SSID.equals(substring)) {
                                if (a(next.frequency)) {
                                    c2 = 2;
                                } else if (b(next.frequency)) {
                                    c2 = 1;
                                }
                            }
                        }
                    }
                    if (c2 != 2) {
                        if (c2 != 1) {
                            return "";
                        }
                        return l.f513f;
                    }
                }
                return "5G";
            } catch (Exception e2) {
                k.d(l.f508a, "getWifiFreeBand error", e2);
                return "";
            }
        }

        private static boolean b(int i) {
            return i > 2400 && i < 2500;
        }
    }

    public static int a(Context context) {
        String b2 = b(context);
        if (!TextUtils.isEmpty(b2) && !b2.equals(l)) {
            if (b2.equals(f513f)) {
                return 1;
            }
            if (b2.equals(g)) {
                return 2;
            }
            if (b2.equals(h)) {
                return 4;
            }
            if (b2.startsWith(i)) {
                return 8;
            }
            if (b2.equals(j)) {
                return 16;
            }
        }
        return 0;
    }

    public static boolean a() {
        Context a2 = I.a();
        if (a2 == null) {
            return false;
        }
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) a2.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                return activeNetworkInfo.isConnectedOrConnecting();
            }
            return false;
        } catch (Exception unused) {
            k.b("isNetworkConnected exception");
            return false;
        }
    }

    public static String b(Context context) {
        try {
            if (e.w(context)) {
                return a.a(context);
            }
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return l;
            }
            if (!activeNetworkInfo.isConnected()) {
                return l;
            }
            if (activeNetworkInfo.getType() == 1) {
                return i;
            }
            if (activeNetworkInfo.getType() == 0) {
                switch (activeNetworkInfo.getSubtype()) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                    case 16:
                        return f513f;
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 14:
                    case 15:
                    case 17:
                        return g;
                    case 13:
                    case 18:
                    case 19:
                        return h;
                    default:
                        return k;
                }
            }
            return k;
        } catch (Exception e2) {
            k.d(f508a, "getNetworkState error", e2);
        }
    }
}
