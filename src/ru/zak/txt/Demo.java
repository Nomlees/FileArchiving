package ru.zak.txt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Demo {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите путь к файлу:");
        String filename = scanner.nextLine();
        String[] file = filename.split("\\.");
       // String files = file[0];
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(file[0]+".zip"));
             FileInputStream fis = new FileInputStream(filename)) {
            ZipEntry entry1 = new ZipEntry("notes.txt");
            zout.putNextEntry(entry1);
            // считываем содержимое файла в массив byte
            byte[] buffer = new byte[fis.available()];

            // добавляем содержимое к архиву
            zout.write(buffer);
            // закрываем текущую запись для новой записи

            zout.closeEntry();
            fis.read(buffer);

            long size =  entry1.getCompressedSize();
            long nsize = entry1.getSize();
            System.out.println("Размер исходного файла " + nsize);
            System.out.println("Размер после сжатия " +  size);
            System.out.println("Путь к архиву " +file[0]+".zip" );

        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }

    }
}
