package nl.hsleiden.imtpmd.desleutelaar;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Marijn on 16/10/2015.
 */
public class CustomerModel {
    // TRUC : stel dat je JSON name van een kolom heel raar is , dan pas ik hier deze truc toe:
    @SerializedName("hiephia")
    public String name;

    // name (komt uit Json) mapt nu op variabele die we lokaal gebruiker : (hiephoi)

    public String customerName;
    public String adress;
    public String phone;
    public String mail;
    public String type;
    // String json = "[{customerName: 'Pino', adress: 'sesamestreet 16', phone: '0900 pino', email: 'pino@gmail.com', type: 'lock 1'}," +
    // "{customerName: 'Tommy', adress: 'sesamestreet 3', phone: '0900 tommy', email: 'tommy@gmail.com', type: 'lock 3'}, " +
    // "{customerName: 'Grover', adress: 'sesamestreet 4', phone: '0900 grover', email: 'grover@gmail.com', type: 'lock 2'}]";
}
