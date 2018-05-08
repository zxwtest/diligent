package xyx.njtech.edu.cn.diligentnode.lock.verification;

public interface ILockView {

    /**
     *  错误
     *  @describe
     */
    void onError();

    /**
     *  正确
     *  @describe
     */
    void onSuccess();
}
