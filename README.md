# Intercepting Loggers

This project is spike to interception and inject uuid to loggers with logback.

# MDC Interceptor

The MDC (Mapped diagnostic context) manages contextual information on a per-thread basis.
Making possible interrupter of logs and inject uuid, this example, correlationID at logs.

# Advantage

It's not necessary UUID propagation in all logs of process. It's scalable and easy maintenance.

# Note

It was necessary register interceptor in WebMvcConfigurer and add new variable with name
correlationID at file logback.xml.

