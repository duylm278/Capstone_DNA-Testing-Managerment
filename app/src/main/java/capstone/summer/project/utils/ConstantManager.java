package capstone.summer.project.utils;

public class ConstantManager {
//    public static final String HOST = "http://10.14.0.210:45457/";
    public static final String HOST = "http://172.20.10.3:56524/";
//public static final String HOST = "http://192.168.0.105:56524/";
    public static final String USER_ID = "UserID";
    public static final String USER_PASSWORD = "UserPassword";
    public static final String USER_MANAGER = "UserManager";

//        public static final String HOST = "http://192.168.43.58:45455/";
    public interface API {
        String GET_CASE = "Cases";
        String GET_SAMPLE = "Samples";
        String GET_SAMPLETYPE = "SampleTypes";
        String GET_LITIGANT = "Litigants";
        String GET_KITS = "Kits";
        String GET_USERS = "Users";
        String GET_SYSTEMS = "Systems";
        String GET_ORGANIZATION = "Organizations";

        String GET_EXTRACTPROCESS = "ExtractProcesses";
        String GET_EXTRACTLOG = "ExtractProcessesLogs";

        String GET_PCRPROCESS = "PCRProcesses";
        String GET_PCRLOG = "PCRProcessesLogs";

        String GET_ELECTROPHORESISPROCESS = "ElectrophoresisProcesses";
        String GET_ELECTROPHORESISLOG = "ElectrophoresisProcessLogs";

        String GET_PURIFYPROCESS = "PurifyProcesses";
        String GET_PURIFYLOG = "PurifyProcessLogs";

        String GET_RESULT = "Results";
        String GET_RESULTLOG = "ResultLogs";
    }
}
