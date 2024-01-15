import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Box;

import javax.swing.BoxLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import wad_display.WADPanel;

public class PickerWindow {
    private JFrame mainFrame;
    private JPanel btnContainer;
    private WADPanel wadContainer;
    private JButton btn_play, btn_saveconfig, btn_loadconfig;

    private String basePath, userHome;

    public PickerWindow(String _basePath) {
        mainFrame = new JFrame("WADdup");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(640, 480);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null); // centers it on screen automatically

        basePath = _basePath;
        userHome = System.getProperty("user.home");

        addComponents(mainFrame.getContentPane());
        initBtnActions();

        mainFrame.setVisible(true);
    }

    public void initBtnActions() {
        btn_saveconfig.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (wadContainer.wadList.size() > 0 && wadContainer.iwadLabel.getIwadPath() != "") {
                    // save config
                }
            }
        });

        btn_play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (wadContainer.iwadLabel.getIwadPath() != "") {
                    List<String> cmdBuilder = new ArrayList<String>();
                    cmdBuilder.add("gzdoom");
                    cmdBuilder.add("-config");
                    cmdBuilder.add(userHome.concat("/.config/gzdoom/gzdoom.ini"));

                    cmdBuilder.add("-iwad");
                    cmdBuilder.add(wadContainer.iwadLabel.getIwadPath());

                    if (wadContainer.wadList.size() > 0) {
                        for (int i = 0; i < wadContainer.wadList.size(); i++) {
                            if (wadContainer.wadList.get(i).sFileType == "deh") {
                                cmdBuilder.add("-deh");
                            }
                            else {
                                cmdBuilder.add("-file");
                            }
                            cmdBuilder.add(wadContainer.wadList.get(i).sWADPath);
                        }
                    }

                    ProcessBuilder pb = new ProcessBuilder(cmdBuilder);
                    Process process;

                    try {
                        process = pb.start();
                    } catch (IOException exc) {
                        //exc.printStackTrace();
                    }
                }
                else {
                    wadContainer.iwadLabel.signalNoIWADError();
                }
            }
        });
    }

    public void addComponents(Container Pane) {
        btnContainer = new JPanel();
        wadContainer = new WADPanel(mainFrame, basePath);

        btn_saveconfig = new JButton("save config");
        btn_loadconfig = new JButton("load config");

        btn_play = new JButton("Play");

        btnContainer.add(btn_saveconfig);
        btnContainer.add(btn_loadconfig);
        btnContainer.add(btn_play);

        mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));

        mainFrame.add(Box.createVerticalGlue());
        mainFrame.add(wadContainer);
        mainFrame.add(btnContainer);
        mainFrame.add(Box.createVerticalGlue());
    }
}