package gavin.framework;

import gavin.framework.helper.BeanHelper;
import gavin.framework.helper.ClassHelper;
import gavin.framework.helper.ConfigHelper;
import gavin.framework.helper.IocHelper;
import gavin.framework.util.ClassUtil;

public final class HelperLoader {
    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ConfigHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName(), false);
        }
    }
}
