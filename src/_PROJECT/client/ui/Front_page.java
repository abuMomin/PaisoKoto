package _PROJECT.client.ui;

import _PROJECT.Config;
import _PROJECT.client.connection.Client;
import _PROJECT.client.user.users;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.stream.Collectors;

class login{
    login(Client client){
        Stage login_stage = new Stage();
        login_stage.setTitle("LOG-IN PAGE");

        Text text_3 = new Text("Login");
        text_3.setFont(Font.font("Tahoma", FontWeight.BOLD, 32));
        text_3.setX(10);
        text_3.setY(25);

        Text text_wrongPass = new Text("Password Do not match");
        text_wrongPass.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        text_wrongPass.setFill(Color.RED);
        text_wrongPass.setVisible(false);

        Text text_fillup = new Text("You must fill up all the fields");
        text_fillup.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        text_fillup.setFill(Color.RED);
        text_fillup.setVisible(false);

        Label lb_email = new Label("Email/ID : ");
        TextField tx_email = new TextField("teacher");
        Label lb_pass = new Label("PassWord : ");
        PasswordField tx_pass = new PasswordField();

        HBox hBox = new HBox(30);
        hBox.getChildren().add(lb_email);
        hBox.getChildren().add(tx_email);
        hBox.setAlignment(Pos.CENTER);

        HBox hBox2 = new HBox(30);
        hBox2.getChildren().add(lb_pass);
        hBox2.getChildren().add(tx_pass);
        hBox2.setAlignment(Pos.CENTER);


        Button login_btn2 = new Button("Login");
        login_btn2.setOnMouseClicked(mEvent ->{
            boolean isTeacher;
            if( (tx_email.getText()).equals("") || (tx_pass.getText()).equals("")){
                text_wrongPass.setVisible(false);
                text_fillup.setVisible(true);
            }
            else {
                users user = null;
                String responseCode = null;
                Object response =  client.sendRequest("checkPass",
                        "user:" + tx_email.getText() + ",pass:" + tx_pass.getText());
                try{
                   user = (users) response;
                }
                catch (Exception e){
                    responseCode = (String) response;
                }

                if(user != null){
                    text_fillup.setVisible(false);
                    text_wrongPass.setVisible(false);
                    System.out.println("Teacher Login Successful...");
                    login_stage.close();

                    new ExamPortal(client, user);

                }
                else if(responseCode != null){
                    text_fillup.setVisible(false);
                    text_wrongPass.setVisible(true);
                }
                else {
                    text_fillup.setVisible(false);
                    text_wrongPass.setVisible(true);
                }
            }
        });



        VBox root = new VBox(20, text_3, hBox,hBox2,login_btn2);
        root.getChildren().add(text_fillup);
        root.getChildren().add(text_wrongPass);
        root.setAlignment(Pos.CENTER);

        Scene login_scene = new Scene(root,600, 600);
        login_stage.setScene(login_scene);
        login_stage.show();
    }
}

