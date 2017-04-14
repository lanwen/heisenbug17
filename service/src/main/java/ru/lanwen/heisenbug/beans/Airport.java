
package ru.lanwen.heisenbug.beans;

import java.io.Serializable;
import java.util.Date;

public class Airport implements Serializable {

    private final static long serialVersionUID = 271283517L;

    protected String scheduled;
    protected String iata;
    protected String name;
    protected Region city;
    protected Region country;
    protected String tz;

    public String getScheduled() {
        return scheduled;
    }

    public void setScheduled(String value) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Airport airport = (Airport) o;

        if (scheduled != null ? !scheduled.equals(airport.scheduled) : airport.scheduled != null) return false;
        if (iata != null ? !iata.equals(airport.iata) : airport.iata != null) return false;
        if (name != null ? !name.equals(airport.name) : airport.name != null) return false;
        if (city != null ? !city.equals(airport.city) : airport.city != null) return false;
        if (country != null ? !country.equals(airport.country) : airport.country != null) return false;
        return tz != null ? tz.equals(airport.tz) : airport.tz == null;
    }

    @Override
    public int hashCode() {
        int result = scheduled != null ? scheduled.hashCode() : 0;
        result = 31 * result + (iata != null ? iata.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (tz != null ? tz.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "scheduled=" + scheduled +
                ", iata='" + iata + '\'' +
                ", name='" + name + '\'' +
                ", city=" + city +
                ", country=" + country +
                ", tz='" + tz + '\'' +
                '}';
    }
}
