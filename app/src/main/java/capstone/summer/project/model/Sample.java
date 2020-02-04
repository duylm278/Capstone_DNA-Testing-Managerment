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
public class Sample {
    private int id;
    private String CollectDate;
    private String Collector;
    private String ReceiveDate;
//    private String Receiver;
    private String DeliverDate;
    private String Deliverer;
    private String StartDate;
    private String EndDate;
    private String SaveFileDate;
    private String GDGSignal;
    private String Amount;
    private Integer Status;
    private String Name;


}
