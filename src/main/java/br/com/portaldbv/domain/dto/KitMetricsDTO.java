package br.com.portaldbv.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KitMetricsDTO {

    private UUID userId;
    private String userName;
    private Integer scarfQuantity = 0;
    private Integer bibleQuantity = 0;
    private Integer activityNotebookQuantity = 0;
    private Integer bottleQuantity = 0;
    private Integer capQuantity = 0;
    private Integer pencilQuantity = 0;
    private Integer bibleStudyQuantity = 0;
    private Integer total = 0;

    public Integer sumTotal() {
        return scarfQuantity + bibleQuantity + activityNotebookQuantity + bottleQuantity + capQuantity + pencilQuantity + bibleStudyQuantity;
    }

}
