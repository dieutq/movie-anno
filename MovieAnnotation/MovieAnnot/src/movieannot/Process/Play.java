/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movieannot.Process;

/**
 *
 * @author dieutq
 */
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
import static com.sun.org.apache.xerces.internal.util.XMLChar.trim;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog.ModalityType;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.Painter;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.FileChooserUI;
import javax.swing.plaf.nimbus.AbstractRegionPainter;
//import movieannot.BasicModel.Aggregate;
import movieannot.BasicModel.BasicModel;
import movieannot.BasicModel.Pair;
import movieannot.BasicModel.PopUpMenu;
import prefuse.data.Node;
import sun.awt.AppContext;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import movieannot.Process.PreviewJFrame;

public class Play extends javax.swing.JFrame implements KeyListener, MouseListener {

    private MediaPlayerFactory mediaPlayerFactory;
    private EmbeddedMediaPlayer mediaPlayer;
    private DefaultListModel model, model1;
    private boolean[] appeeared;
    private Timer timer, timerSlider, timmerlayer;
    private boolean[] keyprsd;
    private long getPlayTiming; //lay thoi gian xuat hien hoac ket thuc cua character
    private String template, filename;
    private static int lockCharacter, lockCharacter1;
    private Canvas c;
    private List<String> tmpChar, tmpChar1 = new ArrayList<>();
    private List<String> eNode;
    private String characterAppeared;
    private int pgPosVer, pgPosHor;
    private List<Long> char1, char2, char3, char4, char5, char6, char7, char8, char9, char10,
            char11, char12, char13, char14, char15, char16, char17, char18, char19, char20,
            char21, char22, char23, char24, char25, char26, char27, char28, char29, char30;
    private List<List<Long>> ListChar;
    private List<List<Pair<Integer, Integer, String>>> ListActor;
    private List<JProgressBar> ListPgAuthor;
    private List<String> ListKeyCatch;
    private List<Pair<Integer, Integer, String>> Actor1, Actor2, Actor3, Actor4, Actor5, Actor6, Actor7, Actor8, Actor9, Actor10,
            Actor11, Actor12, Actor13, Actor14, Actor15, Actor16, Actor17, Actor18, Actor19, Actor20,
            Actor21, Actor22, Actor23, Actor24, Actor25, Actor26, Actor27, Actor28, Actor29, Actor30, AllChrac;
    //Relation Matrix    
    private int[][] Relation, cpRelation, weightRelation, occurtime;

    private List<String> NodeGraph;
    private String[] keyb;
    private boolean isRefreshGraph = false;
    private List<JProgressBar> pgAuthor1, pgAuthor2, pgAuthor3, pgAuthor4, pgAuthor5, pgAuthor6, pgAuthor7, pgAuthor8, pgAuthor9, pgAuthor10, pgAuthor11, pgAuthor12, pgAuthor13, pgAuthor14, pgAuthor15, pgAuthor16, pgAuthor17, pgAuthor18, pgAuthor19, pgAuthor20, pgAuthor21, pgAuthor22, pgAuthor23, pgAuthor24, pgAuthor25, pgAuthor26, pgAuthor27, pgAuthor28, pgAuthor29, pgAuthor30;
    private long LengthOfMovie = 0;
    private final Play gui;
    public String txtCondition;
    public JPopupMenu Pmenu;
    public JMenuItem menuItem;
    public String[] count;
    public List<String> checkchar, checkchar1;
    public List<String> ch0, ch1, ch2, ch3, ch4, ch5, ch6, ch7, ch8, ch9, ch10, ch11, ch12, ch13, ch14, ch15, ch16, ch17, ch18, ch19;
    public JLabel jlb;

    private void ClearRelationMatrix() {
        for (int i = 0; i < Relation.length; i++) {
            for (int j = 0; j < Relation.length; j++) {
                Relation[i][j] = 0;
                cpRelation[i][j] = 0;
                weightRelation[i][j] = 0;
            }
        }
    }

    private void ClearAll() {
        for (int i = 0; i < Relation.length; i++) {
            for (int j = 0; j < Relation.length; j++) {
                Relation[i][j] = 0;
                cpRelation[i][j] = 0;
                weightRelation[i][j] = 0;
            }
        }
        char1.clear();
        char2.clear();
        char3.clear();
        char4.clear();
        char5.clear();
        char6.clear();
        char7.clear();
        char8.clear();
        char9.clear();
        char10.clear();
        char11.clear();
        char12.clear();
        char13.clear();
        char14.clear();
        char15.clear();
        char16.clear();
        char17.clear();
        char18.clear();
        char19.clear();
        char20.clear();
        char21.clear();
        char22.clear();
        char23.clear();
        char24.clear();
        char25.clear();
        char26.clear();
        char27.clear();
        char28.clear();
        char29.clear();
        char30.clear();

        //Actor 
        Actor1.clear();
        Actor2.clear();
        Actor3.clear();
        Actor4.clear();
        Actor5.clear();
        Actor6.clear();
        Actor7.clear();
        Actor8.clear();
        Actor9.clear();
        Actor10.clear();
        Actor11.clear();
        Actor12.clear();
        Actor13.clear();
        Actor14.clear();
        Actor15.clear();
        Actor16.clear();
        Actor17.clear();
        Actor18.clear();
        Actor19.clear();
        Actor20.clear();
        Actor21.clear();
        Actor22.clear();
        Actor23.clear();
        Actor24.clear();
        Actor25.clear();
        Actor26.clear();
        Actor27.clear();
        Actor28.clear();
        Actor29.clear();
        Actor30.clear();
        AllChrac.clear();

        //Processbar
        pgAuthor1.clear();
        pgAuthor2.clear();
        pgAuthor3.clear();
        pgAuthor4.clear();
        pgAuthor5.clear();
        pgAuthor6.clear();
        pgAuthor7.clear();
        pgAuthor8.clear();
        pgAuthor9.clear();
        pgAuthor10.clear();
        pgAuthor11.clear();
        pgAuthor12.clear();
        pgAuthor13.clear();
        pgAuthor14.clear();
        pgAuthor15.clear();
        pgAuthor16.clear();
        pgAuthor17.clear();
        pgAuthor18.clear();
        pgAuthor19.clear();
        pgAuthor20.clear();
        pgAuthor21.clear();
        pgAuthor22.clear();
        pgAuthor23.clear();
        pgAuthor24.clear();
        pgAuthor25.clear();
        pgAuthor26.clear();
        pgAuthor27.clear();
        pgAuthor28.clear();
        pgAuthor29.clear();
        pgAuthor30.clear();
    }

    private void appearedInit() {
        Relation = new int[30][30];
        cpRelation = new int[30][30];
        weightRelation = new int[30][30];
        for (int i = 0; i < Relation.length; i++) {
            for (int j = 0; j < Relation.length; j++) {
                Relation[i][j] = 0;
                cpRelation[i][j] = 0;
                weightRelation[i][j] = 0;
            }
        }
        appeeared = new boolean[31];
        keyprsd = new boolean[31];
        for (int i = 0; i < appeeared.length; i++) {
            appeeared[i] = false;
            keyprsd[i] = false;
        }
        eNode = new ArrayList<>();
    }

    private void SetVisibleObj() {
        charac1.setVisible(false);
        charac2.setVisible(false);
        charac3.setVisible(false);
        charac4.setVisible(false);
        charac5.setVisible(false);
        charac6.setVisible(false);
        charac7.setVisible(false);
        charac8.setVisible(false);
        charac9.setVisible(false);
        charac10.setVisible(false);
        charac11.setVisible(false);
        charac12.setVisible(false);
        charac13.setVisible(false);
        charac14.setVisible(false);
        charac15.setVisible(false);
        charac16.setVisible(false);
        charac17.setVisible(false);
        charac18.setVisible(false);
        charac19.setVisible(false);
        charac20.setVisible(false);
    }

    private void InitKeyboard() {
        keyb = new String[31];
        keyb[1] = "1";
        keyb[2] = "2";
        keyb[3] = "3";
        keyb[4] = "4";
        keyb[5] = "5";
        keyb[6] = "6";
        keyb[7] = "7";
        keyb[8] = "8";
        keyb[9] = "9";
        keyb[10] = "0";
        keyb[11] = "a";
        keyb[12] = "b";
        keyb[13] = "c";
        keyb[14] = "d";
        keyb[15] = "e";
        keyb[16] = "f";
        keyb[17] = "g";
        keyb[18] = "h";
        keyb[19] = "i";
        keyb[20] = "j";
        keyb[21] = "k";
        keyb[22] = "l";
        keyb[23] = "m";
        keyb[24] = "n";
        keyb[25] = "o";
        keyb[26] = "p";
        keyb[27] = "q";
        keyb[28] = "r";
        keyb[29] = "s";
        keyb[30] = "t";
        checkchar = new ArrayList<>();
        checkchar.add("1");
        checkchar.add("2");
        checkchar.add("3");
        checkchar.add("4");
        checkchar.add("5");
        checkchar.add("6");
        checkchar.add("7");
        checkchar.add("8");
        checkchar.add("9");
    }

