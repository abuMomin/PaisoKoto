package _PROJECT;

import _PROJECT.client.ui.Front_page;
import _PROJECT.server.Server;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {

        if(_PROJECT.Config.isServer){
            new Server();
        }
        else {
            launch(args);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        new Front_page();
    }
}
