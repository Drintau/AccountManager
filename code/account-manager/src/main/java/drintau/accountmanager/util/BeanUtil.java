package drintau.accountmanager.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BeanUtil {

    /*=== Bean拷贝 ===*/

    /**
     * 拷贝Bean对象属性到目标类型
     * 通过指定目标类型自动创建实例，然后拷贝属性
     *
     * @param <T>         目标对象类型
     * @param source      源bean对象
     * @param targetClass 目标bean类，自动实例化此对象
     * @return 目标对象
     */
    public static <T> T copy(@NotNull Object source, Class<T> targetClass) {
        T targetObject;
        try {
            targetObject = targetClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("未找到目标类!");
        }
        copyProperties(source, targetObject);
        return targetObject;
    }

    /**
     * 复制属性值
     * @param source 源对象
     * @param target 目标对象
     * @param ignoreProperties 忽略的属性值
     */
    public static void copyProperties(@NotNull Object source, @NotNull Object target, String... ignoreProperties) {
        BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    /*=== List拷贝 ===*/

    /**
     * 拷贝List Bean对象属性
     *
     * @param <S>    源bean类型
     * @param <T>    目标bean类型
     * @param source 源bean对象list
     * @param targetClass 目标bean类，自动实例化此对象
     * @return 目标bean对象list，如果源bean对象list是null或者没有数据，将返回空集合
     */
    public static <S, T> List<T> copyList(Collection<S> source, Class<T> targetClass) {
        List<T> targetList = new ArrayList<>(16);
        if (CollectionUtils.isEmpty(source)) {
            return targetList;
        }
        for (S s : source) {
            T targetObj = copy(s, targetClass);
            targetList.add(targetObj);
        }
        return targetList;
    }

}
