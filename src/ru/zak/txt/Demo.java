package ru.zak.txt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Demo {
    public static void main(String[] args) {
        String filename = path();
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Проверьте путь к файлу и введите заново: ");
            filename = path();
        }

        String[] files = filename.split("\\.");

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(files[0]+".zip"));
             FileInputStream fis = new FileInputStream(filename)) {
            ZipEntry entry1 = new ZipEntry("notes."+files[1]);
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
            System.out.println("Путь к архиву " +files[0]+".zip" );

        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }

    }
    private static String path(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите путь к файлу:");
        String filename = scanner.nextLine();

            return filename;
    }
}
