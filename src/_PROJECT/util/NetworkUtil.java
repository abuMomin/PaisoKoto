package _PROJECT.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//client-server communication -> 2 type of socket in networkUtil
public class NetworkUtil {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public NetworkUtil(String s, int port) throws IOException {
        ///server input,  output stream
        this.socket = new Socket(s, port);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public NetworkUtil(Socket s) throws IOException {
        this.socket = s;
        //client socket's input/output stream
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public Object read() throws IOException, ClassNotFoundException {
        //read from own inputStream
        return ois.readUnshared();
    }

    public void write(Object o) throws IOException {
        //write on outputStream
        oos.writeUnshared(o);
        oos.flush();
    }

    public void closeConnection() throws IOException {
//        ois.close();
//        oos.close();
    }
}

