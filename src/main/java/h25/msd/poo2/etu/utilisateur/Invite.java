/* Danielle Mafouo Fouodji */

package h25.msd.poo2.etu.utilisateur;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Invite extends AbstractUtilisateur {


    public Invite() {
        super("Invité");
    }

    @Override
    public void sauvegardeUtilisateur(DataOutputStream dos) {
        try {
            dos.writeUTF("invite");
            dos.writeUTF(getNom());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static AbstractUtilisateur chargeUtilisateur(DataInputStream dis) {
        Invite invite;
        try {
            String nomInvite = dis.readUTF();
            invite = new Invite();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (dis != null)
                try {
                    dis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
        return invite;
    }
}
