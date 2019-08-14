package com.idobro.kilovoltmetr_dosimetr.viewmodel;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.idobro.kilovoltmetr_dosimetr.bluetooth.SerialListener;

import com.idobro.kilovoltmetr_dosimetr.bluetooth.SerialSocket;

import java.io.IOException;

public class MainActivityViewModel extends AndroidViewModel implements SerialListener {
    private MutableLiveData<String> serverResponse;
    private MutableLiveData<Connected> connectStatus;

    public enum Connected {False, Pending, True}

    private SerialSocket socket;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getServerResponseLiveData() {
        if (serverResponse == null) {
            serverResponse = new MutableLiveData<>();
        }
        return serverResponse;
    }

    public LiveData<Connected> getStatusLiveData() {
        if (connectStatus == null) {
            connectStatus = new MutableLiveData<>();
            connectStatus.setValue(Connected.False);
        }
        return connectStatus;
    }

    @Override
    protected void onCleared() {
        if(connectStatus.getValue() != Connected.False)
            disconnect();
        super.onCleared();
    }

    private Context getContext() {
        return getApplication().getApplicationContext();
    }

    public void connect(BluetoothDevice device) {
        try {
            connectStatus.postValue(Connected.Pending);
            socket = new SerialSocket();
            socket.connect(getContext(), this, device);
        } catch (IOException e) {
            onSerialIoError(e);
        }
    }

    private void disconnect() {
        connectStatus.postValue(Connected.False);
        socket.disconnect();
        socket = null;
    }

    public void send(String string) {
        if (connectStatus.getValue() != Connected.True) {
            Toast.makeText(getContext(), "not connect", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            byte[] data = string.getBytes();
            socket.write(data);
        } catch (Exception e) {
            onSerialConnectError(e);
        }
    }

    //SerialListener
    @Override
    public void onSerialConnect() {
        Log.d("LOG", "MainActivityViewModel -> onSerialConnect : ");

    }

    @Override
    public void onSerialConnectError(Exception e) {
        Log.d("LOG", "MainActivityViewModel -> onSerialConnectError : ");

    }

    @Override
    public void onSerialRead(byte[] data) {
        Log.d("LOG", "MainActivityViewModel -> onSerialRead : ");

    }

    @Override
    public void onSerialIoError(Exception e) {
        Log.d("LOG", "MainActivityViewModel -> onSerialIoError : ");

    }
}
