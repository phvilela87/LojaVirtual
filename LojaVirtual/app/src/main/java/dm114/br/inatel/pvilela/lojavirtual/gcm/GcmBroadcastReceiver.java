package dm114.br.inatel.pvilela.lojavirtual.gcm;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

import dm114.br.inatel.pvilela.lojavirtual.MainActivity;
import dm114.br.inatel.pvilela.lojavirtual.R;
import dm114.br.inatel.pvilela.lojavirtual.models.OrderInfo;
import dm114.br.inatel.pvilela.lojavirtual.models.ProductInfo;
import dm114.br.inatel.pvilela.lojavirtual.models.ProductInterest;

public class GcmBroadcastReceiver extends BroadcastReceiver {
    private Context context;
    private NotificationManager mNotificationManager;
    public static final int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
        this.context = context;
        String messageType = gcm.getMessageType(intent);
        if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
            Bundle extras = intent.getExtras();
            Gson gson = new Gson();
            if (extras.containsKey("product")) {
                String productStr = extras.getString("product");
                if (productStr != null) {
                    ProductInterest productInterest = gson.fromJson(productStr,
                            ProductInterest.class);
                    sendProductNotification(productInterest);
                }
            }
            else if(extras.containsKey("orderInfo")) {
                String productStr = extras.getString("orderInfo");
                if(productStr != null) {
                    OrderInfo orderInfo = gson.fromJson(productStr, OrderInfo.class);
                    sendOrderNotification(orderInfo);
                }
            }
        }
        setResultCode(Activity.RESULT_OK);
    }

    private void sendProductNotification(ProductInterest productInterest) {
        mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("productInterest", productInterest);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder mBuilder = new Notification.Builder(
                context)
                .setSmallIcon(R.drawable.ic_warning_black_24dp)
                .setAutoCancel(true)
                .setContentTitle("Siecola Vendas")
                .setStyle(new Notification.BigTextStyle().bigText("Produto:"
                        + productInterest.getId() + " - "
                        + String.valueOf(productInterest.getPrice())))
                .setContentText(
                        "Produto:" + productInterest.getId() + " - "
                                + String.valueOf(productInterest.getPrice()));
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private void sendOrderNotification(OrderInfo orderInfo) {
        mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("orderInfo", orderInfo);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder mBuilder = new Notification.Builder(
                context)
                .setSmallIcon(R.drawable.ic_warning_black_24dp)
                .setAutoCancel(true)
                .setContentTitle("Siecola Vendas")
                .setStyle(new Notification.BigTextStyle().bigText("Pedido:"
                        + orderInfo.getOrderID() + " - "
                        + String.valueOf(orderInfo.getNewOrderStatus())))
                .setContentText(
                        "Pedido:" + orderInfo.getOrderID() + " - "
                                + String.valueOf(orderInfo.getNewOrderStatus()));
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

}