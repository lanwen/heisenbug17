
package ru.lanwen.heisenbug.beans;

import java.io.Serializable;

public class EticketMeta implements Serializable {

    private final static long serialVersionUID = 271283517L;
    protected SchemaVersion schemaVersion;

    public SchemaVersion getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(SchemaVersion value) {
        this.schemaVersion = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EticketMeta that = (EticketMeta) o;

        return schemaVersion == that.schemaVersion;
    }

    @Override
    public int hashCode() {
        return schemaVersion != null ? schemaVersion.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "EticketMeta{" +
                "schemaVersion=" + schemaVersion +
                '}';
    }
}
