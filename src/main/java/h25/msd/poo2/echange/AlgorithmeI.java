package h25.msd.poo2.echange;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@type")
public interface AlgorithmeI {
    String encrypte(String texteLisible);

    String decrypte(String texteEncrypte);

    @JsonIgnore
    String getNom();

    @JsonIgnore
    int getNombreUsages();

    @JsonIgnore
    void setApplicationUI(ApplicationUI tp2Controller);
}
