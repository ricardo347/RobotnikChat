package br.com.robotnik.robotnikchat.control;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.util.Log;

public class ConnectionStateMonitor extends ConnectivityManager.NetworkCallback {
    private ConnectivityManager connectivityManager ;
    private Context context;
    private boolean connectionStatus;
    private final NetworkRequest networkRequest;


    public ConnectionStateMonitor(Context context) {
        this.context = context;
       this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkRequest = new NetworkRequest.Builder()
                //.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                //.addTransportType(NetworkCapabilities.TRANSPORT_WIFI
                //.addCapability(NetworkCapabilities.NET_CAPABILITY_RCS)
                .addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();
        connectionStatus = true;
    }

    public void enable() {
        //connectivityManager.requestNetwork(networkRequest, this, 1000);
        connectivityManager.registerNetworkCallback(networkRequest , this);
    }
    public void disable(){
        connectivityManager.unregisterNetworkCallback(this);
    }
    // Likewise, you can have a disable method that simply calls ConnectivityManager.unregisterNetworkCallback(NetworkCallback) too.

    public boolean getConnectivityStatus(){

        ConnectivityManager connectivityManager = (ConnectivityManager)context.
            getSystemService(Context.CONNECTIVITY_SERVICE);

        Network network = connectivityManager.getActiveNetwork();
        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);

            boolean result = capabilities != null
                    && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);

            Log.v("getConnectivityStatus",""+result);
        return result;

    }

    @Override
    public void onAvailable(Network network) {
        // Do what you need to do here
        Log.v("ESTAdo", "%%%%%%%%%%%%%%%%%% Subiu");
        connectionStatus = true;
    }
    @Override
    public void onLost(Network network){
        Log.v("ESTAdo", "%%%%%%%%%%%%%%%%%% caiu");
        connectionStatus= false;
    }
    @Override
    public void onUnavailable(){
        Log.v("EStado", "%%%%%%%%%%%%%%%%%%% CAIU DE VEZ");
        connectionStatus = false;
    }

}