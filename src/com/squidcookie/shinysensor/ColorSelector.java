package com.squidcookie.shinysensor;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ColorSelector extends JPanel implements ChangeListener {
    private final JSlider rUpper;
    private final JSlider gUpper;
    private final JSlider bUpper;
    private final JSlider rLower;
    private final JSlider gLower;
    private final JSlider bLower;
    private final JLabel nonShiny;
    private final JLabel shiny;
    private BufferedImage nonShinyImage;
    private BufferedImage shinyImage;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(ColorSelector::createAndShowGUI);
    }

    private void initializeSliderAndLabel(String string, JSlider slider) {
        JLabel label = new JLabel(string, JLabel.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        slider.addChangeListener(this);
        slider.setMinorTickSpacing(15);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
        add(label);
    }

    private void initializeImage(JLabel label, BufferedImage image) {
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createEmptyBorder(10,10,10,10)));
        label.setIcon(new ImageIcon(image));
    }

    public ColorSelector() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        rUpper = new JSlider(JSlider.HORIZONTAL, 0, 255, 255);
        rLower = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        gUpper = new JSlider(JSlider.HORIZONTAL, 0, 255, 255);
        gLower = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        bUpper = new JSlider(JSlider.HORIZONTAL, 0, 255, 255);
        bLower = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);

        String[] strings = {"Red Upper", "Red Lower", "Green Upper", "Green Lower", "Blue Upper", "Blue Lower",};
        JSlider[] sliders = {rUpper, rLower, gUpper, gLower, bUpper, bLower};
        for (int i = 0; i < 6; i++) {
            initializeSliderAndLabel(strings[i], sliders[i]);
            add(sliders[i]);
        }

        nonShiny = new JLabel();
        shiny = new JLabel();
        try {
            nonShinyImage = ImageIO.read(ColorSelector.class.getResource("images/non-shiny.png"));
            shinyImage = ImageIO.read(ColorSelector.class.getResource("images/shiny.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        initializeImage(nonShiny, nonShinyImage);
        initializeImage(shiny, shinyImage);

        JPanel panel = new JPanel();
        panel.add(nonShiny);
        panel.add(shiny);
        add(panel);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("FreeShinyPokemonScam");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ColorSelector colorSelector = new ColorSelector();

        frame.add(colorSelector);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        updateImage(nonShiny, nonShinyImage);
        updateImage(shiny, shinyImage);
    }

    private void updateImage(JLabel label, BufferedImage image) {
        label.setIcon(new ImageIcon(ImageProcessingUtil.filterPixels(image,
                new Color(rUpper.getValue(), gUpper.getValue(), bUpper.getValue()),
                new Color(rLower.getValue(), gLower.getValue(), bLower.getValue()))));
    }
}
