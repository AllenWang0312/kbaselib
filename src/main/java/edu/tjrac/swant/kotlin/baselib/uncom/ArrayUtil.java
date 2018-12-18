package edu.tjrac.swant.kotlin.baselib.uncom;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by wpc on 2016/11/8.
 */

public class ArrayUtil<T extends Object> {

    public ArrayList<String> Add(ArrayList<String> main, ArrayList<String> add) {
        main.addAll(add);
        return main;
    }

    public static void addStringsToStringArray(ArrayList<String> strings, String[] str) {
         for (int i = 0; i < str.length; i++) {
                strings.add(str[i]);
            }
    }
    public static void addStringsToStringArrayIfNotExit(ArrayList<String> strings, String[] str) {
        for (int i = 0; i < str.length; i++) {
            if(!strings.contains(str[i])){
                strings.add(str[i]);
            }
        }
    }

    public static byte[] copyBytes(byte[] from, int start_index, int length) {
        byte[] bytes = new byte[length];
        System.arraycopy(from, start_index, bytes, 0, length);
        return bytes;
    }

    public static String[] add(String[] str1, String[] str2) {
        String[] result = new String[str1.length + str2.length];
        for (int i = 0; i < result.length; i++) {
            if (i < str1.length) {
                result[i] = str1[i];
            } else {
                result[i] = str2[i - str1.length];
            }
        }
        return result;
    }

    public  List<T> asArray(Set<T> bounded_devices) {
        ArrayList<T> devices=new ArrayList<>();
        for(T device:bounded_devices){
            devices.add(device);
        }
        return devices;
    }
}
