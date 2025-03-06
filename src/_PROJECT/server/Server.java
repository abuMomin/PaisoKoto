package _PROJECT.server;


import _PROJECT.Config;
import _PROJECT.util.NetworkUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    //2 types socket. serversocket, client socket
    private ServerSocket serverSocket;
    //from collection API
    public HashMap<String, NetworkUtil> clientMap;


    public Server(){
        //hashmap initialization.
        clientMap  = new HashMap<>();
        try {
            //server socket initialization -> confiq -> port number
            serverSocket = new ServerSocket(Config.serverPort);

            while(true){
                System.out.println("Server Waiting for clients ... ");
                //client socket
                Socket clientSocket = serverSocket.accept();
                // client req serve method -> serve
                serve(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void serve(Socket clientSocket) throws IOException, ClassNotFoundException {
        NetworkUtil netUtil = new NetworkUtil(clientSocket);
        new ReadThreadServer(clientMap, netUtil);
    }

    public static void main(String[] args) {
        new Server();
    }


}
