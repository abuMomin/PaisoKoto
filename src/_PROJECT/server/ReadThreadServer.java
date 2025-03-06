package _PROJECT.server;

import _PROJECT.Config;
import _PROJECT.client.user.AllUsers;
import _PROJECT.client.user.users;
import _PROJECT.util.NetworkUtil;
import org.apache.commons.io.FileUtils;



import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ReadThreadServer implements Runnable{
    Thread t;
    HashMap<String, NetworkUtil> clientMap;
    NetworkUtil networkUtil;
    ReadThreadServer(HashMap<String,NetworkUtil> clientMap,NetworkUtil networkUtil){
        this.clientMap = clientMap;
        this.networkUtil = networkUtil;
        this.t = new Thread(this);
        this.t.start();
    }

    public static void pack(String sourceDirPath, String zipFilePath) throws IOException {
        Path p;
        try {
            p = Files.createFile(Paths.get(zipFilePath));
        }
        catch (Exception e){
            Files.delete(Paths.get(zipFilePath));
             p = Files.createFile(Paths.get(zipFilePath));
        }
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
            Path pp = Paths.get(sourceDirPath);
            Files.walk(pp)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
                        try {
                            zs.putNextEntry(zipEntry);
                            Files.copy(path, zs);
                            zs.closeEntry();
                        } catch (IOException e) {
                            System.err.println(e);
                        }
                    });
        }
    }

    @Override
    public void run() {
        while(true){
            String str = null;
            try {
                str = (String) networkUtil.read();
            }
            catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println(str); // student,uiu,registration done
            //split with '|'
            String[] fields = str.split("\\|");
            //resource -> operation_name
            String resource = fields[0];
            String data = fields[1];

            if (networkUtil != null) {
                try {
                    switch (resource){
                        case "checkPass":
                            String[] strArr = data.split(",");
                            //split user:win
                            String email = strArr[0].split(":")[1];
                            String pass = strArr[1].split(":")[1];

                            List<users> allUsers = new AllUsers().getAllUsers();
                            users loginUser = null;
                            for(users user: allUsers){
                                if(user.getName().equals(email) && user.getPass().equals(pass)){
                                    loginUser = user;
                                    break;
                                }
                            }

                            if(loginUser != null){
                                networkUtil.write(loginUser);

                            }else{
                                networkUtil.write("403");

                            }
                            break;
                        case "uploadQuestion":
                            try {
                                String fileContent = data;
                                System.out.println();
                                InputStream stream = new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8));
                                File file = new File(Config.questionDir + File.separator + "question.txt");
                                file.createNewFile();
                                // common - io  is imoported as library for Fileutils
                                FileUtils.copyInputStreamToFile(stream, file);


                                networkUtil.write("200");
                            }
                            catch (Exception e){
                                networkUtil.write("500");
                            }
                            break;
                           case "uploadAnswer":
                            try {
                                strArr = data.split(",");
                                String id = strArr[0].split(":")[1];
                                String fileContent = strArr[1].split(":")[0];

                                InputStream stream = new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8));
                                File file = new File(Config.ansDir + File.separator + "answer" + id + ".txt");
                                file.createNewFile();

                                FileUtils.copyInputStreamToFile(stream, file);

                                networkUtil.write("200");
                            }
                            catch (Exception e){
                                networkUtil.write("500");
                            }
                            break;

                        case "downloadQuestion":
                            try {

                                File file = new File(Config.questionDir + File.separator + "question.txt");
                                InputStream targetStream = new FileInputStream(file);
                                String result = new BufferedReader(new InputStreamReader(targetStream))
                                        .lines().collect(Collectors.joining("\n"));

                                networkUtil.write(result);
                            }
                            catch (Exception e){
                                networkUtil.write("500");
                            }
                            break;
                        case "downloadAnswers":
                            pack(Config.ansDir,
                                    Config.ansDir + ".zip" );
                            networkUtil.write("200");
                            break;
                        default:
                            networkUtil.write("404");

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
