
package ru.lanwen.heisenbug.beans;

import java.io.Serializable;

public class Airline implements Serializable {

    private final static long serialVersionUID = 271283517L;
    protected String name;
    protected String iata;
    protected String icao;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String value) {
        this.iata = value;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String value) {
        this.icao = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Airline airline = (Airline) o;

        if (name != null ? !name.equals(airline.name) : airline.name != null) return false;
        if (iata != null ? !iata.equals(airline.iata) : airline.iata != null) return false;
        return icao != null ? icao.equals(airline.icao) : airline.icao == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (iata != null ? iata.hashCode() : 0);
        result = 31 * result + (icao != null ? icao.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Airline{" +
                "name='" + name + '\'' +
                ", iata='" + iata + '\'' +
                ", icao='" + icao + '\'' +
                '}';
    }
}
