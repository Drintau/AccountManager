package mjhct.accountmanager.util;

import cn.hutool.extra.cglib.CglibUtil;
import org.springframework.beans.BeanUtils;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class BeanUtil {

    /**
     * 拷贝对象属性
     * @param source 源对象
     * @param targetClass 目标类，自动实例化此对象
     * @param <T> 目标对象类型
     * @return 目标对象
     */
    public static <T> T copy(Object source, Class<T> targetClass) {
        return CglibUtil.copy(source, targetClass);
    }

    /**
     * 复制属性值
     * @param source 源对象
     * @param target 目标对象
     * @param ignoreProperties 忽略的属性值
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    /**
     * 拷贝集合对象属性
     * @param source 源对象List
     * @param target 目标对象
     * @param <S> 源类型
     * @param <T> 目标类型
     * @return 目标对象List
     */
    public static <S, T> List<T> copyList(Collection<S> source, Supplier<T> target) {
        return CglibUtil.copyList(source, target);
    }

}
