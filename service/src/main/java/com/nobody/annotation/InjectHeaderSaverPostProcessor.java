package com.nobody.annotation;

import com.nobody.dao.impl.HeaderDaoImpl;
import com.nobody.entity.Header;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class InjectHeaderSaverPostProcessor implements BeanPostProcessor {

    private HeaderDaoImpl headerDao;

    @Autowired
    public InjectHeaderSaverPostProcessor(HeaderDaoImpl headerDao) {
        this.headerDao = headerDao;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(InjectHeaders.class)) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, buildField());
            }
        }
        return bean;
    }

    private Map<String, String> buildField() {
        List<Header> headers = headerDao.getAll();
        return headers.stream().collect(Collectors.toMap(Header::getKey, Header::getValue, (a, b) -> b));
    }
}
