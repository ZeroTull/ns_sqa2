package com.pinger.automation.core.model.entites.dto;

import com.pinger.automation.core.model.entites.dto.input.PingerInputDataFile;
import com.pinger.automation.core.model.entites.dto.output.PingerOutputDataFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class TestDataDto {
    private PingerInputDataFile inputDataFile = new PingerInputDataFile();
    private PingerOutputDataFile outputDataFile = new PingerOutputDataFile();
}
