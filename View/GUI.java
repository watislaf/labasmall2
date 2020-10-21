package View;

import Objects.Drawable;
import containers.BackPackException;
import containers.Backpack;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

public class GUI {
    private GuiActionListener listener;
    private MyPanel start_panel_;
    JButton create_ball;
    JButton create_cube;
    JButton remove_button;
    Point start_point = new Point(200, 200);
    JMenuItem open_;
    JMenuItem save_;
    Backpack<Drawable> my_backpack = new Backpack<>(6);

    private JFrame frame_;
    private Timer main_timer_;

    public Object GetMainTimer() {
        return main_timer_;
    }

    public void Add(Drawable small) {
        try {
            my_backpack.AddShape(small);
        } catch (BackPackException e) {
            JOptionPane.showConfirmDialog(frame_, "TOO MUCH");
        }
    }

    public Point GetNewPosition() {
        Point newP = (Point) start_point.clone();
        newP.x += 140 * my_backpack.Size();
        return newP;
    }

    public void Remove() {
        my_backpack.RemoveShape();
    }

    public void Open() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XML files", "xml"));
        if (fileChooser.showDialog(frame_, "Открыть") == JFileChooser.APPROVE_OPTION) {
            try {
                my_backpack = Xml.read(fileChooser.getSelectedFile());
            } catch (Exception ignore) {
                System.out.print(ignore);
            }
        }

    }

    public void Save() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XML files", "xml"));
        if (fileChooser.showDialog(frame_, "Сохранить") == JFileChooser.APPROVE_OPTION) {
            try {
                Xml.write(my_backpack, fileChooser.getSelectedFile());
                JOptionPane.showMessageDialog(frame_, "Файл успешно сохранен", "Ура!",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                System.out.print(e.toString());
            }

        }
    }

    class MyPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(Color.BLUE);
            for (Drawable obj : my_backpack.Data()) {
                obj.Draw(g2d);
            }
        }
    }

    public void StartWindow() {
        listener = new GuiActionListener(this);

        start_panel_ = new MyPanel();

        frame_ = new JFrame("Oh yes this is laba2");
        frame_.setSize(500, 500);
        frame_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_.add(start_panel_);
        BorderLayout layout = new BorderLayout();

        frame_.setLayout(layout);
        create_ball = new JButton("Create ball");
        create_ball.addActionListener(listener);

        create_cube = new JButton("Create cube");
        create_cube.addActionListener(listener);

        remove_button = new JButton("Remove");
        remove_button.addActionListener(listener);

        start_panel_.setLayout(new GridLayout(15, 0));
        start_panel_.add(create_ball);
        start_panel_.add(create_cube);
        start_panel_.add(remove_button);

        frame_.add(start_panel_, BorderLayout.CENTER);
        Menu();

        main_timer_ = new Timer(60, listener);
        main_timer_.start();

        frame_.setVisible(true);
        frame_.repaint();
    }

    private void Menu() {
        JMenuBar menu_ = new JMenuBar();
        JMenu file = new JMenu("File");
        JButton about = new JButton("About");
        about.setOpaque(true);
        about.setContentAreaFilled(false);
        about.setBorderPainted(false);
        about.setFocusable(false);
        open_ = new JMenuItem("Open from XML");
        save_ = new JMenuItem("Write to XML");

        save_.addActionListener(listener);
        open_.addActionListener(listener);
        file.add(open_);
        file.add(save_);

        menu_.add(about);
        menu_.add(file);
        frame_.setJMenuBar(menu_);
        about.addActionListener((event) -> {
            JOptionPane.showMessageDialog(frame_, "Laba", "About",
                    JOptionPane.INFORMATION_MESSAGE);
        });

    }

    public void Repaint() {
        frame_.repaint();
    }
}

