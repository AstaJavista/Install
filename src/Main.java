import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();

        File dirSrc = dirCreate("D://Games/src", sb);
        File dirRes = dirCreate("D://Games/res", sb);
        File dirSave = dirCreate("D://Games/savegames", sb);
        File dirTemp = dirCreate("D://Games/temp", sb);
        File dirMain = dirCreate("D://Games/src/main", sb);
        File dirTest = dirCreate("D://Games/src/test", sb);

        File fileMain = fileCreate(dirMain, "Main.java", sb);
        File fileUtils = fileCreate(dirMain, "Utils.java", sb);
        File dirDrawables = dirCreate("D://Games/res/drawables", sb);
        File dirVectors = dirCreate("D://Games/res/vectors", sb);
        File dirIcons = dirCreate("D://Games/res/icons", sb);
        File fileTemp = fileCreate(dirTemp, "temp.txt", sb);

        try (FileWriter writer = new FileWriter(fileTemp, true)) {
            writer.append(sb);
            System.out.println("Файл записан");
        } catch (IOException e) {
            System.out.println("Файл не записан");
        }

        GameProgress save1 = new GameProgress(100, 3, 2, 153.2);
        GameProgress save2 = new GameProgress(80, 8, 13, 864.7);
        GameProgress save3 = new GameProgress(93, 18, 21, 1653.6);

        saveGame("D://Games/savegames/save1.dat", save1);
        saveGame("D://Games/savegames/save2.dat", save2);
        saveGame("D://Games/savegames/save3.dat", save3);

        List<String> saves = new ArrayList<>();
        saves.add("D://Games/savegames/save1.dat");
        saves.add("D://Games/savegames/save2.dat");
        saves.add("D://Games/savegames/save3.dat");

        zipFiles("D://Games/savegames/zip.zip", saves);
    }

    public static File dirCreate(String path, StringBuilder sb) {
        File dir = new File(path);
        if (dir.mkdir()) {
            sb.append("Создана директория ").append(dir.getName()).append("\r\n");
        }
        return dir;
    }

    public static File fileCreate(File dir, String name, StringBuilder sb) {
        File file = new File(dir, name);
        try {
            if (file.createNewFile())
                sb.append("Создан файл ").append(file.getName()).append("\r\n");
        } catch (IOException e) {
            sb.append("Файл ").append(file.getName()).append(" не создан").append("\r\n");
        }
        return file;
    }

    public static void saveGame(String path, GameProgress save) {
        try (FileOutputStream fos = new FileOutputStream(path, false);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(save);
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }

    public static void zipFiles(String path, List<String> saves) {
        int count = 0;
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (String str : saves) {
                count++;
                FileInputStream fis = new FileInputStream(str);
                ZipEntry entry = new ZipEntry("save" + count + ".dat");
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
                new File(str).delete();
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }
}

