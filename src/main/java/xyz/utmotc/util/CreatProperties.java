package xyz.utmotc.util;

import java.io.*;
import java.util.Properties;

public class CreatProperties {

    static File directory;
    static File proFile;
    static Properties props;
    static FileWriter fileWriter;

    public static Properties getPro(){
        return props;
    }

    public static String getMyUrt(){
        directory = new File(""); //实例化一个File对象。参数不同时，获取的最终结果也不同
//        String s = directory.getCanonicalPath();//获取标准路径。该方法需要放置在try/catch块中，或声明抛出异常
        String dirUrl = directory.getAbsolutePath();//获取绝对路径。
        System.out.println(dirUrl);
        return dirUrl;
    }

    public static boolean isExist(String name){
        proFile = new File(getMyUrt()+"/"+name);
        if (!proFile.exists()) {
            System.out.println("没有配置文件");
            return false;
        }else {
            System.out.println("有配置文件");
            return true;
        }

    }



    public static void creatPro(){
        if (!isExist("info.properties")) {
            //如果不存在配置文件
            try {
                proFile.createNewFile();
                writeData();
            } catch (IOException e) {
                System.out.println("配置文件创建失败");
                throw new RuntimeException(e);
            }
        }
    }


    public static void writeData(){
        //写入默认数据
        // Properties格式文件的写入
        try {
            props = new Properties();
            props.put("username", "11111111111");
            props.put("password", "111111");
            props.put("autoLoginState", "0");

            // 使用“输出流”，将Properties集合中的KV键值对，写入*.properties文件
            try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(getMyUrt()+"/info.properties"))){
                props.store(bos, "Author:Utmotc");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateData(String filename,String key, String value){
        Properties pro = new Properties();
        String enConding = "UTF-8";
        try{
            //当前工作目录
            String filePath = getMyUrt()+"/"+filename;
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), enConding));
            pro.load(br);
            OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(filePath), enConding);
            pro.setProperty(key, value);
            // 以适合使用 load 方法加载到 Properties 表中的格式，
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流
            pro.store(oStreamWriter, "Update '" + key + "' value");
            br.close();
            oStreamWriter.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



}
