package com.pinger.automation.core.model.entites.dto.output;

import com.pinger.automation.core.model.entites.dto.PingerDataFileDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PingerOutputDataFile extends PingerDataFileDto {
    private OutputDataDto dto;
}
