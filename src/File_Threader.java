import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.concurrent.CountDownLatch;

public class File_Threader extends Thread{

    File file;
    ArrayList<Line> h;
    CountDownLatch c;

    public File_Threader(File file, ArrayList<Line> h, CountDownLatch c){
        this.file = file;
        this.h = h;
        this.c = c;
    }

    @Override
    public void run() {

        try{
            BufferedReader reader1 = new BufferedReader(new FileReader(file));
            int i=0;

            while (reader1.readLine() != null) {
                i++;
                //System.out.println(i);
            }

            CountDownLatch cl = new CountDownLatch(i);
            BufferedReader reader2 = new BufferedReader(new FileReader(file));
            String nextRecord;
            while ((nextRecord = reader2.readLine()) != null) {
                Line_Threader l = new Line_Threader(nextRecord, h, cl);
                l.start();
            }

            cl.await();
            c.countDown();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    }
