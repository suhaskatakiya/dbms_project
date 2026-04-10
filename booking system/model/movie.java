package model;

public class movie {
    private String name;
    private boolean isAdultOnly;

    public movie(String name, boolean isAdultOnly) {
        this.name = name;
        this.isAdultOnly = isAdultOnly;
    }

    public String getName() {
        return name;
    }

    public boolean isAdultOnly() {
        return isAdultOnly;
    }
}