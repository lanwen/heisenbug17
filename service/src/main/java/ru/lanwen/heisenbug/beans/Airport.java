
package ru.lanwen.heisenbug.beans;

import lombok.Data;

import java.io.Serializable;

@Data
public class Airport implements Serializable {

    private final static long serialVersionUID = 271283517L;

    protected String scheduled;
    protected String iata;
    protected String name;
    protected Region city;
    protected Region country;
    protected String tz;
}
