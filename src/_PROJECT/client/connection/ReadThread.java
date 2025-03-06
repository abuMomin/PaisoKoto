package _PROJECT.client.connection;

import _PROJECT.util.NetworkUtil;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ReadThread implements Runnable{
    Thread t;
    Object response;
    NetworkUtil networkUtil;

    public ReadThread(NetworkUtil networkUtil){
        this.networkUtil = networkUtil;
        t = new Thread(this);
        t.start();
    }

     void setResponse(Object response) {
        this.response = response;
    }

    public Object getResponse() {
        return response;
    }

    @Override
    public void run() {
        try {
                setResponse(networkUtil.read());

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                networkUtil.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
