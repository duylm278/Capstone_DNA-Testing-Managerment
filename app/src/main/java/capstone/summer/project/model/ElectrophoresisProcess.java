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
public class ElectrophoresisProcess {
    private int ID;
    private String StartDate;
    private String EndDate;
    private String GelName;
    private Integer Status;
    private Integer StepPosition;
    private Integer UserID;
    private User User;
    private Integer SamplesID;
    private Sample Sample;
    private Integer SystemID;
    private System System;


}
