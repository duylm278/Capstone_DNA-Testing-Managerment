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
public class Result {
    private int ID;
//    private T Cols;
//    private T Rows;
    private String StartDate;
    private String EndDate;
    private String Software;
    private String Note;
    private String GDGSignal;
    private Integer SampleID;

    private Integer SamplesID;
    private Sample Sample;
    private String SampleName;
    private Integer UserID;
    private User User;
    private String ExcelName;
    private Integer Status;

}
