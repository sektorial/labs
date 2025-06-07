package lab2;

import java.io.File;
import java.io.IOException;

public class Lab2_Parent {
    public static void main(String[] args) throws IOException, InterruptedException {
        String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String childClass = "lab2.Lab2_Child";

        ProcessBuilder pb = new ProcessBuilder(javaBin, "-cp", classpath, childClass);
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);
        Process child = pb.start();

        System.out.println("Child process started, PID=" + child.pid());
        System.out.println("Enter 't' and press Enter to terminate it");

        int ch;
        while ((ch = System.in.read()) != -1) {
            if (ch == 't') {
                child.destroy();
                break;
            }
        }

        child.waitFor();
        System.out.println("Child process terminated, exit code=" + child.exitValue());
    }
}