class ExamPortal{
    ExamPortal(Client client, users user){

        boolean isTeacher = false;
        if(user.getType() == 1){
            isTeacher = true;
        }

        if(isTeacher){
            Stage teach_stage = new Stage();
            teach_stage.setTitle("Teacher Portal");

            Text text_intro = new Text("Welcome to Teachers Portal");
            text_intro.setFont(Font.font("Tahoma", FontWeight.BOLD, 24));
            text_intro.setFill(Color.BLUE);

            Label lb_que = new Label("Question Path");
            TextField tx_que = new TextField("C:\\Users\\prith\\Desktop\\question\\question.txt");
            Button upld_btn = new Button("Upload");

            VBox vBox_1 = new VBox(7);
            HBox hBox_1 = new HBox(10, lb_que, tx_que);
            hBox_1.setAlignment(Pos.CENTER);
            vBox_1.getChildren().add(hBox_1);
            vBox_1.getChildren().add(upld_btn);
            vBox_1.setAlignment(Pos.CENTER);

            //upload Button event
            upld_btn.setOnMouseClicked(e ->{
                // write here

                try{
                    String filePath = tx_que.getText();
                    File file = new File(filePath);
                    //Inputstream is wht !!!
                    InputStream targetStream = new FileInputStream(file);
                    // file to inputStream ... inputStream to string ->>> string because it'll be written in server Output Stream
                    //Text file k socket diye send korar code
                    String result = new BufferedReader(new InputStreamReader(targetStream))
                            .lines().collect(Collectors.joining("\n"));
                    //sendreq in always 
                    String response = (String) (client.sendRequest("uploadQuestion", result));
                    if(response.equals("200")){
                        System.out.println("uploaded successfully");
                    }
                    else{
                        System.out.println("upload failed");
                    }


                }
                catch (Exception ex){
                    System.out.println("upload failed");
                   // ex.printStackTrace();
                }
            });


            Label lb_timer = new Label("Exam Time(minutes)");
            lb_timer.setVisible(false);
            Spinner spinnerTimer = new Spinner(5,180,30);
            spinnerTimer.setPrefWidth(120f);
            Button timer_btn = new Button("Set Timer");
            TextField tx_timer_confrm = new TextField("Timer Confirmed to -> ");
            tx_timer_confrm.setVisible(false);

            VBox vBox_2 = new VBox(7);
            HBox hBox_2 = new HBox(10, lb_timer, spinnerTimer,timer_btn);
            hBox_2.setAlignment(Pos.CENTER);
            vBox_2.getChildren().add(hBox_2);
            vBox_2.getChildren().add(tx_timer_confrm);
            vBox_2.setAlignment(Pos.CENTER);



            Label lb_timeLeft = new Label("Exam End Time: ");
            TextField tx_timeLeft = new TextField("");
            if(Config.examFinishTime != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
                tx_timeLeft.setText(Config.examFinishTime.format(formatter));
            }

            Button refresh_btn = new Button("Refresh");
            refresh_btn.setVisible(false);
            //Time left check button event
            refresh_btn.setOnMouseClicked(e3 ->{
                // write here
            });

            HBox hBox_3 = new HBox(10, lb_timeLeft, tx_timeLeft, refresh_btn);
            hBox_3.setAlignment(Pos.CENTER);

            //timer set button event
            timer_btn.setOnMouseClicked(e2 ->{

                if(Config.examFinishTime == null) {
                    int minutes = (int) spinnerTimer.getValue();
                    tx_timer_confrm.setText(tx_timer_confrm.getText() + minutes + " minutes");
                    tx_timer_confrm.setVisible(true);
                    long timestampMillis = System.currentTimeMillis() + (minutes * 1000 * 60);
                    LocalDateTime dateTime =
                            Instant.ofEpochMilli(timestampMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");


                    Config.examFinishTime = dateTime;

                    tx_timeLeft.setText(dateTime.format(formatter));
                }

            });

            Button ans_dwnld_btn = new Button("Student Answer Script Download !!");

            //Student Script Download Button event
            ans_dwnld_btn.setOnMouseClicked(e4 ->{
                try {
                    String response = (String) (client.sendRequest("downloadAnswers", "hello"));
                    System.out.println("answers downloaded successfully");
                }
                catch (Exception e){
                    System.out.println("answers download failed");
                }
                // write here
            });

            Button logOut = new Button("LogOut");
            logOut.setOnMouseClicked(e8 ->{
                teach_stage.close();
                new Front_page();
            });

            VBox root = new VBox(20, text_intro, vBox_1,vBox_2, hBox_3,ans_dwnld_btn,logOut);
            root.setAlignment(Pos.CENTER);

            Scene t_scene = new Scene(root,800,600);
            teach_stage.setScene(t_scene);
            teach_stage.show();
        }

        else{
            Stage stdnt_stage = new Stage();
            stdnt_stage.setTitle("Student Portal");

            Text text_intro = new Text("Welcome to Students Portal");
            text_intro.setFont(Font.font("Tahoma", FontWeight.BOLD, 24));
            text_intro.setFill(Color.GREEN);


            Label lb_que = new Label("Question Download: ");
            Button dwld_btn = new Button("Download");

            HBox hBox_4 = new HBox(10, lb_que, dwld_btn);
            hBox_4.setAlignment(Pos.CENTER);


            //download Button event
            dwld_btn.setOnMouseClicked(e ->{
                // write here
                try {
                    String fileContent = (String) (client.sendRequest("downloadQuestion", "hello"));
                    if(! fileContent.equals("500")) {
                        InputStream stream = new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8));

                        File file = new File("C:\\Users\\" + System.getProperty("user.name") +  "\\Downloads\\question.txt");
                        file.createNewFile();

                        FileUtils.copyInputStreamToFile(stream, file);
                        System.out.println("Question downloaded successfully");
                    }
                    else{
                        System.out.println("Question download failed");                    }
                }
                catch (Exception ex){
                    System.out.println("Question download failed");
                }
            });


            Label lb_timer = new Label("Exam Time(minutes) : ");
            lb_timer.setVisible(false);
            TextField tx_examTime = new TextField("Timer Confirmed to -> ");
            tx_examTime.setVisible(false);


            HBox hBox_5 = new HBox(10, lb_timer, tx_examTime);
            hBox_5.setAlignment(Pos.CENTER);


            Label lb_timeLeft = new Label("Exam End Time: ");
            TextField tx_timeLeft = new TextField("");
            if(Config.examFinishTime != null){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");

                tx_timeLeft = new TextField(Config.examFinishTime.format(formatter));
            }

            Button refresh_btn = new Button("Refresh");
            refresh_btn.setVisible(false);

            //Time left check button event
            refresh_btn.setOnMouseClicked(e3 ->{
                // write here
            });

            HBox hBox_6 = new HBox(10, lb_timeLeft, tx_timeLeft, refresh_btn);
            hBox_6.setAlignment(Pos.CENTER);

            Button ans_upld_btn = new Button("Upload your Answer Script !!");
            TextField tx_ans_upload = new TextField("C:\\Users\\prith\\Desktop\\ans\\answer.txt");

            HBox hBox_7 = new HBox(10,tx_ans_upload, ans_upld_btn);
            hBox_7.setAlignment(Pos.CENTER);

            //Student Script Download Button event
            ans_upld_btn.setOnMouseClicked(e4 ->{
                try{
                    String filePath = tx_ans_upload.getText();
                    File file = new File(filePath);
                    InputStream targetStream = new FileInputStream(file);
                    String result = new BufferedReader(new InputStreamReader(targetStream))
                            .lines().collect(Collectors.joining("\n"));

                    String response = (String) (client.sendRequest("uploadAnswer", "id:" + user.getId() + "," + result));
                    if(response.equals("200")){
                        System.out.println("uploaded successfully");
                    }
                    else{
                        System.out.println("upload failed");
                    }


                }
                catch (Exception ex){
                    System.out.println("upload failed");
                    // ex.printStackTrace();
                }
            });

            Button logOut = new Button("LogOut");
            logOut.setOnMouseClicked(e8 ->{
                stdnt_stage.close();
                new Front_page();
            });

            VBox root = new VBox(20, text_intro, hBox_4,hBox_5,hBox_6,hBox_7,logOut);
            root.setAlignment(Pos.CENTER);

            Scene s_scene = new Scene(root,800,600);
            stdnt_stage.setScene(s_scene);
            stdnt_stage.show();
        }
    }
}

