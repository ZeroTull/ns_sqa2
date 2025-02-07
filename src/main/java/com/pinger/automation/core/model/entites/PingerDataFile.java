package com.pinger.automation.core.model.entites;

import lombok.Data;

@Data
public class PingerDataFile {
    private String name;
    private String directory;
    private String path;

    public String getPath() {
        return directory.concat(name);
    }
}
