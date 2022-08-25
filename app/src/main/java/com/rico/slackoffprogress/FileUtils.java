package com.rico.slackoffprogress;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {


    public FileUtils() {

    }


    /**
     * 创建文件
     */
    public static boolean createFile(String path) {
        try {
            File dir = new File(path);
            // if (dir.exists() == true)
            // dir.delete();

            dir.getParentFile().mkdirs();
            return true;

        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 删除文件夹下的所有文件
     *
     * @param path 文件夹路径
     */
    public static void deleteDirectory(String path) {
        File root = new File(path);
        File[] currentFiles = root.listFiles();
        if (currentFiles != null) {
            for (int n = 0; n < currentFiles.length; n++) {
                File file = currentFiles[n];
                if (file.exists())
                    file.delete();
            }
        }
    }

    /**
     * 递归删除文件和文件夹
     *
     * @param path 要删除的根目录
     */
    public static void recursionDeleteFile(String path) {
        File file = new File(path);
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                recursionDeleteFile(f.getPath());
            }
            file.delete();
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists())
            file.delete();
    }

    /**
     * 删除文件
     *
     * @param file 文件Flie
     */
    public static void deleteFile(File file) {
        if (file.exists())
            file.delete();
    }

    /**
     * @param file
     * @return
     */
    public static byte[] fileToBytes(File file) {
        if (file == null) {
            return null;
        }
        try {

            FileInputStream inStream = new FileInputStream(file);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;

            while ((len = inStream.read(buffer)) != -1) {
                // 写入数据
                outStream.write(buffer, 0, len);
            }
            // 得到文件的二进制数据

            inStream.close();
            outStream.close();
            return outStream.toByteArray();
        } catch (IOException e) {
            System.err.println(e);
        }
        return null;
    }

    /**
     * @param path
     * @return
     */
    static public byte[] fileToBytes(String path) {
        if (path == null || path.isEmpty()) {
            return null;
        }
        File file = new File(path);
        try {

            FileInputStream inStream = new FileInputStream(file);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;

            while ((len = inStream.read(buffer)) != -1) {
                // 写入数据
                outStream.write(buffer, 0, len);
            }
            // 得到文件的二进制数据

            inStream.close();
            outStream.close();
            return outStream.toByteArray();
        } catch (IOException e) {
            System.err.println(e);
        }
        return null;
    }

    /**
     * 复制文件
     *
     * @param srcPath
     * @param descPath
     */
    public static boolean copyFile(String srcPath, String descPath) {
        boolean isSuccess = true;

        InputStream inStream = null;
        FileOutputStream fs = null;
        File srcFile = new File(srcPath);
        if (srcFile.exists()) {
            @SuppressWarnings("unused")
            int bytesum = 0;
            int byteread = 0;

            try {
                // 文件存在时
                inStream = new FileInputStream(srcFile); // 读入原文件
                fs = new FileOutputStream(descPath);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();

                isSuccess = true;
            } catch (Exception e) {
                e.printStackTrace();

                isSuccess = false;
            } finally {
                try {
                    if (fs != null)
                        fs.close();
                    if (inStream != null)
                        inStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }

        return isSuccess;
    }


    /**
     * 把文件数据写入文件
     *
     * @param file   要写入文件
     * @param data   文件数据
     * @param offset 写入位置
     * @param count  写入长度
     */
    public static boolean writeFile(File file, byte[] data, int offset, int count) {
        boolean rtnSuccuss = true;

        FileOutputStream fos = null;
        try {
            if (file.exists() == false) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            fos = new FileOutputStream(file, true);
            fos.write(data, offset, count);
            fos.flush();

            rtnSuccuss = true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            rtnSuccuss = false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return rtnSuccuss;
    }

    /**
     * 获取文件的字节数组
     *
     * @param filePath 文件目录
     * @param fileName 文件名
     * @throws IOException
     */
    public static byte[] readFile(String filePath, String fileName) throws IOException {
        File file = new File(filePath, fileName);
        return readFile(file.getPath());
    }

    /**
     * 获取文件的字节数组
     *
     * @param filePath 文件路径
     * @throws IOException
     */
    public static byte[] readFile(String filePath) {
        File file = new File(filePath);
        return readFile(file);
    }

    /**
     * 获取文件的字节数组
     *
     * @param file 文件路径
     * @throws IOException
     */
    public static byte[] readFile(File file)  {

        byte[] b=new byte[0];
        try
        {
            file.createNewFile();
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            int leng = bis.available();
            b = new byte[leng];
            bis.read(b, 0, leng);
            bis.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return b;

    }


    public static boolean recreateFile(String path) {
        File file = new File(path);
        boolean b = false;
        if (file.exists()) {
            file.delete();
        }
        try {
            b = file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 获取某个文件夹下所有文件
     *
     * @param path
     * @return
     */
    public static List<File> getFilse(String path, String[] Prefix) {

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        File[] array = file.listFiles();
        List<File> listFiles = new ArrayList<File>();
        for (int i = 0; i < array.length; i++) {
            String fileName = array[i].getName();
            String prefix = fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
            for (int n = 0; n < Prefix.length; n++) {
                if (prefix.equals(Prefix[n].toUpperCase())) {
                    listFiles.add(array[i]);
                }
            }

        }
        return listFiles;
    }

    /**
     * 把对象写入文件
     *
     * @param obj  对象
     * @param path
     */
    public static void writeObject2File(Serializable obj, String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.flush();
            fos.close();
            oos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Object readFile2Object(String path) {
        Object obj = null;
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            obj = ois.readObject();
            fis.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return obj;
    }

    /**
     * 把文件数据写入文件
     *
     * @param file 要写入文件
     * @param data 文件数据
     */
    public static boolean writeFile(File file, byte[] data) {
        if (file.getParentFile().exists() == false)
            file.getParentFile().mkdirs();
        if (file.isFile() == false) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        RandomAccessFile randomFile = null;

        try {
            randomFile = new RandomAccessFile(file.getAbsolutePath(), "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        try {
            // 文件长度，字节数
            long offset = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(offset);
            randomFile.write(data);
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取文本文件编码
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static String getCharset(File file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bin = new BufferedInputStream(fis);
        int p = (bin.read() << 8) + bin.read();
        String code;

        switch (p) {
            case 0xEFBB:
                code = "UTF-8";
                break;
            case 0xFFFE:
                code = "Unicode";
                break;
            case 0xFEFF:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }
        fis.close();
        bin.close();
        return code;
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath
     * @return
     */
    public static boolean existsFilePath(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 创建文件
     *
     * @param filePath
     * @return
     */
    public static boolean createFilePath(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return false;
            }
            return true;
        }
        return true;
    }

    /**
     * 创建目录
     *
     * @param DirectoryPath
     * @return
     */
    public static boolean createDirectoryPath(String DirectoryPath) {
        File folder = new File(DirectoryPath);
        if (!folder.exists() && !folder.isDirectory()) {
            return folder.mkdirs();
        }
        return true;
    }
}
