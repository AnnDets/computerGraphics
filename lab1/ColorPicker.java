import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorPicker extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel colorDisplayPanel;

    // RGB components
    private JTextField rField, gField, bField;

    // HSV components
    private JTextField hField, sField, vField;

    // CMYK components
    private JTextField cField, mField, yField, kField;

    public ColorPicker() {
        setTitle("Color Picker");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();

        colorDisplayPanel = new JPanel();
        colorDisplayPanel.setPreferredSize(new Dimension(400, 200));
        add(colorDisplayPanel, BorderLayout.NORTH);

        tabbedPane.addTab("RGB", createRGBPanel());
        tabbedPane.addTab("HSV", createHSVPanel());
        tabbedPane.addTab("CMYK", createCMYKPanel());

        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createRGBPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        panel.add(new JLabel("R:"));
        rField = new JTextField("255");
        panel.add(rField);

        panel.add(new JLabel("G:"));
        gField = new JTextField("255");
        panel.add(gField);

        panel.add(new JLabel("B:"));
        bField = new JTextField("255");
        panel.add(bField);

        JButton updateButton = new JButton("Update Color");
        updateButton.addActionListener(e -> updateColorFromRGB());
        panel.add(updateButton);

        return panel;
    }

    private JPanel createHSVPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        panel.add(new JLabel("H:"));
        hField = new JTextField("360");
        panel.add(hField);

        panel.add(new JLabel("S:"));
        sField = new JTextField("100");
        panel.add(sField);

        panel.add(new JLabel("V:"));
        vField = new JTextField("100");
        panel.add(vField);

        JButton updateButton = new JButton("Update Color");
        updateButton.addActionListener(e -> updateColorFromHSV());
        panel.add(updateButton);

        return panel;
    }

    private JPanel createCMYKPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        panel.add(new JLabel("C:"));
        cField = new JTextField("0");
        panel.add(cField);

        panel.add(new JLabel("M:"));
        mField = new JTextField("0");
        panel.add(mField);

        panel.add(new JLabel("Y:"));
        yField = new JTextField("0");
        panel.add(yField);

        panel.add(new JLabel("K:"));
        kField = new JTextField("0");
        panel.add(kField);

        JButton updateButton = new JButton("Update Color");
        updateButton.addActionListener(e -> updateColorFromCMYK());
        panel.add(updateButton);

        return panel;
    }

    private void updateColorFromRGB() {
        int r = Integer.parseInt(rField.getText());
        int g = Integer.parseInt(gField.getText());
        int b = Integer.parseInt(bField.getText());
        Color color = new Color(r, g, b);
        updateColor(color);
        updateCMYK(color);
        updateHSV(color);
    }

    private void updateColorFromHSV() {
        float h = Float.parseFloat(hField.getText());
        float s = Float.parseFloat(sField.getText()) / 100;
        float v = Float.parseFloat(vField.getText()) / 100;
        Color color = Color.getHSBColor(h / 360, s, v);
        updateColor(color);
        updateCMYK(color);
        updateRGB(color);
    }

    private void updateColorFromCMYK() {
        float c = Float.parseFloat(cField.getText()) / 100;
        float m = Float.parseFloat(mField.getText()) / 100;
        float y = Float.parseFloat(yField.getText()) / 100;
        float k = Float.parseFloat(kField.getText()) / 100;
        int r = (int) ((1 - c) * (1 - k) * 255);
        int g = (int) ((1 - m) * (1 - k) * 255);
        int b = (int) ((1 - y) * (1 - k) * 255);
        Color color = new Color(r, g, b);
        updateColor(color);
        updateHSV(color);
        updateRGB(color);
    }

    private void updateColor(Color color) {
        colorDisplayPanel.setBackground(color);
    }

    private void updateCMYK(Color color) {
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;
        float k = 1 - Math.max(r, Math.max(g, b));
        float c = (1 - r - k) / (1 - k);
        float m = (1 - g - k) / (1 - k);
        float y = (1 - b - k) / (1 - k);
        cField.setText(String.format("%.0f", c * 100));
        mField.setText(String.format("%.0f", m * 100));
        yField.setText(String.format("%.0f", y * 100));
        kField.setText(String.format("%.0f", k * 100));
    }

    private void updateHSV(Color color) {
        float[] hsv = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        hField.setText(String.format("%.0f", hsv[0] * 360));
        sField.setText(String.format("%.0f", hsv[1] * 100));
        vField.setText(String.format("%.0f", hsv[2] * 100));
    }

    private void updateRGB(Color color) {
        rField.setText(String.valueOf(color.getRed()));
        gField.setText(String.valueOf(color.getGreen()));
        bField.setText(String.valueOf(color.getBlue()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ColorPicker::new);
    }
}