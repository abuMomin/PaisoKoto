package _PROJECT.client.connection;

import _PROJECT.util.NetworkUtil;

import java.io.IOException;
import java.util.Scanner;

public class WriteThreadClient implements Runnable{
    Thread t;
    Object data;
    NetworkUtil networkUtil;


    public WriteThreadClient(NetworkUtil networkUtil,Object data){
        this.data = data;
        this.networkUtil = networkUtil;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            // this networkUtil is server's -> writing on server's output stream
            networkUtil.write(this.data);
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
