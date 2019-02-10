package uuweb.net.imageTools;

import uuweb.net.imageTools.utility.EncryptUtility;

import java.io.File;

/**
 * @author ZDT
 * Created at 2019/2/10 12:19
 */
public class Application {
    public static void main(String args[]) {
        encryptFiles();
        decryptFiles();
    }
    private static void encryptFiles(){
        File path = new File("./out/decrypt/");
        System.out.println(path.getAbsolutePath());
        File[] allfiles = path.listFiles();
        File[] fileImgs = path.listFiles(pathname -> pathname.getName().endsWith(".JPG"));
        for (File file: fileImgs) {
            EncryptUtility.encryptFile(file.getPath(),"./out/encrypt/"+file.getName(),"password");
        }
    }
    private static void decryptFiles(){
        File path = new File("./out/encrypt/");
        System.out.println(path.getAbsolutePath());
        File[] allfiles = path.listFiles();
        File[] fileImgs = path.listFiles(pathname -> pathname.getName().endsWith(".JPG"));
        for (File file: fileImgs) {
            EncryptUtility.decryptFile(file.getPath(),"./out/decrypt/"+file.getName(),"password");
        }
    }
}
