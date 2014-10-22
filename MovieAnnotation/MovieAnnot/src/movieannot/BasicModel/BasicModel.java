/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movieannot.BasicModel;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.ListModel;

/**
 *
 * @author dieutq
 */
public class BasicModel {

    private static BasicModel instance = null;

    protected BasicModel() {
    }

    public static BasicModel getInstance() {
        if (instance == null) {
            instance = new BasicModel();
        }
        return instance;
    }

    //Write function here
    public void AddtoList(JList list, ListModel model) {
        list.setModel(model);
        list.revalidate();
        list.repaint();
    }

    public void CopyMatrix(int[][] src, int[][] dest) {
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, dest[i], 0, src.length);
        }
    }
    
    public void removeElement(String textLine, List<Pair<Integer, Integer, String>> Actor, int[][] relation, List<JProgressBar> pgAuthor) {        
        for (JProgressBar pgAuthor1 : pgAuthor) {
            if (pgAuthor1.getString() == null ? textLine == null : pgAuthor1.getString().equals(textLine)) {
                
            }
        }      
                
    }

    public boolean CheckKeyPress(String tmp, DefaultListModel tmpList) {
        boolean checked;
        List<String> tmpChar1 = new ArrayList<>();
        for (int i = 0; i < tmpList.size(); i++) {
            String line = tmpList.get(i).toString();
            List<String> tmpChar = new ArrayList<>();
            tmpChar = Arrays.asList(line.split("#"));
            tmpChar1.add(tmpChar.get(0));
        }
        checked = tmpChar1.contains(tmp);        
        return checked;
    }

    public void CopyMatrixZerr(int[][] src, int[][] dest) {
        for (int i = 0; i < src.length; i++) {
            for (int j = 0; j < src.length; j++) {
                if ((src[i][j]) == 1) {
                    if ((dest[i][j] == 0) || (dest[i][j] == 1)) {
                        dest[i][j] = src[i][j];
                    }
                }
            }
        }
    }

    public boolean FindMemmber(int[][] src) {
        boolean found = false;
        for (int[] src1 : src) {
            for (int j = 0; j < src.length; j++) {
                if ((src1[j]) > 1) {
                    found = true;
                    break;
                }
            }
        }
        return found;
    }

    public void WriteRelationToFile(String filename, int[][] Relation) throws IOException {
        String text = "";
        for (int i = 0; i < Relation[0].length; i++) {
            for (int j = 0; j < Relation[0].length; j++) {
                text = text + String.valueOf(Relation[i][j]) + ",";
            }
            if ((text.length() > 0) && (text.charAt(text.length() - 1) == ',')) {
                text = text.substring(0, text.length() - 1) + "\r\n";
            }
            WriteToTextFile(filename, text, false);
        }
    }

    public void WriteToTextFile(String FileName, String TextToWrite, boolean condit) throws IOException {
        Path currentRelativePath = Paths.get("");
        String currentDir = currentRelativePath.toAbsolutePath().toString() + FileName;
        //System.out.print(currentDir);
        try (FileWriter fw = new FileWriter(currentDir, condit)) {
            String line = TextToWrite + "\r\n";
            fw.write(line);
            fw.close();
        }
    }

    public String[][] ReadTextFile(String FileName, int rows) throws IOException {
        //Read result of annotate
        String[][] name = new String[rows][3];
        int row = 0;
        List<String> tmpRead = new ArrayList<>();
        Path currentRelativePath = Paths.get("");
        String currentDir = currentRelativePath.toAbsolutePath().toString() + FileName;
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(currentDir));
            String line = null;
            while (true) {
                line = br.readLine();
                tmpRead = Arrays.asList(line.split("%"));
                for (int i = 0; i < 3; i++) {
                    name[row][i] = tmpRead.get(i);
                }
                if (line == null) {
                    break;
                }
                row++;
            }
            br.close();
        } catch (IOException ex) {
        }
        return name;
    }

    public List<String> ReadTextFileArrayList(String FileName) throws IOException {
        //Read result of annotate
        List<String> name = new ArrayList<>();
        Path currentRelativePath = Paths.get("");
        String currentDir = currentRelativePath.toAbsolutePath().toString() + FileName;
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(currentDir));
            String line = null;
            while (true) {
                line = br.readLine();
                name.add(line);
                if (line == null) {
                    break;
                }
            }
            br.close();
        } catch (IOException ex) {
        }
        return name;
    }

    public String ConvertTime(Long millis) {
        return String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }

    public int RevertTime(String timeInterval) {
        return 0;
    }
}
