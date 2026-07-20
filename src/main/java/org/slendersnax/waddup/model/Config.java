package org.slendersnax.waddup.model;

public class Config {
    private final String name;
    private WADSession wadSession;

    public Config(String name, WADSession wadSession) {
        this.name = name;
        this.wadSession = wadSession;
    }

    public String getName() {
        return name;
    }

    public WADSession getWadSession() {
        return wadSession;
    }

    public void setWadSession(WADSession wadSession) {
        this.wadSession = new WADSession(wadSession);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
