package xyx.njtech.edu.cn.diligentnode.lock.base;

public abstract class BasePresenter<T> {

    public T mView;

    /**
     * 绑定View 初始化时调用
     *
     * @param mView
     * @describe
     */
    public void attch(T mView) {
        this.mView = mView;
    }

    /**
     * 分离view，View销毁时调用
     *
     * @describe
     */
    public void detach() {
        mView = null;
    }


}
