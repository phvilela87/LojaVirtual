package dm114.br.inatel.pvilela.lojavirtual.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dm114.br.inatel.pvilela.lojavirtual.R;

public class WSUtil {
    private WSUtil() {
    }

    public static String getSalesProviderHostAddress(Context context) {
        String host;
        String baseAddress;
        int port;
        SharedPreferences sharedSettings =
                PreferenceManager.getDefaultSharedPreferences(context);
        host = sharedSettings.getString(
                context.getString(R.string.pref_ws_host),
                context.getString(R.string.pref_ws_sales_provider_host));
        port = sharedSettings.getInt(
                context.getString(R.string.pref_host_port),
                Integer.parseInt(context.getString(R.string.pref_ws_default_port)));
        if (host.endsWith("/")) {
            host = host.substring(0, host.length() - 1);
        }
        if (!host.startsWith("http://")) {
            host = "http://" + host;
        }
        baseAddress = host + ":" + port;
        return baseAddress;
    }

    public static String getMessageProviderHostAddress(Context context) {
        String host;
        String baseAddress;
        int port;
        SharedPreferences sharedSettings =
                PreferenceManager.getDefaultSharedPreferences(context);
        host = sharedSettings.getString(
                context.getString(R.string.pref_ws_host),
                context.getString(R.string.pref_ws_message_provider_host));
        port = sharedSettings.getInt(
                context.getString(R.string.pref_host_port),
                Integer.parseInt(context.getString(R.string.pref_ws_default_port)));
        if (host.endsWith("/")) {
            host = host.substring(0, host.length() - 1);
        }
        if (!host.startsWith("http://")) {
            host = "http://" + host;
        }
        baseAddress = host + ":" + port;
        return baseAddress;
    }
}
