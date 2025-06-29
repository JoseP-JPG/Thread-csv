import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;


import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
public class Line_Threader extends Thread{
    //exp4j library used
    String line;
    ArrayList<Line> h = new ArrayList<>();
    CountDownLatch cl;

    public Line_Threader(String line, ArrayList<Line> h, CountDownLatch cl){
        this.line = line;
        this.h = h;
        this.cl = cl;
    }

    @Override
    public void run() {
        String[] s = line.split(",");
        Expression e = new ExpressionBuilder(s[1]).build();
        synchronized (h) {
            h.add(new Line(s[0],e.evaluate()));
            cl.countDown();
        }
    }

}