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
public class ExtractProcess {
    private int ID;
    private String GDGSignal;
    private String StartDate;
    private String EndDate;
    private String Amount;
    private String Note;
    private Integer Status;
    private Integer StepPosition;
    private Integer UserID;
    private User User;
    private Integer KitID;
    private Kit Kit;
    private Integer SamplesID;
    private Sample Sample;
    private Integer SystemIDADN;
    private System SystemADN;
    private Integer SystemIDExtract;
    private System SystemExtract;

}