    public void runTimer1() {
        timmerlayer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mediaPlayer != null) {
                    //float timepos = (float) mediaPlayer.getTime()/ (float) mediaPlayer.getLength();                                       
                    float timepos = (float) mediaPlayer.getTime() / (float) mediaPlayer.getLength();
                    int pos = (int) (timepos * charac1.getWidth());
                    //pos = pos*charac1.getWidth();                                        
                    //System.out.println(pos);
                    jlb.setLocation(pos, charac1.getY());
                    jlb.setVisible(true);
                    jLayeredPane1.moveToFront(jlb);

                }
            }
        });
    }

    public void runTimer() {
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (label.getText() == "") {
                    ListApp.setListData(new Object[0]);
                }
                for (int i = 0; i < appeeared.length; i++) {
                    if (appeeared[i] == true) {
                        if (!label.getText().equals("")) {
                            tmpChar = Arrays.asList(label.getText().split(","));
                            if (tmpChar.contains(String.valueOf(i))) {
                            } else {
                                label.setText(label.getText() + "," + String.valueOf(i));
                            }
                        } else {
                            label.setText(String.valueOf(i));//
                        }
                    }
                }
                String line = "";
                String tmp = label.getText();
                tmpChar1 = Arrays.asList(label.getText().split(","));
                for (String tmpChar11 : tmpChar1) {
                    for (int j = 0; j < appeeared.length; j++) {
                        if ((appeeared[j] == true) && (tmpChar11.equals(String.valueOf(j)))) {
                            line = line + tmpChar11 + ",";
                        }
                    }
                }
                if ((line.length() > 0) && (line.charAt(line.length() - 1) == ',')) {
                    line = line.substring(0, line.length() - 1);
                }
                //make relation matrix
                tmpChar1 = Arrays.asList(line.split(","));

                //System.out.println("DataRelation" + line);   //problem 
                BasicModel.getInstance().CopyMatrix(Relation, cpRelation);
                BasicModel.getInstance().CopyMatrixZerr(Relation, weightRelation);
                //CalculateRelation();

                for (int i = 0; i < Relation[0].length; i++) {
                    if (tmpChar1.contains(String.valueOf(i))) {
                        for (int k = 0; k < tmpChar1.size(); k++) {
                            Relation[i - 1][Integer.parseInt(tmpChar1.get(k)) - 1] = 1;
                        }
                    }
                }

                if (Arrays.deepEquals(cpRelation, Relation)) {
                    isRefreshGraph = true;
                    //System.out.print("isupdate false");
                } else {
                    isRefreshGraph = false;
                    BasicModel.getInstance().CopyMatrixZerr(Relation, weightRelation);
                    RefreshGraph(Relation);//weightRelation
                }
                characterAppeared = line;
                label.setText(characterAppeared);
                //change status of Network
                eNode = Arrays.asList(characterAppeared.split(","));
                PrintToLabel();
                revalidate();
                repaint();
                //RefreshGraph();
                //getName                
                if ((ListCharacter.getModel().getSize() != 0) && (label.getText() != "")) {
                    String app = "";
                    model = (DefaultListModel) ListCharacter.getModel();
                    tmpChar1 = Arrays.asList(label.getText().split(","));
                    for (int i = 0; i < tmpChar1.size(); i++) {
                        try {
                            app = app + model.get(Integer.parseInt(tmpChar1.get(i))) + ",";
                        } catch (Exception xxxx) {
                        }
                    }
                    if ((app.length() > 0) && (app.charAt(app.length() - 1) == ',')) {
                        app = app.substring(0, app.length() - 1);
                    }
                    model1.clear();
                    tmpChar1 = Arrays.asList(app.split(","));
                    for (String tmpChar11 : tmpChar1) {
                        model1.addElement(tmpChar11);
                    }
                    BasicModel.getInstance().AddtoList(ListApp, model1);
                }
                SetFocus();
                if (mediaPlayer.isPlaying() == true) {
                    nameCharacter.setFocusable(false);
                    gui.setFocusable(true);
                } else {
                    nameCharacter.setFocusable(true);
                }
                //gui.setFocusable(true);
            }
        });

        //prefusePanel.revalidate();
        timer.setInitialDelay(0);
        timer.start();
    }

    public void InitStartupData() {
        ListChar = new ArrayList<>();
        ListActor = new ArrayList<>();
        ListPgAuthor = new ArrayList<>();
        ListKeyCatch = new ArrayList<>();
        String[] csvStrings = new String[]{
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p"};
        for (int i = 0; i < csvStrings.length; i++) {
            ListKeyCatch.add(i, csvStrings[i]);
            //System.out.print(csvStrings[i]);
        }
    }

    public void InitCharacterData() {
        char1 = new ArrayList<>();
        char2 = new ArrayList<>();
        char3 = new ArrayList<>();
        char4 = new ArrayList<>();
        char5 = new ArrayList<>();
        char6 = new ArrayList<>();
        char7 = new ArrayList<>();
        char8 = new ArrayList<>();
        char9 = new ArrayList<>();
        char10 = new ArrayList<>();
        char11 = new ArrayList<>();
        char12 = new ArrayList<>();
        char13 = new ArrayList<>();
        char14 = new ArrayList<>();
        char15 = new ArrayList<>();
        char16 = new ArrayList<>();
        char17 = new ArrayList<>();
        char18 = new ArrayList<>();
        char19 = new ArrayList<>();
        char20 = new ArrayList<>();
        char21 = new ArrayList<>();
        char22 = new ArrayList<>();
        char23 = new ArrayList<>();
        char24 = new ArrayList<>();
        char25 = new ArrayList<>();
        char26 = new ArrayList<>();
        char27 = new ArrayList<>();
        char28 = new ArrayList<>();
        char29 = new ArrayList<>();
        char30 = new ArrayList<>();

        //Actor 
        Actor1 = new ArrayList<>();
        Actor2 = new ArrayList<>();
        Actor3 = new ArrayList<>();
        Actor4 = new ArrayList<>();
        Actor5 = new ArrayList<>();
        Actor6 = new ArrayList<>();
        Actor7 = new ArrayList<>();
        Actor8 = new ArrayList<>();
        Actor9 = new ArrayList<>();
        Actor10 = new ArrayList<>();
        Actor11 = new ArrayList<>();
        Actor12 = new ArrayList<>();
        Actor13 = new ArrayList<>();
        Actor14 = new ArrayList<>();
        Actor15 = new ArrayList<>();
        Actor16 = new ArrayList<>();
        Actor17 = new ArrayList<>();
        Actor18 = new ArrayList<>();
        Actor19 = new ArrayList<>();
        Actor20 = new ArrayList<>();
        Actor21 = new ArrayList<>();
        Actor22 = new ArrayList<>();
        Actor23 = new ArrayList<>();
        Actor24 = new ArrayList<>();
        Actor25 = new ArrayList<>();
        Actor26 = new ArrayList<>();
        Actor27 = new ArrayList<>();
        Actor28 = new ArrayList<>();
        Actor29 = new ArrayList<>();
        Actor30 = new ArrayList<>();
        AllChrac = new ArrayList<>();

        //Processbar
        pgAuthor1 = new ArrayList<>();
        pgAuthor2 = new ArrayList<>();
        pgAuthor3 = new ArrayList<>();
        pgAuthor4 = new ArrayList<>();
        pgAuthor5 = new ArrayList<>();
        pgAuthor6 = new ArrayList<>();
        pgAuthor7 = new ArrayList<>();
        pgAuthor8 = new ArrayList<>();
        pgAuthor9 = new ArrayList<>();
        pgAuthor10 = new ArrayList<>();
        pgAuthor11 = new ArrayList<>();
        pgAuthor12 = new ArrayList<>();
        pgAuthor13 = new ArrayList<>();
        pgAuthor14 = new ArrayList<>();
        pgAuthor15 = new ArrayList<>();
        pgAuthor16 = new ArrayList<>();
        pgAuthor17 = new ArrayList<>();
        pgAuthor18 = new ArrayList<>();
        pgAuthor19 = new ArrayList<>();
        pgAuthor20 = new ArrayList<>();
        pgAuthor21 = new ArrayList<>();
        pgAuthor22 = new ArrayList<>();
        pgAuthor23 = new ArrayList<>();
        pgAuthor24 = new ArrayList<>();
        pgAuthor25 = new ArrayList<>();
        pgAuthor26 = new ArrayList<>();
        pgAuthor27 = new ArrayList<>();
        pgAuthor28 = new ArrayList<>();
        pgAuthor29 = new ArrayList<>();
        pgAuthor30 = new ArrayList<>();

        //Charlist
        ch0 = new ArrayList<>();
        ch1 = new ArrayList<>();
        ch2 = new ArrayList<>();
        ch3 = new ArrayList<>();
        ch4 = new ArrayList<>();
        ch5 = new ArrayList<>();
        ch6 = new ArrayList<>();
        ch7 = new ArrayList<>();
        ch8 = new ArrayList<>();
        ch9 = new ArrayList<>();
        ch10 = new ArrayList<>();
        ch11 = new ArrayList<>();
        ch12 = new ArrayList<>();
        ch13 = new ArrayList<>();
        ch14 = new ArrayList<>();
        ch15 = new ArrayList<>();
        ch16 = new ArrayList<>();
        ch17 = new ArrayList<>();
        ch18 = new ArrayList<>();
        ch19 = new ArrayList<>();

    }

    public void PrintToLabel() {
        String tmp;
        model = (DefaultListModel) ListCharacter.getModel();
        if (!char1.isEmpty()) {
            tmp = "";
            for (Long char110 : char1) {
                tmp = tmp + BasicModel.getInstance().ConvertTime(char110) + "~";
            }
            if ((tmp.length() > 0) && (tmp.charAt(tmp.length() - 1) == '~')) {
                tmp = tmp.substring(0, tmp.length() - 1);
            }
            if (model.size() > 0) {
                charac1.setText(model.get(1) + " appear");// + tmp);
            } else {
                charac1.setText("Character 1 appear");// + tmp);
            }
            charac1.revalidate();
            charac1.repaint();
        }
        if (!char2.isEmpty()) {
            tmp = "";
            for (Long char21 : char2) {
                tmp = tmp + BasicModel.getInstance().ConvertTime(char21) + "~";
            }
            if ((tmp.length() > 0) && (tmp.charAt(tmp.length() - 1) == '~')) {
                tmp = tmp.substring(0, tmp.length() - 1);
            }
            if (model.size() > 1) {
                charac2.setText(model.get(2) + " appear");// + tmp);
            } else {
                charac2.setText("Character 2 appear");// + tmp);
            }
            charac2.revalidate();
            charac2.repaint();
        }
        if (!char3.isEmpty()) {
            tmp = "";
            for (Long char31 : char3) {
                tmp = tmp + BasicModel.getInstance().ConvertTime(char31) + "~";
            }
            if ((tmp.length() > 0) && (tmp.charAt(tmp.length() - 1) == '~')) {
                tmp = tmp.substring(0, tmp.length() - 1);
            }
            if (model.size() > 2) {
                charac3.setText(model.get(3) + " appear");// + tmp);
            } else {
                charac3.setText("Character 3 appear");// + tmp);
            }
            charac3.revalidate();
            charac3.repaint();
        }
        if (!char4.isEmpty()) {
            tmp = "";
            for (Long char41 : char4) {
                tmp = tmp + String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(char41), TimeUnit.MILLISECONDS.toSeconds(char41) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(char41))) + "~";
            }
            if ((tmp.length() > 0) && (tmp.charAt(tmp.length() - 1) == '~')) {
                tmp = tmp.substring(0, tmp.length() - 1);
            }
            if (model.size() > 3) {
                charac4.setText(model.get(4) + " appear");// + tmp);
            } else {
                charac4.setText("Character 3 appear");// + tmp);
            }
            charac4.revalidate();
            charac4.repaint();
        }
        if (!char5.isEmpty()) {
            tmp = "";
            for (Long char51 : char5) {
                tmp = tmp + String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(char51), TimeUnit.MILLISECONDS.toSeconds(char51) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(char51))) + "~";
            }
            if ((tmp.length() > 0) && (tmp.charAt(tmp.length() - 1) == '~')) {
                tmp = tmp.substring(0, tmp.length() - 1);
            }
            if (model.size() > 4) {
                charac5.setText(model.get(5) + " appear");// + tmp);
            } else {
                charac5.setText("Character 5 appear");// + tmp);
            }
            charac5.revalidate();
            charac5.repaint();
        }
        if (!char6.isEmpty()) {
            tmp = "";
            for (Long char61 : char6) {
                tmp = tmp + String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(char61), TimeUnit.MILLISECONDS.toSeconds(char61) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(char61))) + "~";
            }
            if ((tmp.length() > 0) && (tmp.charAt(tmp.length() - 1) == '~')) {
                tmp = tmp.substring(0, tmp.length() - 1);
            }
            if (model.size() > 5) {
                charac6.setText(model.get(6) + " appear");// + tmp);
            } else {
                charac6.setText("Character 6 appear");// + tmp);
            }
            charac6.revalidate();
            charac6.repaint();
        }
        if (!char7.isEmpty()) {
            tmp = "";
            for (Long char71 : char7) {
                tmp = tmp + String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(char71), TimeUnit.MILLISECONDS.toSeconds(char71) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(char71))) + "~";
            }
            if ((tmp.length() > 0) && (tmp.charAt(tmp.length() - 1) == '~')) {
                tmp = tmp.substring(0, tmp.length() - 1);
            }
            if (model.size() > 6) {
                charac7.setText(model.get(7) + " appear");// + tmp);
            } else {
                charac7.setText("Character 7 appear");// + tmp);
            }
            charac7.revalidate();
            charac7.repaint();
        }
        if (!char8.isEmpty()) {
            tmp = "";
            for (Long char81 : char8) {
                tmp = tmp + String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(char81), TimeUnit.MILLISECONDS.toSeconds(char81) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(char81))) + "~";
            }
            if ((tmp.length() > 0) && (tmp.charAt(tmp.length() - 1) == '~')) {
                tmp = tmp.substring(0, tmp.length() - 1);
            }
            if (model.size() > 7) {
                charac8.setText(model.get(8) + " appear");// + tmp);
            } else {
                charac8.setText("Character 8 appear");// + tmp);
            }
            charac8.revalidate();
            charac8.repaint();
        }
        if (!char9.isEmpty()) {
            tmp = "";
            for (Long char91 : char9) {
                tmp = tmp + String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(char91), TimeUnit.MILLISECONDS.toSeconds(char91) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(char91))) + "~";
            }
            if ((tmp.length() > 0) && (tmp.charAt(tmp.length() - 1) == '~')) {
                tmp = tmp.substring(0, tmp.length() - 1);
            }
            if (model.size() > 8) {
                charac9.setText(model.get(9) + " appear");// + tmp);
            } else {
                charac9.setText("Character 9 appear");// + tmp);
            }
            charac9.revalidate();
            charac9.repaint();
        }
        if (!char10.isEmpty()) {
            tmp = "";
            for (Long char101 : char10) {
                tmp = tmp + String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(char101), TimeUnit.MILLISECONDS.toSeconds(char101) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(char101))) + "~";
            }
            if ((tmp.length() > 0) && (tmp.charAt(tmp.length() - 1) == '~')) {
                tmp = tmp.substring(0, tmp.length() - 1);
            }
            if (model.size() > 9) {
                charac10.setText(model.get(0) + " appear");// + tmp);
            } else {
                charac10.setText("Character 10 appear");// + tmp);
            }
            charac10.revalidate();
            charac10.repaint();
        }
        if (!char11.isEmpty()) {
            tmp = "";
            for (Long char101 : char11) {
                tmp = tmp + String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(char101), TimeUnit.MILLISECONDS.toSeconds(char101) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(char101))) + "~";
            }
            if ((tmp.length() > 0) && (tmp.charAt(tmp.length() - 1) == '~')) {
                tmp = tmp.substring(0, tmp.length() - 1);
            }
            if (model.size() > 10) {
                charac11.setText(model.get(10) + " appear");// + tmp);
            } else {
                charac11.setText("Character 11 appear");// + tmp);
            }
            charac11.revalidate();
            charac11.repaint();
        }
        if (!char12.isEmpty()) {
            tmp = "";
            for (Long char101 : char12) {
                tmp = tmp + String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(char101), TimeUnit.MILLISECONDS.toSeconds(char101) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(char101))) + "~";
            }
            if ((tmp.length() > 0) && (tmp.charAt(tmp.length() - 1) == '~')) {
                tmp = tmp.substring(0, tmp.length() - 1);
            }
            if (model.size() > 11) {
                charac12.setText(model.get(11) + " appear");// + tmp);
            } else {
                charac12.setText("Character 12 appear");// + tmp);
            }
            charac12.revalidate();
            charac12.repaint();
        }
        if (!char13.isEmpty()) {
            tmp = "";
            for (Long char101 : char13) {
                tmp = tmp + String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(char101), TimeUnit.MILLISECONDS.toSeconds(char101) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(char101))) + "~";
            }
            if ((tmp.length() > 0) && (tmp.charAt(tmp.length() - 1) == '~')) {
                tmp = tmp.substring(0, tmp.length() - 1);
            }
            if (model.size() > 12) {
                charac13.setText(model.get(12) + " appear");// + tmp);
            } else {
                charac13.setText("Character 13 appear");// + tmp);
            }
            charac13.revalidate();
            charac13.repaint();
        }

        if (!char14.isEmpty()) {
            tmp = "";
            for (Long char101 : char14) {
                tmp = tmp + String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(char101), TimeUnit.MILLISECONDS.toSeconds(char101) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(char101))) + "~";
            }
            if ((tmp.length() > 0) && (tmp.charAt(tmp.length() - 1) == '~')) {
                tmp = tmp.substring(0, tmp.length() - 1);
            }
            if (model.size() > 13) {
                charac14.setText(model.get(13) + " appear");// + tmp);
            } else {
                charac14.setText("Character 14 appear");// + tmp);
            }
            charac14.revalidate();
            charac14.repaint();
        }

        if (!char15.isEmpty()) {
            tmp = "";
            for (Long char101 : char15) {
                tmp = tmp + String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(char101), TimeUnit.MILLISECONDS.toSeconds(char101) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(char101))) + "~";
            }
            if ((tmp.length() > 0) && (tmp.charAt(tmp.length() - 1) == '~')) {
                tmp = tmp.substring(0, tmp.length() - 1);
            }
            if (model.size() > 14) {
                charac15.setText(model.get(14) + " appear");// + tmp);
            } else {
                charac15.setText("Character 15 appear");// + tmp);
            }
            charac15.revalidate();
            charac15.repaint();
        }

        if (!char16.isEmpty()) {
            tmp = "";
            for (Long char101 : char16) {
                tmp = tmp + String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(char101), TimeUnit.MILLISECONDS.toSeconds(char101) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(char101))) + "~";
            }
            if ((tmp.length() > 0) && (tmp.charAt(tmp.length() - 1) == '~')) {
                tmp = tmp.substring(0, tmp.length() - 1);
            }
            if (model.size() > 15) {
                charac16.setText(model.get(15) + " appear");// + tmp);
            } else {
                charac16.setText("Character 16 appear");// + tmp);
            }
            charac16.revalidate();
            charac16.repaint();
        }

        if (!char17.isEmpty()) {
            tmp = "";
            for (Long char101 : char17) {
                tmp = tmp + String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(char101), TimeUnit.MILLISECONDS.toSeconds(char101) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(char101))) + "~";
            }
            if ((tmp.length() > 0) && (tmp.charAt(tmp.length() - 1) == '~')) {
                tmp = tmp.substring(0, tmp.length() - 1);
            }
            if (model.size() > 16) {
                charac17.setText(model.get(16) + " appear");// + tmp);
            } else {
                charac17.setText("Character 16 appear");// + tmp);
            }
            charac17.revalidate();
            charac17.repaint();
        }
        if (!char18.isEmpty()) {
            tmp = "";
            for (Long char101 : char18) {
                tmp = tmp + String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(char101), TimeUnit.MILLISECONDS.toSeconds(char101) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(char101))) + "~";
            }
            if ((tmp.length() > 0) && (tmp.charAt(tmp.length() - 1) == '~')) {
                tmp = tmp.substring(0, tmp.length() - 1);
            }
            if (model.size() > 18) {
                charac18.setText(model.get(17) + " appear");// + tmp);
            } else {
                charac18.setText("Character 18 appear");// + tmp);
            }
            charac18.revalidate();
            charac18.repaint();
        }

        if (!char19.isEmpty()) {
            tmp = "";
            for (Long char101 : char19) {
                tmp = tmp + String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(char101), TimeUnit.MILLISECONDS.toSeconds(char101) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(char101))) + "~";
            }
            if ((tmp.length() > 0) && (tmp.charAt(tmp.length() - 1) == '~')) {
                tmp = tmp.substring(0, tmp.length() - 1);
            }
            if (model.size() > 19) {
                charac19.setText(model.get(18) + " appear");// + tmp);
            } else {
                charac19.setText("Character 18 appear");// + tmp);
            }
            charac19.revalidate();
            charac19.repaint();
        }

        if (!char20.isEmpty()) {
            tmp = "";
            for (Long char101 : char20) {
                tmp = tmp + String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(char101), TimeUnit.MILLISECONDS.toSeconds(char101) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(char101))) + "~";
            }
            if ((tmp.length() > 0) && (tmp.charAt(tmp.length() - 1) == '~')) {
                tmp = tmp.substring(0, tmp.length() - 1);
            }
            if (model.size() == 20) {
                charac20.setText(model.get(19) + " appear");// + tmp);
            } else {
                charac20.setText("Character 20 appear");// + tmp);
            }

            charac20.revalidate();
            charac20.repaint();
        }

    }

    public void RefreshRelation() {
        String line = trim(label.getText());
        if (!line.equals("")) {
            List<String> tmpArr = Arrays.asList(line.split(","));
            BasicModel.getInstance().CopyMatrixZerr(cpRelation, weightRelation);
            for (String tmp : tmpArr) {
                for (String str1 : tmpArr) {
                    if (weightRelation[Integer.parseInt(tmp) - 1][Integer.parseInt(str1) - 1] >= 1) {
                        weightRelation[Integer.parseInt(tmp) - 1][Integer.parseInt(str1) - 1] = weightRelation[Integer.parseInt(tmp) - 1][Integer.parseInt(str1) - 1] + 1;
                    }
                }
            }
            //print
            RefreshGraph(Relation);//weight
        }
    }

    private void RePaint(JLabel label) {
        label.setVisible(true);
        label.setForeground(Color.blue);
        label.setBackground(Color.lightGray);
        label.setOpaque(false);
        label.revalidate();
        label.repaint();
    }

    public Play() {
        initComponents();
        InitKeyboard();
        appearedInit();
        InitCharacterData();
        jLayeredPane1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                //System.out.print(e.getX() + "  " + e.getY());
            }
        });
        jlb = new JLabel();
        jLayeredPane1.add(jlb);
        jLayeredPane1.moveToFront(jlb);

        //layeredlayout.setOpaque(true);        
        //layeredlayout.setBackground(Color.BLACK);                
        //layeredlayout.setVisible(false);
        jLayeredPane1.moveToFront(layeredlayout);

        //jLayeredPane1.moveToFront(layeredlayout);
        //InitStartupData();
        //SetVisibleObj();
        /*
         JDialog dialog = new JDialog();
         dialog.setModal(true);
         dialog.setAlwaysOnTop(true);
         dialog.setModalityType(ModalityType.APPLICATION_MODAL);
         */
        setFocusable(true);
        model1 = new DefaultListModel();
        addKeyListener(this);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        gui = this;
        JlistClick(ListCharacter, nameCharacter);
        //runTimer();
        //RefreshGraph();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int i1 = JOptionPane.showConfirmDialog(gui, "Are you sure to exit?", "Closing dialog", JOptionPane.YES_NO_OPTION);
                if (i1 == JOptionPane.YES_OPTION) {
                    for (int i = 0; i < appeeared.length; i++) {
                        if ((appeeared[i] == true) && (i != 0)) {
                            try {
                                BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(i) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
                            } catch (IOException ex) {
                                Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        appeeared[i] = false;
                    }
                    if (appeeared[0] == true) {
                        try {
                            BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(10) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
                        } catch (IOException ex) {
                            Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    /*
                     Path currentRelativePath = Paths.get("");
                     String currentFile = currentRelativePath.toAbsolutePath().toString() + "Pair.txt";
                     File del = new File(currentFile);
                     try {
                     del.delete();
                     } catch (Exception xex) {
                     }
                     try {
                     FillEndSession(false);
                     } catch (IOException ex) {
                     Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                     }
                     */
                    try {
                        WriteListofCharacter(true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                    if (mediaPlayer != null) {
                        mediaPlayer.release();
                    }
                    try {
                        BasicModel.getInstance().WriteRelationToFile("Relation.txt", Relation);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        BasicModel.getInstance().WriteRelationToFile("WeightRelation.txt", weightRelation);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    timer = null;
                    dispose();
                    System.exit(0);
                }
            }
        });
        nameCharacter.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying() == false) {
                        nameCharacter.setFocusable(true);
                    }
                }
            }
        });
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //check = false;
        //System.out.print("press false");
        //false = press start; true = pressend
        //catch character 1        
        if (mediaPlayer != null) {
            LengthOfMovie = mediaPlayer.getLength();
            if (e.getKeyCode() == 32) {
                mediaPlayer.pause();
                SetFocus();
                jButton3.setFocusable(false);
                return;
            }
            //System.out.print(e.getKeyChar());
            DefaultListModel tmpList = new DefaultListModel();
            if (ListCharacter.getModel().getSize() == 0) {
                JOptionPane.showMessageDialog(null, "Please add characters to annotate");
                return;
            }
            tmpList = (DefaultListModel) ListCharacter.getModel();
            String keypress = Character.toString(e.getKeyChar());
            String charName = "";
            //System.out.print(keypress);
            if ((e.getKeyChar() == '1') && (BasicModel.getInstance().CheckKeyPress(keypress, tmpList)) == true) {
                RePaint(charac1);
                if (keyprsd[1] == false) {
                    //System.out.println("character start " + String.valueOf(1) + "  " + String.valueOf(mediaPlayer.getTime()));
                    char1.add(mediaPlayer.getTime());
                    if (ListCharacter.getModel().getSize() > 0) {
                        charName = ListCharacter.getModel().getElementAt(1).toString() + "#" + Long.toString(mediaPlayer.getLength());
                        //System.out.print(ListCharacter.getModel().getElementAt(1).toString());
                    }
                    Pair<Integer, Integer, String> pr = Pair.createPair((int) mediaPlayer.getTime(), null, charName);
                    Actor1.add(pr);
                    pgPosVer = (int) ((mediaPlayer.getTime() * charac1.getWidth()) / LengthOfMovie);
                    CreateProcessBar(pgAuthor1, Actor1, charac1, LengthOfMovie, prefusePanel);
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(1) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[1] = true;
                    keyprsd[1] = true;
                    return;
                }
                if (keyprsd[1] == true) {
                    keyprsd[1] = false;
                    char1.add(mediaPlayer.getTime());
                    Actor1.get(Actor1.size() - 1).setElement1((int) mediaPlayer.getTime());
                    ResizeProgressBar(pgAuthor1.get(pgAuthor1.size() - 1), Actor1, LengthOfMovie, prefusePanel, "1");
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(1) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
                        String line1 = Actor1.get(Actor1.size() - 1).getElement0().toString() + "#" + Actor1.get(Actor1.size() - 1).getElement1().toString() + "#" + Actor1.get(Actor1.size() - 1).getElement2();
                        BasicModel.getInstance().WriteToTextFile("Pair.txt", line1, true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[1] = false;
                    //recalculate weigth                    
                    RefreshRelation();
                    return;
                }
            }

            if ((e.getKeyChar() == '2') && (BasicModel.getInstance().CheckKeyPress(keypress, tmpList) == true)) {
                //RePaint(charac2);
                if (keyprsd[2] == false) {
                    char2.add(mediaPlayer.getTime());
                    if (ListCharacter.getModel().getSize() > 1) {
                        charName = ListCharacter.getModel().getElementAt(2).toString() + "#" + Long.toString(mediaPlayer.getLength());
                    }
                    Pair<Integer, Integer, String> pr = Pair.createPair((int) mediaPlayer.getTime(), null, charName);
                    Actor2.add(pr);
                    pgPosVer = (int) ((mediaPlayer.getTime() * charac2.getWidth()) / LengthOfMovie);
                    CreateProcessBar(pgAuthor2, Actor2, charac2, LengthOfMovie, prefusePanel);
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(2) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[2] = true;
                    keyprsd[2] = true;
                    return;
                }
                if (keyprsd[2] == true) {
                    keyprsd[2] = false;
                    char2.add(mediaPlayer.getTime());
                    Actor2.get(Actor2.size() - 1).setElement1((int) mediaPlayer.getTime());
                    ResizeProgressBar(pgAuthor2.get(pgAuthor2.size() - 1), Actor2, LengthOfMovie, prefusePanel, "2");
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(2) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
                        String line1 = Actor2.get(Actor2.size() - 1).getElement0().toString() + "#" + Actor2.get(Actor2.size() - 1).getElement1().toString() + "#" + Actor2.get(Actor2.size() - 1).getElement2();
                        BasicModel.getInstance().WriteToTextFile("Pair.txt", line1, true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[2] = false;
                    RefreshRelation();
                    return;
                }
            }
            if ((e.getKeyChar() == '3') && (BasicModel.getInstance().CheckKeyPress(keypress, tmpList) == true)) {
                RePaint(charac3);
                if (keyprsd[3] == false) {
                    char3.add(mediaPlayer.getTime());
                    if (ListCharacter.getModel().getSize() > 2) {
                        charName = ListCharacter.getModel().getElementAt(3).toString() + "#" + Long.toString(mediaPlayer.getLength());
                    }
                    Pair<Integer, Integer, String> pr = Pair.createPair((int) mediaPlayer.getTime(), null, charName);
                    Actor3.add(pr);
                    pgPosVer = (int) ((mediaPlayer.getTime() * charac3.getWidth()) / LengthOfMovie);
                    CreateProcessBar(pgAuthor3, Actor3, charac3, LengthOfMovie, prefusePanel);
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(3) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[3] = true;
                    keyprsd[3] = true;
                    return;
                }
                if (keyprsd[3] == true) {
                    keyprsd[3] = false;
                    char3.add(mediaPlayer.getTime());
                    Actor3.get(Actor3.size() - 1).setElement1((int) mediaPlayer.getTime());
                    ResizeProgressBar(pgAuthor3.get(pgAuthor3.size() - 1), Actor3, LengthOfMovie, prefusePanel, "3");
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(3) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
                        String line1 = Actor3.get(Actor3.size() - 1).getElement0().toString() + "#" + Actor3.get(Actor3.size() - 1).getElement1().toString() + "#" + Actor3.get(Actor3.size() - 1).getElement2();
                        BasicModel.getInstance().WriteToTextFile("Pair.txt", line1, true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[3] = false;
                    RefreshRelation();
                    return;
                }
            }
            if ((e.getKeyChar() == '4') && (BasicModel.getInstance().CheckKeyPress(keypress, tmpList) == true)) {
                RePaint(charac4);
                if (keyprsd[4] == false) {
                    char4.add(mediaPlayer.getTime());
                    if (ListCharacter.getModel().getSize() > 3) {
                        charName = ListCharacter.getModel().getElementAt(4).toString() + "#" + Long.toString(mediaPlayer.getLength());
                    }
                    Pair<Integer, Integer, String> pr = Pair.createPair((int) mediaPlayer.getTime(), null, charName);
                    Actor4.add(pr);
                    pgPosVer = (int) ((mediaPlayer.getTime() * charac4.getWidth()) / LengthOfMovie);
                    CreateProcessBar(pgAuthor4, Actor4, charac4, LengthOfMovie, prefusePanel);
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(4) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[4] = true;
                    keyprsd[4] = true;
                    return;
                }
                if (keyprsd[4] == true) {
                    keyprsd[4] = false;
                    char4.add(mediaPlayer.getTime());
                    Actor4.get(Actor4.size() - 1).setElement1((int) mediaPlayer.getTime());
                    ResizeProgressBar(pgAuthor4.get(pgAuthor4.size() - 1), Actor4, LengthOfMovie, prefusePanel, "4");
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(4) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
                        String line1 = Actor4.get(Actor4.size() - 1).getElement0().toString() + "#" + Actor4.get(Actor4.size() - 1).getElement1().toString() + "#" + Actor4.get(Actor4.size() - 1).getElement2();
                        BasicModel.getInstance().WriteToTextFile("Pair.txt", line1, true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[4] = false;
                    RefreshRelation();
                    return;
                }
            }
            if ((e.getKeyChar() == '5') && (BasicModel.getInstance().CheckKeyPress(keypress, tmpList)) == true) {
                RePaint(charac5);
                if (keyprsd[5] == false) {
                    char5.add(mediaPlayer.getTime());
                    if (ListCharacter.getModel().getSize() > 4) {
                        charName = ListCharacter.getModel().getElementAt(5).toString() + "#" + Long.toString(mediaPlayer.getLength());
                    }
                    Pair<Integer, Integer, String> pr = Pair.createPair((int) mediaPlayer.getTime(), null, charName);
                    Actor5.add(pr);
                    pgPosVer = (int) ((mediaPlayer.getTime() * charac5.getWidth()) / LengthOfMovie);
                    CreateProcessBar(pgAuthor5, Actor5, charac5, LengthOfMovie, prefusePanel);
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(5) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[5] = true;
                    keyprsd[5] = true;
                    return;
                }
                if (keyprsd[5] == true) {
                    keyprsd[5] = false;
                    char5.add(mediaPlayer.getTime());
                    Actor5.get(Actor5.size() - 1).setElement1((int) mediaPlayer.getTime());
                    ResizeProgressBar(pgAuthor5.get(pgAuthor5.size() - 1), Actor5, LengthOfMovie, prefusePanel, "5");
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(5) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
                        String line1 = Actor5.get(Actor5.size() - 1).getElement0().toString() + "#" + Actor5.get(Actor5.size() - 1).getElement1().toString() + "#" + Actor5.get(Actor5.size() - 1).getElement2();
                        BasicModel.getInstance().WriteToTextFile("Pair.txt", line1, true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[5] = false;
                    RefreshRelation();
                    return;
                }
            }
            if ((e.getKeyChar() == '6') && (BasicModel.getInstance().CheckKeyPress(keypress, tmpList)) == true) {
                RePaint(charac6);
                if (keyprsd[6] == false) {
                    char6.add(mediaPlayer.getTime());
                    if (ListCharacter.getModel().getSize() > 5) {
                        charName = ListCharacter.getModel().getElementAt(6).toString() + "#" + Long.toString(mediaPlayer.getLength());
                        //System.out.print(charName);
                    }
                    Pair<Integer, Integer, String> pr = Pair.createPair((int) mediaPlayer.getTime(), null, charName);
                    Actor6.add(pr);
                    pgPosVer = (int) ((mediaPlayer.getTime() * charac6.getWidth()) / LengthOfMovie);
                    CreateProcessBar(pgAuthor6, Actor6, charac6, LengthOfMovie, prefusePanel);
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(6) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[6] = true;
                    keyprsd[6] = true;
                    return;
                }
                if (keyprsd[6] == true) {
                    keyprsd[6] = false;
                    char6.add(mediaPlayer.getTime());
                    Actor6.get(Actor6.size() - 1).setElement1((int) mediaPlayer.getTime());
                    ResizeProgressBar(pgAuthor6.get(pgAuthor6.size() - 1), Actor6, LengthOfMovie, prefusePanel, "6");
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(6) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
                        String line1 = Actor6.get(Actor6.size() - 1).getElement0().toString() + "#" + Actor6.get(Actor6.size() - 1).getElement1().toString() + "#" + Actor6.get(Actor6.size() - 1).getElement2();
                        BasicModel.getInstance().WriteToTextFile("Pair.txt", line1, true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[6] = false;
                    RefreshRelation();
                    return;
                }
            }
            if ((e.getKeyChar() == '7') && (BasicModel.getInstance().CheckKeyPress(keypress, tmpList)) == true) {
                RePaint(charac7);
                if (keyprsd[7] == false) {
                    char7.add(mediaPlayer.getTime());
                    if (ListCharacter.getModel().getSize() > 6) {
                        charName = ListCharacter.getModel().getElementAt(7).toString() + "#" + Long.toString(mediaPlayer.getLength());
                    }
                    Pair<Integer, Integer, String> pr = Pair.createPair((int) mediaPlayer.getTime(), null, charName);
                    Actor7.add(pr);
                    pgPosVer = (int) ((mediaPlayer.getTime() * charac7.getWidth()) / LengthOfMovie);
                    CreateProcessBar(pgAuthor7, Actor7, charac7, LengthOfMovie, prefusePanel);
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(7) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[7] = true;
                    keyprsd[7] = true;
                    return;
                }
                if (keyprsd[7] == true) {
                    keyprsd[7] = false;
                    char7.add(mediaPlayer.getTime());
                    Actor7.get(Actor7.size() - 1).setElement1((int) mediaPlayer.getTime());
                    ResizeProgressBar(pgAuthor7.get(pgAuthor7.size() - 1), Actor7, LengthOfMovie, prefusePanel, "7");
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(7) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
                        String line1 = Actor7.get(Actor7.size() - 1).getElement0().toString() + "#" + Actor7.get(Actor7.size() - 1).getElement1().toString() + "#" + Actor7.get(Actor7.size() - 1).getElement2();
                        BasicModel.getInstance().WriteToTextFile("Pair.txt", line1, true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[7] = false;
                    RefreshRelation();
                    return;
                }
            }
            if ((e.getKeyChar() == '8') && (BasicModel.getInstance().CheckKeyPress(keypress, tmpList)) == true) {
                RePaint(charac8);
                if (keyprsd[8] == false) {
                    char8.add(mediaPlayer.getTime());
                    if (ListCharacter.getModel().getSize() > 7) {
                        charName = ListCharacter.getModel().getElementAt(8).toString() + "#" + Long.toString(mediaPlayer.getLength());
                    }
                    Pair<Integer, Integer, String> pr = Pair.createPair((int) mediaPlayer.getTime(), null, charName);
                    Actor8.add(pr);
                    pgPosVer = (int) ((mediaPlayer.getTime() * charac8.getWidth()) / LengthOfMovie);
                    CreateProcessBar(pgAuthor8, Actor8, charac8, LengthOfMovie, prefusePanel);
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(8) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[8] = true;
                    keyprsd[8] = true;
                    return;
                }
                if (keyprsd[8] == true) {
                    keyprsd[8] = false;
                    char8.add(mediaPlayer.getTime());
                    Actor8.get(Actor8.size() - 1).setElement1((int) mediaPlayer.getTime());
                    ResizeProgressBar(pgAuthor8.get(pgAuthor8.size() - 1), Actor8, LengthOfMovie, prefusePanel, "8");
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(8) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
                        String line1 = Actor8.get(Actor8.size() - 1).getElement0().toString() + "#" + Actor8.get(Actor8.size() - 1).getElement1().toString() + "#" + Actor8.get(Actor8.size() - 1).getElement2();
                        BasicModel.getInstance().WriteToTextFile("Pair.txt", line1, true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[8] = false;
                    RefreshRelation();
                    return;
                }
            }
            if ((e.getKeyChar() == '9') && (BasicModel.getInstance().CheckKeyPress(keypress, tmpList)) == true) {
                RePaint(charac9);
                if (keyprsd[9] == false) {
                    char9.add(mediaPlayer.getTime());
                    if (ListCharacter.getModel().getSize() > 8) {
                        charName = ListCharacter.getModel().getElementAt(9).toString() + "#" + Long.toString(mediaPlayer.getLength());
                    }
                    Pair<Integer, Integer, String> pr = Pair.createPair((int) mediaPlayer.getTime(), null, charName);
                    Actor9.add(pr);
                    pgPosVer = (int) ((mediaPlayer.getTime() * charac9.getWidth()) / LengthOfMovie);
                    CreateProcessBar(pgAuthor9, Actor9, charac9, LengthOfMovie, prefusePanel);
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(9) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[9] = true;
                    keyprsd[9] = true;
                    return;
                }
                if (keyprsd[9] == true) {
                    keyprsd[9] = false;
                    char9.add(mediaPlayer.getTime());
                    Actor9.get(Actor9.size() - 1).setElement1((int) mediaPlayer.getTime());
                    ResizeProgressBar(pgAuthor9.get(pgAuthor9.size() - 1), Actor9, LengthOfMovie, prefusePanel, "9");
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(9) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
                        String line1 = Actor9.get(Actor9.size() - 1).getElement0().toString() + "#" + Actor9.get(Actor9.size() - 1).getElement1().toString() + "#" + Actor9.get(Actor9.size() - 1).getElement2();
                        BasicModel.getInstance().WriteToTextFile("Pair.txt", line1, true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[9] = false;
                    RefreshRelation();
                    return;
                }
            }
            if ((e.getKeyChar() == '0') && (BasicModel.getInstance().CheckKeyPress(keypress, tmpList)) == true) {
                RePaint(charac10);
                if (keyprsd[10] == false) {
                    char10.add(mediaPlayer.getTime());
                    if (ListCharacter.getModel().getSize() > 9) {
                        charName = ListCharacter.getModel().getElementAt(0).toString() + "#" + Long.toString(mediaPlayer.getLength());
                    }
                    Pair<Integer, Integer, String> pr = Pair.createPair((int) mediaPlayer.getTime(), null, charName);
                    Actor10.add(pr);
                    pgPosVer = (int) ((mediaPlayer.getTime() * charac10.getWidth()) / LengthOfMovie);
                    CreateProcessBar(pgAuthor10, Actor10, charac10, LengthOfMovie, prefusePanel);
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(10) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[10] = true;
                    keyprsd[10] = true;
                    return;
                }
                if (keyprsd[10] == true) {
                    keyprsd[10] = false;
                    char10.add(mediaPlayer.getTime());
                    Actor10.get(Actor10.size() - 1).setElement1((int) mediaPlayer.getTime());
                    ResizeProgressBar(pgAuthor10.get(pgAuthor10.size() - 1), Actor10, LengthOfMovie, prefusePanel, "10");
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(10) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
                        String line1 = Actor10.get(Actor10.size() - 1).getElement0().toString() + "#" + Actor10.get(Actor10.size() - 1).getElement1().toString() + "#" + Actor10.get(Actor10.size() - 1).getElement2();
                        BasicModel.getInstance().WriteToTextFile("Pair.txt", line1, true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[10] = false;
                    RefreshRelation();
                    return;
                }
            }
            if ((e.getKeyChar() == 'a' | (e.getKeyChar() == 'A')) && ((BasicModel.getInstance().CheckKeyPress(keypress.toLowerCase(), tmpList)) == true)) {
                RePaint(charac11);
                if (keyprsd[11] == false) {
                    char11.add(mediaPlayer.getTime());
                    if (ListCharacter.getModel().getSize() > 10) {
                        charName = ListCharacter.getModel().getElementAt(10).toString() + "#" + Long.toString(mediaPlayer.getLength());
                    }
                    Pair<Integer, Integer, String> pr = Pair.createPair((int) mediaPlayer.getTime(), null, charName);
                    Actor11.add(pr);
                    charac11.revalidate();
                    charac11.repaint();
                    pgPosVer = (int) ((mediaPlayer.getTime() * charac11.getWidth()) / LengthOfMovie);
                    CreateProcessBar(pgAuthor11, Actor11, charac11, LengthOfMovie, prefusePanel);
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(11) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[11] = true;
                    keyprsd[11] = true;
                    return;
                }
                if (keyprsd[11] == true) {
                    keyprsd[11] = false;
                    char11.add(mediaPlayer.getTime());
                    Actor11.get(Actor11.size() - 1).setElement1((int) mediaPlayer.getTime());
                    ResizeProgressBar(pgAuthor11.get(pgAuthor11.size() - 1), Actor11, LengthOfMovie, prefusePanel, "11");
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(11) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
                        String line1 = Actor11.get(Actor11.size() - 1).getElement0().toString() + "#" + Actor11.get(Actor11.size() - 1).getElement1().toString() + "#" + Actor11.get(Actor11.size() - 1).getElement2();
                        BasicModel.getInstance().WriteToTextFile("Pair.txt", line1, true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[11] = false;
                    RefreshRelation();
                    return;
                }
            }
            if (((e.getKeyChar() == 'b') | (e.getKeyChar() == 'B')) && ((BasicModel.getInstance().CheckKeyPress(keypress.toLowerCase(), tmpList)) == true)) {
                RePaint(charac12);
                if (keyprsd[12] == false) {
                    char12.add(mediaPlayer.getTime());
                    if (ListCharacter.getModel().getSize() > 11) {
                        charName = ListCharacter.getModel().getElementAt(11).toString() + "#" + Long.toString(mediaPlayer.getLength());
                    }
                    Pair<Integer, Integer, String> pr = Pair.createPair((int) mediaPlayer.getTime(), null, charName);
                    Actor12.add(pr);
                    pgPosVer = (int) ((mediaPlayer.getTime() * charac12.getWidth()) / LengthOfMovie);
                    CreateProcessBar(pgAuthor12, Actor12, charac12, LengthOfMovie, prefusePanel);
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(12) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[12] = true;
                    keyprsd[12] = true;
                    return;
                }
                if (keyprsd[12] == true) {
                    keyprsd[12] = false;
                    char12.add(mediaPlayer.getTime());
                    Actor12.get(Actor12.size() - 1).setElement1((int) mediaPlayer.getTime());
                    ResizeProgressBar(pgAuthor12.get(pgAuthor12.size() - 1), Actor12, LengthOfMovie, prefusePanel, "12");
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(12) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
                        String line1 = Actor12.get(Actor12.size() - 1).getElement0().toString() + "#" + Actor12.get(Actor12.size() - 1).getElement1().toString() + "#" + Actor12.get(Actor12.size() - 1).getElement2();
                        BasicModel.getInstance().WriteToTextFile("Pair.txt", line1, true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[12] = false;
                    RefreshRelation();
                    return;
                }
            }
            if (((e.getKeyChar() == 'c') | (e.getKeyChar() == 'C')) && ((BasicModel.getInstance().CheckKeyPress(keypress.toLowerCase(), tmpList)) == true)) {
                RePaint(charac13);
                if (keyprsd[13] == false) {
                    char13.add(mediaPlayer.getTime());
                    if (ListCharacter.getModel().getSize() > 12) {
                        charName = ListCharacter.getModel().getElementAt(12).toString() + "#" + Long.toString(mediaPlayer.getLength());
                    }
                    Pair<Integer, Integer, String> pr = Pair.createPair((int) mediaPlayer.getTime(), null, charName);
                    Actor13.add(pr);
                    pgPosVer = (int) ((mediaPlayer.getTime() * charac13.getWidth()) / LengthOfMovie);
                    CreateProcessBar(pgAuthor13, Actor13, charac13, LengthOfMovie, prefusePanel);
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(13) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[13] = true;
                    keyprsd[13] = true;
                    return;
                }
                if (keyprsd[13] == true) {
                    keyprsd[13] = false;
                    char13.add(mediaPlayer.getTime());
                    Actor13.get(Actor13.size() - 1).setElement1((int) mediaPlayer.getTime());
                    ResizeProgressBar(pgAuthor13.get(pgAuthor13.size() - 1), Actor13, LengthOfMovie, prefusePanel, "13");
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(13) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
                        String line1 = Actor13.get(Actor13.size() - 1).getElement0().toString() + "#" + Actor13.get(Actor13.size() - 1).getElement1().toString() + "#" + Actor13.get(Actor13.size() - 1).getElement2();
                        BasicModel.getInstance().WriteToTextFile("Pair.txt", line1, true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[13] = false;
                    RefreshRelation();
                    return;
                }
            }
            if (((e.getKeyChar() == 'd') | (e.getKeyChar() == 'D')) && ((BasicModel.getInstance().CheckKeyPress(keypress.toLowerCase(), tmpList)) == true)) {
                RePaint(charac14);
                if (keyprsd[14] == false) {
                    char14.add(mediaPlayer.getTime());
                    if (ListCharacter.getModel().getSize() > 13) {
                        charName = ListCharacter.getModel().getElementAt(13).toString() + "#" + Long.toString(mediaPlayer.getLength());
                    }
                    Pair<Integer, Integer, String> pr = Pair.createPair((int) mediaPlayer.getTime(), null, charName);
                    Actor14.add(pr);
                    pgPosVer = (int) ((mediaPlayer.getTime() * charac14.getWidth()) / LengthOfMovie);
                    CreateProcessBar(pgAuthor14, Actor14, charac14, LengthOfMovie, prefusePanel);
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(14) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[14] = true;
                    keyprsd[14] = true;
                    return;
                }
                if (keyprsd[14] == true) {
                    keyprsd[14] = false;
                    char14.add(mediaPlayer.getTime());
                    Actor14.get(Actor14.size() - 1).setElement1((int) mediaPlayer.getTime());
                    ResizeProgressBar(pgAuthor14.get(pgAuthor14.size() - 1), Actor14, LengthOfMovie, prefusePanel, "14");
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(14) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
                        String line1 = Actor14.get(Actor14.size() - 1).getElement0().toString() + "#" + Actor14.get(Actor14.size() - 1).getElement1().toString() + "#" + Actor14.get(Actor14.size() - 1).getElement2();
                        BasicModel.getInstance().WriteToTextFile("Pair.txt", line1, true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[14] = false;
                    RefreshRelation();
                    return;
                }
            }
            if (((e.getKeyChar() == 'e') | (e.getKeyChar() == 'E')) && ((BasicModel.getInstance().CheckKeyPress(keypress.toLowerCase(), tmpList)) == true)) {
                RePaint(charac15);
                if (keyprsd[15] == false) {
                    char15.add(mediaPlayer.getTime());
                    if (ListCharacter.getModel().getSize() > 14) {
                        charName = ListCharacter.getModel().getElementAt(14).toString() + "#" + Long.toString(mediaPlayer.getLength());
                    }
                    Pair<Integer, Integer, String> pr = Pair.createPair((int) mediaPlayer.getTime(), null, charName);
                    Actor15.add(pr);
                    pgPosVer = (int) ((mediaPlayer.getTime() * charac15.getWidth()) / LengthOfMovie);
                    CreateProcessBar(pgAuthor15, Actor15, charac15, LengthOfMovie, prefusePanel);
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(15) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[15] = true;
                    keyprsd[15] = true;
                    return;
                }
                if (keyprsd[15] == true) {
                    keyprsd[15] = false;
                    char15.add(mediaPlayer.getTime());
                    Actor15.get(Actor15.size() - 1).setElement1((int) mediaPlayer.getTime());
                    ResizeProgressBar(pgAuthor15.get(pgAuthor15.size() - 1), Actor15, LengthOfMovie, prefusePanel, "15");
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(15) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
                        String line1 = Actor15.get(Actor15.size() - 1).getElement0().toString() + "#" + Actor15.get(Actor15.size() - 1).getElement1().toString() + "#" + Actor15.get(Actor15.size() - 1).getElement2();
                        BasicModel.getInstance().WriteToTextFile("Pair.txt", line1, true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[15] = false;
                    RefreshRelation();
                    return;
                }
            }
            if (((e.getKeyChar() == 'f') | (e.getKeyChar() == 'F')) && ((BasicModel.getInstance().CheckKeyPress(keypress.toLowerCase(), tmpList)) == true)) {
                RePaint(charac16);
                if (keyprsd[16] == false) {
                    char16.add(mediaPlayer.getTime());
                    if (ListCharacter.getModel().getSize() > 15) {
                        charName = ListCharacter.getModel().getElementAt(15).toString() + "#" + Long.toString(mediaPlayer.getLength());
                    }
                    Pair<Integer, Integer, String> pr = Pair.createPair((int) mediaPlayer.getTime(), null, charName);
                    Actor16.add(pr);
                    pgPosVer = (int) ((mediaPlayer.getTime() * charac16.getWidth()) / LengthOfMovie);
                    CreateProcessBar(pgAuthor16, Actor16, charac16, LengthOfMovie, prefusePanel);
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(16) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[16] = true;
                    keyprsd[16] = true;
                    return;
                }
                if (keyprsd[16] == true) {
                    keyprsd[16] = false;
                    char16.add(mediaPlayer.getTime());
                    Actor16.get(Actor16.size() - 1).setElement1((int) mediaPlayer.getTime());
                    ResizeProgressBar(pgAuthor16.get(pgAuthor16.size() - 1), Actor16, LengthOfMovie, prefusePanel, "16");
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(16) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
                        String line1 = Actor16.get(Actor16.size() - 1).getElement0().toString() + "#" + Actor16.get(Actor16.size() - 1).getElement1().toString() + "#" + Actor16.get(Actor16.size() - 1).getElement2();
                        BasicModel.getInstance().WriteToTextFile("Pair.txt", line1, true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[16] = false;
                    RefreshRelation();
                    return;
                }
            }
            if (((e.getKeyChar() == 'g') | (e.getKeyChar() == 'G')) && ((BasicModel.getInstance().CheckKeyPress(keypress.toLowerCase(), tmpList)) == true)) {
                RePaint(charac17);
                if (keyprsd[17] == false) {
                    char17.add(mediaPlayer.getTime());
                    if (ListCharacter.getModel().getSize() > 16) {
                        charName = ListCharacter.getModel().getElementAt(16).toString() + "#" + Long.toString(mediaPlayer.getLength());
                    }
                    Pair<Integer, Integer, String> pr = Pair.createPair((int) mediaPlayer.getTime(), null, charName);
                    Actor17.add(pr);
                    pgPosVer = (int) ((mediaPlayer.getTime() * charac16.getWidth()) / LengthOfMovie);
                    CreateProcessBar(pgAuthor17, Actor17, charac17, LengthOfMovie, prefusePanel);
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(17) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[17] = true;
                    keyprsd[17] = true;
                    return;
                }
                if (keyprsd[17] == true) {
                    keyprsd[17] = false;
                    char17.add(mediaPlayer.getTime());
                    Actor17.get(Actor17.size() - 1).setElement1((int) mediaPlayer.getTime());
                    ResizeProgressBar(pgAuthor17.get(pgAuthor17.size() - 1), Actor17, LengthOfMovie, prefusePanel, "17");
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(17) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
                        String line1 = Actor17.get(Actor17.size() - 1).getElement0().toString() + "#" + Actor17.get(Actor17.size() - 1).getElement1().toString() + "#" + Actor17.get(Actor17.size() - 1).getElement2();
                        BasicModel.getInstance().WriteToTextFile("Pair.txt", line1, true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[17] = false;
                    RefreshRelation();
                    return;
                }
            }
            if (((e.getKeyChar() == 'h') | (e.getKeyChar() == 'H')) && ((BasicModel.getInstance().CheckKeyPress(keypress.toLowerCase(), tmpList)) == true)) {
                RePaint(charac18);
                if (keyprsd[18] == false) {
                    char18.add(mediaPlayer.getTime());
                    if (ListCharacter.getModel().getSize() > 17) {
                        charName = ListCharacter.getModel().getElementAt(17).toString() + "#" + Long.toString(mediaPlayer.getLength());
                    }
                    Pair<Integer, Integer, String> pr = Pair.createPair((int) mediaPlayer.getTime(), null, charName);
                    Actor18.add(pr);
                    pgPosVer = (int) ((mediaPlayer.getTime() * charac18.getWidth()) / LengthOfMovie);
                    CreateProcessBar(pgAuthor18, Actor18, charac18, LengthOfMovie, prefusePanel);
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(18) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[18] = true;
                    keyprsd[18] = true;
                    return;
                }
                if (keyprsd[18] == true) {
                    keyprsd[18] = false;
                    char18.add(mediaPlayer.getTime());
                    Actor18.get(Actor18.size() - 1).setElement1((int) mediaPlayer.getTime());
                    ResizeProgressBar(pgAuthor18.get(pgAuthor18.size() - 1), Actor18, LengthOfMovie, prefusePanel, "18");
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(18) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
                        String line1 = Actor18.get(Actor18.size() - 1).getElement0().toString() + "#" + Actor18.get(Actor18.size() - 1).getElement1().toString() + "#" + Actor18.get(Actor18.size() - 1).getElement2();
                        BasicModel.getInstance().WriteToTextFile("Pair.txt", line1, true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[18] = false;
                    RefreshRelation();
                    return;
                }
            }
            if (((e.getKeyChar() == 'i') | (e.getKeyChar() == 'I')) && ((BasicModel.getInstance().CheckKeyPress(keypress.toLowerCase(), tmpList)) == true)) {
                RePaint(charac19);
                if (keyprsd[19] == false) {
                    char19.add(mediaPlayer.getTime());
                    if (ListCharacter.getModel().getSize() > 18) {
                        charName = ListCharacter.getModel().getElementAt(18).toString() + "#" + Long.toString(mediaPlayer.getLength());
                    }
                    Pair<Integer, Integer, String> pr = Pair.createPair((int) mediaPlayer.getTime(), null, charName);
                    Actor19.add(pr);
                    pgPosVer = (int) ((mediaPlayer.getTime() * charac19.getWidth()) / LengthOfMovie);
                    CreateProcessBar(pgAuthor19, Actor19, charac19, LengthOfMovie, prefusePanel);
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(19) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[19] = true;
                    keyprsd[19] = true;
                    return;
                }
                if (keyprsd[19] == true) {
                    keyprsd[19] = false;
                    char19.add(mediaPlayer.getTime());
                    Actor19.get(Actor19.size() - 1).setElement1((int) mediaPlayer.getTime());
                    ResizeProgressBar(pgAuthor19.get(pgAuthor19.size() - 1), Actor19, LengthOfMovie, prefusePanel, "19");
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(19) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
                        String line1 = Actor19.get(Actor19.size() - 1).getElement0().toString() + "#" + Actor19.get(Actor19.size() - 1).getElement1().toString() + "#" + Actor19.get(Actor19.size() - 1).getElement2();
                        BasicModel.getInstance().WriteToTextFile("Pair.txt", line1, true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[19] = false;
                    RefreshRelation();
                    return;
                }
            }
            if (((e.getKeyChar() == 'j') | (e.getKeyChar() == 'J')) && ((BasicModel.getInstance().CheckKeyPress(keypress.toLowerCase(), tmpList)) == true)) {
                RePaint(charac20);
                if (keyprsd[20] == false) {
                    char20.add(mediaPlayer.getTime());
                    if (ListCharacter.getModel().getSize() >= 19) {
                        charName = ListCharacter.getModel().getElementAt(19).toString() + "#" + Long.toString(mediaPlayer.getLength());
                    }
                    Pair<Integer, Integer, String> pr = Pair.createPair((int) mediaPlayer.getTime(), null, charName);
                    Actor20.add(pr);
                    pgPosVer = (int) ((mediaPlayer.getTime() * charac20.getWidth()) / LengthOfMovie);
                    CreateProcessBar(pgAuthor20, Actor20, charac20, LengthOfMovie, prefusePanel);
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(20) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[20] = true;
                    keyprsd[20] = true;
                    return;
                }
                if (keyprsd[20] == true) {
                    keyprsd[20] = false;
                    char20.add(mediaPlayer.getTime());
                    Actor20.get(Actor20.size() - 1).setElement1((int) mediaPlayer.getTime());
                    ResizeProgressBar(pgAuthor20.get(pgAuthor20.size() - 1), Actor20, LengthOfMovie, prefusePanel, "20");
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(20) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
                        String line1 = Actor20.get(Actor20.size() - 1).getElement0().toString() + "#" + Actor20.get(Actor20.size() - 1).getElement1().toString() + "#" + Actor20.get(Actor20.size() - 1).getElement2();
                        BasicModel.getInstance().WriteToTextFile("Pair.txt", line1, true);
                    } catch (IOException ex) {
                        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    appeeared[20] = false;
                    RefreshRelation();
                    return;
                }
            }
            if ((e.getKeyCode() == 127)) {
                if (txtCondition != "") {
                    System.out.print(txtCondition);
                }

            }
            /*
             if ((e.getKeyChar() == 'a') | (e.getKeyChar() == 'A')) {
             if (keyprsd[21] == false) {
             //System.out.println("character start " + String.valueOf(0) + "  " + String.valueOf(mediaPlayer.getTime()));
             try {
             BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(21) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
             char21.add(mediaPlayer.getTime());
             } catch (IOException ex) {
             Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
             }
             appeeared[21] = true;
             keyprsd[21] = true;
             return;
             }
             if (keyprsd[21] == true) {
             keyprsd[21] = false;
             //System.out.println("character end " + String.valueOf(0) + "  " + String.valueOf(mediaPlayer.getTime()));
             try {
             BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(21) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
             char21.add(mediaPlayer.getTime());
             } catch (IOException ex) {
             Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
             }
             appeeared[21] = false;
             return;
             }
             }
             if ((e.getKeyChar() == 'S') | (e.getKeyChar() == 's')) {
             if (keyprsd[22] == false) {
             //System.out.println("character start " + String.valueOf(0) + "  " + String.valueOf(mediaPlayer.getTime()));
             try {
             BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(22) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
             char22.add(mediaPlayer.getTime());
             } catch (IOException ex) {
             Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
             }
             appeeared[22] = true;
             keyprsd[22] = true;
             return;
             }
             if (keyprsd[22] == true) {
             keyprsd[22] = false;
             //System.out.println("character end " + String.valueOf(0) + "  " + String.valueOf(mediaPlayer.getTime()));
             try {
             BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(22) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
             char22.add(mediaPlayer.getTime());
             } catch (IOException ex) {
             Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
             }
             appeeared[22] = false;
             return;
             }
             }
             if ((e.getKeyChar() == 'd') | (e.getKeyChar() == 'D')) {
             if (keyprsd[23] == false) {
             //System.out.println("character start " + String.valueOf(0) + "  " + String.valueOf(mediaPlayer.getTime()));
             try {
             BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(23) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
             char23.add(mediaPlayer.getTime());
             } catch (IOException ex) {
             Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
             }
             appeeared[23] = true;
             keyprsd[23] = true;
             return;
             }
             if (keyprsd[23] == true) {
             keyprsd[23] = false;
             //System.out.println("character end " + String.valueOf(0) + "  " + String.valueOf(mediaPlayer.getTime()));
             try {
             BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(23) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
             char23.add(mediaPlayer.getTime());
             } catch (IOException ex) {
             Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
             }
             appeeared[23] = false;
             return;
             }
             }
             if ((e.getKeyChar() == 'f') | (e.getKeyChar() == 'F')) {
             if (keyprsd[24] == false) {
             //System.out.println("character start " + String.valueOf(0) + "  " + String.valueOf(mediaPlayer.getTime()));
             try {
             BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(24) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
             char24.add(mediaPlayer.getTime());
             } catch (IOException ex) {
             Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
             }
             appeeared[24] = true;
             keyprsd[24] = true;
             return;
             }
             if (keyprsd[24] == true) {
             keyprsd[24] = false;
             //System.out.println("character end " + String.valueOf(0) + "  " + String.valueOf(mediaPlayer.getTime()));
             try {
             BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(24) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
             char24.add(mediaPlayer.getTime());
             } catch (IOException ex) {
             Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
             }
             appeeared[24] = false;
             return;
             }
             }
             if ((e.getKeyChar() == 'g') | (e.getKeyChar() == 'G')) {
             if (keyprsd[25] == false) {
             //System.out.println("character start " + String.valueOf(0) + "  " + String.valueOf(mediaPlayer.getTime()));
             try {
             BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(25) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
             char25.add(mediaPlayer.getTime());
             } catch (IOException ex) {
             Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
             }
             appeeared[25] = true;
             keyprsd[25] = true;
             return;
             }
             if (keyprsd[25] == true) {
             keyprsd[25] = false;
             //System.out.println("character end " + String.valueOf(0) + "  " + String.valueOf(mediaPlayer.getTime()));
             try {
             BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(25) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
             char25.add(mediaPlayer.getTime());
             } catch (IOException ex) {
             Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
             }
             appeeared[25] = false;
             return;
             }
             }
             if ((e.getKeyChar() == 'h') | (e.getKeyChar() == 'H')) {
             if (keyprsd[26] == false) {
             //System.out.println("character start " + String.valueOf(0) + "  " + String.valueOf(mediaPlayer.getTime()));
             try {
             BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(26) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
             char26.add(mediaPlayer.getTime());
             } catch (IOException ex) {
             Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
             }
             appeeared[26] = true;
             keyprsd[26] = true;
             return;
             }
             if (keyprsd[26] == true) {
             keyprsd[26] = false;
             //System.out.println("character end " + String.valueOf(0) + "  " + String.valueOf(mediaPlayer.getTime()));
             try {
             BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(26) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
             char26.add(mediaPlayer.getTime());
             } catch (IOException ex) {
             Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
             }
             appeeared[26] = false;
             return;
             }
             }
             if ((e.getKeyChar() == 'j') | (e.getKeyChar() == 'J')) {
             if (keyprsd[27] == false) {
             //System.out.println("character start " + String.valueOf(0) + "  " + String.valueOf(mediaPlayer.getTime()));
             try {
             BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(27) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
             char27.add(mediaPlayer.getTime());
             } catch (IOException ex) {
             Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
             }
             appeeared[27] = true;
             keyprsd[27] = true;
             return;
             }
             if (keyprsd[27] == true) {
             keyprsd[27] = false;
             //System.out.println("character end " + String.valueOf(0) + "  " + String.valueOf(mediaPlayer.getTime()));
             try {
             BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(27) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
             char27.add(mediaPlayer.getTime());
             } catch (IOException ex) {
             Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
             }
             appeeared[27] = false;
             return;
             }
             }
             if ((e.getKeyChar() == 'k') | (e.getKeyChar() == 'K')) {
             if (keyprsd[28] == false) {
             //System.out.println("character start " + String.valueOf(0) + "  " + String.valueOf(mediaPlayer.getTime()));
             try {
             BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(28) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
             char28.add(mediaPlayer.getTime());
             } catch (IOException ex) {
             Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
             }
             appeeared[28] = true;
             keyprsd[28] = true;
             return;
             }
             if (keyprsd[28] == true) {
             keyprsd[28] = false;
             //System.out.println("character end " + String.valueOf(0) + "  " + String.valueOf(mediaPlayer.getTime()));
             try {
             BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(28) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
             char28.add(mediaPlayer.getTime());
             } catch (IOException ex) {
             Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
             }
             appeeared[28] = false;
             return;
             }
             }
             if ((e.getKeyChar() == 'l') | (e.getKeyChar() == 'L')) {
             if (keyprsd[29] == false) {
             //System.out.println("character start " + String.valueOf(0) + "  " + String.valueOf(mediaPlayer.getTime()));
             try {
             BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(29) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
             char29.add(mediaPlayer.getTime());
             } catch (IOException ex) {
             Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
             }
             appeeared[29] = true;
             keyprsd[29] = true;
             return;
             }
             if (keyprsd[29] == true) {
             keyprsd[29] = false;
             //System.out.println("character end " + String.valueOf(0) + "  " + String.valueOf(mediaPlayer.getTime()));
             try {
             BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(29) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
             char29.add(mediaPlayer.getTime());
             } catch (IOException ex) {
             Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
             }
             appeeared[29] = false;
             return;
             }
             }
             if ((e.getKeyChar() == 'z') | (e.getKeyChar() == 'Z')) {
             if (keyprsd[30] == false) {
             //System.out.println("character start " + String.valueOf(0) + "  " + String.valueOf(mediaPlayer.getTime()));
             try {
             BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(30) + "%Start%" + String.valueOf(mediaPlayer.getTime()), true);
             char30.add(mediaPlayer.getTime());
             } catch (IOException ex) {
             Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
             }
             appeeared[30] = true;
             keyprsd[30] = true;
             return;
             }
             if (keyprsd[30] == true) {
             keyprsd[30] = false;
             //System.out.println("character end " + String.valueOf(0) + "  " + String.valueOf(mediaPlayer.getTime()));
             try {
             BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(30) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);
             char30.add(mediaPlayer.getTime());
             } catch (IOException ex) {
             Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
             }
             appeeared[30] = false;
             return;
             }
             }
             */
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //label.setText("Key Typed: " + e.getKeyChar());

    }

    private void SetFocus() {
        //Keep focus on frame
        AddCharacter.setFocusable(false);
        ListCharacter.setFocusable(false);
        ListApp.setFocusable(false);
        jPanel1.setFocusable(false);
        jPanel2.setFocusable(false);
        jButton1.setFocusable(false);
        prefusePanel.setFocusable(false);
        pause.setFocusable(false);
        stop.setFocusable(false);
        panelPlay.setFocusable(false);
        positionSlider.setFocusable(false);
        namecharaterAppeared.setFocusable(false);
        jButton3.setFocusable(false);
        jLabel1.setFocusable(false);
        jLabel2.setFocusable(false);
        jScrollPane1.setFocusable(false);
        prefusePanel.setFocusable(false);
        AddCharacter.setFocusable(false);
        charac1.setFocusable(false);
        charac2.setFocusable(false);
        charac3.setFocusable(false);
        charac4.setFocusable(false);
        charac5.setFocusable(false);
        charac6.setFocusable(false);
        charac7.setFocusable(false);
        charac8.setFocusable(false);
        charac9.setFocusable(false);
        charac10.setFocusable(false);
        charac11.setFocusable(false);
        charac12.setFocusable(false);
        charac13.setFocusable(false);
        charac14.setFocusable(false);
        charac15.setFocusable(false);
        charac16.setFocusable(false);
        charac17.setFocusable(false);
        charac18.setFocusable(false);
        charac19.setFocusable(false);
        charac20.setFocusable(false);
        LoadData.setFocusable(false);
        //c.setFocusable(false);
    }

    private void WriteListofCharacter(boolean condt) throws IOException {
        if (ListCharacter.getModel().getSize() != 0) {
            for (int i = 0; i < ListCharacter.getModel().getSize(); i++) {
                BasicModel.getInstance().WriteToTextFile("character.txt", (String) ListCharacter.getModel().getElementAt(i), condt);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelPlay = new javax.swing.JPanel();
        pause = new javax.swing.JButton();
        stop = new javax.swing.JButton();
        positionSlider = new javax.swing.JSlider();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        label = new javax.swing.JLabel();
        LoadData = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        listCharacter = new javax.swing.JScrollPane();
        ListCharacter = new javax.swing.JList();
        AddCharacter = new javax.swing.JButton();
        nameCharacter = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        namecharaterAppeared = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ListApp = new javax.swing.JList();
        jPanel3 = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        layeredlayout = new javax.swing.JLabel();
        scrollPane1 = new java.awt.ScrollPane();
        prefusePanel = new javax.swing.JPanel();
        charac1 = new javax.swing.JLabel();
        charac2 = new javax.swing.JLabel();
        charac3 = new javax.swing.JLabel();
        charac4 = new javax.swing.JLabel();
        charac5 = new javax.swing.JLabel();
        charac6 = new javax.swing.JLabel();
        charac7 = new javax.swing.JLabel();
        charac8 = new javax.swing.JLabel();
        charac9 = new javax.swing.JLabel();
        charac10 = new javax.swing.JLabel();
        charac11 = new javax.swing.JLabel();
        charac12 = new javax.swing.JLabel();
        charac13 = new javax.swing.JLabel();
        charac14 = new javax.swing.JLabel();
        charac15 = new javax.swing.JLabel();
        charac16 = new javax.swing.JLabel();
        charac17 = new javax.swing.JLabel();
        charac18 = new javax.swing.JLabel();
        charac19 = new javax.swing.JLabel();
        charac20 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Movie Annotation System");
        setResizable(false);

        jPanel1.setBorder(new javax.swing.border.MatteBorder(null));

        panelPlay.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panelPlayLayout = new javax.swing.GroupLayout(panelPlay);
        panelPlay.setLayout(panelPlayLayout);
        panelPlayLayout.setHorizontalGroup(
            panelPlayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelPlayLayout.setVerticalGroup(
            panelPlayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 441, Short.MAX_VALUE)
        );

        pause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movieannot/Process/pause.png"))); // NOI18N
        pause.setToolTipText("Pause/Play [BackSpace]");
        pause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseActionPerformed(evt);
            }
        });

        stop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movieannot/Process/stop.png"))); // NOI18N
        stop.setToolTipText("Stop");
        stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopActionPerformed(evt);
            }
        });

        positionSlider.setForeground(new java.awt.Color(153, 153, 153));
        positionSlider.setMaximum(1000);
        positionSlider.setToolTipText("\"Position\"");
        positionSlider.setValue(0);
        positionSlider.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movieannot/Process/Eject.jpg"))); // NOI18N
        jButton1.setToolTipText("Open Movie");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 153));
        jLabel2.setText("Press (#) key to annotate. Press it again to end annotation session!");

        jLabel1.setText("Current key(s) are catching:");

        LoadData.setText("Load");
        LoadData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoadDataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelPlay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(pause)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stop)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(positionSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(label, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LoadData)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(4, 4, 4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(LoadData)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(panelPlay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(pause)
                        .addComponent(stop))
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(positionSlider, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        ListCharacter.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        listCharacter.setViewportView(ListCharacter);

        AddCharacter.setText("Add");
        AddCharacter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddCharacterActionPerformed(evt);
            }
        });

        nameCharacter.setText("Anonymous");
        nameCharacter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameCharacterActionPerformed(evt);
            }
        });

        jButton3.setText("Remove");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        namecharaterAppeared.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        namecharaterAppeared.setForeground(new java.awt.Color(0, 0, 153));
        namecharaterAppeared.setText("Character(s) appearing");
        namecharaterAppeared.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jScrollPane1.setBorder(null);
        jScrollPane1.setForeground(new java.awt.Color(255, 51, 102));
        jScrollPane1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        ListApp.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ListApp.setForeground(new java.awt.Color(255, 0, 102));
        jScrollPane1.setViewportView(ListApp);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameCharacter)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(AddCharacter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3))
                    .addComponent(listCharacter, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(namecharaterAppeared, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nameCharacter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddCharacter)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listCharacter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(namecharaterAppeared, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setToolTipText("AnNet");
        jPanel3.setPreferredSize(new java.awt.Dimension(500, 500));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 496, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        layeredlayout.setForeground(new java.awt.Color(0, 51, 153));
        layeredlayout.setText("jLabel3");

        prefusePanel.setPreferredSize(new java.awt.Dimension(670, 500));

        charac1.setBackground(new java.awt.Color(153, 153, 153));
        charac1.setForeground(new java.awt.Color(153, 153, 153));

        charac2.setForeground(new java.awt.Color(153, 153, 153));

        charac3.setForeground(new java.awt.Color(153, 153, 153));

        charac4.setForeground(new java.awt.Color(153, 153, 153));

        charac5.setForeground(new java.awt.Color(153, 153, 153));

        charac6.setForeground(new java.awt.Color(153, 153, 153));

        charac7.setForeground(new java.awt.Color(153, 153, 153));

        charac8.setForeground(new java.awt.Color(153, 153, 153));

        charac9.setForeground(new java.awt.Color(153, 153, 153));

        charac10.setForeground(new java.awt.Color(153, 153, 153));

        charac11.setBackground(new java.awt.Color(153, 153, 153));
        charac11.setForeground(new java.awt.Color(153, 153, 153));

        charac12.setForeground(new java.awt.Color(153, 153, 153));

        charac13.setForeground(new java.awt.Color(153, 153, 153));

        charac14.setForeground(new java.awt.Color(153, 153, 153));

        charac15.setForeground(new java.awt.Color(153, 153, 153));

        charac16.setForeground(new java.awt.Color(153, 153, 153));

        charac17.setForeground(new java.awt.Color(153, 153, 153));

        charac18.setForeground(new java.awt.Color(153, 153, 153));

        charac19.setForeground(new java.awt.Color(153, 153, 153));

        charac20.setForeground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout prefusePanelLayout = new javax.swing.GroupLayout(prefusePanel);
        prefusePanel.setLayout(prefusePanelLayout);
        prefusePanelLayout.setHorizontalGroup(
            prefusePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(prefusePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(prefusePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(prefusePanelLayout.createSequentialGroup()
                        .addGroup(prefusePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(charac10, javax.swing.GroupLayout.DEFAULT_SIZE, 1375, Short.MAX_VALUE)
                            .addComponent(charac9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(charac4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(charac5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(charac6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(charac7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(charac8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(charac11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(charac12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(charac13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(charac20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(charac19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(charac14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(charac15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(charac16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(charac17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(charac18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(19, 19, 19))
                    .addGroup(prefusePanelLayout.createSequentialGroup()
                        .addGroup(prefusePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(charac3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1347, Short.MAX_VALUE)
                            .addComponent(charac2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(charac1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        prefusePanelLayout.setVerticalGroup(
            prefusePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(prefusePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(charac1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(charac2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(charac3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(charac4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(charac5, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(charac6, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(charac7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(charac8, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(charac9, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(charac10, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(charac11, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(charac12, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(charac13, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(charac14, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(charac15, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(charac16, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(charac17, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(charac18, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(charac19, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(charac20, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        scrollPane1.add(prefusePanel);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(layeredlayout, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1410, Short.MAX_VALUE)))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(layeredlayout, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jLayeredPane1.setLayer(layeredlayout, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(scrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:        
        JFileChooser fc = new JFileChooser();
        int status = fc.showOpenDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            File selectedFile = fc.getSelectedFile();
            filename = selectedFile.getAbsolutePath();
            if (mediaPlayerFactory == null) {
                mediaPlayerFactory = new MediaPlayerFactory();
            }
            mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
            if (c == null) {
                c = new Canvas();
                c.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        mediaPlayer.setEnableMouseInputHandling(false);
                        mediaPlayer.setEnableKeyInputHandling(false);
                        mediaPlayer.pause();
                        SetFocus();
                        //nameCharacter.setFocusable(false);
                        //gui.setFocusable(true);
                    }
                });
            }
            c.setBounds(0, 0, panelPlay.getWidth(), panelPlay.getHeight());
            c.setBackground(Color.black);
            c.invalidate();
            c.validate();
            c.repaint();
            panelPlay.setLayout(new BorderLayout());
            panelPlay.add(c, BorderLayout.CENTER);
            mediaPlayer.setVideoSurface(mediaPlayerFactory.newVideoSurface(c));
            mediaPlayer.playMedia(filename);//"file:///E:/3.flv");                        
            lockCharacter = ListCharacter.getModel().getSize();
            LengthOfMovie = mediaPlayer.getLength();
            runTimerSlider();
            positionSlider.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    setSliderBasedPosition();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    setSliderBasedPosition();
                }
            });
            positionSlider.validate();
            positionSlider.repaint();
            runTimer();

            runTimer1();
            timmerlayer.setInitialDelay(0);
            timmerlayer.start();
            SetFocus();
            panelPlay.invalidate();
            panelPlay.validate();
            panelPlay.repaint();
            if (ListCharacter.getModel().getSize() > 0) {
                lockCharacter1 = ListCharacter.getModel().getSize();
            }
            SetFocus();
            nameCharacter.setFocusable(false);
            gui.setFocusable(true);

        } else if (status == JFileChooser.CANCEL_OPTION) {
            return;
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void pauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseActionPerformed
        // TODO add your handling code here:
        if (mediaPlayer != null) {
            runTimer1();
            timmerlayer.setInitialDelay(0);
            timmerlayer.start();
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.setPause(true);
                lockCharacter = ListCharacter.getModel().getSize();
                SetFocus();
                nameCharacter.setFocusable(false);
                gui.setFocusable(true);
                return;
            } else {
                mediaPlayer.setPause(false);
                lockCharacter = ListCharacter.getModel().getSize();
                SetFocus();
                nameCharacter.setFocusable(false);
                gui.setFocusable(true);
                //timmerlayer.stop();
            }

        }

    }//GEN-LAST:event_pauseActionPerformed

    private void stopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopActionPerformed
        // TODO add your handling code here:
        if (mediaPlayer != null) {
            for (int i = 0; i < appeeared.length; i++) {
                if ((appeeared[i] == true) && (i != 0)) {
                    try {
                        BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(i) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);

                    } catch (IOException ex) {
                        Logger.getLogger(Play.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
                appeeared[i] = false;
            }
            if (appeeared[0] == true) {
                try {
                    BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(10) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);

                } catch (IOException ex) {
                    Logger.getLogger(Play.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                FillEndSession(false);

            } catch (IOException ex) {
                Logger.getLogger(Play.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            try {
                BasicModel.getInstance().WriteRelationToFile("Relation.txt", Relation);

            } catch (IOException ex) {
                Logger.getLogger(Play.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            try {
                BasicModel.getInstance().WriteRelationToFile("WeightRelation.txt", weightRelation);

            } catch (IOException ex) {
                Logger.getLogger(Play.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            mediaPlayer.stop();
            try {
                //Keep focus on frame
                WriteListofCharacter(true);

            } catch (IOException ex) {
                Logger.getLogger(Play.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        timer.stop();

        SetFocus();
    }//GEN-LAST:event_stopActionPerformed

    private void FillEndSession(boolean condi) throws IOException {
        if (Actor1.size() > 0) {
            if (Actor1.get(Actor1.size() - 1).getElement1() == null) {
                Actor1.get(Actor1.size() - 1).setElement1((int) mediaPlayer.getTime());
            }
            for (Pair<Integer, Integer, String> Act : Actor1) {
                String line = Act.getElement0().toString() + "#" + Act.getElement1().toString() + "#" + Act.getElement2().toString();
                BasicModel.getInstance().WriteToTextFile("Pair.txt", line, true);
            }
        }
        if (Actor2.size() > 0) {
            if (Actor2.get(Actor2.size() - 1).getElement1() == null) {
                Actor2.get(Actor2.size() - 1).setElement1((int) mediaPlayer.getTime());
            }
            for (Pair<Integer, Integer, String> Act : Actor2) {
                String line = Act.getElement0().toString() + "#" + Act.getElement1().toString() + "#" + Act.getElement2().toString();
                BasicModel.getInstance().WriteToTextFile("Pair.txt", line, true);
            }
        }
        if (Actor3.size() > 0) {
            if (Actor3.get(Actor3.size() - 1).getElement1() == null) {
                Actor3.get(Actor3.size() - 1).setElement1((int) mediaPlayer.getTime());
            }
            for (Pair<Integer, Integer, String> Act : Actor3) {
                String line = Act.getElement0().toString() + "#" + Act.getElement1().toString() + "#" + Act.getElement2().toString();
                BasicModel.getInstance().WriteToTextFile("Pair.txt", line, true);
            }
        }
        if (Actor4.size() > 0) {
            if (Actor4.get(Actor4.size() - 1).getElement1() == null) {
                Actor4.get(Actor4.size() - 1).setElement1((int) mediaPlayer.getTime());
            }
            for (Pair<Integer, Integer, String> Act : Actor4) {
                String line = Act.getElement0().toString() + "#" + Act.getElement1().toString() + "#" + Act.getElement2().toString();
                BasicModel.getInstance().WriteToTextFile("Pair.txt", line, true);
            }
        }
        if (Actor5.size() > 0) {
            if (Actor5.get(Actor5.size() - 1).getElement1() == null) {
                Actor5.get(Actor5.size() - 1).setElement1((int) mediaPlayer.getTime());
            }
            for (Pair<Integer, Integer, String> Act : Actor5) {
                String line = Act.getElement0().toString() + "#" + Act.getElement1().toString() + "#" + Act.getElement2().toString();
                BasicModel.getInstance().WriteToTextFile("Pair.txt", line, true);
            }
        }
        if (Actor6.size() > 0) {
            if (Actor6.get(Actor6.size() - 1).getElement1() == null) {
                Actor6.get(Actor6.size() - 1).setElement1((int) mediaPlayer.getTime());
            }
            for (Pair<Integer, Integer, String> Act : Actor6) {
                String line = Act.getElement0().toString() + "#" + Act.getElement1().toString() + "#" + Act.getElement2().toString();
                BasicModel.getInstance().WriteToTextFile("Pair.txt", line, true);
            }
        }
        if (Actor7.size() > 0) {
            if (Actor7.get(Actor7.size() - 1).getElement1() == null) {
                Actor7.get(Actor7.size() - 1).setElement1((int) mediaPlayer.getTime());
            }
            for (Pair<Integer, Integer, String> Act : Actor7) {
                String line = Act.getElement0().toString() + "#" + Act.getElement1().toString() + "#" + Act.getElement2().toString();
                BasicModel.getInstance().WriteToTextFile("Pair.txt", line, true);
            }
        }
        if (Actor8.size() > 0) {
            if (Actor8.get(Actor8.size() - 1).getElement1() == null) {
                Actor8.get(Actor8.size() - 1).setElement1((int) mediaPlayer.getTime());
            }
            for (Pair<Integer, Integer, String> Act : Actor8) {
                String line = Act.getElement0().toString() + "#" + Act.getElement1().toString() + "#" + Act.getElement2().toString();
                BasicModel.getInstance().WriteToTextFile("Pair.txt", line, true);
            }
        }
        if (Actor9.size() > 0) {
            if (Actor9.get(Actor9.size() - 1).getElement1() == null) {
                Actor9.get(Actor9.size() - 1).setElement1((int) mediaPlayer.getTime());
            }
            for (Pair<Integer, Integer, String> Act : Actor9) {
                String line = Act.getElement0().toString() + "#" + Act.getElement1().toString() + "#" + Act.getElement2().toString();
                BasicModel.getInstance().WriteToTextFile("Pair.txt", line, true);
            }
        }
        if (Actor10.size() > 0) {
            if (Actor10.get(Actor10.size() - 1).getElement1() == null) {
                Actor10.get(Actor10.size() - 1).setElement1((int) mediaPlayer.getTime());
            }
            for (Pair<Integer, Integer, String> Act : Actor10) {
                String line = Act.getElement0().toString() + "#" + Act.getElement1().toString() + "#" + Act.getElement2().toString();
                BasicModel.getInstance().WriteToTextFile("Pair.txt", line, true);
            }
        }
        if (Actor11.size() > 0) {
            if (Actor11.get(Actor11.size() - 1).getElement1() == null) {
                Actor11.get(Actor11.size() - 1).setElement1((int) mediaPlayer.getTime());
            }
            for (Pair<Integer, Integer, String> Act : Actor11) {
                String line = Act.getElement0().toString() + "#" + Act.getElement1().toString() + "#" + Act.getElement2().toString();
                BasicModel.getInstance().WriteToTextFile("Pair.txt", line, true);
            }
        }
        if (Actor12.size() > 0) {
            if (Actor12.get(Actor12.size() - 1).getElement1() == null) {
                Actor12.get(Actor12.size() - 1).setElement1((int) mediaPlayer.getTime());
            }
            for (Pair<Integer, Integer, String> Act : Actor12) {
                String line = Act.getElement0().toString() + "#" + Act.getElement1().toString() + "#" + Act.getElement2().toString();
                BasicModel.getInstance().WriteToTextFile("Pair.txt", line, true);
            }
        }
        if (Actor13.size() > 0) {
            if (Actor13.get(Actor13.size() - 1).getElement1() == null) {
                Actor13.get(Actor13.size() - 1).setElement1((int) mediaPlayer.getTime());
            }
            for (Pair<Integer, Integer, String> Act : Actor13) {
                String line = Act.getElement0().toString() + "#" + Act.getElement1().toString() + "#" + Act.getElement2().toString();
                BasicModel.getInstance().WriteToTextFile("Pair.txt", line, true);
            }
        }
        if (Actor14.size() > 0) {
            if (Actor14.get(Actor14.size() - 1).getElement1() == null) {
                Actor14.get(Actor14.size() - 1).setElement1((int) mediaPlayer.getTime());
            }
            for (Pair<Integer, Integer, String> Act : Actor14) {
                String line = Act.getElement0().toString() + "#" + Act.getElement1().toString() + "#" + Act.getElement2().toString();
                BasicModel.getInstance().WriteToTextFile("Pair.txt", line, true);
            }
        }
        if (Actor15.size() > 0) {
            if (Actor15.get(Actor15.size() - 1).getElement1() == null) {
                Actor15.get(Actor15.size() - 1).setElement1((int) mediaPlayer.getTime());
            }
            for (Pair<Integer, Integer, String> Act : Actor15) {
                String line = Act.getElement0().toString() + "#" + Act.getElement1().toString() + "#" + Act.getElement2().toString();
                BasicModel.getInstance().WriteToTextFile("Pair.txt", line, true);
            }
        }
        if (Actor16.size() > 0) {
            if (Actor16.get(Actor16.size() - 1).getElement1() == null) {
                Actor16.get(Actor16.size() - 1).setElement1((int) mediaPlayer.getTime());
            }
            for (Pair<Integer, Integer, String> Act : Actor16) {
                String line = Act.getElement0().toString() + "#" + Act.getElement1().toString() + "#" + Act.getElement2().toString();
                BasicModel.getInstance().WriteToTextFile("Pair.txt", line, true);
            }
        }
        if (Actor17.size() > 0) {
            if (Actor17.get(Actor17.size() - 1).getElement1() == null) {
                Actor17.get(Actor17.size() - 1).setElement1((int) mediaPlayer.getTime());
            }
            for (Pair<Integer, Integer, String> Act : Actor17) {
                String line = Act.getElement0().toString() + "#" + Act.getElement1().toString() + "#" + Act.getElement2().toString();
                BasicModel.getInstance().WriteToTextFile("Pair.txt", line, true);
            }
        }
        if (Actor18.size() > 0) {
            if (Actor18.get(Actor18.size() - 1).getElement1() == null) {
                Actor18.get(Actor18.size() - 1).setElement1((int) mediaPlayer.getTime());
            }
            for (Pair<Integer, Integer, String> Act : Actor18) {
                String line = Act.getElement0().toString() + "#" + Act.getElement1().toString() + "#" + Act.getElement2().toString();
                BasicModel.getInstance().WriteToTextFile("Pair.txt", line, true);
            }
        }
        if (Actor19.size() > 0) {
            if (Actor19.get(Actor19.size() - 1).getElement1() == null) {
                Actor19.get(Actor19.size() - 1).setElement1((int) mediaPlayer.getTime());
            }
            for (Pair<Integer, Integer, String> Act : Actor19) {
                String line = Act.getElement0().toString() + "#" + Act.getElement1().toString() + "#" + Act.getElement2().toString();
                BasicModel.getInstance().WriteToTextFile("Pair.txt", line, true);
            }
        }
        if (Actor20.size() > 0) {
            if (Actor20.get(Actor20.size() - 1).getElement1() == null) {
                Actor20.get(Actor20.size() - 1).setElement1((int) mediaPlayer.getTime());
            }
            for (Pair<Integer, Integer, String> Act : Actor20) {
                String line = Act.getElement0().toString() + "#" + Act.getElement1().toString() + "#" + Act.getElement2().toString();
                BasicModel.getInstance().WriteToTextFile("Pair.txt", line, true);
            }
        }

    }

    private void JlistClick(final JList jlist, final JTextField jtext) {
        jlist.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    List<String> name = new ArrayList<>();
                    String getName = (String) jlist.getSelectedValue();
                    name = Arrays.asList(getName.split("#"));
                    jtext.setText(name.get(1));
                }
            }
        });
    }

    private void AddCharacterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddCharacterActionPerformed
        // TODO add your handling code here: 

        if (ListCharacter.getModel().getSize() == 20) {
            JOptionPane.showMessageDialog(null, "Number of character less than 20 are accepted");
            SetFocus();
            nameCharacter.setFocusable(false);
            gui.setFocusable(true);
            return;
        }
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying() == true) {
                SetFocus();
                nameCharacter.setFocusable(false);
                gui.setFocusable(false);
            }
        }
        gui.setFocusable(false);
        model = new DefaultListModel();
        List<String> name = new ArrayList<>();
        int tmp = 0;
        String value;
        if (ListCharacter.getModel().getSize() != 0) {
            for (int i = 0; i < ListCharacter.getModel().getSize(); i++) {
                model.addElement((String) ListCharacter.getModel().getElementAt(i));
                name.add((String) ListCharacter.getModel().getElementAt(i));
            }
            for (int i = 1; i < model.getSize() + 1; i++) {
                value = keyb[i] + "#" + trim(nameCharacter.getText());
                if (name.contains(value)) {
                    SetFocus();
                    nameCharacter.setFocusable(false);
                    gui.setFocusable(true);
                    return;
                } else {
                    tmp = name.size();
                    //System.out.print(name.size());
                }
            }
            model.addElement((String) (keyb[name.size() + 1] + "#" + trim(nameCharacter.getText())));
        } else {
            model.addElement((String) (String.valueOf(1) + "#" + trim(nameCharacter.getText())));
        }

        List<String> tmpcrt1 = new ArrayList<>();
        for (int i = 0; i < model.getSize(); i++) {
            tmpcrt1.add((model.getElementAt(i).toString()));
            //tmpcrt1.add();
        }
        Collections.sort(tmpcrt1);
        DefaultListModel mod = new DefaultListModel();
        for (String valuea : tmpcrt1) {
            mod.addElement(valuea);
        }

        BasicModel.getInstance().AddtoList(ListCharacter, mod);
        //Keep focus on frame
        for (int i = 0; i < model.getSize(); i++) {
            Relation[i][i] = 1;
        }

        RefreshGraph(Relation);//weight
        SetFocus();
        if (mediaPlayer != null) {
            nameCharacter.setFocusable(false);
        }
        if (mediaPlayer == null) {
            nameCharacter.setFocusable(true);
        }
        gui.setFocusable(true);

    }//GEN-LAST:event_AddCharacterActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (mediaPlayer == null) {
            model = (DefaultListModel) ListCharacter.getModel();
            List<String> arc = new ArrayList<>();
            if (trim(nameCharacter.getText()) != "") {
                if (model != null) {
                    for (int x = 0; x < model.size(); x++) {
                        String line = (String) model.getElementAt(x);
                        arc = Arrays.asList(line.split("#"));
                        String line1 = "";
                        line1 = arc.get(0) + "#" + trim(nameCharacter.getText());
                        if (line.equals(line1)) {
                            model.remove(x);
                        }
                    }
                    DefaultListModel modela = new DefaultListModel();
                    model = (DefaultListModel) ListCharacter.getModel();
                    for (int i = 1; i < model.getSize() + 1; i++) {
                        String line = (String) model.getElementAt(i - 1);
                        arc = Arrays.asList(line.split("#"));
                        modela.addElement(keyb[i] + "#" + arc.get(1));
                    }
                    BasicModel.getInstance().AddtoList(ListCharacter, modela);
                }
            }
            RefreshGraph(Relation);//weight
            SetFocus();
            //nameCharacter.setFocusable(false);
            //gui.setFocusable(true);

        }
        if (mediaPlayer != null) {
            lockCharacter1 = ListCharacter.getModel().getSize();
            if (lockCharacter1 == lockCharacter) {
                JOptionPane.showMessageDialog(null, "Cannot delete the character that is processing");
                return;
            } else {
                model = (DefaultListModel) ListCharacter.getModel();
                List<String> arc = new ArrayList<>();
                if (trim(nameCharacter.getText()) != "") {
                    if (model != null) {
                        for (int x = lockCharacter; x < model.size(); x++) {
                            String line = (String) model.getElementAt(x);
                            arc = Arrays.asList(line.split("#"));
                            String line1 = "";
                            line1 = arc.get(0) + "#" + trim(nameCharacter.getText());
                            if (line.equals(line1)) {
                                model.remove(x);
                            }
                        }
                        DefaultListModel modela = new DefaultListModel();
                        model = (DefaultListModel) ListCharacter.getModel();
                        for (int i = 1; i < model.getSize() + 1; i++) {
                            String line = (String) model.getElementAt(i - 1);
                            arc = Arrays.asList(line.split("#"));
                            modela.addElement(keyb[i] + "#" + arc.get(1));
                        }
                        BasicModel.getInstance().AddtoList(ListCharacter, modela);
                    }

                }
            }
            RefreshGraph(Relation);//weight
            SetFocus();
            //nameCharacter.setFocusable(false);
            //gui.setFocusable(true);
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    public void runTimerSlider() {
        timerSlider = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float positionValue = (float) positionSlider.getValue() / 1000.0f;
                if (positionValue > 0.99f) {
                    positionValue = 0.99f;
                }
                positionSlider.setValue((int) ((mediaPlayer.getTime() * 1000) / mediaPlayer.getLength()));
                positionSlider.setToolTipText(BasicModel.getInstance().ConvertTime(mediaPlayer.getTime()));
            }
        });
        timerSlider.start();
    }


    private void nameCharacterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameCharacterActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_nameCharacterActionPerformed

    private void LoadDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadDataActionPerformed
        // TODO add your handling code here:
        String filname;
        List<String> tmpcrt = new ArrayList<>();
        List<String> arc1 = new ArrayList<>();
        List<String> characterName = new ArrayList<>();
        DefaultListModel mod = new DefaultListModel();
        //Pair<Integer, Integer, String> pr = Pair.createPair(null, null, null);
        JFileChooser fc = new JFileChooser();
        int status = fc.showOpenDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
            ClearAll();
            File selectedFile = fc.getSelectedFile();
            filname = selectedFile.getAbsolutePath();
            BufferedReader br;
            try {
                br = new BufferedReader(new FileReader(filname));
                String line;
                while (true) {
                    line = br.readLine();
                    if (line == null) {
                        break;
                    } else {
                        List<String> arc = new ArrayList<>();
                        Pair<Integer, Integer, String> pr = Pair.createPair(null, null, null);
                        arc = Arrays.asList(line.split("#"));
                        if (arc.size() != 5) {
                            break;
                        }
                        pr.setElement0(Integer.parseInt(arc.get(0)));
                        pr.setElement1(Integer.parseInt(arc.get(1)));
                        pr.setElement2(arc.get(2) + "#" + arc.get(3) + "#" + arc.get(4));
                        characterName.add(pr.getElement2());
                        if (arc.get(2).equals("1")) {
                            Actor1.add(pr);
                            //CreateProcessBar(pgAuthor1, Actor1, charac1, Long.parseLong(arc.get(4)), prefusePanel);
                            //ResizeProgressBar(pgAuthor1.get(pgAuthor1.size() - 1), Actor1, Long.parseLong(arc.get(4)), prefusePanel, "1"); //
                            CreateProcessBarLoad(pgAuthor1, pr, charac1);
                            charac1.setText(arc.get(3) + " is appeared");
                        }
                        if (arc.get(2).equals("2")) {
                            Actor2.add(pr);
                            //CreateProcessBar(pgAuthor1, Actor1, charac1, LengthOfMovie, prefusePanel);
                            CreateProcessBarLoad(pgAuthor2, pr, charac2);
                            //CreateProcessBar(pgAuthor2, Actor2, charac2, Long.parseLong(arc.get(4)), prefusePanel);
                            //ResizeProgressBar(pgAuthor2.get(pgAuthor2.size() - 1), Actor2, Long.parseLong(arc.get(4)), prefusePanel, "2");
                            charac2.setText(arc.get(3) + " is appeared");
                        }

                        if (arc.get(2).equals("3")) {
                            Actor3.add(pr);
                            CreateProcessBarLoad(pgAuthor3, pr, charac3);
                            //CreateProcessBar(pgAuthor3, Actor3, charac3, Long.parseLong(arc.get(4)), prefusePanel);
                            //ResizeProgressBar(pgAuthor3.get(pgAuthor3.size() - 1), Actor3, Long.parseLong(arc.get(4)), prefusePanel, "3");
                            charac3.setText(arc.get(3) + " is appeared");
                        }
                        if (arc.get(2).equals("4")) {
                            Actor4.add(pr);
                            CreateProcessBarLoad(pgAuthor4, pr, charac4);
                            //CreateProcessBar(pgAuthor4, Actor4, charac4, Long.parseLong(arc.get(4)), prefusePanel);
                            //ResizeProgressBar(pgAuthor4.get(pgAuthor4.size() - 1), Actor4, Long.parseLong(arc.get(4)), prefusePanel, "4");
                            charac4.setText(arc.get(3) + " is appeared");
                        }
                        if (arc.get(2).equals("5")) {
                            Actor5.add(pr);
                            //CreateProcessBar(pgAuthor5, Actor5, charac5, Long.parseLong(arc.get(4)), prefusePanel);
                            //ResizeProgressBar(pgAuthor5.get(pgAuthor5.size() - 1), Actor5, Long.parseLong(arc.get(4)), prefusePanel, "4");
                            CreateProcessBarLoad(pgAuthor5, pr, charac5);
                            charac5.setText(arc.get(3) + " is appeared");
                        }
                        if (arc.get(2).equals("6")) {
                            Actor6.add(pr);
                            //CreateProcessBar(pgAuthor6, Actor6, charac6, Long.parseLong(arc.get(4)), prefusePanel);
                            //ResizeProgressBar(pgAuthor6.get(pgAuthor6.size() - 1), Actor6, Long.parseLong(arc.get(4)), prefusePanel, "4");
                            CreateProcessBarLoad(pgAuthor6, pr, charac6);
                            charac6.setText(arc.get(3) + " is appeared");
                        }
                        if (arc.get(2).equals("7")) {
                            Actor7.add(pr);
                            //CreateProcessBar(pgAuthor7, Actor7, charac7, Long.parseLong(arc.get(4)), prefusePanel);
                            //ResizeProgressBar(pgAuthor7.get(pgAuthor7.size() - 1), Actor7, Long.parseLong(arc.get(4)), prefusePanel, "4");
                            CreateProcessBarLoad(pgAuthor7, pr, charac7);
                            charac7.setText(arc.get(3) + " is appeared");
                        }
                        if (arc.get(2).equals("8")) {
                            Actor8.add(pr);
                            //CreateProcessBar(pgAuthor8, Actor8, charac8, Long.parseLong(arc.get(4)), prefusePanel);
                            //ResizeProgressBar(pgAuthor8.get(pgAuthor8.size() - 1), Actor8, Long.parseLong(arc.get(4)), prefusePanel, "4");
                            CreateProcessBarLoad(pgAuthor8, pr, charac8);
                            charac8.setText(arc.get(3) + " is appeared");
                        }
                        if (arc.get(2).equals("9")) {
                            Actor9.add(pr);
                            //CreateProcessBar(pgAuthor9, Actor9, charac9, Long.parseLong(arc.get(4)), prefusePanel);
                            //ResizeProgressBar(pgAuthor9.get(pgAuthor9.size() - 1), Actor9, Long.parseLong(arc.get(4)), prefusePanel, "4");
                            CreateProcessBarLoad(pgAuthor9, pr, charac9);
                            charac9.setText(arc.get(3) + " is appeared");
                        }
                        if (arc.get(2).equals("0")) {
                            Actor10.add(pr);
                            //CreateProcessBar(pgAuthor10, Actor10, charac10, Long.parseLong(arc.get(4)), prefusePanel);
                            //ResizeProgressBar(pgAuthor10.get(pgAuthor10.size() - 1), Actor10, Long.parseLong(arc.get(4)), prefusePanel, "4");
                            CreateProcessBarLoad(pgAuthor10, pr, charac10);
                            charac10.setText(arc.get(3) + " is appeared");
                        }
                        if (arc.get(2).toLowerCase().equals("a")) {
                            Actor11.add(pr);
                            //CreateProcessBar(pgAuthor11, Actor11, charac11, Long.parseLong(arc.get(4)), prefusePanel);
                            //ResizeProgressBar(pgAuthor11.get(pgAuthor11.size() - 1), Actor11, Long.parseLong(arc.get(4)), prefusePanel, "4");
                            CreateProcessBarLoad(pgAuthor11, pr, charac11);
                            charac11.setText(arc.get(3) + " is appeared");
                        }
                        if (arc.get(2).toLowerCase().equals("b")) {
                            Actor12.add(pr);
                            //CreateProcessBar(pgAuthor12, Actor12, charac12, Long.parseLong(arc.get(4)), prefusePanel);
                            //ResizeProgressBar(pgAuthor12.get(pgAuthor12.size() - 1), Actor12, Long.parseLong(arc.get(4)), prefusePanel, "4");
                            CreateProcessBarLoad(pgAuthor12, pr, charac12);
                            charac12.setText(arc.get(3) + " is appeared");
                        }
                        if (arc.get(2).toLowerCase().equals("c")) {
                            Actor13.add(pr);
                            //CreateProcessBar(pgAuthor13, Actor13, charac13, Long.parseLong(arc.get(4)), prefusePanel);
                            //ResizeProgressBar(pgAuthor13.get(pgAuthor13.size() - 1), Actor13, Long.parseLong(arc.get(4)), prefusePanel, "4");
                            CreateProcessBarLoad(pgAuthor13, pr, charac13);
                            charac13.setText(arc.get(3) + " is appeared");
                        }
                        if (arc.get(2).toLowerCase().equals("d")) {
                            Actor14.add(pr);
                            //CreateProcessBar(pgAuthor14, Actor14, charac14, Long.parseLong(arc.get(4)), prefusePanel);
                            //ResizeProgressBar(pgAuthor14.get(pgAuthor14.size() - 1), Actor14, Long.parseLong(arc.get(4)), prefusePanel, "4");
                            CreateProcessBarLoad(pgAuthor14, pr, charac14);
                            charac14.setText(arc.get(3) + " is appeared");
                        }
                        if (arc.get(2).toLowerCase().equals("e")) {
                            Actor15.add(pr);
                            //CreateProcessBar(pgAuthor15, Actor15, charac15, Long.parseLong(arc.get(4)), prefusePanel);
                            //ResizeProgressBar(pgAuthor15.get(pgAuthor15.size() - 1), Actor15, Long.parseLong(arc.get(4)), prefusePanel, "4");
                            CreateProcessBarLoad(pgAuthor15, pr, charac15);
                            charac15.setText(arc.get(3) + " is appeared");
                        }
                        if (arc.get(2).toLowerCase().equals("f")) {
                            Actor16.add(pr);
                            //CreateProcessBar(pgAuthor16, Actor16, charac16, Long.parseLong(arc.get(4)), prefusePanel);
                            //ResizeProgressBar(pgAuthor16.get(pgAuthor16.size() - 1), Actor16, Long.parseLong(arc.get(4)), prefusePanel, "4");
                            CreateProcessBarLoad(pgAuthor16, pr, charac16);
                            charac16.setText(arc.get(3) + " is appeared");
                        }
                        if (arc.get(2).toLowerCase().equals("g")) {
                            Actor17.add(pr);
                            //CreateProcessBar(pgAuthor17, Actor17, charac17, Long.parseLong(arc.get(4)), prefusePanel);
                            //ResizeProgressBar(pgAuthor17.get(pgAuthor17.size() - 1), Actor17, Long.parseLong(arc.get(4)), prefusePanel, "4");
                            CreateProcessBarLoad(pgAuthor17, pr, charac17);
                            charac17.setText(arc.get(3) + " is appeared");
                        }
                        if (arc.get(2).toLowerCase().equals("h")) {
                            Actor18.add(pr);
                            //CreateProcessBar(pgAuthor18, Actor18, charac18, Long.parseLong(arc.get(4)), prefusePanel);
                            //ResizeProgressBar(pgAuthor18.get(pgAuthor18.size() - 1), Actor18, Long.parseLong(arc.get(4)), prefusePanel, "4");
                            CreateProcessBarLoad(pgAuthor18, pr, charac18);
                            charac18.setText(arc.get(3) + " is appeared");
                        }
                        if (arc.get(2).toLowerCase().equals("i")) {
                            Actor19.add(pr);
                            //CreateProcessBar(pgAuthor19, Actor19, charac19, Long.parseLong(arc.get(4)), prefusePanel);
                            //ResizeProgressBar(pgAuthor19.get(pgAuthor19.size() - 1), Actor19, Long.parseLong(arc.get(4)), prefusePanel, "4");
                            CreateProcessBarLoad(pgAuthor19, pr, charac19);
                            charac19.setText(arc.get(3) + " is appeared");
                        }
                        if (arc.get(2).toLowerCase().equals("j")) {
                            Actor20.add(pr);
                            //CreateProcessBar(pgAuthor20, Actor20, charac20, Long.parseLong(arc.get(4)), prefusePanel);
                            //ResizeProgressBar(pgAuthor20.get(pgAuthor20.size() - 1), Actor20, Long.parseLong(arc.get(4)), prefusePanel, "4");
                            CreateProcessBarLoad(pgAuthor20, pr, charac20);
                            charac20.setText(arc.get(3) + " is appeared");
                        }
                    }
                }
                br.close();
            } catch (IOException ex) {
            }
            Set<String> uniqueList = new HashSet<String>(characterName);
            for (String value : uniqueList) {
                arc1 = Arrays.asList(value.split("#"));
                tmpcrt.add(arc1.get(0) + "#" + arc1.get(1));
            }
            Collections.sort(tmpcrt);
            for (String value : tmpcrt) {
                mod.addElement(value);
            }
            BasicModel.getInstance().AddtoList(ListCharacter, mod);
            CalculateRelation();
            BasicModel.getInstance().CopyMatrix(Relation, cpRelation);
            //for (int i )
            
            
            CreateGephiGraph(); //Gephi
            //RefreshGraph(Relation);//weight// Prefuse
            
            occurtime = new int[20][20];
            calculatecoocur();

            //How many time
            List<String> counttimes = new ArrayList<>();

            for (int i = 0; i < count.length - 1; i++) {
                if (count[i] != null) {
                    counttimes.add(count[i]);
                }
                //System.out.print(count[i] + " ,");
            }

            List<String> counttimes1 = new ArrayList<>();
            for (int i = 0; i < counttimes.size() - 1; i++) {

                if (!counttimes.get(i).equals(counttimes.get(i + 1))) {
                    counttimes1.add(counttimes.get(i));
                }

            }
            /*
             for (int i = 0; i < counttimes1.size(); i ++) {
             System.out.print(counttimes1.get(i) + " ,");
             }
             counttimes1 so lan gap nhau */
            List<String> arc = new ArrayList<>();
            //int dem1, dem2, dem3, dem4, dem5, dem6, dem7, dem8, dem9, dem10, dem11, dem12, dem13, dem14;
            int curtime[][] = new int[20][20];
            for (int i = 0; i < counttimes1.size(); i++) {
                arc = Arrays.asList(counttimes1.get(i).split("#"));
                
                //Character 0
                if (arc.contains("0") && arc.contains("1")) {
                    curtime[0][1] = curtime[0][1] + 1;
                    curtime[1][0] = curtime[0][1];
                }
                if (arc.contains("0") && arc.contains("2")) {
                    curtime[0][2] = curtime[0][2] + 1;
                    curtime[2][0] = curtime[0][2];
                }
                if (arc.contains("0") && arc.contains("3")) {
                    curtime[0][3] = curtime[0][3] + 1;
                    curtime[3][0] = curtime[0][3];
                }
                if (arc.contains("0") && arc.contains("4")) {
                    curtime[0][4] = curtime[0][4] + 1;
                    curtime[4][0] = curtime[0][4];
                }
                if (arc.contains("0") && arc.contains("5")) {
                    curtime[0][5] = curtime[0][5] + 1;
                    curtime[5][0] = curtime[0][5];
                }
                if (arc.contains("0") && arc.contains("6")) {
                    curtime[0][6] = curtime[0][6] + 1;
                    curtime[6][0] = curtime[0][6];
                }
                if (arc.contains("0") && arc.contains("7")) {
                    curtime[0][7] = curtime[0][7] + 1;
                    curtime[7][0] = curtime[0][7];
                }
                if (arc.contains("0") && arc.contains("8")) {
                    curtime[0][8] = curtime[0][8] + 1;
                    curtime[8][0] = curtime[0][8];
                }
                if (arc.contains("0") && arc.contains("9")) {
                    curtime[0][9] = curtime[0][9] + 1;
                    curtime[9][0] = curtime[0][9];
                }
                if (arc.contains("0") && arc.contains("10")) {
                    curtime[0][10] = curtime[0][10] + 1;
                    curtime[10][0] = curtime[0][10];
                }
                if (arc.contains("0") && arc.contains("11")) {
                    curtime[0][11] = curtime[0][11] + 1;
                    curtime[11][0] = curtime[0][11];
                }
                if (arc.contains("0") && arc.contains("12")) {
                    curtime[0][12] = curtime[0][12] + 1;
                    curtime[12][0] = curtime[0][12];
                }
                if (arc.contains("0") && arc.contains("13")) {
                    curtime[0][13] = curtime[0][13] + 1;
                    curtime[13][0] = curtime[0][13];
                }
                if (arc.contains("0") && arc.contains("14")) {
                    curtime[0][14] = curtime[0][14] + 1;
                    curtime[14][0] = curtime[0][14];
                }
                
                
                //Character 1
                                
                if (arc.contains("1") && arc.contains("2")) {
                    curtime[1][2] = curtime[1][2] + 1;
                    curtime[2][1] = curtime[1][2];
                }
                if (arc.contains("1") && arc.contains("0")) {
                    curtime[1][0] = curtime[1][0] + 1;
                    curtime[0][1] = curtime[1][0];
                }
                if (arc.contains("1") && arc.contains("3")) {
                    curtime[1][3] = curtime[1][3] + 1;
                    curtime[3][1] = curtime[1][3];
                }
                if (arc.contains("1") && arc.contains("4")) {
                    curtime[1][4] = curtime[1][4] + 1;
                    curtime[4][1] = curtime[1][4];
                }
                if (arc.contains("1") && arc.contains("5")) {
                    curtime[1][5] = curtime[1][5] + 1;
                    curtime[5][1] = curtime[1][5];
                }
                if (arc.contains("1") && arc.contains("6")) {
                    curtime[1][6] = curtime[1][6] + 1;
                    curtime[6][1] = curtime[1][6];
                }
                if (arc.contains("1") && arc.contains("7")) {
                    curtime[1][7] = curtime[1][7] + 1;
                    curtime[7][1] = curtime[1][7];
                }
                if (arc.contains("1") && arc.contains("8")) {
                    curtime[1][8] = curtime[1][8] + 1;
                    curtime[8][1] = curtime[1][8];
                }
                if (arc.contains("1") && arc.contains("9")) {
                    curtime[1][9] = curtime[1][9] + 1;
                    curtime[9][1] = curtime[1][9];
                }
                if (arc.contains("1") && arc.contains("10")) {
                    curtime[1][10] = curtime[1][10] + 1;
                    curtime[10][1] = curtime[1][10];
                }
                if (arc.contains("1") && arc.contains("11")) {
                    curtime[1][11] = curtime[1][11] + 1;
                    curtime[11][1] = curtime[1][11];
                }
                if (arc.contains("1") && arc.contains("12")) {
                    curtime[1][12] = curtime[1][12] + 1;
                    curtime[12][1] = curtime[1][12];
                }
                if (arc.contains("1") && arc.contains("13")) {
                    curtime[1][13] = curtime[1][13] + 1;
                    curtime[13][1] = curtime[1][13];
                }
                if (arc.contains("1") && arc.contains("14")) {
                    curtime[1][14] = curtime[1][14] + 1;
                    curtime[14][1] = curtime[1][14];
                }
                
                //Character 2                                 
                if (arc.contains("2") && arc.contains("3")) {
                    curtime[2][3] = curtime[2][3] + 1;
                    curtime[3][2] = curtime[2][3];
                }
                if (arc.contains("2") && arc.contains("4")) {
                    curtime[2][4] = curtime[2][4] + 1;
                    curtime[4][2] = curtime[2][4];
                }
                if (arc.contains("2") && arc.contains("5")) {
                    curtime[2][5] = curtime[2][5] + 1;
                    curtime[5][2] = curtime[2][5];
                }
                if (arc.contains("2") && arc.contains("6")) {
                    curtime[2][6] = curtime[2][6] + 1;
                    curtime[6][2] = curtime[2][6];
                }
                if (arc.contains("2") && arc.contains("7")) {
                    curtime[2][7] = curtime[2][7] + 1;
                    curtime[7][2] = curtime[2][7];
                }
                if (arc.contains("2") && arc.contains("8")) {
                    curtime[2][8] = curtime[2][8] + 1;
                    curtime[8][2] = curtime[2][8];
                }
                if (arc.contains("2") && arc.contains("9")) {
                    curtime[2][9] = curtime[2][9] + 1;
                    curtime[9][2] = curtime[2][9];
                }
                if (arc.contains("2") && arc.contains("10")) {
                    curtime[2][10] = curtime[2][10] + 1;
                    curtime[10][2] = curtime[2][10];
                }
                if (arc.contains("2") && arc.contains("11")) {
                    curtime[2][11] = curtime[2][11] + 1;
                    curtime[11][2] = curtime[2][11];
                }
                if (arc.contains("2") && arc.contains("12")) {
                    curtime[2][12] = curtime[2][12] + 1;
                    curtime[12][2] = curtime[2][12];
                }
                if (arc.contains("2") && arc.contains("13")) {
                    curtime[2][13] = curtime[2][13] + 1;
                    curtime[13][2] = curtime[2][13];
                }
                if (arc.contains("2") && arc.contains("14")) {
                    curtime[2][14] = curtime[2][14] + 1;
                    curtime[14][2] = curtime[2][14];
                }
                
                //Character 3
                                 
                if (arc.contains("3") && arc.contains("4")) {
                    curtime[3][4] = curtime[3][4] + 1;
                    curtime[4][3] = curtime[3][4];
                }
                if (arc.contains("3") && arc.contains("5")) {
                    curtime[3][5] = curtime[3][5] + 1;
                    curtime[5][3] = curtime[3][5];
                }
                if (arc.contains("3") && arc.contains("6")) {
                    curtime[3][6] = curtime[3][6] + 1;
                    curtime[6][3] = curtime[3][6];
                }
                if (arc.contains("3") && arc.contains("7")) {
                    curtime[3][7] = curtime[3][7] + 1;
                    curtime[7][3] = curtime[3][7];
                }
                if (arc.contains("3") && arc.contains("8")) {
                    curtime[3][8] = curtime[3][8] + 1;
                    curtime[8][3] = curtime[3][8];
                }
                if (arc.contains("3") && arc.contains("9")) {
                    curtime[3][9] = curtime[3][9] + 1;
                    curtime[9][3] = curtime[3][9];
                }
                if (arc.contains("3") && arc.contains("10")) {
                    curtime[3][10] = curtime[3][10] + 1;
                    curtime[10][3] = curtime[3][10];
                }
                if (arc.contains("3") && arc.contains("11")) {
                    curtime[3][11] = curtime[3][11] + 1;
                    curtime[11][3] = curtime[3][11];
                }
                if (arc.contains("3") && arc.contains("12")) {
                    curtime[3][12] = curtime[3][12] + 1;
                    curtime[12][3] = curtime[3][12];
                }
                if (arc.contains("3") && arc.contains("13")) {
                    curtime[3][13] = curtime[3][13] + 1;
                    curtime[13][3] = curtime[3][13];
                }
                if (arc.contains("3") && arc.contains("14")) {
                    curtime[3][14] = curtime[3][14] + 1;
                    curtime[14][3] = curtime[3][14];
                }
                
                //Character 4
                if (arc.contains("4") && arc.contains("5")) {
                    curtime[4][5] = curtime[4][5] + 1;
                    curtime[5][4] = curtime[4][5];
                }
                if (arc.contains("4") && arc.contains("6")) {
                    curtime[4][6] = curtime[4][6] + 1;
                    curtime[6][4] = curtime[4][6];
                }
                if (arc.contains("4") && arc.contains("7")) {
                    curtime[4][7] = curtime[4][7] + 1;
                    curtime[7][4] = curtime[4][7];
                }
                if (arc.contains("4") && arc.contains("8")) {
                    curtime[4][8] = curtime[4][8] + 1;
                    curtime[8][4] = curtime[4][8];
                }
                if (arc.contains("4") && arc.contains("9")) {
                    curtime[4][9] = curtime[4][9] + 1;
                    curtime[9][4] = curtime[4][9];
                }
                if (arc.contains("4") && arc.contains("10")) {
                    curtime[4][10] = curtime[4][10] + 1;
                    curtime[10][4] = curtime[4][10];
                }
                if (arc.contains("4") && arc.contains("11")) {
                    curtime[4][11] = curtime[4][11] + 1;
                    curtime[11][4] = curtime[4][11];
                }
                if (arc.contains("4") && arc.contains("12")) {
                    curtime[4][12] = curtime[4][12] + 1;
                    curtime[12][4] = curtime[4][12];
                }
                if (arc.contains("4") && arc.contains("13")) {
                    curtime[4][13] = curtime[4][13] + 1;
                    curtime[13][4] = curtime[4][13];
                }
                if (arc.contains("4") && arc.contains("14")) {
                    curtime[4][14] = curtime[4][14] + 1;
                    curtime[14][4] = curtime[4][14];
                }
                
                
                //Character 5
                if (arc.contains("5") && arc.contains("6")) {
                    curtime[5][6] = curtime[5][6] + 1;
                    curtime[6][5] = curtime[5][6];
                }
                if (arc.contains("5") && arc.contains("7")) {
                    curtime[5][7] = curtime[5][7] + 1;
                    curtime[7][5] = curtime[5][7];
                }
                if (arc.contains("5") && arc.contains("8")) {
                    curtime[5][8] = curtime[5][8] + 1;
                    curtime[8][5] = curtime[5][8];
                }
                if (arc.contains("5") && arc.contains("9")) {
                    curtime[5][9] = curtime[5][9] + 1;
                    curtime[9][5] = curtime[5][9];
                }
                if (arc.contains("5") && arc.contains("10")) {
                    curtime[5][10] = curtime[5][10] + 1;
                    curtime[10][5] = curtime[5][10];
                }
                if (arc.contains("5") && arc.contains("11")) {
                    curtime[5][11] = curtime[5][11] + 1;
                    curtime[11][5] = curtime[5][11];
                }
                if (arc.contains("5") && arc.contains("12")) {
                    curtime[5][12] = curtime[5][12] + 1;
                    curtime[12][5] = curtime[5][12];
                }
                if (arc.contains("5") && arc.contains("13")) {
                    curtime[5][13] = curtime[5][13] + 1;
                    curtime[13][5] = curtime[5][13];
                }
                if (arc.contains("5") && arc.contains("14")) {
                    curtime[5][14] = curtime[5][14] + 1;
                    curtime[14][5] = curtime[5][14];
                }
                
                 //Character 6                
                if (arc.contains("6") && arc.contains("7")) {
                    curtime[6][7] = curtime[6][7] + 1;
                    curtime[7][6] = curtime[6][7];
                }
                if (arc.contains("6") && arc.contains("8")) {
                    curtime[6][8] = curtime[6][8] + 1;
                    curtime[8][6] = curtime[6][8];
                }
                if (arc.contains("6") && arc.contains("9")) {
                    curtime[6][9] = curtime[6][9] + 1;
                    curtime[9][6] = curtime[6][9];
                }
                if (arc.contains("6") && arc.contains("10")) {
                    curtime[6][10] = curtime[6][10] + 1;
                    curtime[10][6] = curtime[6][10];
                }
                if (arc.contains("6") && arc.contains("11")) {
                    curtime[6][11] = curtime[6][11] + 1;
                    curtime[11][6] = curtime[6][11];
                }
                if (arc.contains("6") && arc.contains("12")) {
                    curtime[6][12] = curtime[6][12] + 1;
                    curtime[12][6] = curtime[6][12];
                }
                if (arc.contains("6") && arc.contains("13")) {
                    curtime[6][13] = curtime[6][13] + 1;
                    curtime[13][6] = curtime[6][13];
                }
                if (arc.contains("6") && arc.contains("14")) {
                    curtime[6][14] = curtime[6][14] + 1;
                    curtime[14][6] = curtime[6][14];
                }
                
                 //Character 7                                
                if (arc.contains("7") && arc.contains("8")) {
                    curtime[7][8] = curtime[7][8] + 1;
                    curtime[8][7] = curtime[7][8];
                }
                if (arc.contains("7") && arc.contains("9")) {
                    curtime[7][9] = curtime[7][9] + 1;
                    curtime[9][7] = curtime[7][9];
                }
                if (arc.contains("7") && arc.contains("10")) {
                    curtime[7][10] = curtime[7][10] + 1;
                    curtime[10][7] = curtime[7][10];
                }
                if (arc.contains("7") && arc.contains("11")) {
                    curtime[7][11] = curtime[7][11] + 1;
                    curtime[11][7] = curtime[7][11];
                }
                if (arc.contains("7") && arc.contains("12")) {
                    curtime[7][12] = curtime[7][12] + 1;
                    curtime[12][7] = curtime[7][12];
                }
                if (arc.contains("7") && arc.contains("13")) {
                    curtime[7][13] = curtime[7][13] + 1;
                    curtime[13][7] = curtime[7][13];
                }
                if (arc.contains("7") && arc.contains("14")) {
                    curtime[7][14] = curtime[7][14] + 1;
                    curtime[14][7] = curtime[7][14];
                }
                
                 //Character 8                                                
                if (arc.contains("8") && arc.contains("9")) {
                    curtime[8][9] = curtime[8][9] + 1;
                    curtime[9][8] = curtime[8][9];
                }
                if (arc.contains("8") && arc.contains("10")) {
                    curtime[8][10] = curtime[8][10] + 1;
                    curtime[10][8] = curtime[8][10];
                }
                if (arc.contains("8") && arc.contains("11")) {
                    curtime[8][11] = curtime[8][11] + 1;
                    curtime[11][8] = curtime[8][11];
                }
                if (arc.contains("8") && arc.contains("12")) {
                    curtime[8][12] = curtime[8][12] + 1;
                    curtime[12][8] = curtime[8][12];
                }
                if (arc.contains("8") && arc.contains("13")) {
                    curtime[8][13] = curtime[8][13] + 1;
                    curtime[13][8] = curtime[8][13];
                }
                if (arc.contains("8") && arc.contains("14")) {
                    curtime[8][14] = curtime[8][14] + 1;
                    curtime[14][8] = curtime[8][14];
                }
                
                //Character 9                                                               
                if (arc.contains("9") && arc.contains("10")) {
                    curtime[9][10] = curtime[9][10] + 1;
                    curtime[10][9] = curtime[9][10];
                }
                if (arc.contains("9") && arc.contains("11")) {
                    curtime[9][11] = curtime[9][11] + 1;
                    curtime[11][9] = curtime[9][11];
                }
                if (arc.contains("9") && arc.contains("12")) {
                    curtime[9][12] = curtime[9][12] + 1;
                    curtime[12][9] = curtime[9][12];
                }
                if (arc.contains("9") && arc.contains("13")) {
                    curtime[9][13] = curtime[9][13] + 1;
                    curtime[13][9] = curtime[9][13];
                }
                if (arc.contains("9") && arc.contains("14")) {
                    curtime[9][14] = curtime[9][14] + 1;
                    curtime[14][9] = curtime[9][14];
                }
                
                //Character 10                                                                              
                if (arc.contains("10") && arc.contains("11")) {
                    curtime[10][11] = curtime[10][11] + 1;
                    curtime[11][10] = curtime[10][11];
                }
                if (arc.contains("10") && arc.contains("12")) {
                    curtime[10][12] = curtime[10][12] + 1;
                    curtime[12][10] = curtime[10][12];
                }
                if (arc.contains("10") && arc.contains("13")) {
                    curtime[10][13] = curtime[10][13] + 1;
                    curtime[13][10] = curtime[10][13];
                }
                if (arc.contains("10") && arc.contains("14")) {
                    curtime[10][14] = curtime[10][14] + 1;
                    curtime[14][10] = curtime[10][14];
                }
                
                //Character 11
                if (arc.contains("11") && arc.contains("12")) {
                    curtime[11][12] = curtime[11][12] + 1;
                    curtime[12][11] = curtime[11][12];
                }
                if (arc.contains("11") && arc.contains("13")) {
                    curtime[11][13] = curtime[11][13] + 1;
                    curtime[13][11] = curtime[11][13];
                }
                if (arc.contains("11") && arc.contains("14")) {
                    curtime[11][14] = curtime[11][14] + 1;
                    curtime[14][11] = curtime[11][14];
                }
                
                //Character 12
                if (arc.contains("12") && arc.contains("13")) {
                    curtime[12][13] = curtime[12][13] + 1;
                    curtime[13][12] = curtime[12][13];
                }
                if (arc.contains("12") && arc.contains("14")) {
                    curtime[12][14] = curtime[12][14] + 1;
                    curtime[14][12] = curtime[12][14];
                }
                
                //Character 13                
                if (arc.contains("13") && arc.contains("14")) {
                    curtime[13][14] = curtime[13][14] + 1;
                    curtime[14][13] = curtime[13][14];
                }
                
                /*
                
                 if (counttimes1.get(i).length() > 2) {
                 System.out.print(counttimes1.get(i) + " ,");
                 }
                 */
            }

            for (int i = 0; i < curtime.length; i++) {
                for (int j = 0; j < curtime.length; j++) {
                   if (curtime[i][j] <10 && curtime[i][j] !=0) {
                       curtime[i][j] += 10;
                   }
                   System.out.print(curtime[i][j] + " ,");         
                }
                System.out.println();
            }

        }
    }//GEN-LAST:event_LoadDataActionPerformed

    public void RepaintScrollPanel() {
        int LengthMovie = 0;
        List<String> arc = new ArrayList<>();
        //scrollPane1.remove(c);
        if (!Actor1.isEmpty()) {
            arc = Arrays.asList(Actor1.get(0).getElement2().split("#"));
            LengthMovie = Integer.parseInt(arc.get(2));
            CreateProcessBar(pgAuthor1, Actor1, charac1, LengthMovie, prefusePanel);
            ResizeProgressBar(pgAuthor1.get(pgAuthor1.size() - 1), Actor1, LengthMovie, prefusePanel, "1");
            charac1.setText(arc.get(3) + " is appeared");
        }
        scrollPane1.revalidate();
        scrollPane1.repaint();
    }

    public void RemoveOnList(List<Pair<Integer, Integer, String>> Actor, List<JProgressBar> prg, String txtCondition) {
        String tmp = "";
        for (int i = 0; i < Actor.size(); i++) {
            tmp = Actor.get(i).getElement0().toString() + "#" + Actor.get(i).getElement1().toString() + "#" + Actor.get(i).getElement2();
            if (tmp.equals(txtCondition)) {
                Actor.remove(i);
            }
        }
        tmp = "";
        for (int i = 0; i < prg.size(); i++) {
            tmp = prg.get(i).getString();
            if (tmp.equals(txtCondition)) {
                prg.get(i).setVisible(false);
                prg.remove(i);

            }
        }
        count = null;
        AllChrac.clear();
        CalculateRelation();
        RefreshGraph(Relation);//weight

        // need continues
    }

    private void writetocsv(List<Pair<Integer, Integer, String>> Actor) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
        File file = new File(df.format(new Date()) + "_Statistics.csv");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            CsvWriter csvOutput = new CsvWriter(new FileWriter(file, true), ',');
        } catch (IOException ex) {
            Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void CalculateRelation() {
        int LengthMovie = 0;
        List<String> arc = new ArrayList<>();
        if (!Actor1.isEmpty()) {
            arc = Arrays.asList(Actor1.get(0).getElement2().split("#"));
            LengthMovie = Integer.parseInt(arc.get(2));
            for (Pair<Integer, Integer, java.lang.String> Actor : Actor1) {
                AllChrac.add(Actor);
            }
        }
        if (!Actor2.isEmpty()) {
            arc = Arrays.asList(Actor2.get(0).getElement2().split("#"));
            LengthMovie = Integer.parseInt(arc.get(2));
            for (Pair<Integer, Integer, java.lang.String> Actor : Actor2) {
                AllChrac.add(Actor);
            }
        }
        if (!Actor3.isEmpty()) {
            arc = Arrays.asList(Actor3.get(0).getElement2().split("#"));
            LengthMovie = Integer.parseInt(arc.get(2));
            for (Pair<Integer, Integer, java.lang.String> Actor : Actor3) {
                AllChrac.add(Actor);
            }
        }
        if (!Actor4.isEmpty()) {
            arc = Arrays.asList(Actor4.get(0).getElement2().split("#"));
            LengthMovie = Integer.parseInt(arc.get(2));
            for (Pair<Integer, Integer, java.lang.String> Actor : Actor4) {
                AllChrac.add(Actor);
            }
        }
        if (!Actor5.isEmpty()) {
            arc = Arrays.asList(Actor5.get(0).getElement2().split("#"));
            LengthMovie = Integer.parseInt(arc.get(2));
            for (Pair<Integer, Integer, java.lang.String> Actor : Actor5) {
                AllChrac.add(Actor);
            }
        }
        if (!Actor6.isEmpty()) {
            arc = Arrays.asList(Actor6.get(0).getElement2().split("#"));
            LengthMovie = Integer.parseInt(arc.get(2));
            for (Pair<Integer, Integer, java.lang.String> Actor : Actor6) {
                AllChrac.add(Actor);
            }
        }
        if (!Actor7.isEmpty()) {
            arc = Arrays.asList(Actor7.get(0).getElement2().split("#"));
            LengthMovie = Integer.parseInt(arc.get(2));
            for (Pair<Integer, Integer, java.lang.String> Actor : Actor7) {
                AllChrac.add(Actor);
            }
        }
        if (!Actor8.isEmpty()) {
            arc = Arrays.asList(Actor8.get(0).getElement2().split("#"));
            LengthMovie = Integer.parseInt(arc.get(2));
            for (Pair<Integer, Integer, java.lang.String> Actor : Actor8) {
                AllChrac.add(Actor);
            }
        }
        if (!Actor9.isEmpty()) {
            arc = Arrays.asList(Actor9.get(0).getElement2().split("#"));
            LengthMovie = Integer.parseInt(arc.get(2));
            for (Pair<Integer, Integer, java.lang.String> Actor : Actor9) {
                AllChrac.add(Actor);
            }
        }
        if (!Actor10.isEmpty()) {
            arc = Arrays.asList(Actor10.get(0).getElement2().split("#"));
            LengthMovie = Integer.parseInt(arc.get(2));
            for (Pair<Integer, Integer, java.lang.String> Actor : Actor10) {
                AllChrac.add(Actor);
            }
        }
        if (!Actor11.isEmpty()) {
            arc = Arrays.asList(Actor11.get(0).getElement2().split("#"));
            LengthMovie = Integer.parseInt(arc.get(2));
            for (Pair<Integer, Integer, java.lang.String> Actor : Actor11) {
                AllChrac.add(Actor);
            }
        }
        if (!Actor12.isEmpty()) {
            arc = Arrays.asList(Actor12.get(0).getElement2().split("#"));
            LengthMovie = Integer.parseInt(arc.get(2));
            for (Pair<Integer, Integer, java.lang.String> Actor : Actor12) {
                AllChrac.add(Actor);
            }
        }
        if (!Actor13.isEmpty()) {
            arc = Arrays.asList(Actor13.get(0).getElement2().split("#"));
            LengthMovie = Integer.parseInt(arc.get(2));
            for (Pair<Integer, Integer, java.lang.String> Actor : Actor13) {
                AllChrac.add(Actor);
            }
        }
        if (!Actor14.isEmpty()) {
            arc = Arrays.asList(Actor14.get(0).getElement2().split("#"));
            LengthMovie = Integer.parseInt(arc.get(2));
            for (Pair<Integer, Integer, java.lang.String> Actor : Actor14) {
                AllChrac.add(Actor);
            }
        }
        if (!Actor15.isEmpty()) {
            arc = Arrays.asList(Actor15.get(0).getElement2().split("#"));
            LengthMovie = Integer.parseInt(arc.get(2));
            for (Pair<Integer, Integer, java.lang.String> Actor : Actor15) {
                AllChrac.add(Actor);
            }
        }
        if (!Actor16.isEmpty()) {
            arc = Arrays.asList(Actor16.get(0).getElement2().split("#"));
            LengthMovie = Integer.parseInt(arc.get(2));
            for (Pair<Integer, Integer, java.lang.String> Actor : Actor16) {
                AllChrac.add(Actor);
            }
        }
        if (!Actor17.isEmpty()) {
            arc = Arrays.asList(Actor17.get(0).getElement2().split("#"));
            LengthMovie = Integer.parseInt(arc.get(2));
            for (Pair<Integer, Integer, java.lang.String> Actor : Actor17) {
                AllChrac.add(Actor);
            }
        }
        if (!Actor18.isEmpty()) {
            arc = Arrays.asList(Actor18.get(0).getElement2().split("#"));
            for (Pair<Integer, Integer, java.lang.String> Actor : Actor18) {
                AllChrac.add(Actor);
            }
        }
        if (!Actor19.isEmpty()) {
            arc = Arrays.asList(Actor19.get(0).getElement2().split("#"));
            LengthMovie = Integer.parseInt(arc.get(2));
            for (Pair<Integer, Integer, java.lang.String> Actor : Actor19) {
                AllChrac.add(Actor);
            }
        }
        if (!Actor20.isEmpty()) {
            arc = Arrays.asList(Actor20.get(0).getElement2().split("#"));
            LengthMovie = Integer.parseInt(arc.get(2));
            for (Pair<Integer, Integer, java.lang.String> Actor : Actor20) {
                AllChrac.add(Actor);
            }
        }
        if (count == null) {
            count = new String[(int) LengthMovie];
        }
        //for (int i = 0; i < count.length; i++) {count[i] = "0";}        
        List<String> tmpChar = new ArrayList<>();
        for (int i = 0; i < AllChrac.size(); i++) {
            for (int j = (int) AllChrac.get(i).getElement0(); j <= (int) AllChrac.get(i).getElement1(); j++) {
                tmpChar = Arrays.asList(AllChrac.get(i).getElement2().split("#"));
                if (count[j] == null) {
                    if ("0".equals(tmpChar.get(0))) {
                        count[j] = "0";
                    }
                    if ("a".equals(tmpChar.get(0))) {
                        count[j] = "10";
                    }
                    if ("b".equals(tmpChar.get(0))) {
                        count[j] = "11";
                    }
                    if ("c".equals(tmpChar.get(0))) {
                        count[j] = "12";
                    }
                    if ("d".equals(tmpChar.get(0))) {
                        count[j] = "13";
                    }
                    if ("e".equals(tmpChar.get(0))) {
                        count[j] = "14";
                    }
                    if ("f".equals(tmpChar.get(0))) {
                        count[j] = "15";
                    }
                    if ("g".equals(tmpChar.get(0))) {
                        count[j] = "16";
                    }
                    if ("h".equals(tmpChar.get(0))) {
                        count[j] = "17";
                    }
                    if ("i".equals(tmpChar.get(0))) {
                        count[j] = "18";
                    }
                    if ("j".equals(tmpChar.get(0))) {
                        count[j] = "19";
                    }
                    if (checkchar.contains(tmpChar.get(0))) {
                        count[j] = tmpChar.get(0);
                    }

                } else {
                    if ("0".equals(tmpChar.get(0))) {
                        count[j] = count[j] + "#" + "0";
                    }
                    if ("a".equals(tmpChar.get(0))) {
                        count[j] = count[j] + "#" + "10";
                    }
                    if ("b".equals(tmpChar.get(0))) {
                        count[j] = count[j] + "#" + "11";
                    }
                    if ("c".equals(tmpChar.get(0))) {
                        count[j] = count[j] + "#" + "12";
                    }
                    if ("d".equals(tmpChar.get(0))) {
                        count[j] = count[j] + "#" + "13";
                    }
                    if ("e".equals(tmpChar.get(0))) {
                        count[j] = count[j] + "#" + "14";
                    }
                    if ("f".equals(tmpChar.get(0))) {
                        count[j] = count[j] + "#" + "15";
                    }
                    if ("g".equals(tmpChar.get(0))) {
                        count[j] = count[j] + "#" + "16";
                    }
                    if ("h".equals(tmpChar.get(0))) {
                        count[j] = count[j] + "#" + "17";
                    }
                    if ("i".equals(tmpChar.get(0))) {
                        count[j] = count[j] + "#" + "18";
                    }
                    if ("j".equals(tmpChar.get(0))) {
                        count[j] = count[j] + "#" + "19";
                    }
                    if (checkchar.contains(tmpChar.get(0))) {
                        count[j] = count[j] + "#" + tmpChar.get(0);
                    }
                }
            }
        }

        ClearRelationMatrix();

        //calculate weigth
        List<String> str = new ArrayList<>();
        //Set<String> uniqueList = new HashSet<String[]>(count[]);
        for (java.lang.String count1 : count) {
            if (count1 != null) {
                str.add(count1);
                //System.out.println(count[i].toString());
            }
        }
        Set<String> uniqueList = new HashSet<>(str);
        for (String tmp : uniqueList) {
            str = Arrays.asList(tmp.split("#"));
            for (int i = 0; i < Relation[0].length; i++) {
                if ((tmp.contains(String.valueOf(i)))) {
                    for (int k = 0; k < str.size(); k++) {
                        Relation[i][Integer.parseInt(str.get(k))] = 1;
                        Relation[Integer.parseInt(str.get(k))][i] = 1;
                    }
                }
            }
        }

    }

    private void calculatecoocur() {
        List<String> str = new ArrayList<>();
        //occurtime = new int[20][20];
        for (java.lang.String count1 : count) {
            if ((count1 != null) && (count1.length() > 1)) {
                str = Arrays.asList(count1.split("#"));
                if ((str.size() > 1) && str.contains(String.valueOf("0"))) {
                    ch0.add(count1);
                }
                if ((str.size() > 1) && str.contains(String.valueOf("1"))) {
                    ch1.add(count1);
                }
                if ((str.size() > 1) && str.contains(String.valueOf("2"))) {
                    ch2.add(count1);
                }
                if ((str.size() > 1) && str.contains(String.valueOf("3"))) {
                    ch3.add(count1);
                }
                if ((str.size() > 1) && str.contains(String.valueOf("4"))) {
                    ch4.add(count1);
                }
                if ((str.size() > 1) && str.contains(String.valueOf("5"))) {
                    ch5.add(count1);
                }
                if ((str.size() > 1) && str.contains(String.valueOf("6"))) {
                    ch6.add(count1);
                }
                if ((str.size() > 1) && str.contains(String.valueOf("7"))) {
                    ch7.add(count1);
                }
                if ((str.size() > 1) && str.contains(String.valueOf("8"))) {
                    ch8.add(count1);
                }
                if ((str.size() > 1) && str.contains(String.valueOf("9"))) {
                    ch9.add(count1);
                }
                if ((str.size() > 1) && str.contains(String.valueOf("10"))) {
                    ch10.add(count1);
                }
                if ((str.size() > 1) && str.contains(String.valueOf("11"))) {
                    ch11.add(count1);
                }
                if ((str.size() > 1) && str.contains(String.valueOf("12"))) {
                    ch12.add(count1);
                }
                if ((str.size() > 1) && str.contains(String.valueOf("13"))) {
                    ch13.add(count1);
                }
                if ((str.size() > 1) && str.contains(String.valueOf("14"))) {
                    ch14.add(count1);
                }
                if ((str.size() > 1) && str.contains(String.valueOf("15"))) {
                    ch15.add(count1);
                }
                if ((str.size() > 1) && str.contains(String.valueOf("16"))) {
                    ch16.add(count1);
                }
                if ((str.size() > 1) && str.contains(String.valueOf("17"))) {
                    ch17.add(count1);
                }
                if ((str.size() > 1) && str.contains(String.valueOf("18"))) {
                    ch18.add(count1);
                }
                if ((str.size() > 1) && str.contains(String.valueOf("19"))) {
                    ch19.add(count1);
                }
            }
        }
        if (ch0.size() > 0) {
            int countc2 = 0, countc3 = 0, countc4 = 0, countc5 = 0, countc0 = 0,
                    countc6 = 0, countc7 = 0, countc8 = 0, countc9 = 0, countc10 = 0, countc11 = 0, countc12 = 0, countc13 = 0, countc14 = 0, countc15 = 0, countc16 = 0, countc17 = 0, countc18 = 0, countc19 = 0;
            for (int i = 0; i < ch0.size(); i++) {
                {
                    str = Arrays.asList(ch0.get(i).split("#"));
                    if (str.contains("1")) {
                        countc0 = countc0 + 1;
                    }
                    if (str.contains("2")) {
                        countc2 = countc2 + 1;
                    }
                    if (str.contains("3")) {
                        countc3 = countc3 + 1;
                    }
                    if (str.contains("4")) {
                        countc4 = countc4 + 1;
                    }
                    if (str.contains("5")) {
                        countc5 = countc5 + 1;
                    }
                    if (str.contains("6")) {
                        countc6 = countc6 + 1;
                    }
                    if (str.contains("7")) {
                        countc7 = countc7 + 1;
                    }
                    if (str.contains("8")) {
                        countc8 = countc8 + 1;
                    }
                    if (str.contains("9")) {
                        countc9 = countc9 + 1;
                    }
                    if (str.contains("10")) {
                        countc10 = countc10 + 1;
                    }
                    if (str.contains("11")) {
                        countc11 = countc11 + 1;
                    }
                    if (str.contains("12")) {
                        countc12 = countc12 + 1;
                    }
                    if (str.contains("13")) {
                        countc13 = countc13 + 1;
                    }
                    if (str.contains("14")) {
                        countc14 = countc14 + 1;
                    }
                    if (str.contains("15")) {
                        countc15 = countc15 + 1;
                    }
                    if (str.contains("16")) {
                        countc16 = countc16 + 1;
                    }
                    if (str.contains("17")) {
                        countc17 = countc17 + 1;
                    }
                    if (str.contains("18")) {
                        countc18 = countc18 + 1;
                    }
                    if (str.contains("19")) {
                        countc19 = countc19 + 1;
                    }
                }
            }
            occurtime[0][1] = countc0;
            occurtime[1][0] = countc0;
            occurtime[0][2] = countc2;
            occurtime[2][0] = countc2;
            occurtime[0][3] = countc3;
            occurtime[3][0] = countc3;
            occurtime[0][4] = countc4;
            occurtime[4][0] = countc4;
            occurtime[0][5] = countc5;
            occurtime[5][0] = countc5;
            occurtime[0][6] = countc6;
            occurtime[6][0] = countc6;
            occurtime[0][7] = countc7;
            occurtime[7][0] = countc7;
            occurtime[0][8] = countc8;
            occurtime[8][0] = countc8;
            occurtime[0][9] = countc9;
            occurtime[9][0] = countc9;
            occurtime[0][10] = countc10;
            occurtime[10][0] = countc10;
            occurtime[0][11] = countc11;
            occurtime[11][0] = countc11;
            occurtime[0][12] = countc12;
            occurtime[12][0] = countc12;
            occurtime[0][13] = countc13;
            occurtime[13][0] = countc13;
            occurtime[0][14] = countc14;
            occurtime[14][0] = countc14;
            occurtime[0][15] = countc15;
            occurtime[15][0] = countc15;
            occurtime[0][16] = countc16;
            occurtime[16][0] = countc16;
            occurtime[0][17] = countc17;
            occurtime[17][0] = countc17;
            occurtime[0][18] = countc18;
            occurtime[18][0] = countc18;
            occurtime[0][19] = countc19;
            occurtime[19][0] = countc19;
        }
        if (ch1.size() > 0) {
            int countc2 = 0, countc3 = 0, countc4 = 0, countc5 = 0, countc0 = 0,
                    countc6 = 0, countc7 = 0, countc8 = 0, countc9 = 0, countc10 = 0, countc11 = 0, countc12 = 0, countc13 = 0, countc14 = 0, countc15 = 0, countc16 = 0, countc17 = 0, countc18 = 0, countc19 = 0;
            for (int i = 0; i < ch1.size(); i++) {
                {
                    str = Arrays.asList(ch1.get(i).split("#"));
                    if (str.contains("0")) {
                        countc0 = countc0 + 1;
                    }
                    if (str.contains("2")) {
                        countc2 = countc2 + 1;
                    }
                    if (str.contains("3")) {
                        countc3 = countc3 + 1;
                    }
                    if (str.contains("4")) {
                        countc4 = countc4 + 1;
                    }
                    if (str.contains("5")) {
                        countc5 = countc5 + 1;
                    }
                    if (str.contains("6")) {
                        countc6 = countc6 + 1;
                    }
                    if (str.contains("7")) {
                        countc7 = countc7 + 1;
                    }
                    if (str.contains("8")) {
                        countc8 = countc8 + 1;
                    }
                    if (str.contains("9")) {
                        countc9 = countc9 + 1;
                    }
                    if (str.contains("10")) {
                        countc10 = countc10 + 1;
                    }
                    if (str.contains("11")) {
                        countc11 = countc11 + 1;
                    }
                    if (str.contains("12")) {
                        countc12 = countc12 + 1;
                    }
                    if (str.contains("13")) {
                        countc13 = countc13 + 1;
                    }
                    if (str.contains("14")) {
                        countc14 = countc14 + 1;
                    }
                    if (str.contains("15")) {
                        countc15 = countc15 + 1;
                    }
                    if (str.contains("16")) {
                        countc16 = countc16 + 1;
                    }
                    if (str.contains("17")) {
                        countc17 = countc17 + 1;
                    }
                    if (str.contains("18")) {
                        countc18 = countc18 + 1;
                    }
                    if (str.contains("19")) {
                        countc19 = countc19 + 1;
                    }
                }
            }

            occurtime[1][0] = countc0;
            occurtime[0][1] = countc0;
            occurtime[1][2] = countc2;
            occurtime[2][1] = countc2;
            occurtime[1][3] = countc3;
            occurtime[3][1] = countc3;
            occurtime[1][4] = countc4;
            occurtime[4][1] = countc4;
            occurtime[1][5] = countc5;
            occurtime[5][1] = countc5;
            occurtime[1][6] = countc6;
            occurtime[6][1] = countc6;
            occurtime[1][7] = countc7;
            occurtime[7][1] = countc7;
            occurtime[1][8] = countc8;
            occurtime[8][1] = countc8;
            occurtime[1][9] = countc9;
            occurtime[9][1] = countc9;
            occurtime[1][10] = countc10;
            occurtime[10][1] = countc10;
            occurtime[1][11] = countc11;
            occurtime[11][1] = countc11;
            occurtime[1][12] = countc12;
            occurtime[12][1] = countc12;
            occurtime[1][13] = countc13;
            occurtime[13][1] = countc13;
            occurtime[1][14] = countc14;
            occurtime[14][1] = countc14;
            occurtime[1][15] = countc15;
            occurtime[15][1] = countc15;
            occurtime[1][16] = countc16;
            occurtime[16][1] = countc16;
            occurtime[1][17] = countc17;
            occurtime[17][1] = countc17;
            occurtime[1][18] = countc18;
            occurtime[18][1] = countc18;
            occurtime[1][19] = countc11;
            occurtime[19][1] = countc11;
        }

        if (ch2.size() > 0) {
            int countc2 = 0, countc3 = 0, countc4 = 0, countc5 = 0, countc0 = 0,
                    countc6 = 0, countc7 = 0, countc8 = 0, countc9 = 0, countc10 = 0, countc11 = 0, countc12 = 0, countc13 = 0, countc14 = 0, countc15 = 0, countc16 = 0, countc17 = 0, countc18 = 0, countc19 = 0;
            for (int i = 0; i < ch2.size(); i++) {
                {
                    str = Arrays.asList(ch2.get(i).split("#"));
                    if (str.contains("0")) {
                        countc0 = countc0 + 1;
                    }
                    if (str.contains("3")) {
                        countc3 = countc3 + 1;
                    }
                    if (str.contains("4")) {
                        countc4 = countc4 + 1;
                    }
                    if (str.contains("5")) {
                        countc5 = countc5 + 1;
                    }
                    if (str.contains("6")) {
                        countc6 = countc6 + 1;
                    }
                    if (str.contains("7")) {
                        countc7 = countc7 + 1;
                    }
                    if (str.contains("8")) {
                        countc8 = countc8 + 1;
                    }
                    if (str.contains("9")) {
                        countc9 = countc9 + 1;
                    }
                    if (str.contains("10")) {
                        countc10 = countc10 + 1;
                    }
                    if (str.contains("11")) {
                        countc11 = countc11 + 1;
                    }
                    if (str.contains("12")) {
                        countc12 = countc12 + 1;
                    }
                    if (str.contains("13")) {
                        countc13 = countc13 + 1;
                    }
                    if (str.contains("14")) {
                        countc14 = countc14 + 1;
                    }
                    if (str.contains("15")) {
                        countc15 = countc15 + 1;
                    }
                    if (str.contains("16")) {
                        countc16 = countc16 + 1;
                    }
                    if (str.contains("17")) {
                        countc17 = countc17 + 1;
                    }
                    if (str.contains("18")) {
                        countc18 = countc18 + 1;
                    }
                    if (str.contains("19")) {
                        countc19 = countc19 + 1;
                    }
                }
            }
            occurtime[2][0] = countc0;
            occurtime[0][2] = countc0;
            occurtime[2][3] = countc3;
            occurtime[3][2] = countc3;
            occurtime[2][4] = countc4;
            occurtime[4][2] = countc4;
            occurtime[2][5] = countc5;
            occurtime[5][2] = countc5;
            occurtime[2][6] = countc6;
            occurtime[6][2] = countc6;
            occurtime[2][7] = countc7;
            occurtime[7][2] = countc7;
            occurtime[2][8] = countc8;
            occurtime[8][2] = countc8;
            occurtime[2][9] = countc9;
            occurtime[9][2] = countc9;
            occurtime[2][10] = countc10;
            occurtime[10][2] = countc10;
            occurtime[2][11] = countc11;
            occurtime[11][2] = countc11;
            occurtime[2][12] = countc12;
            occurtime[12][2] = countc12;
            occurtime[2][13] = countc13;
            occurtime[13][2] = countc13;
            occurtime[2][14] = countc14;
            occurtime[14][2] = countc14;
            occurtime[2][15] = countc15;
            occurtime[15][2] = countc15;
            occurtime[2][16] = countc16;
            occurtime[16][2] = countc16;
            occurtime[2][17] = countc17;
            occurtime[17][2] = countc17;
            occurtime[2][18] = countc18;
            occurtime[18][2] = countc18;
            occurtime[2][19] = countc19;
            occurtime[19][2] = countc19;
        }

        if (ch3.size() > 0) {
            int countc2 = 0, countc3 = 0, countc4 = 0, countc5 = 0, countc0 = 0,
                    countc6 = 0, countc7 = 0, countc8 = 0, countc9 = 0, countc10 = 0, countc11 = 0, countc12 = 0, countc13 = 0, countc14 = 0, countc15 = 0, countc16 = 0, countc17 = 0, countc18 = 0, countc19 = 0;
            for (int i = 0; i < ch3.size(); i++) {
                {
                    str = Arrays.asList(ch3.get(i).split("#"));
                    if (str.contains("4")) {
                        countc4 = countc4 + 1;
                    }
                    if (str.contains("5")) {
                        countc5 = countc5 + 1;
                    }
                    if (str.contains("6")) {
                        countc6 = countc6 + 1;
                    }
                    if (str.contains("7")) {
                        countc7 = countc7 + 1;
                    }
                    if (str.contains("8")) {
                        countc8 = countc8 + 1;
                    }
                    if (str.contains("9")) {
                        countc9 = countc9 + 1;
                    }
                    if (str.contains("10")) {
                        countc10 = countc10 + 1;
                    }
                    if (str.contains("11")) {
                        countc11 = countc11 + 1;
                    }
                    if (str.contains("12")) {
                        countc12 = countc12 + 1;
                    }
                    if (str.contains("13")) {
                        countc13 = countc13 + 1;
                    }
                    if (str.contains("14")) {
                        countc14 = countc14 + 1;
                    }
                    if (str.contains("15")) {
                        countc15 = countc15 + 1;
                    }
                    if (str.contains("16")) {
                        countc16 = countc16 + 1;
                    }
                    if (str.contains("17")) {
                        countc17 = countc17 + 1;
                    }
                    if (str.contains("18")) {
                        countc18 = countc18 + 1;
                    }
                    if (str.contains("19")) {
                        countc19 = countc19 + 1;
                    }
                }
            }

            occurtime[3][4] = countc4;
            occurtime[4][3] = countc4;
            occurtime[3][5] = countc5;
            occurtime[5][3] = countc5;
            occurtime[3][6] = countc6;
            occurtime[6][3] = countc6;
            occurtime[3][7] = countc7;
            occurtime[7][3] = countc7;
            occurtime[3][8] = countc8;
            occurtime[8][3] = countc8;
            occurtime[3][9] = countc9;
            occurtime[9][3] = countc9;
            occurtime[3][10] = countc10;
            occurtime[10][3] = countc10;
            occurtime[3][11] = countc11;
            occurtime[11][3] = countc11;
            occurtime[3][12] = countc12;
            occurtime[12][3] = countc12;
            occurtime[3][13] = countc13;
            occurtime[13][3] = countc13;
            occurtime[3][14] = countc14;
            occurtime[14][3] = countc14;
            occurtime[3][15] = countc15;
            occurtime[15][3] = countc15;
            occurtime[3][16] = countc16;
            occurtime[16][3] = countc16;
            occurtime[3][17] = countc17;
            occurtime[17][3] = countc17;
            occurtime[3][18] = countc18;
            occurtime[18][3] = countc18;
            occurtime[3][19] = countc19;
            occurtime[19][3] = countc19;
        }

        if (ch4.size() > 0) {
            int countc2 = 0, countc3 = 0, countc4 = 0, countc5 = 0, countc0 = 0,
                    countc6 = 0, countc7 = 0, countc8 = 0, countc9 = 0, countc10 = 0, countc11 = 0, countc12 = 0, countc13 = 0, countc14 = 0, countc15 = 0, countc16 = 0, countc17 = 0, countc18 = 0, countc19 = 0;
            for (int i = 0; i < ch4.size(); i++) {
                {
                    str = Arrays.asList(ch4.get(i).split("#"));
                    if (str.contains("5")) {
                        countc5 = countc5 + 1;
                    }
                    if (str.contains("6")) {
                        countc6 = countc6 + 1;
                    }
                    if (str.contains("7")) {
                        countc7 = countc7 + 1;
                    }
                    if (str.contains("8")) {
                        countc8 = countc8 + 1;
                    }
                    if (str.contains("9")) {
                        countc9 = countc9 + 1;
                    }
                    if (str.contains("10")) {
                        countc10 = countc10 + 1;
                    }
                    if (str.contains("11")) {
                        countc11 = countc11 + 1;
                    }
                    if (str.contains("12")) {
                        countc12 = countc12 + 1;
                    }
                    if (str.contains("13")) {
                        countc13 = countc13 + 1;
                    }
                    if (str.contains("14")) {
                        countc14 = countc14 + 1;
                    }
                    if (str.contains("15")) {
                        countc15 = countc15 + 1;
                    }
                    if (str.contains("16")) {
                        countc16 = countc16 + 1;
                    }
                    if (str.contains("17")) {
                        countc17 = countc17 + 1;
                    }
                    if (str.contains("18")) {
                        countc18 = countc18 + 1;
                    }
                    if (str.contains("19")) {
                        countc19 = countc19 + 1;
                    }
                }
            }

            occurtime[4][5] = countc5;
            occurtime[5][4] = countc5;
            occurtime[4][6] = countc6;
            occurtime[6][4] = countc6;
            occurtime[4][7] = countc7;
            occurtime[7][4] = countc7;
            occurtime[4][8] = countc8;
            occurtime[8][4] = countc8;
            occurtime[4][9] = countc9;
            occurtime[9][4] = countc9;
            occurtime[4][10] = countc10;
            occurtime[10][4] = countc10;
            occurtime[4][11] = countc11;
            occurtime[11][4] = countc11;
            occurtime[4][12] = countc12;
            occurtime[12][4] = countc12;
            occurtime[4][13] = countc13;
            occurtime[13][4] = countc13;
            occurtime[4][14] = countc14;
            occurtime[14][4] = countc14;
            occurtime[4][15] = countc15;
            occurtime[15][4] = countc15;
            occurtime[4][16] = countc16;
            occurtime[16][4] = countc16;
            occurtime[4][17] = countc17;
            occurtime[17][4] = countc17;
            occurtime[4][18] = countc18;
            occurtime[18][4] = countc18;
            occurtime[4][19] = countc19;
            occurtime[19][4] = countc19;

        }

        if (ch5.size() > 0) {
            int countc2 = 0, countc3 = 0, countc4 = 0, countc5 = 0, countc0 = 0,
                    countc6 = 0, countc7 = 0, countc8 = 0, countc9 = 0, countc10 = 0, countc11 = 0, countc12 = 0, countc13 = 0, countc14 = 0, countc15 = 0, countc16 = 0, countc17 = 0, countc18 = 0, countc19 = 0;
            for (int i = 0; i < ch5.size(); i++) {
                {
                    str = Arrays.asList(ch5.get(i).split("#"));
                    if (str.contains("6")) {
                        countc6 = countc6 + 1;
                    }
                    if (str.contains("7")) {
                        countc7 = countc7 + 1;
                    }
                    if (str.contains("8")) {
                        countc8 = countc8 + 1;
                    }
                    if (str.contains("9")) {
                        countc9 = countc9 + 1;
                    }
                    if (str.contains("10")) {
                        countc10 = countc10 + 1;
                    }
                    if (str.contains("11")) {
                        countc11 = countc11 + 1;
                    }
                    if (str.contains("12")) {
                        countc12 = countc12 + 1;
                    }
                    if (str.contains("13")) {
                        countc13 = countc13 + 1;
                    }
                    if (str.contains("14")) {
                        countc14 = countc14 + 1;
                    }
                    if (str.contains("15")) {
                        countc15 = countc15 + 1;
                    }
                    if (str.contains("16")) {
                        countc16 = countc16 + 1;
                    }
                    if (str.contains("17")) {
                        countc17 = countc17 + 1;
                    }
                    if (str.contains("18")) {
                        countc18 = countc18 + 1;
                    }
                    if (str.contains("19")) {
                        countc19 = countc19 + 1;
                    }
                }
            }

            occurtime[5][6] = countc6;
            occurtime[6][5] = countc6;
            occurtime[5][7] = countc7;
            occurtime[7][5] = countc7;
            occurtime[5][8] = countc8;
            occurtime[8][5] = countc8;
            occurtime[5][9] = countc9;
            occurtime[9][5] = countc9;
            occurtime[5][10] = countc10;
            occurtime[10][5] = countc10;
            occurtime[5][11] = countc11;
            occurtime[11][5] = countc11;
            occurtime[5][12] = countc12;
            occurtime[12][5] = countc12;
            occurtime[5][13] = countc13;
            occurtime[13][5] = countc13;
            occurtime[5][14] = countc14;
            occurtime[14][5] = countc14;
            occurtime[5][15] = countc15;
            occurtime[15][5] = countc15;
            occurtime[5][16] = countc16;
            occurtime[16][5] = countc16;
            occurtime[5][17] = countc17;
            occurtime[17][5] = countc17;
            occurtime[5][18] = countc18;
            occurtime[18][5] = countc18;
            occurtime[5][19] = countc19;
            occurtime[19][5] = countc19;

        }

        if (ch6.size() > 0) {
            int countc2 = 0, countc3 = 0, countc4 = 0, countc5 = 0, countc0 = 0,
                    countc6 = 0, countc7 = 0, countc8 = 0, countc9 = 0, countc10 = 0, countc11 = 0, countc12 = 0, countc13 = 0, countc14 = 0, countc15 = 0, countc16 = 0, countc17 = 0, countc18 = 0, countc19 = 0;
            for (int i = 0; i < ch6.size(); i++) {
                {
                    str = Arrays.asList(ch6.get(i).split("#"));
                    if (str.contains("7")) {
                        countc7 = countc7 + 1;
                    }
                    if (str.contains("8")) {
                        countc8 = countc8 + 1;
                    }
                    if (str.contains("9")) {
                        countc9 = countc9 + 1;
                    }
                    if (str.contains("10")) {
                        countc10 = countc10 + 1;
                    }
                    if (str.contains("11")) {
                        countc11 = countc11 + 1;
                    }
                    if (str.contains("12")) {
                        countc12 = countc12 + 1;
                    }
                    if (str.contains("13")) {
                        countc13 = countc13 + 1;
                    }
                    if (str.contains("14")) {
                        countc14 = countc14 + 1;
                    }
                    if (str.contains("15")) {
                        countc15 = countc15 + 1;
                    }
                    if (str.contains("16")) {
                        countc16 = countc16 + 1;
                    }
                    if (str.contains("17")) {
                        countc17 = countc17 + 1;
                    }
                    if (str.contains("18")) {
                        countc18 = countc18 + 1;
                    }
                    if (str.contains("19")) {
                        countc19 = countc19 + 1;
                    }
                }
            }

            occurtime[6][7] = countc7;
            occurtime[7][6] = countc7;
            occurtime[6][8] = countc8;
            occurtime[8][6] = countc8;
            occurtime[6][9] = countc9;
            occurtime[9][6] = countc9;
            occurtime[6][10] = countc10;
            occurtime[10][6] = countc10;
            occurtime[6][11] = countc11;
            occurtime[11][6] = countc11;
            occurtime[6][12] = countc12;
            occurtime[12][6] = countc12;
            occurtime[6][13] = countc13;
            occurtime[13][6] = countc13;
            occurtime[6][14] = countc14;
            occurtime[14][6] = countc14;
            occurtime[6][15] = countc15;
            occurtime[15][6] = countc15;
            occurtime[6][16] = countc16;
            occurtime[16][6] = countc16;
            occurtime[6][17] = countc17;
            occurtime[17][6] = countc17;
            occurtime[6][18] = countc18;
            occurtime[18][6] = countc18;
            occurtime[6][19] = countc19;
            occurtime[19][6] = countc19;
        }

        if (ch7.size() > 0) {
            int countc2 = 0, countc3 = 0, countc4 = 0, countc5 = 0, countc0 = 0,
                    countc6 = 0, countc7 = 0, countc8 = 0, countc9 = 0, countc10 = 0, countc11 = 0, countc12 = 0, countc13 = 0, countc14 = 0, countc15 = 0, countc16 = 0, countc17 = 0, countc18 = 0, countc19 = 0;
            for (int i = 0; i < ch7.size(); i++) {
                {
                    str = Arrays.asList(ch7.get(i).split("#"));
                    if (str.contains("8")) {
                        countc8 = countc8 + 1;
                    }
                    if (str.contains("9")) {
                        countc9 = countc9 + 1;
                    }
                    if (str.contains("10")) {
                        countc10 = countc10 + 1;
                    }
                    if (str.contains("11")) {
                        countc11 = countc11 + 1;
                    }
                    if (str.contains("12")) {
                        countc12 = countc12 + 1;
                    }
                    if (str.contains("13")) {
                        countc13 = countc13 + 1;
                    }
                    if (str.contains("14")) {
                        countc14 = countc14 + 1;
                    }
                    if (str.contains("15")) {
                        countc15 = countc15 + 1;
                    }
                    if (str.contains("16")) {
                        countc16 = countc16 + 1;
                    }
                    if (str.contains("17")) {
                        countc17 = countc17 + 1;
                    }
                    if (str.contains("18")) {
                        countc18 = countc18 + 1;
                    }
                    if (str.contains("19")) {
                        countc19 = countc19 + 1;
                    }
                }
            }

            occurtime[7][8] = countc8;
            occurtime[8][7] = countc8;
            occurtime[7][9] = countc9;
            occurtime[9][7] = countc9;
            occurtime[7][10] = countc10;
            occurtime[10][7] = countc10;
            occurtime[7][11] = countc11;
            occurtime[11][7] = countc11;
            occurtime[7][12] = countc12;
            occurtime[12][7] = countc12;
            occurtime[7][13] = countc13;
            occurtime[13][7] = countc13;
            occurtime[7][14] = countc14;
            occurtime[14][7] = countc14;
            occurtime[7][15] = countc15;
            occurtime[15][7] = countc15;
            occurtime[7][16] = countc16;
            occurtime[16][7] = countc16;
            occurtime[7][17] = countc17;
            occurtime[17][7] = countc17;
            occurtime[7][18] = countc18;
            occurtime[18][7] = countc18;
            occurtime[7][19] = countc19;
            occurtime[19][7] = countc19;

        }

        if (ch8.size() > 0) {
            int countc2 = 0, countc3 = 0, countc4 = 0, countc5 = 0, countc0 = 0,
                    countc6 = 0, countc7 = 0, countc8 = 0, countc9 = 0, countc10 = 0, countc11 = 0, countc12 = 0, countc13 = 0, countc14 = 0, countc15 = 0, countc16 = 0, countc17 = 0, countc18 = 0, countc19 = 0;
            for (int i = 0; i < ch8.size(); i++) {
                {
                    str = Arrays.asList(ch8.get(i).split("#"));

                    if (str.contains("9")) {
                        countc9 = countc9 + 1;
                    }
                    if (str.contains("10")) {
                        countc10 = countc10 + 1;
                    }
                    if (str.contains("11")) {
                        countc11 = countc11 + 1;
                    }
                    if (str.contains("12")) {
                        countc12 = countc12 + 1;
                    }
                    if (str.contains("13")) {
                        countc13 = countc13 + 1;
                    }
                    if (str.contains("14")) {
                        countc14 = countc14 + 1;
                    }
                    if (str.contains("15")) {
                        countc15 = countc15 + 1;
                    }
                    if (str.contains("16")) {
                        countc16 = countc16 + 1;
                    }
                    if (str.contains("17")) {
                        countc17 = countc17 + 1;
                    }
                    if (str.contains("18")) {
                        countc18 = countc18 + 1;
                    }
                    if (str.contains("19")) {
                        countc19 = countc19 + 1;
                    }
                }
            }

            occurtime[8][9] = countc9;
            occurtime[9][8] = countc9;
            occurtime[8][10] = countc10;
            occurtime[10][8] = countc10;
            occurtime[8][11] = countc11;
            occurtime[11][8] = countc11;
            occurtime[8][12] = countc12;
            occurtime[12][8] = countc12;
            occurtime[8][13] = countc13;
            occurtime[13][8] = countc13;
            occurtime[8][14] = countc14;
            occurtime[14][8] = countc14;
            occurtime[8][15] = countc15;
            occurtime[15][8] = countc15;
            occurtime[8][16] = countc16;
            occurtime[16][8] = countc16;
            occurtime[8][17] = countc17;
            occurtime[17][8] = countc17;
            occurtime[8][18] = countc18;
            occurtime[18][8] = countc18;
            occurtime[8][19] = countc19;
            occurtime[19][8] = countc19;

        }

        if (ch9.size() > 0) {
            int countc2 = 0, countc3 = 0, countc4 = 0, countc5 = 0, countc0 = 0,
                    countc6 = 0, countc7 = 0, countc8 = 0, countc9 = 0, countc10 = 0, countc11 = 0, countc12 = 0, countc13 = 0, countc14 = 0, countc15 = 0, countc16 = 0, countc17 = 0, countc18 = 0, countc19 = 0;
            for (int i = 0; i < ch9.size(); i++) {
                {
                    str = Arrays.asList(ch9.get(i).split("#"));

                    if (str.contains("10")) {
                        countc10 = countc10 + 1;
                    }
                    if (str.contains("11")) {
                        countc11 = countc11 + 1;
                    }
                    if (str.contains("12")) {
                        countc12 = countc12 + 1;
                    }
                    if (str.contains("13")) {
                        countc13 = countc13 + 1;
                    }
                    if (str.contains("14")) {
                        countc14 = countc14 + 1;
                    }
                    if (str.contains("15")) {
                        countc15 = countc15 + 1;
                    }
                    if (str.contains("16")) {
                        countc16 = countc16 + 1;
                    }
                    if (str.contains("17")) {
                        countc17 = countc17 + 1;
                    }
                    if (str.contains("18")) {
                        countc18 = countc18 + 1;
                    }
                    if (str.contains("19")) {
                        countc19 = countc19 + 1;
                    }
                }
            }

            occurtime[9][10] = countc10;
            occurtime[10][9] = countc10;
            occurtime[9][11] = countc11;
            occurtime[11][9] = countc11;
            occurtime[9][12] = countc12;
            occurtime[12][9] = countc12;
            occurtime[9][13] = countc13;
            occurtime[13][9] = countc13;
            occurtime[9][14] = countc14;
            occurtime[14][9] = countc14;
            occurtime[9][15] = countc15;
            occurtime[15][9] = countc15;
            occurtime[9][16] = countc16;
            occurtime[16][9] = countc16;
            occurtime[9][17] = countc17;
            occurtime[17][9] = countc17;
            occurtime[9][18] = countc18;
            occurtime[18][9] = countc18;
            occurtime[9][19] = countc19;
            occurtime[19][9] = countc19;

        }

        if (ch10.size() > 0) {
            int countc2 = 0, countc3 = 0, countc4 = 0, countc5 = 0, countc0 = 0,
                    countc6 = 0, countc7 = 0, countc8 = 0, countc9 = 0, countc10 = 0, countc11 = 0, countc12 = 0, countc13 = 0, countc14 = 0, countc15 = 0, countc16 = 0, countc17 = 0, countc18 = 0, countc19 = 0;
            for (int i = 0; i < ch10.size(); i++) {
                {
                    str = Arrays.asList(ch10.get(i).split("#"));
                    if (str.contains("11")) {
                        countc11 = countc11 + 1;
                    }
                    if (str.contains("12")) {
                        countc12 = countc12 + 1;
                    }
                    if (str.contains("13")) {
                        countc13 = countc13 + 1;
                    }
                    if (str.contains("14")) {
                        countc14 = countc14 + 1;
                    }
                    if (str.contains("15")) {
                        countc15 = countc15 + 1;
                    }
                    if (str.contains("16")) {
                        countc16 = countc16 + 1;
                    }
                    if (str.contains("17")) {
                        countc17 = countc17 + 1;
                    }
                    if (str.contains("18")) {
                        countc18 = countc18 + 1;
                    }
                    if (str.contains("19")) {
                        countc19 = countc19 + 1;
                    }
                }
            }

            occurtime[10][11] = countc11;
            occurtime[11][10] = countc11;
            occurtime[10][12] = countc12;
            occurtime[12][10] = countc12;
            occurtime[10][13] = countc13;
            occurtime[13][10] = countc13;
            occurtime[10][14] = countc14;
            occurtime[14][10] = countc14;
            occurtime[10][15] = countc15;
            occurtime[15][10] = countc15;
            occurtime[10][16] = countc16;
            occurtime[16][10] = countc16;
            occurtime[10][17] = countc17;
            occurtime[17][10] = countc17;
            occurtime[10][18] = countc18;
            occurtime[18][10] = countc18;
            occurtime[10][19] = countc19;
            occurtime[19][10] = countc19;

        }

        if (ch11.size() > 0) {
            int countc2 = 0, countc3 = 0, countc4 = 0, countc5 = 0, countc0 = 0,
                    countc6 = 0, countc7 = 0, countc8 = 0, countc9 = 0, countc10 = 0, countc11 = 0, countc12 = 0, countc13 = 0, countc14 = 0, countc15 = 0, countc16 = 0, countc17 = 0, countc18 = 0, countc19 = 0;
            for (int i = 0; i < ch11.size(); i++) {
                {
                    str = Arrays.asList(ch10.get(i).split("#"));
                    if (str.contains("12")) {
                        countc12 = countc12 + 1;
                    }
                    if (str.contains("13")) {
                        countc13 = countc13 + 1;
                    }
                    if (str.contains("14")) {
                        countc14 = countc14 + 1;
                    }
                    if (str.contains("15")) {
                        countc15 = countc15 + 1;
                    }
                    if (str.contains("16")) {
                        countc16 = countc16 + 1;
                    }
                    if (str.contains("17")) {
                        countc17 = countc17 + 1;
                    }
                    if (str.contains("18")) {
                        countc18 = countc18 + 1;
                    }
                    if (str.contains("19")) {
                        countc19 = countc19 + 1;
                    }
                }
            }

            occurtime[11][12] = countc12;
            occurtime[12][11] = countc12;
            occurtime[11][13] = countc13;
            occurtime[13][11] = countc13;
            occurtime[11][14] = countc14;
            occurtime[14][11] = countc14;
            occurtime[11][15] = countc15;
            occurtime[15][11] = countc15;
            occurtime[11][16] = countc16;
            occurtime[16][11] = countc16;
            occurtime[11][17] = countc17;
            occurtime[17][11] = countc17;
            occurtime[11][18] = countc18;
            occurtime[18][11] = countc18;
            occurtime[11][19] = countc19;
            occurtime[19][11] = countc19;

        }

        if (ch12.size() > 0) {
            int countc2 = 0, countc3 = 0, countc4 = 0, countc5 = 0, countc0 = 0,
                    countc6 = 0, countc7 = 0, countc8 = 0, countc9 = 0, countc10 = 0, countc11 = 0, countc12 = 0, countc13 = 0, countc14 = 0, countc15 = 0, countc16 = 0, countc17 = 0, countc18 = 0, countc19 = 0;
            for (int i = 0; i < ch12.size(); i++) {
                {
                    str = Arrays.asList(ch12.get(i).split("#"));

                    if (str.contains("13")) {
                        countc13 = countc13 + 1;
                    }
                    if (str.contains("14")) {
                        countc14 = countc14 + 1;
                    }
                    if (str.contains("15")) {
                        countc15 = countc15 + 1;
                    }
                    if (str.contains("16")) {
                        countc16 = countc16 + 1;
                    }
                    if (str.contains("17")) {
                        countc17 = countc17 + 1;
                    }
                    if (str.contains("18")) {
                        countc18 = countc18 + 1;
                    }
                    if (str.contains("19")) {
                        countc19 = countc19 + 1;
                    }
                }
            }

            occurtime[12][13] = countc13;
            occurtime[13][12] = countc13;
            occurtime[12][14] = countc14;
            occurtime[14][12] = countc14;
            occurtime[12][15] = countc15;
            occurtime[15][12] = countc15;
            occurtime[12][16] = countc16;
            occurtime[16][12] = countc16;
            occurtime[12][17] = countc17;
            occurtime[17][12] = countc17;
            occurtime[12][18] = countc18;
            occurtime[18][12] = countc18;
            occurtime[12][19] = countc19;
            occurtime[19][12] = countc19;

        }

        if (ch13.size() > 0) {
            int countc2 = 0, countc3 = 0, countc4 = 0, countc5 = 0, countc0 = 0,
                    countc6 = 0, countc7 = 0, countc8 = 0, countc9 = 0, countc10 = 0, countc11 = 0, countc12 = 0, countc13 = 0, countc14 = 0, countc15 = 0, countc16 = 0, countc17 = 0, countc18 = 0, countc19 = 0;
            for (int i = 0; i < ch13.size(); i++) {
                {
                    str = Arrays.asList(ch13.get(i).split("#"));

                    if (str.contains("14")) {
                        countc14 = countc14 + 1;
                    }
                    if (str.contains("15")) {
                        countc15 = countc15 + 1;
                    }
                    if (str.contains("16")) {
                        countc16 = countc16 + 1;
                    }
                    if (str.contains("17")) {
                        countc17 = countc17 + 1;
                    }
                    if (str.contains("18")) {
                        countc18 = countc18 + 1;
                    }
                    if (str.contains("19")) {
                        countc19 = countc19 + 1;
                    }
                }
            }

            occurtime[13][14] = countc14;
            occurtime[14][13] = countc14;
            occurtime[13][15] = countc15;
            occurtime[15][13] = countc15;
            occurtime[13][16] = countc16;
            occurtime[16][13] = countc16;
            occurtime[13][17] = countc17;
            occurtime[17][13] = countc17;
            occurtime[13][18] = countc18;
            occurtime[18][13] = countc18;
            occurtime[13][19] = countc19;
            occurtime[19][13] = countc19;

        }

        if (ch14.size() > 0) {
            int countc2 = 0, countc3 = 0, countc4 = 0, countc5 = 0, countc0 = 0,
                    countc6 = 0, countc7 = 0, countc8 = 0, countc9 = 0, countc10 = 0, countc11 = 0, countc12 = 0, countc13 = 0, countc14 = 0, countc15 = 0, countc16 = 0, countc17 = 0, countc18 = 0, countc19 = 0;
            for (int i = 0; i < ch14.size(); i++) {
                {
                    str = Arrays.asList(ch14.get(i).split("#"));
                    if (str.contains("15")) {
                        countc15 = countc15 + 1;
                    }
                    if (str.contains("16")) {
                        countc16 = countc16 + 1;
                    }
                    if (str.contains("17")) {
                        countc17 = countc17 + 1;
                    }
                    if (str.contains("18")) {
                        countc18 = countc18 + 1;
                    }
                    if (str.contains("19")) {
                        countc19 = countc19 + 1;
                    }
                }
            }

            occurtime[14][15] = countc15;
            occurtime[15][14] = countc15;
            occurtime[14][16] = countc16;
            occurtime[16][14] = countc16;
            occurtime[14][17] = countc17;
            occurtime[17][14] = countc17;
            occurtime[14][18] = countc18;
            occurtime[18][14] = countc18;
            occurtime[14][19] = countc19;
            occurtime[19][14] = countc19;
        }

        if (ch15.size() > 0) {
            int countc2 = 0, countc3 = 0, countc4 = 0, countc5 = 0, countc0 = 0,
                    countc6 = 0, countc7 = 0, countc8 = 0, countc9 = 0, countc10 = 0, countc11 = 0, countc12 = 0, countc13 = 0, countc14 = 0, countc15 = 0, countc16 = 0, countc17 = 0, countc18 = 0, countc19 = 0;
            for (int i = 0; i < ch15.size(); i++) {
                {
                    str = Arrays.asList(ch15.get(i).split("#"));
                    if (str.contains("16")) {
                        countc16 = countc16 + 1;
                    }
                    if (str.contains("17")) {
                        countc17 = countc17 + 1;
                    }
                    if (str.contains("18")) {
                        countc18 = countc18 + 1;
                    }
                    if (str.contains("19")) {
                        countc19 = countc19 + 1;
                    }
                }
            }

            occurtime[15][16] = countc16;
            occurtime[16][15] = countc16;
            occurtime[15][17] = countc17;
            occurtime[17][15] = countc17;
            occurtime[15][18] = countc18;
            occurtime[18][15] = countc18;
            occurtime[15][19] = countc19;
            occurtime[19][15] = countc19;
        }

        if (ch16.size() > 0) {
            int countc2 = 0, countc3 = 0, countc4 = 0, countc5 = 0, countc0 = 0,
                    countc6 = 0, countc7 = 0, countc8 = 0, countc9 = 0, countc10 = 0, countc11 = 0, countc12 = 0, countc13 = 0, countc14 = 0, countc15 = 0, countc16 = 0, countc17 = 0, countc18 = 0, countc19 = 0;
            for (int i = 0; i < ch16.size(); i++) {
                {
                    str = Arrays.asList(ch16.get(i).split("#"));
                    if (str.contains("17")) {
                        countc17 = countc17 + 1;
                    }
                    if (str.contains("18")) {
                        countc18 = countc18 + 1;
                    }
                    if (str.contains("19")) {
                        countc19 = countc19 + 1;
                    }
                }
            }

            occurtime[16][17] = countc17;
            occurtime[17][16] = countc17;
            occurtime[16][18] = countc18;
            occurtime[18][16] = countc18;
            occurtime[16][19] = countc19;
            occurtime[19][16] = countc19;

        }

        if (ch17.size() > 0) {
            int countc2 = 0, countc3 = 0, countc4 = 0, countc5 = 0, countc0 = 0,
                    countc6 = 0, countc7 = 0, countc8 = 0, countc9 = 0, countc10 = 0, countc11 = 0, countc12 = 0, countc13 = 0, countc14 = 0, countc15 = 0, countc16 = 0, countc17 = 0, countc18 = 0, countc19 = 0;
            for (int i = 0; i < ch17.size(); i++) {
                {
                    str = Arrays.asList(ch17.get(i).split("#"));
                    if (str.contains("18")) {
                        countc18 = countc18 + 1;
                    }
                    if (str.contains("19")) {
                        countc19 = countc19 + 1;
                    }
                }
            }

            occurtime[17][18] = countc18;
            occurtime[18][17] = countc18;
            occurtime[17][19] = countc19;
            occurtime[19][17] = countc19;

        }

        if (ch18.size() > 0) {
            int countc2 = 0, countc3 = 0, countc4 = 0, countc5 = 0, countc0 = 0,
                    countc6 = 0, countc7 = 0, countc8 = 0, countc9 = 0, countc10 = 0, countc11 = 0, countc12 = 0, countc13 = 0, countc14 = 0, countc15 = 0, countc16 = 0, countc17 = 0, countc18 = 0, countc19 = 0;
            for (int i = 0; i < ch18.size(); i++) {
                {
                    str = Arrays.asList(ch18.get(i).split("#"));
                    if (str.contains("19")) {
                        countc19 = countc19 + 1;
                    }
                }
            }
            occurtime[18][19] = countc19;
            occurtime[19][18] = countc19;
        }

        for (int i = 0; i < occurtime.length; i++) {
            for (int in = 0; in < occurtime.length; in++) {
                if (occurtime[i][in] < 10) {
                    occurtime[i][in] = occurtime[i][in]*10;
                }
                System.out.print(occurtime[i][in] + "#");
            }
            System.out.println("");
        }

    }

    private void CalculateFreqMeet() {
        List<String> str = new ArrayList<>();
        for (int i = 0; i < count.length - 1; i++) {
            if (count[i] != null) {
                str = Arrays.asList(count[i].split("#"));
                if (str.contains("0")) {
                    if (count[i + 1] == null) {

                    }
                    if (count[i + 1] != null) {
                        if (count[i + 1] != count[i]) {

                        }
                    }
                }
            }
        }
    }

    private void RepaintAll() {
        gui.revalidate();
        gui.repaint();
    }

    private void DellAcJpData() {
        List<String> tmp = new ArrayList<>();
        tmp = Arrays.asList(txtCondition.split("#"));
        if (tmp.size() > 0) {
            if ("1".equals(tmp.get(2))) {
                RemoveOnList(Actor1, pgAuthor1, txtCondition);
            }
        }
        if (tmp.size() > 0) {
            if ("2".equals(tmp.get(2))) {
                RemoveOnList(Actor2, pgAuthor2, txtCondition);
            }
        }
        if (tmp.size() > 0) {
            if ("3".equals(tmp.get(2))) {
                RemoveOnList(Actor3, pgAuthor3, txtCondition);
            }
        }
        if (tmp.size() > 0) {
            if ("4".equals(tmp.get(2))) {
                RemoveOnList(Actor4, pgAuthor4, txtCondition);
            }
        }
        if (tmp.size() > 0) {
            if ("5".equals(tmp.get(2))) {
                RemoveOnList(Actor5, pgAuthor5, txtCondition);
            }
        }
        if (tmp.size() > 0) {
            if ("6".equals(tmp.get(2))) {
                RemoveOnList(Actor6, pgAuthor6, txtCondition);
            }
        }
        if (tmp.size() > 0) {
            if ("7".equals(tmp.get(2))) {
                RemoveOnList(Actor7, pgAuthor7, txtCondition);
            }
        }
        if (tmp.size() > 0) {
            if ("8".equals(tmp.get(2))) {
                RemoveOnList(Actor8, pgAuthor8, txtCondition);
            }
        }
        if (tmp.size() > 0) {
            if ("9".equals(tmp.get(2))) {
                RemoveOnList(Actor9, pgAuthor9, txtCondition);
            }
        }
        if (tmp.size() > 0) {
            if ("0".equals(tmp.get(2))) {
                RemoveOnList(Actor10, pgAuthor10, txtCondition);
            }
        }
        if (tmp.size() > 0) {
            if ("q".equals(tmp.get(2).toLowerCase())) {
                RemoveOnList(Actor11, pgAuthor11, txtCondition);
            }
        }
        if (tmp.size() > 0) {
            if ("w".equals(tmp.get(2).toLowerCase())) {
                RemoveOnList(Actor12, pgAuthor12, txtCondition);
            }
        }
        if (tmp.size() > 0) {
            if ("e".equals(tmp.get(2).toLowerCase())) {
                RemoveOnList(Actor13, pgAuthor13, txtCondition);
            }
        }
        if (tmp.size() > 0) {
            if ("r".equals(tmp.get(2).toLowerCase())) {
                RemoveOnList(Actor14, pgAuthor14, txtCondition);
            }
        }
        if (tmp.size() > 0) {
            if ("t".equals(tmp.get(2).toLowerCase())) {
                RemoveOnList(Actor15, pgAuthor15, txtCondition);
            }
        }
        if (tmp.size() > 0) {
            if ("y".equals(tmp.get(2).toLowerCase())) {
                RemoveOnList(Actor16, pgAuthor16, txtCondition);
            }
        }
        if (tmp.size() > 0) {
            if ("u".equals(tmp.get(2).toLowerCase())) {
                RemoveOnList(Actor17, pgAuthor17, txtCondition);
            }
        }
        if (tmp.size() > 0) {
            if ("i".equals(tmp.get(2).toLowerCase())) {
                RemoveOnList(Actor18, pgAuthor18, txtCondition);
            }
        }
        if (tmp.size() > 0) {
            if ("o".equals(tmp.get(2).toLowerCase())) {
                RemoveOnList(Actor19, pgAuthor19, txtCondition);
            }
        }
        if (tmp.size() > 0) {
            if ("p".equals(tmp.get(2).toLowerCase())) {
                RemoveOnList(Actor20, pgAuthor20, txtCondition);
            }
        }
        txtCondition = "";
    }

    private void CreateProcessBarLoad(List<JProgressBar> tmpPrg, final Pair<Integer, Integer, String> Actor, JLabel label) {
        final JProgressBar jp = new JProgressBar();
        jp.setString(Actor.getElement0().toString() + "#" + Actor.getElement1().toString() + "#" + Actor.getElement2());
        String line = jp.getString();
        txtCondition = line;
        List<String> tmpChar = new ArrayList<>();
        tmpChar = Arrays.asList(line.split("#"));
        float timepos = Float.parseFloat(tmpChar.get(0)) / Float.parseFloat(tmpChar.get(tmpChar.size() - 1));
        float timeend = Float.parseFloat(tmpChar.get(1)) / Float.parseFloat(tmpChar.get(tmpChar.size() - 1));
        int posend = (int) (timeend * label.getWidth());
        int pos = (int) (timepos * label.getWidth());
        jp.setBounds(pos - 12, label.getY(), posend - pos, label.getHeight());
        //System.out.print(posend - pos);
        jp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jp.setToolTipText("Start: " + Actor.getElement0().toString() + " - End: " + Actor.getElement1().toString() + "Length: " + BasicModel.getInstance().ConvertTime((long) Actor.getElement1() - Actor.getElement0()));
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    String line = jp.getString();
                    txtCondition = line;
                    List<String> tmpChar = new ArrayList<>();
                    tmpChar = Arrays.asList(line.split("#"));
                    float timepos = Float.parseFloat(tmpChar.get(0)) / Float.parseFloat(tmpChar.get(tmpChar.size() - 1));
                    int pos = (int) (timepos * charac1.getWidth());
                    jlb.setBounds(pos, charac1.getY(), 1, 500);
                    jlb.setBackground(Color.gray);
                    jlb.setLocation(pos, charac1.getY());
                    jlb.setOpaque(true);
                    tmpChar = Arrays.asList(line.split("#"));
                    if (mediaPlayer != null) {
                        mediaPlayer.pause();
                        float psrate = Float.parseFloat(tmpChar.get(0)) / (float) mediaPlayer.getLength();
                        mediaPlayer.setPosition(psrate);
                    }
                }
                if (e.getClickCount() == 1 && !e.isConsumed()) {
                    e.consume();
                    String line = jp.getString();
                    txtCondition = line;
                    List<String> tmpChar = new ArrayList<>();
                    tmpChar = Arrays.asList(line.split("#"));
                    float timepos = Float.parseFloat(tmpChar.get(0)) / Float.parseFloat(tmpChar.get(tmpChar.size() - 1));
                    int pos = (int) (timepos * charac1.getWidth());
                    jlb.setBounds(pos, charac1.getY(), 1, 500);
                    jlb.setBackground(Color.gray);
                    jlb.setLocation(pos, charac1.getY());
                    jlb.setOpaque(true);
                }
                if (e.getButton() == MouseEvent.BUTTON3 && !e.isConsumed()) {
                    e.consume();
                    txtCondition = jp.getString();
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    Pmenu = new JPopupMenu();
                    menuItem = new JMenuItem("Delete");
                    Pmenu.add(menuItem);
                    menuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //System.out.println(e.toString());
                            int i1 = JOptionPane.showConfirmDialog(gui, "Are you sure to delete " + txtCondition + "?", "Delete confirmation", JOptionPane.YES_NO_OPTION);
                            if (i1 == JOptionPane.YES_OPTION) {
                                //System.out.println(txtCondition);                                
                                if (!txtCondition.equals("")) {
                                    DellAcJpData();
                                }
                            }

                        }
                    });
                    jp.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent Me) {
                            if (Me.isPopupTrigger()) {
                                Pmenu.show(Me.getComponent(), Me.getX(), Me.getY());
                            }
                        }
                    });

                }
            }

        });

        jp.setMinimum(0);
        jp.setMaximum(10);
        jp.setValue(10);
        jp.setForeground(Color.green);
        jp.setBackground(Color.green);
        jp.setOpaque(true);
        jp.setVisible(true);
        //jp.setString(Actor.getElement0().toString());        
        jp.setFocusable(false);
        jp.setBorderPainted(true);
        jp.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tmpPrg.add(jp);
        jp.validate();
        jp.repaint();
        prefusePanel.add(jp);
        scrollPane1.revalidate();
        scrollPane1.repaint();
        prefusePanel.revalidate();
        prefusePanel.repaint();
        jLayeredPane1.revalidate();
        jLayeredPane1.repaint();
    }

    private void CreateProcessBar(List<JProgressBar> tmpPrg, final List<Pair<Integer, Integer, String>> Actor, JLabel label, long Length, JPanel prefuse) {
        final JProgressBar jp = new JProgressBar();
        int pgPos, tmpInv, tmpstart;
        tmpstart = Actor.get(Actor.size() - 1).getElement0();
        float tmpPos;
        tmpPos = (float) tmpstart / (float) Length;
        tmpInv = Actor.get(Actor.size() - 1).getElement0() * label.getWidth();
        pgPos = (int) (tmpPos * label.getWidth());
        jp.setBounds(label.getX() + pgPos, label.getY(), label.getWidth() - pgPos, label.getHeight());
        //System.out.print(pgPos); 

        //jp.setString(Actor.get(Actor.size() - 1).getElement0().toString());
        jp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jp.setToolTipText("Annotating segment");
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                //System.out.print(e.getX() + "   " + e.getY());
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    //jLayeredPane1.moveToFront(jlb);
                    String line = jp.getString();
                    txtCondition = line;
                    List<String> tmpChar = new ArrayList<>();
                    tmpChar = Arrays.asList(line.split("#"));
                    if (mediaPlayer != null) {
                        mediaPlayer.pause();
                        if (Actor.get(Actor.size() - 1).getElement1() == null) {
                            Actor.get(Actor.size() - 1).setElement1((int) mediaPlayer.getTime());
                        }
                        float psrate = Float.parseFloat(tmpChar.get(1)) / (float) mediaPlayer.getLength();
                        //System.out.print((int) psrate*charac1.getWidth());
                        //System.out.print(tmpChar.get(1));
                        /*
                         float positionValue = psrate / 1000.0f;
                         if (positionValue > 0.99f) {
                         positionValue = 0.99f;
                         }
                         */
                        mediaPlayer.setPosition(psrate);
                    }
                }
                if (e.getClickCount() == 1 && !e.isConsumed()) {
                    e.consume();
                    String line = jp.getString();
                    txtCondition = line;
                    List<String> tmpChar = new ArrayList<>();
                    tmpChar = Arrays.asList(line.split("#"));
                    float timepos = Float.parseFloat(tmpChar.get(1)) / Float.parseFloat(tmpChar.get(tmpChar.size() - 1));
                    int pos = (int) (timepos * charac1.getWidth());
                    jlb.setBounds(pos, charac1.getY(), 2, 500);
                    jlb.setBackground(Color.red);
                    jlb.setLocation(pos, charac1.getY());
                    jlb.setOpaque(true);
                    //layeredlayout.setLocation(pos, charac1.getY());
                    //layeredlayout.setOpaque(true);
                    //layeredlayout.setVisible(true);
                    //jLayeredPane1.moveToFront(layeredlayout);
                    //layeredlayout.setOpaque(true);

                    //jLayeredPane1.moveToBack(scrollPane1);
                    //jLayeredPane1.moveToBack(prefusePanel);
                    //jLayeredPane1.moveToBack(jlb);
                }
                if (e.getButton() == MouseEvent.BUTTON3 && !e.isConsumed()) {
                    e.consume();
                    txtCondition = jp.getString();
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    Pmenu = new JPopupMenu();
                    menuItem = new JMenuItem("Delete");
                    Pmenu.add(menuItem);
                    menuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //System.out.println(e.toString());
                            int i1 = JOptionPane.showConfirmDialog(gui, "Are you sure to delete " + txtCondition + "?", "Delete confirmation", JOptionPane.YES_NO_OPTION);
                            if (i1 == JOptionPane.YES_OPTION) {
                                //System.out.println(txtCondition);                                
                                if (!txtCondition.equals("")) {
                                    DellAcJpData();
                                }
                            }

                        }
                    });
                    jp.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent Me) {
                            if (Me.isPopupTrigger()) {
                                Pmenu.show(Me.getComponent(), Me.getX(), Me.getY());
                            }
                        }
                    });

                }
            }

        });
        jp.setForeground(Color.green);
        jp.setBackground(Color.green);
        jp.setOpaque(true);
        jp.repaint();
        jp.setString(Actor.get(Actor.size() - 1).getElement0().toString());
        jp.setMinimum(0);
        jp.setMaximum(10);
        jp.setFocusable(false);
        jp.setBorderPainted(true);
        jp.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tmpPrg.add(jp);
        prefuse.add(jp);
        prefuse.add(jp);
        scrollPane1.revalidate();
        scrollPane1.repaint();
        prefuse.revalidate();
        prefuse.repaint();
    }

    private void ResizeProgressBar(final JProgressBar tmpPrg, List<Pair<Integer, Integer, String>> Actor, long lengthof, JPanel panel, String charSign) {
        int setWid, lengthwidth;
        final String timeStart, timeEnd;
        int abc;
        abc = Actor.get(Actor.size() - 1).getElement0();
        timeStart = BasicModel.getInstance().ConvertTime((long) abc);
        abc = Actor.get(Actor.size() - 1).getElement1();
        timeEnd = BasicModel.getInstance().ConvertTime((long) abc);
        int timeInter = Actor.get(Actor.size() - 1).getElement1() - Actor.get(Actor.size() - 1).getElement0();
        lengthwidth = (int) lengthof - Actor.get(Actor.size() - 1).getElement0();
        setWid = timeInter * tmpPrg.getWidth();
        setWid = (int) setWid / lengthwidth;
        final long timeinterval = Actor.get(Actor.size() - 1).getElement1() - Actor.get(Actor.size() - 1).getElement0();
        //(lengthof-Actor.get(Actor.size()-1).getElement0())));
        tmpPrg.setBounds(tmpPrg.getX(), tmpPrg.getY(), setWid, charac1.getHeight());
        tmpPrg.setString(tmpPrg.getString() + "#" + Actor.get(Actor.size() - 1).getElement1().toString() + "#" + Actor.get(Actor.size() - 1).getElement2());

        //System.out.print(tmpPrg.getString());
        tmpPrg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                tmpPrg.setToolTipText("Start: " + timeStart + " - End: " + timeEnd + "Length: " + BasicModel.getInstance().ConvertTime(timeinterval));
            }
        });

        //System.out.print(BasicModel.getInstance().ConvertTime(timeinterval));
        tmpPrg.setValue(10);
        tmpPrg.setBackground(Color.green);
        tmpPrg.setForeground(Color.green);

        tmpPrg.setOpaque(true);
        tmpPrg.repaint();
        panel.revalidate();
        panel.repaint();
    }
    
    public void CreateGephiGraph() {
        BuildGephiGraph bd = new BuildGephiGraph();
        bd.script(jPanel3);
        jPanel3.setSize(500, 500);        
        //jPanel3.add(bd);
        //jPanel3.validate();
        //jPanel3.repaint();
        SetFocus();
    }           
    

    public void RefreshGraph(int[][] relation) {
        jPanel3.removeAll();
        NodeGraph = new ArrayList<>();
        for (int i = 0; i < ListCharacter.getModel().getSize(); i++) {
            //model.addElement((String) ListCharacter.getModel().getElementAt(i));
            NodeGraph.add((String) ListCharacter.getModel().getElementAt(i));
        }
        AggregateDecoratorDemo ex;
        ex = new AggregateDecoratorDemo(NodeGraph, relation);
        jPanel3.setSize(500, 500);
        jPanel3.add(ex);
        jPanel3.validate();
        jPanel3.repaint();
        SetFocus();
        ex.removeAll();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Play.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        Path currentRelativePath = Paths.get("");
        String currentFile = currentRelativePath.toAbsolutePath().toString() + "\\lib\\vlc";
        System.out.println(currentFile);
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "c:/program files/videolan/vlc");
        //"c:/program files/videolan/vlc");
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            private KeyListener ex;

            @Override
            public void run() {
                Play pl = new Play();
                pl.setVisible(true);
            }
        }
        );
    }

    private void setSliderBasedPosition() {
        if (!mediaPlayer.isSeekable()) {
            return;
        }
        float positionValue = (float) positionSlider.getValue() / 1000.0f;
        // Avoid end of file freeze-up 
        if (positionValue > 0.99f) {
            positionValue = 0.99f;
        }
        mediaPlayer.setPosition(positionValue);
        //end all session
        for (int i = 0; i < appeeared.length; i++) {
            if (appeeared[i] == true) {
                try {
                    BasicModel.getInstance().WriteToTextFile("Result.txt", String.valueOf(i) + "%End%" + String.valueOf(mediaPlayer.getTime()), true);

                } catch (IOException ex) {
                    Logger.getLogger(Play.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            appeeared[i] = false;
        }
        SetFocus();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddCharacter;
    private javax.swing.JList ListApp;
    private javax.swing.JList ListCharacter;
    private javax.swing.JButton LoadData;
    private javax.swing.JLabel charac1;
    private javax.swing.JLabel charac10;
    private javax.swing.JLabel charac11;
    private javax.swing.JLabel charac12;
    private javax.swing.JLabel charac13;
    private javax.swing.JLabel charac14;
    private javax.swing.JLabel charac15;
    private javax.swing.JLabel charac16;
    private javax.swing.JLabel charac17;
    private javax.swing.JLabel charac18;
    private javax.swing.JLabel charac19;
    private javax.swing.JLabel charac2;
    private javax.swing.JLabel charac20;
    private javax.swing.JLabel charac3;
    private javax.swing.JLabel charac4;
    private javax.swing.JLabel charac5;
    private javax.swing.JLabel charac6;
    private javax.swing.JLabel charac7;
    private javax.swing.JLabel charac8;
    private javax.swing.JLabel charac9;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label;
    private javax.swing.JLabel layeredlayout;
    private javax.swing.JScrollPane listCharacter;
    private javax.swing.JTextField nameCharacter;
    private javax.swing.JLabel namecharaterAppeared;
    private javax.swing.JPanel panelPlay;
    private javax.swing.JButton pause;
    private javax.swing.JSlider positionSlider;
    private javax.swing.JPanel prefusePanel;
    private java.awt.ScrollPane scrollPane1;
    private javax.swing.JButton stop;
    // End of variables declaration//GEN-END:variables

    private String String(char c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
