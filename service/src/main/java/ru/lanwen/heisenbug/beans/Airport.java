
package ru.lanwen.heisenbug.beans;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class Airport implements Serializable {

    private final static long serialVersionUID = 271283517L;

    protected ZonedDateTime scheduled;
    protected String iata;
    protected String name;
    protected Region city;
    protected Region country;
    protected String tz;

    public ZonedDateTime getScheduled() {
        return scheduled;
    }

    public void setScheduled(ZonedDateTime value) {
        this.scheduled = value;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String value) {
        this.iata = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }
    public Region getCity() {
        return city;
    }

    public void setCity(Region value) {
        this.city = value;
    }

    public Region getCountry() {
        return country;
    }

    public void setCountry(Region value) {
        this.country = value;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String value) {
        this.tz = value;
    }

}
