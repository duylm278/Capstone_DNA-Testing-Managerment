package capstone.summer.project.model;
//
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
public class Login {
    private String Username;
    private String JwtString;

}





//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//public class Case {
//
//    @SerializedName("ID")
//    @Expose
//    private Integer iD;
//    @SerializedName("Code")
//    @Expose
//    private String code;
//    @SerializedName("QDTCReceiveDate")
//    @Expose
//    private String qDTCReceiveDate;
//    @SerializedName("QDTCNumber")
//    @Expose
//    private String qDTCNumber;
//    @SerializedName("QDTCSignDate")
//    @Expose
//    private String qDTCSignDate;
//    @SerializedName("Status")
//    @Expose
//    private String status;
//    @SerializedName("OrganizationID")
//    @Expose
//    private Integer organizationID;
//
//    public Integer getID() {
//        return iD;
//    }
//
//    public void setID(Integer iD) {
//        this.iD = iD;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getQDTCReceiveDate() {
//        return qDTCReceiveDate;
//    }
//
//    public void setQDTCReceiveDate(String qDTCReceiveDate) {
//        this.qDTCReceiveDate = qDTCReceiveDate;
//    }
//
//    public String getQDTCNumber() {
//        return qDTCNumber;
//    }
//
//    public void setQDTCNumber(String qDTCNumber) {
//        this.qDTCNumber = qDTCNumber;
//    }
//
//    public String getQDTCSignDate() {
//        return qDTCSignDate;
//    }
//
//    public void setQDTCSignDate(String qDTCSignDate) {
//        this.qDTCSignDate = qDTCSignDate;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public Integer getOrganizationID() {
//        return organizationID;
//    }
//
//    public void setOrganizationID(Integer organizationID) {
//        this.organizationID = organizationID;
//    }
//
//}