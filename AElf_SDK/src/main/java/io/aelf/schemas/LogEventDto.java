package io.aelf.schemas;

import java.util.List;


public class LogEventDto {
    private String Address;
    private String Name;
    private List<String> Indexed;
    private String NonIndexed;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<String> getIndexed() {
        return Indexed;
    }

    public void setIndexed(List<String> indexed) {
        Indexed = indexed;
    }

    public String getNonIndexed() {
        return NonIndexed;
    }

    public void setNonIndexed(String nonIndexed) {
        NonIndexed = nonIndexed;
    }
}
