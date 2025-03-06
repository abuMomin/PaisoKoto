package _PROJECT.client.ui;


import javafx.application.Application; // to use JAVAFX -> extend the abstract Application class
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

class ConfirmationPage{
    ConfirmationPage(String id, String email, String pass){
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Signup Page 2");

        Text text_2 = new Text("Student Account Created");
        text_2.setFont(Font.font("Tahoma", FontWeight.BOLD, 30));
        text_2.setX(10);
        text_2.setY(25);

        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(5);
        gp.setVgap(8);

        Label lb_id = new Label("User Name: ");
        Label lb_email = new Label("Email: ");
        Label lb_password = new Label("Password: ");

        Label lb_idShow = new Label(id);
        Label lb_emailShow = new Label(email);
        Label lb_passwordShow = new Label(pass);

        gp.add(text_2, 0, 0, 3, 2);               // node, col, row, colspan, rowspan

        gp.add(lb_email, 0, 2, 2, 2);
        gp.add(lb_emailShow, 2,  2, 7, 2);
        gp.add(lb_id, 0, 4, 2, 2);
        gp.add(lb_idShow, 2, 4, 5, 2);
        gp.add(lb_password, 0, 6, 2, 2);
        gp.add(lb_passwordShow, 2,  6, 5, 2);


        VBox root = new VBox();
        root.getChildren().add(gp);

        Scene scene = new Scene(root,600, 600);
        secondaryStage.setScene(scene);
        secondaryStage.show();
    }
}

public class SignUpPage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.setTitle("Signup Page");

        Text text = new Text("Create an Student Account");
        text.setFont(Font.font("Tahoma", FontWeight.BOLD, 24));
        text.setX(10);
        text.setY(25);

        Text text_fillup = new Text("You must fill up all the fields");
        text_fillup.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        text_fillup.setFill(Color.RED);
        text_fillup.setVisible(false);

        Text text_wrongPass = new Text("Password Do not match");
        text_wrongPass.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        text_wrongPass.setFill(Color.RED);
        text_wrongPass.setVisible(false);


        //to get Project src absolute path
        String path = "src/_PROJECT/Files";
        File file = new File(path);
        String absolutePath = file.getAbsolutePath();
        absolutePath += "\\UIU_Logo.png";

        Image uiu_img = new Image(new FileInputStream(absolutePath));
        //Setting the image view
        ImageView imageView = new ImageView(uiu_img);
        //Setting the position of the image
//        imageView.setX(50);
//        imageView.setY(100);

        //setting the fit height and width of the image view
        imageView.setFitHeight(100);
        imageView.setFitWidth(400);

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);

        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(5);
        gp.setVgap(8);

        Label lb_email = new Label("Email: ");
        TextField tx_email = new TextField("win.momin@gmail.com");
        Label lb_id = new Label("University ID: ");
        TextField tx_id = new TextField("011193076");
        Label lb_pass1 = new Label("Password: ");
        PasswordField tx_pass1 = new PasswordField();
        Label lb_pass2 = new Label("Confirm Password: ");
        PasswordField tx_pass2 = new PasswordField();

        Button btn_subm = new Button("Submit");
        btn_subm.setOnMouseClicked(mouseEvent -> {

            if( !(tx_email.getText()).equals("") && !(tx_id.getText()).equals("") && !(tx_pass1.getText()).equals("")  && !(tx_pass2.getText()).equals("") ){
                if( (tx_pass1.getText()).equals(tx_pass2.getText()) ){
                    primaryStage.close();
                    new ConfirmationPage(tx_id.getText(), tx_email.getText(), tx_pass1.getText());
                }
                else {
                    text_fillup.setVisible(false);
                    text_wrongPass.setVisible(true);
                }
            }
            else{
                text_wrongPass.setVisible(false);
                text_fillup.setVisible(true);
            }
        });


        gp.add(text, 0, 0, 3, 3);               // node, col, row, colspan, rowspan
        gp.add(lb_email, 0, 3, 2, 2);
        gp.add(tx_email, 2,  3, 3, 2);

        gp.add(lb_id, 0, 5, 2, 2);
        gp.add(tx_id, 2,  5, 3, 2);

        gp.add(lb_pass1, 0, 7, 2, 2);
        gp.add(tx_pass1, 2,  7, 1, 2);
        gp.add(lb_pass2, 0, 9, 2, 2);
        gp.add(tx_pass2, 2, 9, 1, 2);

        gp.add(btn_subm, 2, 11, 3, 3);
        gp.add(text_fillup, 0, 18,10,3);
        gp.add(text_wrongPass, 0, 18,10,3);
        gp.add(imageView, 0, 24,20,5);


        Scene scene = new Scene(gp,600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
