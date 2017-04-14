
package ru.lanwen.heisenbug.beans;

import lombok.Data;

import java.io.Serializable;

@Data
public class Region implements Serializable {

    private final static long serialVersionUID = 271283517L;
    protected String name;
    protected double latitude;
    protected double longitude;
}
