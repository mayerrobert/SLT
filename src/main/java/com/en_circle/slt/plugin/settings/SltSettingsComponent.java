package com.en_circle.slt.plugin.settings;

import com.en_circle.slt.plugin.SltBundle;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class SltSettingsComponent {

    private final JPanel root;
    private final JBTextField sbclExecutable = new JBTextField("sbcl");
    private final JBTextField port = new JBTextField("4005");
    private final JBTextField quicklispStartScript = new JBTextField("~/quicklisp/setup.lisp");

    public SltSettingsComponent() {
        root = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel(SltBundle.message("slt.ui.settings.executable")),
                        sbclExecutable, 1, false)
                .addLabeledComponent(new JBLabel(SltBundle.message("slt.ui.settings.port")),
                        port, 1, false)
                .addLabeledComponent(new JBLabel(SltBundle.message("slt.ui.settings.qlpath")),
                        quicklispStartScript, 1, false)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return root;
    }

    public JComponent getPreferredFocusedComponent() {
        return sbclExecutable;
    }

    @NotNull
    public String getSbclExecutable() {
        return sbclExecutable.getText();
    }

    public void setSbclExecutable(@NotNull String newText) {
        sbclExecutable.setText(newText);
    }

    @NotNull
    public String getQuicklispStartScript() {
        return quicklispStartScript.getText();
    }

    public void setQuicklispStartScript(@NotNull String newText) {
        quicklispStartScript.setText(newText);
    }

    public int getPort() {
        return Integer.parseInt(port.getText());
    }

    public void setPort(int p) {
        port.setText("" + p);
    }

}
