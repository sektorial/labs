package lab4;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Lab4_Parent {
    public static void main(String[] args) throws Exception {
        RandomAccessFile raf = new RandomAccessFile("calc.dat", "rw");
        raf.setLength(12);
        FileChannel fc = raf.getChannel();
        MappedByteBuffer mb = fc.map(FileChannel.MapMode.READ_WRITE, 0, 12);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("First number: ");
        int a = Integer.parseInt(br.readLine());
        System.out.print("Second number: ");
        int b = Integer.parseInt(br.readLine());
        mb.putInt(a);
        mb.putInt(b);
        raf.close();

        String java = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        String cp = System.getProperty("java.class.path");
        Process p = new ProcessBuilder(java, "-cp", cp, "lab4.Lab4_Child")
                .inheritIO()
                .start();
        p.waitFor();

        raf = new RandomAccessFile("calc.dat", "r");
        fc = raf.getChannel();
        mb = fc.map(FileChannel.MapMode.READ_ONLY, 0, 12);
        mb.getInt();
        mb.getInt();
        int res = mb.getInt();
        System.out.println("Result: " + res);
        raf.close();
    }
}
