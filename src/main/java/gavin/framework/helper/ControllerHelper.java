package gavin.framework.helper;

import gavin.framework.annotation.Action;
import gavin.framework.bean.Handler;
import gavin.framework.bean.Request;
import gavin.framework.util.ArrayUtil;
import gavin.framework.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class ControllerHelper {
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static {
        //获取所有Controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtil.isNotEmpty(controllerClassSet)) {
            //遍历这些类
            for (Class<?> controllerClass : controllerClassSet) {
                //获取Controller类中定义的方法
                Method[] methods = controllerClass.getDeclaredMethods();
                if (ArrayUtil.isNotEmpty(methods)) {
                    for (Method method : methods) {
                        //判断当前方法中是否带有Action注解
                        if (method.isAnnotationPresent(Action.class)) {
                            //从Action注解中获取URL映射规则
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            //验证URL映射规则
                            if (mapping.matches("\\w+:/\\w*")) {
                                String[] array = mapping.split(":");
                                if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                                    //获取请求方法的请求路径
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(controllerClass, method);
                                    //初始化Action Map
                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //获取handler
    public static Handler getHandler(String requetMethod, String requestPath){
        Request request = new Request(requetMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
