package org.sopra.rogueguild.view.components;

import java.io.PrintStream;

import org.sopra.rogueguild.view.utils.FramePrinter;

/**
 * Componente de vista responsable de mostrar mensajes al jugador.
 *
 * Encapsula los mensajes recurrentes del sistema (continuar, salir, prompt)
 * y delega el renderizado en un marco visual a {@link org.sopra.rogueguild.view.utils.FramePrinter}.
 *
 * @author Marc Nacher
 * @author Maria Herrero
 */
public class MessageView {
    private final FramePrinter frame;

    public MessageView(PrintStream out, int width) {
        this.frame = new FramePrinter(out, width);
    }

    public void showMessage(String message) {
        frame.box(message);
    }

    public void pressKeyMessage() {
        showMessage("Pulsa ENTER para continuar");
    }

    public void quitMessage() {
        showMessage("Nos vemos pronto.");
    }

    public void showPrompt(String prompt) {
        System.out.print(prompt);
    }
}
