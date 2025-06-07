package lab4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Lab4_Child {
    public static void main(String[] args) throws Exception {
        RandomAccessFile raf = new RandomAccessFile("calc.dat", "rw");
        FileChannel fc = raf.getChannel();
        MappedByteBuffer mb = fc.map(FileChannel.MapMode.READ_WRITE, 0, 12);
        int a = mb.getInt();
        int b = mb.getInt();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter operation (+ - * /): ");
        String op = br.readLine().trim();
        int res;
        switch (op) {
            case "+":
                res = a + b;
                break;
            case "-":
                res = a - b;
                break;
            case "*":
                res = a * b;
                break;
            case "/":
                res = b != 0 ? a / b : 0;
                break;
            default:
                res = 0;
        }
        mb.putInt(res);
        raf.close();
    }
}
