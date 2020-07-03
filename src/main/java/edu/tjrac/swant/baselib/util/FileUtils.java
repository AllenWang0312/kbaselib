package edu.tjrac.swant.baselib.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

import androidx.core.content.FileProvider;

/**
 * Created by wpc on 2017/4/14.
 * 文件相关util
 */

public class FileUtils {
    //    内置sd卡路径
//    String sdcardPath = System.getenv("EXTERNAL_STORAGE");
//    内置sd卡路径
//    String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
//    var ExtSDCardPath: String? = null
//    get() {
//        if (field == null) {
//            field = System.getenv("SECONDARY_STORAGE")
//        }
//        return field
//    }
    public static String getSDcardPath() {
//        Environment.getDownloadCacheDirectory().getAbsolutePath()
        return Environment.getExternalStorageDirectory().getPath();
    }


    public static void openFile(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        String type = FileUtils.getMIMEType(file);
        Uri url = FileUtils.getUriFromFile(context,file);
        intent.setDataAndType(url, type);
        context.startActivity(intent);
    }

    private static Uri getUriFromFile(Context context, File file) {
        if (Build.VERSION.SDK_INT >  Build.VERSION_CODES.M) {
            String authority = context.getPackageName() + ".provider";
            return FileProvider.getUriForFile(context, authority, file);
        } else {
            return Uri.fromFile(file);
        }
    }
//    public static void openFile(Context context, @NotNull File file) {
//        Intent intent = new Intent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        //设置intent的Action属性
//        intent.setAction(Intent.ACTION_VIEW);
//        //获取文件file的MIME类型
//        String type = getMIMEType(file);
//        //设置intent的data和Type属性。
//        Uri uri = null;
//        if (Build.VERSION.SDK_INT >= 24) {
//            uri = FileProvider.getUriForFile(context.getApplicationContext(), "lms2.xz.act.provider", file);
//        } else {
//            uri = Uri.fromFile(file);
//        }
//        intent.setDataAndType(uri, type);
//        //跳转
//        context.startActivity(intent);
//    }

//    @SuppressLint("NewApi")
//    public static void showChoseFileToPlayDialog(String dirPath,
//                                                 String fileType, final Activity context) {
//        if (new File(dirPath).exists()) {
//            Log.i("目录存在", dirPath);
//            final ArrayList<FileInfo> items = getFileInfoListWithDirPathAndEnd(
//                    dirPath, fileType);
//            if (items.size() == 0) {
//                Toast.makeText(context, "文件夹为空", Toast.LENGTH_SHORT).show();
//            } else {
//                ChoseFileDialog dialog = new ChoseFileDialog(context, items,
//                        new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> arg0,
//                                                    View arg1, int arg2, long arg3) {
//                                // TODO Auto-generated method stub
//                                FileInfo fi = items.get(arg2);
//                                String path = fi.getDirPath() + fi.getName();
//                                openFile(context, new File(path));
//                            }
//                        }, null);
//                dialog.show(context.getFragmentManager(), "chosefiledialog");
//            }
//
//            // if (MainActivity.videodatas == null
//            // || MainActivity.videodatas.size() <= 0) {
//            // Toast.makeText(SoftUpdateActivity.this, "路径下不存在视频",
//            // Toast.LENGTH_SHORT).show();
//            // } else {
//            // MainActivity.videolist_layout
//            // .setVisibility(View.VISIBLE);
//            // }
//        } else {
//            Toast.makeText(context, "目录不存在", Toast.LENGTH_SHORT).show();
//        }
//    }


    static String getSizeString(long size) {
        if (size / 1000 / 1000 / 1000f > 1) {
            return size / 1000 / 1000 / 1000f + "G";
        } else {
            return size / 1000 / 1000f + "M";
        }
    }

//    /**
//     * 获取外置SD卡路径
//     * @return 应该就一条记录或空
//     */
//    public static String getExtSDCardPath() {
//        List<String> lResult = new ArrayList<String>();
//        try {
//            Runtime rt = Runtime.getRuntime();
//            Process proc = rt.exec("mount");
//            InputStream is = proc.getInputStream();
//            InputStreamReader isr = new InputStreamReader(is);
//            BufferedReader br = new BufferedReader(isr);
//            String line;
//            while ((line = br.readLine()) != null) {
//                if (line.contains("sdcard")) {
//                    String[] arr = line.split(" ");
//                    String path = arr[1];
//                    File file = new File(path);
//                    if (file.isDirectory()) {
//                        lResult.add(path);
//                    }
//                }
//            }
//            isr.close();
//        } catch (Exception e) {
//        }
//        if (lResult.size() > 0) {
//            return lResult.get(0);
//        }
//        return null;
//    }

