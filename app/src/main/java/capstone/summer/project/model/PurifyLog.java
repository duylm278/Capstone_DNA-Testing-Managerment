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
public class PurifyLog {
    private int ID;
    private String EditDate;
    private PurifyProcess Log;
    private int PurifyProcessID;
    private int UserID;
    private PurifyProcess LogPrevious;


}
