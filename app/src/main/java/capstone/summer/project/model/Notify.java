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
public class Notify {
    private String Content;
    private boolean Readed;
    private Integer ReceiverId;
    private Integer SenderId;
    private String SenderName;
    private String createAt;

}
