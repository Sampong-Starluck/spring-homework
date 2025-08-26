package org.sampong.springLearning.share.configuration;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.sampong.springLearning.share.annotation.Logger;
import org.sampong.springLearning.share.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.util.UUID;

@Aspect
@Component
@Slf4j
public class LoggerAnnotation {

    @Around("@annotation(logger)")
    public Object loggerAround(ProceedingJoinPoint joinPoint, Logger logger) {

        Object process = null;
        try {
            // Generate unique request ID
            var startUUID = UUID.randomUUID().toString();
            var endedUUID = UUID.randomUUID().toString();

            // Get process ID
            String processId = ManagementFactory.getRuntimeMXBean()
                    .getName()
                    .split("@")[0]; // format: pid@hostname

            log.info("Start >>====>> [{}] [PID:{}] {}", startUUID, processId, logger.value());

            process = joinPoint.proceed();
            log.info("End   <<====<< [{}] [PID:{}] {}", endedUUID, processId, logger.value());
        } catch (Throwable e) {
            log.error(e.getMessage());
//            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
        return process;
    }
}
