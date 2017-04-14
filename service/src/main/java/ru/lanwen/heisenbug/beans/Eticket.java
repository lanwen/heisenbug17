
package ru.lanwen.heisenbug.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Eticket implements Serializable {

    private final static long serialVersionUID = 271283517L;
    protected EticketMeta meta;
    protected List<Flight> flights;
}
