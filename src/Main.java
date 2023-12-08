import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void saveGame(String path, GameProgress gameProgress) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void zipFile(String path, List<String> filesToWrite) {
        FileOutputStream fos = null;
        byte[] buffer = new byte[1024];
        try {
            fos = new FileOutputStream(path);
            ZipOutputStream zos = new ZipOutputStream(fos);
            for (String filePath : filesToWrite) {
                File file = new File(filePath);
                FileInputStream fis = new FileInputStream(file);
                zos.putNextEntry(new ZipEntry(file.getName()));
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }

                zos.closeEntry();
                fis.close();
            }
            zos.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        List<String> filesToWrite = new ArrayList<String>();
        //1
        GameProgress gameProgress1 = new GameProgress(67, 3, 5, 1.67);
        GameProgress gameProgress2 = new GameProgress(100, 1, 1, 0.53);
        GameProgress gameProgress3 = new GameProgress(10, 0, 15, 2);
        //2
        saveGame("/Users/ivangoyda/Documents/Games/savegames/save1.dat", gameProgress1);
        saveGame("/Users/ivangoyda/Documents/Games/savegames/save2.dat", gameProgress2);
        saveGame("/Users/ivangoyda/Documents/Games/savegames/save3.dat", gameProgress3);
        //3
        filesToWrite.add("/Users/ivangoyda/Documents/Games/savegames/save1.dat");
        filesToWrite.add("/Users/ivangoyda/Documents/Games/savegames/save2.dat");
        filesToWrite.add("/Users/ivangoyda/Documents/Games/savegames/save3.dat");
        zipFile("/Users/ivangoyda/Documents/Games/savegames/save.zip", filesToWrite);
    }
}