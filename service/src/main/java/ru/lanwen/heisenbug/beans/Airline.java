
package ru.lanwen.heisenbug.beans;

import lombok.Data;

import java.io.Serializable;

@Data
public class Airline implements Serializable {

    private final static long serialVersionUID = 271283517L;
    protected String name;
    protected String iata;
    protected String icao;
}
