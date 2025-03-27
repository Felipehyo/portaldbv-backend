package br.com.portaldbv.domain.dto;

import br.com.portaldbv.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PresencePercentageMetricsDTO {

    private User user;
    private Integer percentage;

    public String getUserName() {
        return user.getName();
    }

}
