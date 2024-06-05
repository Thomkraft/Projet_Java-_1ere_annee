package Interface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.DefaultView;
import org.graphstream.ui.swingViewer.Viewer;

public class FenetreGraph extends JInternalFrame {
    private Viewer viewer;
    private DefaultView view;
    private JButton btEcran = new JButton("Agrandir");
    private JButton btnPrevious = new JButton("Précédent");
    private JButton btnNext = new JButton("Suivant");
    private JTextField txtGraphNumber = new JTextField(3);
    private boolean isFullScreen = false;
    private JFrame fullScreenFrame;

    public FenetreGraph(Graph graph) {
        super("Graphes", false, false, false, false);
        setSize(1170, 400);

        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        view = (DefaultView) viewer.addDefaultView(false);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(view, BorderLayout.CENTER);

        btEcran.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isFullScreen) {
                    restoreWindow();
                } else {
                    maximizeWindow();
                }
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.add(btnPrevious);
        leftPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        leftPanel.add(txtGraphNumber);
        leftPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        leftPanel.add(btnNext);

        controlPanel.add(leftPanel, BorderLayout.WEST);
        controlPanel.add(btEcran, BorderLayout.EAST);

        getContentPane().add(controlPanel, BorderLayout.SOUTH);
        this.setResizable(true);
    }

    private void maximizeWindow() {
        isFullScreen = true;
        btEcran.setText("Retrecir");

        fullScreenFrame = new JFrame();
        fullScreenFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        fullScreenFrame.setUndecorated(true);
        fullScreenFrame.setLayout(new BorderLayout());
        fullScreenFrame.add(view, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.add(btnPrevious);
        leftPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        leftPanel.add(txtGraphNumber);
        leftPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        leftPanel.add(btnNext);

        controlPanel.add(leftPanel, BorderLayout.WEST);
        controlPanel.add(btEcran, BorderLayout.EAST);

        fullScreenFrame.add(controlPanel, BorderLayout.SOUTH);
        fullScreenFrame.setVisible(true);
        this.setVisible(false);
    }

    private void restoreWindow() {
        isFullScreen = false;
        btEcran.setText("Agrandir");

        fullScreenFrame.setVisible(false);
        this.setVisible(true);
    }

    public void addPreviousButtonListener(ActionListener listener) {
        btnPrevious.addActionListener(listener);
    }

    public void addNextButtonListener(ActionListener listener) {
        btnNext.addActionListener(listener);
    }

    public int getGraphNumber() {
        try {
            return Integer.parseInt(txtGraphNumber.getText());
        } catch (NumberFormatException e) {
            return 1;
        }
    }
}
