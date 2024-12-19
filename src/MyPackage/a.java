package MyPackage;

import javax.swing.SwingUtilities;

public class a {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new b().setVisible(true));
    }
}