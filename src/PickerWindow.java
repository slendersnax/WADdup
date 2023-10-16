import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.Action;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import wad_display.WADPanel;

public class PickerWindow {
    JFrame mainFrame;
    JPanel btnContainer;
    WADPanel wadContainer;
    JButton btn_iwadPicker, btn_pwadPicker, btn_play;
    JLabel name_iwad;
    JScrollPane pwadScroller;

    JFileChooser fileChooser;
    String basePath, iwadPath;

    public PickerWindow(String _basePath) {
        mainFrame = new JFrame("GZDoom WAD Picker");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(640, 480);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null); // centers it on screen automatically

        basePath = _basePath;
        iwadPath = "";

        addComponents(mainFrame.getContentPane());
        initBtnActions();

        mainFrame.setVisible(true);
    }

    public void initBtnActions() {
        btn_iwadPicker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileChooser.showOpenDialog(mainFrame);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    name_iwad.setText(file.getName());
                    iwadPath = file.getAbsolutePath();
                }
            }
        });

        btn_pwadPicker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser.setMultiSelectionEnabled(true);
                int returnVal = fileChooser.showOpenDialog(mainFrame);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    File[] selFiles = fileChooser.getSelectedFiles();

                    for(int i = 0; i < selFiles.length; i ++) {
                        wadContainer.addWad(selFiles[i].getName(), selFiles[i].getAbsolutePath(), mainFrame);
                    }
                }

                fileChooser.setMultiSelectionEnabled(false);
            }
        });

        btn_play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (iwadPath != "") {
                    List<String> cmdBuilder = new ArrayList<String>();
                    cmdBuilder.add("gzdoom");
                    cmdBuilder.add("-config");
                    cmdBuilder.add("/home/abel/.config/gzdoom/gzdoom.ini");

                    cmdBuilder.add("-iwad");
                    cmdBuilder.add(iwadPath);

                    if (wadContainer.wadList.size() > 0) {
                        for (int i = 0; i < wadContainer.wadList.size(); i++) {
                            cmdBuilder.add("-file");
                            cmdBuilder.add(wadContainer.wadList.get(i).sWADPath);
                        }
                    }

                    ProcessBuilder pb = new ProcessBuilder(cmdBuilder);
                    Process process;

                    try {
                        process = pb.start();
                        //System.exit(0);
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
                else {
                    name_iwad.setText("PLEASE PICK AN IWAD FILE");
                }
            }
        });
    }

    public void addComponents(Container Pane) {
        btnContainer = new JPanel();
        wadContainer = new WADPanel();
        btn_iwadPicker = new JButton("Pick IWAD");
        btn_pwadPicker = new JButton("Pick PWADS");
        btn_play = new JButton("Play");
        name_iwad = new JLabel("[IWAD]");
        fileChooser = new JFileChooser(new File(basePath));
        pwadScroller = new JScrollPane(wadContainer);

        // viewing in details mode by default
        Action details = fileChooser.getActionMap().get("viewTypeDetails");
        details.actionPerformed(null);

        pwadScroller.getVerticalScrollBar().setUnitIncrement(16);

        name_iwad.setHorizontalAlignment(SwingConstants.CENTER);
        name_iwad.setVerticalAlignment(SwingConstants.CENTER);

        btnContainer.add(btn_iwadPicker);
        btnContainer.add(btn_pwadPicker);
        btnContainer.add(btn_play);

        Pane.add(name_iwad, BorderLayout.PAGE_START);
        Pane.add(pwadScroller, BorderLayout.CENTER);
        Pane.add(btnContainer, BorderLayout.PAGE_END);
    }
}