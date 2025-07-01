import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class DirectoryManager {


    File input;
    File output;

    public DirectoryManager() {

        JFileChooser fcInput = new JFileChooser();
        fcInput.setCurrentDirectory(new File(System.getProperty("user.home")));
        fcInput.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fcInput.setDialogTitle("Choose Input Folder:");
        fcInput.showOpenDialog(null);

        //System.out.println(fcInput.getCurrentDirectory());
        System.out.println(fcInput.getSelectedFile());

        JFileChooser fcOutput = new JFileChooser();
        fcOutput.setCurrentDirectory(new File(System.getProperty("user.home")));
        fcOutput.setDialogTitle("Choose Output Folder and file name:");
        //fcOutput.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fcOutput.setFileFilter(new FileNameExtensionFilter("csv file", "csv"));
        fcOutput.showSaveDialog(null);

        String filename = fcOutput.getSelectedFile().toString();
        if (!filename .endsWith(".csv")) {
            filename += ".csv";
            fcOutput.setSelectedFile(new File(filename));
        }

        //System.out.println(fcOutput.getCurrentDirectory());
        System.out.println(fcOutput.getSelectedFile());

        input = fcInput.getSelectedFile();
        output = fcOutput.getSelectedFile();

    }

    public void calculations(){
        ArrayList<Line> h = new ArrayList<>();
        File[] files = getInput().listFiles();

        if (files != null) {
            CountDownLatch c = new CountDownLatch(files.length);
            for (File file : files) {
                File_Threader f = new File_Threader(file, h, c);
                f.start();
            }
            try {
                c.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            h=Line.merge(h);



            try {

                FileWriter outputWriter = new FileWriter(getOutput().getAbsoluteFile());
                System.out.println("File created");
                for (Line h1 : h){
                    System.out.println(h1);
                    try {
                        outputWriter.write(h1+"\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    outputWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                System.out.println("O sistema não conseguiu localizar o caminho especificado");
            }




        }
    }

    public File getInput() {
        return input;
    }

    public File getOutput() {
        return output;
    }
}
