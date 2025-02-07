package com.pinger.automation.core.model.entites.dto;

import com.pinger.automation.core.model.entites.PingerInputDataFile;
import com.pinger.automation.core.model.entites.PingerOutputDataFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class TestDataDto {
    private PingerInputDataFile inputData;
    private PingerOutputDataFile outputData;
}
