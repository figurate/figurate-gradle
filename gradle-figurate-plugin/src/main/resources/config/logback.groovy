import ch.qos.logback.classic.Level
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%-5level %-30logger %msg%n"
    }
}

appender("FILE", RollingFileAppender) {
    file = "$logDir/$logFilename"
    append = true
    encoder(PatternLayoutEncoder) {
        pattern = "[%date] %level %logger - %msg%n"
    }
    rollingPolicy(FixedWindowRollingPolicy) {
        fileNamePattern = "$logDir/$logFilename.%i"
        minIndex = 1
        maxIndex = 4
    }
    triggeringPolicy(SizeBasedTriggeringPolicy) {
        maxFileSize = '100KB'
    }
}

root(Level.INFO, ["CONSOLE", "FILE"])
