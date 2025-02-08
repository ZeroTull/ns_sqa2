package com.pinger.automation.core.model.entites.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PingerDataFileDto {
    private String name;
    private String directory;
    private String path;

    public String getPath() {
        return directory.concat(name);
    }
}
