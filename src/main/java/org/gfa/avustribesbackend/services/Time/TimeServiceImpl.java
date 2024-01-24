package org.gfa.avustribesbackend.services.Time;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class TimeServiceImpl implements TimeService {
    @Override
    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }

    @Override
    public long calculateElapsedTime(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        return duration.getSeconds();
    }
}
