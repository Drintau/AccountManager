package mjhct.accountmanager.util;

import cn.hutool.core.util.StrUtil;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * BeanCopier属性缓存
 * 缓存用于防止多次反射造成的性能问题
 */
public enum BeanCopierCache {
    /**
     * BeanCopier属性缓存单例
     */
    INSTANCE;

    private final ConcurrentHashMap<String, BeanCopier> cache = new ConcurrentHashMap<>(16);

    /**
     * 获得类与转换器生成的key在{@link BeanCopier}的Map中对应的元素
     *
     * @param srcClass    源Bean的类
     * @param targetClass 目标Bean的类
     * @param converter   转换器
     * @return Map中对应的BeanCopier
     */
    public BeanCopier get(Class<?> srcClass, Class<?> targetClass, Converter converter) {
        final String key = genKey(srcClass, targetClass, converter);
        BeanCopier beanCopier = cache.get(key);

        if (beanCopier == null) {
             beanCopier = BeanCopier.create(srcClass, targetClass, converter != null);
             cache.put(key, beanCopier);
        }

        return beanCopier;
    }

    /**
     * 获得类与转换器生成的key
     *
     * @param srcClass    源Bean的类
     * @param targetClass 目标Bean的类
     * @param converter   转换器
     * @return 属性名和Map映射的key
     */
    private String genKey(Class<?> srcClass, Class<?> targetClass, Converter converter) {
        String key = StrUtil.format("{}#{}", srcClass.getName(), targetClass.getName());
        if(null != converter){
            key += "#" + converter.getClass().getName();
        }
        return key;
    }

}
