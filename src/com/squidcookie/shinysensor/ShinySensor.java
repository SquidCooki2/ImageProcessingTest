package com.squidcookie.shinysensor;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class ShinySensor extends JPanel implements ChangeListener {
    private JSlider rUpper;
    private JSlider gUpper;
    private JSlider bUpper;
    private JSlider rLower;
    private JSlider gLower;
    private JSlider bLower;
    private JLabel nonShiny;
    private JLabel shiny;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public void initializeSliders(String string, JSlider slider) {
        JLabel label = new JLabel(string, JLabel.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        slider.addChangeListener(this);
        slider.setMinorTickSpacing(15);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
        add(label);
    }

    public void initializePictures(String string, JLabel label) {
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createEmptyBorder(10,10,10,10)));
        label.setIcon(new ImageIcon(Objects.requireNonNull(ShinySensor.class.getResource(string))));
    }

    public ShinySensor() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        rUpper = new JSlider(JSlider.HORIZONTAL, 0, 255, 255 / 2);
        rLower = new JSlider(JSlider.HORIZONTAL, 0, 255, 255 / 2);
        gUpper = new JSlider(JSlider.HORIZONTAL, 0, 255, 255 / 2);
        gLower = new JSlider(JSlider.HORIZONTAL, 0, 255, 255 / 2);
        bUpper = new JSlider(JSlider.HORIZONTAL, 0, 255, 255 / 2);
        bLower = new JSlider(JSlider.HORIZONTAL, 0, 255, 255 / 2);

        String[] strings = {"Red Upper", "Red Lower", "Green Upper", "Green Lower", "Blue Upper", "Blue Lower",};
        JSlider[] sliders = {rUpper, rLower, gUpper, gLower, bUpper, bLower};
        for (int i = 0; i < 6; i++) {
            initializeSliders(strings[i], sliders[i]);
            add(sliders[i]);
        }

        nonShiny = new JLabel();
        shiny = new JLabel();
        initializePictures("images/mewtwo.png", nonShiny);
        initializePictures("images/shiny-mewtwo.png", shiny);

        JPanel panel = new JPanel();
        panel.add(nonShiny);
        panel.add(shiny);
        add(panel);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("FreeShinyPokemonScam");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ShinySensor shinySensor = new ShinySensor();

        frame.add(shinySensor);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        updateImage("images/mewtwo");
        updateImage("images/shiny-mewtwo");
    }

    public void updateImage(String name) {
        File file;
        BufferedImage image = null;

        try {
            file = new File(ShinySensor.class.getResource(name + ".png").toURI());
            image = ImageIO.read(file);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        int height = image.getHeight();
        int width = image.getWidth();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                RGB imageRGB = new RGB(image.getRGB(i, j));

                if (imageRGB.r < rUpper.getValue() && imageRGB.r > rLower.getValue() &&
                        imageRGB.g < gUpper.getValue() && imageRGB.g > gLower.getValue() &&
                        imageRGB.b < bUpper.getValue() && imageRGB.b > bLower.getValue()) {
                    image.setRGB(i, j, 0xffffffff);
                } else {
                    image.setRGB(i, j, 0xff000000);
                }
            }
        }

        if (name.equals("images/shiny-mewtwo")) {
            shiny.setIcon(new ImageIcon(image));
        } else {
            nonShiny.setIcon(new ImageIcon(image));
        }
    }
}
