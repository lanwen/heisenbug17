
package ru.lanwen.heisenbug.beans;

public enum SchemaVersion {

    V_1("V1");
    private final String value;

    SchemaVersion(String v) {
        value = v;
    }

    public String value() {
        return value;
    }
}
