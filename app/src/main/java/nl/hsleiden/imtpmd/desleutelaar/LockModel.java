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
    public String infoExerpt;
    public String info;
    public int prize;
    // String json = "[{lockName: ' lock 1', infoExerpt: 'bla', info: 'blabla', prize: 4}," +
    // "{lockName: ' lock 2', infoExerpt: 'bla', info: 'blabla', prize: 2}," +
    // "{lockName: ' lock 3', infoExerpt: 'bla', info: 'blabla', prize: 3}]";
}
