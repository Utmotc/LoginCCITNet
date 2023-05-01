package xyz.utmotc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Login {

    public static boolean Go(String name, String pwd, String tag) throws IOException {
        String url = "http://1.1.1.4/ac_portal/login.php";
        // 设置请求体数据
        String requestBody = "opr=pwdLogin&userName="+name+"&pwd="+pwd+"&auth_tag="+tag+"&rememberPwd=0";
//        requestBody = "opr=pwdLogin&userName=17743080264&pwd=3f8ceeaba955&auth_tag=1682426867448&rememberPwd=0";

        byte[] requestBodyBytes = requestBody.getBytes(StandardCharsets.UTF_8);
        // 创建连接
        URL apiUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection)apiUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);
        conn.setFixedLengthStreamingMode(requestBodyBytes.length);
        // 发送请求体数据
        try(OutputStream out = conn.getOutputStream()) {
            out.write(requestBodyBytes);
        }
        // 读取响应数据
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
            BufferedReader in = new BufferedReader(inputStreamReader);

            StringBuilder response = new StringBuilder();
            String line;
            while((line = in.readLine()) != null) {
                response.append(line);
            }
            String responseString = response.toString();
            if (responseString.contains("true")){
//                System.out.println("登录成功");
//                System.out.println(responseString);
                return true;
            }else {
//                System.out.println("登录失败");
//                System.out.println(responseString);
                return false;
            }
        }catch (IOException e){
            System.out.println("获取响应数据失败");
//            System.out.println(e);
            return false;
        }

    }

}
