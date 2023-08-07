package com.squidcookie.shinysensor;

import com.github.sarxos.webcam.Webcam;

import javax.swing.*;
import java.awt.*;

public class ShinySensor extends JPanel {
    private final Webcam webcam;
    private final JLabel picture;
    private final VideoFeed videoFeed;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(ShinySensor::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Sensor — confirm colors have been properly selected before launching Sensor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ShinySensor shinySensor = new ShinySensor();

        frame.add(shinySensor);
        frame.pack();
        frame.setVisible(true);
    }

    public ShinySensor() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(320, 240));
        webcam.open();

        picture = new JLabel();
        picture.setHorizontalAlignment(JLabel.CENTER);
        picture.setAlignmentX(Component.CENTER_ALIGNMENT);
        picture.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createEmptyBorder(10,10,10,10)));
        picture.setIcon(new ImageIcon(webcam.getImage()));

        videoFeed = new VideoFeed();
        videoFeed.start();

        add(picture);
    }

    public class VideoFeed extends Thread {
        @Override
        public void run() {
            while(true) {
//                picture.setIcon(new ImageIcon(webcam.getImage()));
                picture.setIcon(new ImageIcon(ImageProcessingUtil.filterPixels(webcam.getImage(), new Color(225,150,150), new Color(55, 55, 55))));
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
