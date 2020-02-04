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
public class ExtractLog {
    private int ID;
    private String EditDate;
    private ExtractProcess Log;
    private int ExtractProcessID;
    private int UserID;
    private ExtractProcess LogPrevious;

}
