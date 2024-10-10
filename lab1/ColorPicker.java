import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorPicker extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel colorDisplayPanel;

    // RGB components
    private JSpinner rSpinner, gSpinner, bSpinner;
    private JSlider rSlider, gSlider, bSlider;

    // HSV components
    private JSpinner hSpinner, sSpinner, vSpinner;
    private JSlider hSlider, sSlider, vSlider;

    // CMYK components
    private JSpinner cSpinner, mSpinner, ySpinner, kSpinner;
    private JSlider cSlider, mSlider, ySlider, kSlider;

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
        panel.setLayout(new GridLayout(4, 3));

        panel.add(new JLabel("R:"));
        rSpinner = createSpinner(0, 255, 255);
        rSlider = createSlider(0, 255, 255);
        linkSpinnerAndSlider(rSpinner, rSlider);
        panel.add(rSpinner);
        panel.add(rSlider);

        panel.add(new JLabel("G:"));
        gSpinner = createSpinner(0, 255, 255);
        gSlider = createSlider(0, 255, 255);
        linkSpinnerAndSlider(gSpinner, gSlider);
        panel.add(gSpinner);
        panel.add(gSlider);

        panel.add(new JLabel("B:"));
        bSpinner = createSpinner(0, 255, 255);
        bSlider = createSlider(0, 255, 255);
        linkSpinnerAndSlider(bSpinner, bSlider);
        panel.add(bSpinner);
        panel.add(bSlider);

        JButton updateButton = new JButton("Update Color");
        updateButton.addActionListener(e -> updateColorFromRGB());

        return panel;
    }

    private JPanel createHSVPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        panel.add(new JLabel("H:"));
        hSpinner = createSpinner(0, 360, 360);
        hSlider = createSlider(0, 360, 360);
        linkSpinnerAndSlider(hSpinner, hSlider);
        panel.add(hSpinner);
        panel.add(hSlider);
        panel.add(new JLabel("S:"));
        sSpinner = createSpinner(0, 100, 100);
        sSlider = createSlider(0, 100, 100);
        linkSpinnerAndSlider(sSpinner, sSlider);
        panel.add(sSpinner);
        panel.add(sSlider);

        panel.add(new JLabel("L:"));
        vSpinner = createSpinner(0, 100, 50);
        vSlider = createSlider(0, 100, 50);
        linkSpinnerAndSlider(vSpinner, vSlider);
        panel.add(vSpinner);
        panel.add(vSlider);

        JButton updateButton = new JButton("Update Color");
        updateButton.addActionListener(e -> updateColorFromHSV());

        return panel;
    }

    private JPanel createCMYKPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        panel.add(new JLabel("C:"));
        cSpinner = createSpinner(0, 100, 0);
        cSlider = createSlider(0, 100, 0);
        linkSpinnerAndSlider(cSpinner, cSlider);
        panel.add(cSpinner);
        panel.add(cSlider);

        panel.add(new JLabel("M:"));
        mSpinner = createSpinner(0, 100, 0);
        mSlider = createSlider(0, 100, 0);
        linkSpinnerAndSlider(mSpinner, mSlider);
        panel.add(mSpinner);
        panel.add(mSlider);

        panel.add(new JLabel("Y:"));
        ySpinner = createSpinner(0, 100, 0);
        ySlider = createSlider(0, 100, 0);
        linkSpinnerAndSlider(ySpinner, ySlider);
        panel.add(ySpinner);
        panel.add(ySlider);

        panel.add(new JLabel("K:"));
        kSpinner = createSpinner(0, 100, 0);
        kSlider = createSlider(0, 100, 0);
        linkSpinnerAndSlider(kSpinner, kSlider);
        panel.add(kSpinner);
        panel.add(kSlider);

        JButton updateButton = new JButton("Update Color");
        updateButton.addActionListener(e -> updateColorFromCMYK());

        return panel;
    }

    private void updateColorFromRGB() {
        int r = (int) rSpinner.getValue();
        int g = (int) gSpinner.getValue();
        int b = (int) bSpinner.getValue();
        Color color = new Color(r, g, b);
        updateColor(color);
        updateCMYK(color);
        updateHSV(color);
    }

    private void updateColorFromHSV() {
        float h = (int) hSpinner.getValue();
        float s = (int) sSpinner.getValue() / 100f;
        float v = (int) vSpinner.getValue() / 100f;
        Color color = Color.getHSBColor(h / 360, s, v);
        updateColor(color);
        updateCMYK(color);
        updateRGB(color);
    }

    private void updateColorFromCMYK() {
        float c = (int) cSpinner.getValue() / 100f;
        float m = (int) mSpinner.getValue() / 100f;
        float y = (int) ySpinner.getValue() / 100f;
        float k = (int) kSpinner.getValue() / 100f;
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
        cSpinner.setValue(Math.round(c * 100));
        mSpinner.setValue(Math.round(m * 100));
        ySpinner.setValue(Math.round(y * 100));
        kSpinner.setValue(Math.round(k * 100));
    }

    private void updateHSV(Color color) {
        float[] hsv = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        hSpinner.setValue(Math.round(hsv[0]));
        sSpinner.setValue(Math.round(hsv[1] * 100));
        vSpinner.setValue(Math.round(hsv[2] * 100));
    }

    private void updateRGB(Color color) {
        rSpinner.setValue(color.getRed());
        gSpinner.setValue(color.getGreen());
        bSpinner.setValue(color.getBlue());
    }

    private JSpinner createSpinner(int min, int max, int initial) {
        SpinnerNumberModel model = new SpinnerNumberModel(initial, min, max, 1);
        return new JSpinner(model);
    }

    private JSlider createSlider(int min, int max, int initial) {
        JSlider slider = new JSlider(min, max, initial);
        slider.setMajorTickSpacing(max / 5);
        slider.setPaintTicks(true);
        return slider;
    }

    private void linkSpinnerAndSlider(JSpinner spinner, JSlider slider) {
        spinner.addChangeListener(e -> {
            slider.setValue((int) spinner.getValue());
            updateColorFromCurrentTab();
        });
        slider.addChangeListener(e -> {
            spinner.setValue(slider.getValue());
            updateColorFromCurrentTab();
        });
    }

    private void updateColorFromCurrentTab() {
        int selectedIndex = tabbedPane.getSelectedIndex();
        if (selectedIndex == 0) {
            updateColorFromRGB();
        } else if (selectedIndex == 1) {
            updateColorFromHSV();
        } else if (selectedIndex == 2) {
            updateColorFromCMYK();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ColorPicker::new);
    }
}
