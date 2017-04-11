
package ru.lanwen.heisenbug.beans;

import java.io.Serializable;

public class Flight implements Serializable {

    private final static long serialVersionUID = 271283517L;
    protected String number;
    protected String status;
    protected Airline airline;
    protected Airport departure;
    protected Airport arrival;

    public String getNumber() {
        return number;
    }

    public void setNumber(String value) {
        this.number = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline value) {
        this.airline = value;
    }

    public Airport getDeparture() {
        return departure;
    }

    public void setDeparture(Airport value) {
        this.departure = value;
    }

    public Airport getArrival() {
        return arrival;
    }

    public void setArrival(Airport value) {
        this.arrival = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        if (number != null ? !number.equals(flight.number) : flight.number != null) return false;
        if (status != null ? !status.equals(flight.status) : flight.status != null) return false;
        if (airline != null ? !airline.equals(flight.airline) : flight.airline != null) return false;
        if (departure != null ? !departure.equals(flight.departure) : flight.departure != null) return false;
        return arrival != null ? arrival.equals(flight.arrival) : flight.arrival == null;
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (airline != null ? airline.hashCode() : 0);
        result = 31 * result + (departure != null ? departure.hashCode() : 0);
        result = 31 * result + (arrival != null ? arrival.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "number='" + number + '\'' +
                ", status='" + status + '\'' +
                ", airline=" + airline +
                ", departure=" + departure +
                ", arrival=" + arrival +
                '}';
    }
}
