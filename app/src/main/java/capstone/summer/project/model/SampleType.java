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
public class SampleType {
    private int ID;
    private String Signal;
    private String UserID;
    private boolean LitigantsCheck;
    private boolean  Sex;
    private String Name;


}
