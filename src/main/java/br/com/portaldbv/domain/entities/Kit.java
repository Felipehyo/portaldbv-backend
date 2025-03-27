package br.com.portaldbv.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Kit {

    private Long id;
    private User user;
    private Boolean scarf = Boolean.FALSE;
    private Boolean bible = Boolean.FALSE;
    private Boolean activityNotebook = Boolean.FALSE;
    private Boolean bottle = Boolean.FALSE;
    private Boolean cap = Boolean.FALSE;
    private Boolean pencil = Boolean.FALSE;
    private Boolean bibleStudy = Boolean.FALSE;

}