package pspro.damas.servidor;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class EIS {
    private final ObjectOutputStream flujoSalida;

    public EIS(ObjectOutputStream flujoSalida) {
        this.flujoSalida = flujoSalida;
    }

    public void enviarTexto(String texto) {
        try {
            flujoSalida.writeUTF(texto);
            flujoSalida.flush();
            System.out.println(texto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
