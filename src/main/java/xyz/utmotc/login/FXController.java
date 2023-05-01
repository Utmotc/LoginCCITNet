package xyz.utmotc.login;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import xyz.utmotc.util.CreatProperties;
import xyz.utmotc.util.DragUtil;
import xyz.utmotc.util.Login;
import xyz.utmotc.util.RC4;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;


public class FXController implements Initializable {

    @FXML
    private CheckBox autoLoginCheck;

    @FXML
    private Button closeButton;

    @FXML
    private Label loginLabel;

    @FXML
    private TextField passworldText;

    @FXML
    private Label tittleLabel;

    @FXML
    private TextField usernameText;

    @FXML
    private Button loginButton;

    @FXML
    private Button onKeyLoginButton;

    @FXML
    private Button saveButton;

    @FXML
    private Label topLabel;

    @FXML
    private Button readme;
    Properties pro;


    @FXML
    void closeButtonEntered(MouseEvent event) {
        closeButton.setStyle("-fx-text-fill: #ab67ff;-fx-background-color: transparent;");
    }

    @FXML
    void closeButtonOnClick(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void closeButtonExit(MouseEvent event) {
        closeButton.setStyle("-fx-text-fill: #68acff;-fx-background-color: transparent;");
    }

    @FXML
    void loginButtonEntered(MouseEvent event) {
        loginButton.setStyle("-fx-text-fill: #ab67ff;-fx-background-color: transparent;");
    }

    @FXML
    void loginButtonOnClick(ActionEvent event) {
        loging();
        init();
    }

    @FXML
    void loginButtonExit(MouseEvent event) {
        loginButton.setStyle("-fx-text-fill: #68acff;-fx-background-color: transparent;");
    }

    @FXML
    void onKeyLoginButtonEntered(MouseEvent event) {
        onKeyLoginButton.setStyle("-fx-text-fill: #ab67ff;-fx-background-color: transparent;");
    }

    @FXML
    void onKeyLoginButtonOnClick(ActionEvent event) {
        loging();
        init(pro.getProperty("username"),pro.getProperty("password"));
    }

    @FXML
    void onKeyLoginButtonExit(MouseEvent event) {
        onKeyLoginButton.setStyle("-fx-text-fill: #68acff;-fx-background-color: transparent;");
    }

    @FXML
    void saveButtonEntered(MouseEvent event) {
        saveButton.setStyle("-fx-text-fill: #ab67ff;-fx-background-color: transparent;");
    }

    @FXML
    void saveButtonExit(MouseEvent event) {
        saveButton.setStyle("-fx-text-fill: #68acff;-fx-background-color: transparent;");
    }

    @FXML
    void saveButtonOnClick(ActionEvent event) {
        CreatProperties.updateData("info.properties","username",usernameText.getText());
        CreatProperties.updateData("info.properties","password",passworldText.getText());
        loginLabel.setText("账号密码已保存");
        loginLabel.setStyle("-fx-text-fill: #5cc465");
        pro = getPro();
    }

    @FXML
    void autoLoginOnClick(ActionEvent event) {
        loginLabel.setStyle("-fx-text-fill: #5cc465");
        if (autoLoginCheck.isSelected()){
//            System.out.println("打开");
            loginLabel.setText("自动登录-打开");
            CreatProperties.updateData("info.properties","autoLoginState","1");
//            pro.setProperty("autoLoginState","1");
        }else {
//            System.out.println("关闭");
            loginLabel.setText("自动登录-关闭");
            CreatProperties.updateData("info.properties","autoLoginState","0");
//            pro.setProperty("autoLoginState","0");
        }
    }

    @FXML
    void readmeOnClick(ActionEvent event) {
        loginLabel.setStyle("-fx-text-fill: #5cc465");
        loginLabel.setText("自己琢磨吧，不写了");
    }

    public void init(){

        boolean loginState = loginCIT();
        if (loginState){
            loginSuccess();
        }else {
            loginError();
        }

    }

    public void init(String username,String passworld){

        boolean loginState = loginCIT(username,passworld);
        if (loginState){
            loginSuccess();
        }else {
            loginError();
        }

    }

    public boolean loginCIT(){

        String username = usernameText.getText();
        String passworld = passworldText.getText();
        if ("".equals(username) || "".equals(passworld)){
            return false;
        }

        long tag = new Date().getTime();
//        System.out.println("时间戳："+tag);
        String pw = RC4.do_encrypt_rc4(passworld, String.valueOf(tag));
//        System.out.println("密文："+pw);
        try {
            boolean goState = Login.Go(username, pw, String.valueOf(tag));
            if (goState)
                return true;
        } catch (IOException e) {
//            loginError();
//            throw new RuntimeException(e);
            System.out.println("连接错误");
//            System.out.println(e);
        }
        return false;
    }

    public boolean loginCIT(String username,String passworld){

        usernameText.setText(username);
        passworldText.setText(passworld);

        if ("".equals(username) || "".equals(passworld)){
            return false;
        }

        long tag = new Date().getTime();
//        System.out.println("时间戳："+tag);
        String pw = RC4.do_encrypt_rc4(passworld, String.valueOf(tag));
//        System.out.println("密文："+pw);

        try {
            boolean goState = Login.Go(username, pw, String.valueOf(tag));
            if (goState)
                return true;
        } catch (IOException e) {
            loginError();
//            throw new RuntimeException(e);
            System.out.println("连接错误");
//            System.out.println(e);
        }
        return false;
    }

    public void loginSuccess(){
        loginLabel.setText("登录成功!");
        loginLabel.setStyle("-fx-text-fill: #5cc465");
    }
    public void loginError(){
        loginLabel.setText("登录失败!");
        loginLabel.setStyle("-fx-text-fill: #ec3030");
    }
    public void loging(){
        loginLabel.setText("登录中...");
        loginLabel.setStyle("-fx-text-fill: #bbb848");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXApplication.topLabel = topLabel;
        CreatProperties.creatPro();
        pro = getPro();
//        System.out.println(pro.getProperty("username"));
//        pro = CreatProperties.getPro();
        autoLoginCheck.setSelected(false);
        if ("1".equals(pro.getProperty("autoLoginState")) && CreatProperties.isExist("info.properties")){
            loging();
            //1代表自动登录
            init(pro.getProperty("username"),pro.getProperty("password"));
            autoLoginCheck.setSelected(true);
        }
    }

    public Properties getPro(){
        Properties properties = new Properties();
        //使用ClassLoader加载properties配置文件生成对应的输入流
//        InputStream in1 = FXController.class.getClassLoader().getResourceAsStream("conf/info.properties");
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(CreatProperties.getMyUrt()+"/"+"info.properties"));
//            System.out.println(CreatProperties.getMyUrt()+"/"+"info.properties");
            //使用properties对象加载输入流
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }


}
