package _PROJECT.client.user;

import java.io.Serializable;

//making it serialized  due so that user_object can written in the OutputStream of the Socket
public class users implements Serializable {
    String name;
    String pass;
    int type;
    int id;

    public users(){}
    public users(String name, String pass, int type, int id) {
        this.name = name;
        this.pass = pass;
        this.type = type;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
