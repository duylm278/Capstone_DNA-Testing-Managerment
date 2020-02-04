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
public class PurifyProcess {
    private int ID;
    private String StartDate;
    private String EndDate;
    private String Note;
    private Integer Status;
    private Integer SamplesID;
    private Sample Sample;
    private Integer KitID;
    private Kit Kit;
    private Integer UserID;
    private User User;

}
