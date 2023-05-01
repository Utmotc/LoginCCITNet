package xyz.utmotc.util;

import java.util.ArrayList;
import java.util.List;

public class RC4 {

    public  static  String  do_encrypt_rc4(String  src,  String  passwd)  {
        int  i  =  0,  j  =  0,  a  =  0,  b  =  0,  c  =  0;
        List<Integer> key  =  new ArrayList<>();
        List<Integer>  sbox  =  new  ArrayList<>();
        int  plen  =  passwd.length();
        int  size  =  src.length();
        String  output  =  "";

        //  初始化密钥key和状态向量sbox
        for  (i  =  0;  i  <  256;  i++)  {
            key.add((int)passwd.charAt(i  %  plen));
            sbox.add(i);
        }
        //  状态向量打乱
        for  (i  =  0;  i  <  256;  i++)  {
            j  =  (j  +  sbox.get(i)  +  key.get(i))  %  256;
            int  temp  =  sbox.get(i);
            sbox.set(i,  sbox.get(j));
            sbox.set(j,  temp);
        }
        //  秘钥流的生成与加密
        for  (i  =  0;  i  <  size;  i++)  {
            //  子密钥生成
            a  =  (a  +  1)  %  256;
            b  =  (b  +  sbox.get(a))  %  256;
            int  temp  =  sbox.get(a);
            sbox.set(a,  sbox.get(b));
            sbox.set(b,  temp);
            c  =  (sbox.get(a)  +  sbox.get(b))  %  256;
            //  明文字节由子密钥异或加密
            temp  =  (int)src.charAt(i)  ^  sbox.get(c);
            //  密文字节转换成hex，格式对齐修正（取最后两位，若为一位（[0x0，0xF]），则改成[00,  0F]）
            String  hex  =  String.format("%02x",  temp);
            //  输出
            output  +=  hex;
        }
        return  output;
    }




}
