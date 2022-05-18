import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();

        File dirSrc = new File("D://Games/src");
        if (dirSrc.mkdir()) {
            sb.append("Создана директория ").append(dirSrc.getName());
            sb.append("\r\n");
        }
        File dirRes = new File("D://Games/res");
        if (dirRes.mkdir()) {
            sb.append("Создана директория ").append(dirRes.getName());
            sb.append("\r\n");
        }
        File dirSave = new File("D://Games/savegames");
        if (dirSave.mkdir()) {
            sb.append("Создана директория ").append(dirSave.getName());
            sb.append("\r\n");
        }
        File dirTemp = new File("D://Games/temp");
        if (dirTemp.mkdir()) {
            sb.append("Создана директория ").append(dirTemp.getName());
            sb.append("\r\n");
        }

        File dirMain = new File("D://Games/src/main");
        if (dirMain.mkdir()) {
            sb.append("Создана директория ").append(dirMain.getName());
            sb.append("\r\n");
        }
        File dirTest = new File("D://Games/src/test");
        if (dirTest.mkdir()) {
            sb.append("Создана директория ").append(dirTest.getName());
            sb.append("\r\n");
        }

        File fileMain = new File(dirMain, "Main.java");
        try {
            if (fileMain.createNewFile())
                sb.append("Создан файл ").append(fileMain.getName());
            sb.append("\r\n");
        } catch (IOException e) {
            sb.append("Файл ").append(fileMain.getName()).append(" не создан");
            sb.append("\r\n");
        }

        File fileUtils = new File(dirMain, "Utils.java");
        try {
            if (fileUtils.createNewFile())
                sb.append("Создан файл ").append(fileUtils.getName());
            sb.append("\r\n");
        } catch (IOException e) {
            sb.append("Файл ").append(fileUtils.getName()).append(" не создан");
            sb.append("\r\n");
        }

        File dirDrawables = new File(dirRes, "drawables");
        if (dirDrawables.mkdir()) {
            sb.append("Создана директория ").append(dirDrawables.getName());
            sb.append("\r\n");
        }
        File dirVectors = new File(dirRes, "vectors");
        if (dirVectors.mkdir()) {
            sb.append("Создана директория ").append(dirVectors.getName());
            sb.append("\r\n");
        }
        File dirIcons = new File(dirRes, "icons");
        if (dirIcons.mkdir()) {
            sb.append("Создана директория ").append(dirIcons.getName());
            sb.append("\r\n");
        }

        File fileTemp = new File(dirTemp, "temp.txt");
        try {
            if (fileTemp.createNewFile())
                sb.append("Создан файл ").append(fileTemp.getName());
            sb.append("\r\n");
        } catch (IOException e) {
            sb.append("Файл ").append(fileTemp.getName()).append(" не создан");
            sb.append("\r\n");
        }

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

