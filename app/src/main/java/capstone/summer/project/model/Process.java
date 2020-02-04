package capstone.summer.project.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Process {
   private String processName;
   private String processStatus;
   private String sampleID;
   //    public Process(String processName, String processStatus) {
//        this.processName = processName;
//        this.processStatus = processStatus;
//    }
//
//    public String getProcessName() {
//        return processName;
//    }
//
//    public void setProcessName(String processName) {
//        this.processName = processName;
//    }
//
//    public String getProcessStatus() {
//        return processStatus;
//    }

//    public void setProcessStatus(String processStatus) {
//        this.processStatus = processStatus;
//    }
}
