package com.pinger.automation.core.model.entites.dto.input;

import com.pinger.automation.core.model.entites.dto.PingerDataFileDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PingerInputDataFile extends PingerDataFileDto {
    private InputDataDto dto;
}
