package capstone.summer.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElectrophoresisLog {
    private int ID;
    private String EditDate;
    private ElectrophoresisProcess Log;
    private int ElectrophoresisProcessID;
    private int UserID;
    private ElectrophoresisProcess LogPrevious;


}
