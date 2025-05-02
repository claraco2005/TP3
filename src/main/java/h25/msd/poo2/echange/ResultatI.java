package h25.msd.poo2.echange;

import java.time.LocalDateTime;

public interface ResultatI extends Comparable<ResultatI> {

    AlgorithmeI getAlgoUtilise();

    LocalDateTime getQuand();

    double getDureeTraitement();

    String getTexteOriginal();

    String getTexteFinal();
}
