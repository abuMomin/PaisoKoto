package _PROJECT.client.connection;

import _PROJECT.Config;
import _PROJECT.util.NetworkUtil;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

// client's netwrok util for server

public class Client {
    NetworkUtil networkUtil;
    public Client(){
        String serverAddress = Config.serverIP;
        int port = Config.serverPort;

        try {
            NetworkUtil networkUtil = new NetworkUtil(serverAddress,port);
            this.networkUtil = networkUtil;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object sendRequest(String resource, String data){
        new WriteThreadClient(networkUtil, resource + "|" + data);
        ReadThread readThread = new ReadThread(networkUtil);
        try {
            readThread.t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return readThread.getResponse();
    }

}
