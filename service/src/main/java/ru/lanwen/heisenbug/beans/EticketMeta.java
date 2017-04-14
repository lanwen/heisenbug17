
package ru.lanwen.heisenbug.beans;

import lombok.Data;

import java.io.Serializable;

@Data
public class EticketMeta implements Serializable {

    private final static long serialVersionUID = 271283517L;
    protected SchemaVersion schemaVersion;
}
