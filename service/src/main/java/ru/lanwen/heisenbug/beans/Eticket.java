
package ru.lanwen.heisenbug.beans;

import java.io.Serializable;
import java.util.List;

public class Eticket implements Serializable {

    private final static long serialVersionUID = 271283517L;
    protected EticketMeta meta;
    protected List<Flight> flights;

    public EticketMeta getMeta() {
        return meta;
    }

    public void setMeta(EticketMeta value) {
        this.meta = value;
    }

    public List<Flight> getFlights() {
        return this.flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Eticket eticket = (Eticket) o;

        if (meta != null ? !meta.equals(eticket.meta) : eticket.meta != null) return false;
        return flights != null ? flights.equals(eticket.flights) : eticket.flights == null;
    }

    @Override
    public int hashCode() {
        int result = meta != null ? meta.hashCode() : 0;
        result = 31 * result + (flights != null ? flights.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "Eticket{" +
                "meta=" + meta +
                ", flights=" + flights +
                '}';
    }
}