    public static String getExtSDCardAPPDataPath(Context context) {
        File file = context.getCacheDir();

        if (!file.exists()) {
            file.mkdirs();
        }
        String path=file.getAbsolutePath();
        Log.i("cache dir",path);
        return path;
    }


    public static boolean writeByteArrayToFile(byte[] bytes, String dirPath, String filename) {
        createOrExistsDir(dirPath);
        File file = createFileIfNotExist(dirPath, filename);
        return writeByteArrayToFile(bytes, file);
    }

    public static boolean writeByteArrayToFile(byte[] bytes, File file) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                fos.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

//
//    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
//    public static long getTotalSpace(File path) {
//        if (path == null) {
//            return -1;
//        }
//        //如果这个sdk大于9 那就使用系统的api
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
//
//            return path.getTotalSpace();
//        } else//小于9 系统没有这个api 我们就自己算吧。
//        {
//            final StatFs statFs = new StatFs(path.getPath());
//            return statFs.getBlockSize() * statFs.getBlockCount();
//        }
//    }


    public static boolean createOrExistsDir(String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    public static File getFileByPath(String filePath) {
        return filePath == null || filePath.trim().equals("") ? null : new File(filePath);
    }

    public static boolean createOrExistsDir(File file) {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static File createFileIfNotExist(String dirPath, String filename) {
        File file = new File(dirPath, filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static void deleteFiles(String path, ArrayList<Integer> select) {
        File dir = new File(path);
        String[] files = dir.list();
        for (Integer index : select) {
            deleteFile(new File(path, files[index]));

        }
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {//判断文件是否存在
            if (file.isFile()) {//判断是否是文件
                file.delete();//删除文件
            } else if (file.isDirectory()) {//否则如果它是一个目录
                File[] files = file.listFiles();//声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) {//遍历目录下所有的文件
                    deleteFile(files[i]);//把每个文件用这个方法进行迭代
                }
                file.delete();//删除文件夹
            }
        } else {
        }
    }

    public static void deleteFile(File file) {
        if (file.exists()) {//判断文件是否存在
            if (file.isFile()) {//判断是否是文件
                file.delete();//删除文件
            } else if (file.isDirectory()) {//否则如果它是一个目录
                File[] files = file.listFiles();//声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) {//遍历目录下所有的文件
                    deleteFile(files[i]);//把每个文件用这个方法进行迭代
                }
                file.delete();//删除文件夹
            }
        } else {
        }
    }

    public static void deleteFiles(File root) {
        File files[] = root.listFiles();
        if (files != null) {
            for (File f : files) {
                if (!f.isDirectory() && f.exists()) { // 判断是否存在
                    try {
                        f.delete();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static String getAbsPath(ArrayList<String> rote) {
        StringBuffer path = new StringBuffer();
        for (String str : rote) {
            path.append("/" + str);
        }
        return path.toString();
    }


    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param file
     */
    public static String getMIMEType(File file) {

        String type = "*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /* 获取文件的后缀名*/
        String end = extensionName(fName);
        if (end == "") return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }

        return type;
    }


    //MIME_MapTable是所有文件的后缀名所对应的MIME类型的一个String数组：
    private static final String[][] MIME_MapTable = {
            //{后缀名，MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "head_chose_file/plain"},
            {".class", "application/octet-stream"},
            {".conf", "head_chose_file/plain"},
            {".cpp", "head_chose_file/plain"},
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "head_chose_file/plain"},
            {".htm", "head_chose_file/html"},
            {".html", "head_chose_file/html"},
            {".jar", "application/java-archive"},
            {".java", "head_chose_file/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "head_chose_file/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".md", "application/markdown"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "head_chose_file/plain"},
            {".rc", "head_chose_file/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "head_chose_file/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "head_chose_file/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            {".xml", "head_chose_file/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"},
            {"", "*/*"}
    };

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            File newfile = new File(newPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                if (!newfile.exists()) {
                    newfile.createNewFile();
                }
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath) {

        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();

        }

    }

    public static void moveFile(String filePath, String newDirPath) {
        if (filePath == null || filePath.length() == 0
                || newDirPath == null || newDirPath.length() == 0) {
            return;
        }
        try {
            //拷贝文件

            String name = filePath.substring(filePath.lastIndexOf("/"));
            copyFile(filePath, newDirPath + name);
            //删除原文件
            removeFile(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeFile(String filePath) {
        if (filePath == null || filePath.length() == 0) {
            return;
        }
        try {
            File file = new File(filePath);
            if (file.exists()) {
                removeFile(file);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void removeFile(File file) {
        //如果是文件直接删除
        if (file.isFile()) {
            file.delete();
            return;
        }
        //如果是目录，递归判断，如果是空目录，直接删除，如果是文件，遍历删除
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                removeFile(f);
            }
            file.delete();
        }
    }

    public static boolean endWith(String end, String[] types) {
        for (String item : types) {
//            L.i("endWith",item+"_"+end);
            if (item.equals(end)) {
                return true;
            }
        }
        return false;
    }

    public static boolean endWith(Context context, String end, int id) {
        String[] types = context.getResources().getStringArray(id);
        for (String item : types) {
//            L.i("endWith",item+"_"+end);
            if (item.equals(end)) {
                return true;
            }
        }
        return false;
    }


    public static String getName(String url) {
        return url.substring(url.lastIndexOf("/"));
    }

    public static String extensionName(String file) {
        int dotIndex = file.lastIndexOf(".");
        if (dotIndex < 0) {
            return "";
        }
        return file.substring(dotIndex).toLowerCase();
    }


    public static final int SIZETYPE_B = 1;//获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;//获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;//获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;//获取文件大小单位为GB的double值

    public static float getAvailInfo(int sizeType) {
        File sdcard_filedir = Environment.getExternalStorageDirectory();//得到sdcard的目录作为一个文件对象
        long usableSpace = sdcard_filedir.getUsableSpace();//获取文件目录对象剩余空间
//        long totalSpace = sdcard_filedir.getTotalSpace();

//        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
//        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
//        am.getMemoryInfo(outInfo);
        if (sizeType == 1) {
            return usableSpace;
        } else if (sizeType == 2) {
            return usableSpace / 1024;
        } else if (sizeType == 3) {
            return usableSpace / 1024f / 1024f;
        } else {
            return usableSpace / 1024f / 1024f / 1024f;
        }
    }

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            LogUtil.E(TAG,"获取文件大小失败!");
        }
        return FormetFileSize(blockSize, sizeType);
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        return getAutoFileOrFilesSize(new File(filePath));
    }

    public static String getAutoFileOrFilesSize(File file) {
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            LogUtil.E(TAG,"获取文件大小失败!");
        }
        return FormetFileSize(blockSize);
    }

    /**
     * 获取指定文件大小
     *
     * @param file
     * @return
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
//            LogUtil.E(TAG,"获取文件大小不存在!");
        }
        return size;
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    private static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    public static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    /**
     * 将文本文件中的内容读入到buffer中
     *
     * @param buffer   buffer
     * @param filePath 文件路径
     * @throws IOException 异常
     * @author cn.outofmemory
     * @date 2013-1-7
     */
    public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        FileInputStream is = new FileInputStream(filePath);
        readToBuffer(buffer, is);
    }

    public static void readToBuffer(StringBuffer buffer, File file) throws IOException {
        FileInputStream is = new FileInputStream(file);
        readToBuffer(buffer, is);
    }

    public static void readToBuffer(StringBuffer buffer, FileInputStream is) throws IOException {
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
    }

    /**
     * 读取文本文件内容
     *
     * @param filePath 文件所在路径
     * @return 文本内容
     * @throws IOException 异常
     * @author cn.outofmemory
     * @date 2013-1-7
     */
    public static String readFile(String filePath) throws IOException {
        StringBuffer sb = new StringBuffer();
        FileUtils.readToBuffer(sb, filePath);
        return sb.toString();
    }

    public static String readFile(File file) throws IOException {
        StringBuffer sb = new StringBuffer();
        FileUtils.readToBuffer(sb, file);
        return sb.toString();
    }

    public static String getFromAssets(Context context, String fileName) {
        String line = "";
        String Result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);

            while ((line = bufReader.readLine()) != null)
                Result += line;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result;
    }

    public static String getJson(Context mContext, String fileName) {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder();
        AssetManager am = mContext.getAssets();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    am.open(fileName)));
            String next = "";
            while (null != (next = br.readLine())) {
                sb.append(next);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            sb.delete(0, sb.length());
        }
        return sb.toString().trim();
    }
}
