package nl.hsleiden.imtpmd.desleutelaar;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Marijn on 16/10/2015.
 */
public class LockModel {
    // TRUC : stel dat je JSON name van een kolom heel raar is , dan pas ik hier deze truc toe:
    @SerializedName("hiephio")
    public String name;

    // name (komt uit Json) mapt nu op variabele die we lokaal gebruiker : (hiephoi)

    public String lockName;
    public String INFO_EXCERPT;
    public String INFO;
    public float PRIZE;
    //  String json = "[{name: ' module 1', ects: 3}," +
    // "{name: ' module 2', ects: 3}, " +
    //        "{name: ' module 3', ects: 1}]";
}
