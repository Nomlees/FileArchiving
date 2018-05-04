package ru.zak.txt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Класс для архивирования текстовых файлов
 *
 * Заставская А.К 15ИТ18
 */
public class Demo {
    public static void main(String[] args) {
        console();
    }

    /**
     * Метод организует ввод пути через консоль
     * @return - возвращает введённый пользователем путь
     */
    private static String path(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите путь к файлу:");
        String filename = scanner.nextLine();
        filename = check(filename);

        return filename;
    }

    /**
     * Метод проверяет введенные пользователем данные на существование, если файл не существует
     * вызывается метод path().
     * @return - возвращает проверенный путь к файлу
     */
    private static String check(String filename){
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Проверьте путь к файлу и введите заново: ");
            filename = path();
        }

        return filename;

    }


    /**
     * Метод zip-архивации, создаёт архив и записывает в него файл.
     * @param files - введённый пользователем путь, разделённый на путь и расшерение файла
     * @param filename - введённый пользователем путь
     * @return - возвращает файл, уже добавленный в архив.
     */
    private static ZipEntry archiving(String[] files, String filename){
        //Создание архива
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(files[0]+".zip"));
             //Считывание из файла
             FileInputStream fis = new FileInputStream(filename)) {

            //Отдельная запись в архиве
            ZipEntry entry1 = new ZipEntry("notes."+files[1]);

            zout.putNextEntry(entry1);
            // считываем содержимое файла в массив byte
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            // добавляем содержимое к архиву
            zout.write(buffer);
            // закрываем текущую запись для новой записи
            zout.closeEntry();
            return entry1;

        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
        return null;
    }

    /**
     * Метод вывода информации об исходном размере файла, о размере после сжатия и пути сохранения
     *
     */
    private static void console(){
        String filename = path();
        System.out.println("Архивирование...");
        String[] files = filename.split("\\.");
        ZipEntry entry1 = archiving(files,filename);
        assert entry1 != null;
        long sizeArchiv =  entry1.getCompressedSize();//размер занимаемый файлом
        long sizeFile = entry1.getSize();//Размер файла в несжатом виде;
        System.out.println("Успешно!");
        System.out.println("Размер исходного файла " + sizeFile+" байт");
        System.out.println("Размер после сжатия " +  sizeArchiv+" байт");
        System.out.println("Путь к архиву " +files[0]+".zip" );
    }

}
