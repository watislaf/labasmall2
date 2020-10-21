package View;

import Objects.SmallBall;
import Objects.SmallCube;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GuiActionListener implements ActionListener {
    private final GUI gui_;

    public GuiActionListener(GUI gui) {
        gui_ = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui_.GetMainTimer()) {
            gui_.Repaint();
        }

        if (e.getSource() == gui_.create_ball || e.getSource() == gui_.create_cube) {
            String inputValue = JOptionPane.showInputDialog("Please input a value");
            try {
                Integer lol = Integer.valueOf(inputValue);
                Point x = gui_.GetNewPosition();
                if (e.getSource() == gui_.create_ball) {
                    gui_.Add(new SmallBall(x, lol));
                } else {
                    gui_.Add(new SmallCube(x, lol));
                }
            } catch (Exception ignored) {
            }
        }
        if (e.getSource() == gui_.open_) {
            gui_.Open();
        }


        if (e.getSource() == gui_.save_)
            gui_.Save();


        if (e.getSource() == gui_.remove_button) {
            gui_.Remove();
        }
    }
}