public class Front_page {

    public Front_page(){
        Stage stage = new Stage();
        stage.setTitle("Online Exam System");

        Text text = new Text("Online Exam System");
        text.setFont(Font.font("Tahoma", FontWeight.BOLD, 24));

        //to get Project src absolute path
        String path = "src/_PROJECT/Files";
        File file = new File(path);
        String absolutePath = file.getAbsolutePath();
        absolutePath += "\\UIU_Logo_2.png";

        Image img = null;
        try {
            img = new Image(new FileInputStream(absolutePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView uiu_small_img = new ImageView(img);

        //Setting the position of the image
        uiu_small_img.setX(300);
        uiu_small_img.setY(20);

        //setting the fit height and width of the image view
        uiu_small_img.setFitHeight(180);
        uiu_small_img.setFitWidth(250);


        Button login_btn = new Button("TEACHER/STUDENT PORTAL");

        login_btn.setOnMouseClicked(mEvent ->{
            stage.close();
            new login(new Client());
        });


        VBox vBox = new VBox(50, login_btn);
        vBox.setAlignment(Pos.CENTER);

        VBox vBox2 = new VBox(100);
        vBox2.setAlignment(Pos.CENTER);
        vBox2.getChildren().add(text);
        vBox2.getChildren().add(vBox);

        Button exit_btn = new Button("Exit");
        exit_btn.setOnMouseClicked(e10 ->{
            stage.close();
        });

        VBox root = new VBox(20, uiu_small_img, vBox2,exit_btn);
        root.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(root,600, 500);
        stage.setScene(scene);
        stage.show();
    }

}
