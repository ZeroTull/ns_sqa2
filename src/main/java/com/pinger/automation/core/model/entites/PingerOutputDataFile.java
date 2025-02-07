package com.pinger.automation.core.model.entites;

import com.pinger.automation.core.model.entites.dto.PingOutputDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PingerOutputDataFile extends PingerDataFile {
    private PingOutputDto dto;
}
