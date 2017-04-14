
package ru.lanwen.heisenbug.beans;

import lombok.Data;

import java.io.Serializable;

@Data
public class Flight implements Serializable {

    private final static long serialVersionUID = 271283517L;
    protected String number;
    protected String status;
    protected Airline airline;
    protected Airport departure;
    protected Airport arrival;
}
