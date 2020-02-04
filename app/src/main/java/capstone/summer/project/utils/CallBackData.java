package capstone.summer.project.utils;

public interface CallBackData<T> {
    void onSuccess(T t);
    void onFail(String message);
}
