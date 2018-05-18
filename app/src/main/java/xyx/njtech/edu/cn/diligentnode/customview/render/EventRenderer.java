package xyx.njtech.edu.cn.diligentnode.customview.render;

import android.support.annotation.LayoutRes;
import android.view.View;
import xyx.njtech.edu.cn.diligentnode.models.CalendarEvent;

import java.lang.reflect.ParameterizedType;


public abstract class EventRenderer<T extends CalendarEvent> {

    // 初始化一些布局的信息
    public abstract void render(final View view, final T event);

    //返回一个布局文件
    @LayoutRes
    public abstract int getEventLayout();

    /**
     * 得到映射布局的泛型类型
     * @return 布局的泛型类型
     */
    public Class<T> getRenderType() {
        //ParameterizedType 表示参数化类型
        //getGenericSuperclass()获得带有泛型的父类
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        //返回表示此类型实际类型参数的 Type 对象的数组
        return (Class<T>) type.getActualTypeArguments()[0];
    }
}
