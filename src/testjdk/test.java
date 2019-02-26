package com.microsoft.band.sdk.sampleapp.accelerometer;

import android.content.Context;

import android.os.Environment;

import java.io.BufferedWriter;

import java.io.DataOutputStream;

import java.io.File;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;

import java.io.FileWriter;

import java.io.IOException;

public class txtFileOperation {

    private Context context;

    private String SDPATH;//path of the SD card

    public txtFileOperation(Context context) {

        this.context = context;

        SDPATH = Environment.getExternalStorageDirectory().getPath();

    }

    public File createSDFile(String fileName) throws IOException {

        File file = new File(SDPATH + "//" + fileName);

        if (!file.exists()) {

            file.createNewFile();

        }

        return file;

    }

    public void writeSDFile(String str,String fileName)

    {

        try {

            str=readSDFile(fileName)+str;

            FileWriter fw = new FileWriter(SDPATH + "//" + fileName);

            File f = new File(SDPATH + "//" + fileName);

            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(str);

            FileOutputStream os = new FileOutputStream(f);

            DataOutputStream out = new DataOutputStream(os);

            out.writeShort(2);

            out.writeUTF("");

            System.out.println(out);

            bw.close();

            fw.flush();

            fw.close();

            System.out.println(fw);

        } catch (Exception e) {

        }

    }

    public String readSDFile(String fileName) {

        StringBuffer sb = new StringBuffer();

        File file = new File(SDPATH + "//" + fileName);

        try {

            FileInputStream fis = new FileInputStream(file);

            int c;

            while ((c = fis.read()) != -1) {

                sb.append((char) c);

            }

            fis.close();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return sb.toString();

    }

    public String getSDPATH() {

        return SDPATH;

    }

}