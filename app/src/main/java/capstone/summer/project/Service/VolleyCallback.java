package capstone.summer.project.Service;

public interface VolleyCallback<T> {
    void onSuccess(T data);
    void onFail(T data);
}
