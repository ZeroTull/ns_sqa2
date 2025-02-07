package com.pinger.automation.core.model.entites;

import com.pinger.automation.core.model.entites.dto.OutputDataDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PingerInputDataFile extends PingerDataFile {
    private OutputDataDto dto;
}
