package br.com.spikelogbackintercept.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {
    private final ObjectMapper mapper;

    @Pointcut("execution(* br.com.spikelogbackintercept.application..*.*(..))")
    public void pointCut() {
    }

    @Before(value = "pointCut()")
    public void logMethodBefore(JoinPoint point) {
        Class<?> declaringType = point.getSignature().getDeclaringType();
        final Logger log = LoggerFactory.getLogger(declaringType);
        try {
            final Method method = ((MethodSignature) point.getSignature()).getMethod();
            method.setAccessible(true);
            final String methodName = method.getName();
            String simpleName = declaringType.getSimpleName();
            Object[] args = point.getArgs();

            List<JsonNode> argsCrypt = Arrays.stream(args)
                    .map(this::getValueJson)
                    .collect(Collectors.toList());

            log.info(simpleName + "@" + methodName + " Input - " + getJsonNode(argsCrypt));
        } catch (RuntimeException ex) {
            log.error("Error during intercept logger", ex);
        }
    }


    @AfterReturning(pointcut = "pointCut()", returning = "result")
    public void logMethodAfter(JoinPoint point, Object result) {
        Class<?> declaringType = point.getSignature().getDeclaringType();
        final Logger log = LoggerFactory.getLogger(declaringType);
        try {
            final Method method = ((MethodSignature) point.getSignature()).getMethod();
            final String methodName = method.getName();
            String simpleName = declaringType.getSimpleName();

            JsonNode valueJson = getValueJson(result);

            log.info(simpleName + "@" + methodName + " Output - " + valueJson);
        } catch (RuntimeException ex) {
            log.error("Error during intercept logger", ex);
        }
    }

    private JsonNode getValueJson(Object arg) {
        if (arg == null) {
            return null;
        } else if (isSimpleField(arg)) {
            final boolean mask = isAnyMatchLogMask(arg.getClass().getAnnotations());
            return mask ? getNodeEncrypt() : getJsonNode(arg);
        } else if (arg instanceof Optional<?>) {
            final Optional<?> optional = (Optional<?>) arg;
            return optional.map(this::getValueJson).orElse(null);
        } else if (arg instanceof Iterable<?>) {
            final Iterable<?> iterable = (Iterable<?>) arg;
            List<JsonNode> jsonNodes = StreamSupport.stream(iterable.spliterator(), false)
                    .map(this::getValueJson)
                    .collect(Collectors.toList());
            return getJsonNode(jsonNodes);
        } else if (arg instanceof Map) {
            final Map<?, ?> maps = (Map<?, ?>) arg;
            return getJsonNode(maps);
        }
        return getValue(arg);
    }

    private JsonNode getValue(Object arg) {
        Class<?> aClass = arg.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        Map<String, Object> fields = new HashMap<>();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            boolean mask = isAnyMatchLogMask(declaredField.getAnnotations());
            try {
                fields.put(declaredField.getName(), mask ? getNodeEncrypt() : declaredField.get(arg));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return getJsonNode(fields);
    }

    private static boolean isAnyMatchLogMask(Annotation[] annotations) {
        return Arrays.stream(annotations).anyMatch(LogMask.class::isInstance);
    }

    private static TextNode getNodeEncrypt() {
        return new TextNode("***");
    }

    private JsonNode getJsonNode(Object object) {
        return mapper.valueToTree(object);
    }

    private boolean isSimpleField(Object object) {
        Class<?> className = object.getClass();
        return SIMPLE_FIELD_TYPES.contains(className) || className.isEnum() || className.isArray();
    }

    private static final HashSet<Class<?>> SIMPLE_FIELD_TYPES = new HashSet<>(
            Arrays.asList(
                    String.class,
                    Long.class,
                    Integer.class,
                    Short.class,
                    Byte.class,
                    Double.class,
                    Float.class,
                    Character.class,
                    Boolean.class,
                    Date.class,
                    LocalDate.class,
                    LocalDateTime.class,
                    Instant.class,
                    BigDecimal.class
            ));
}