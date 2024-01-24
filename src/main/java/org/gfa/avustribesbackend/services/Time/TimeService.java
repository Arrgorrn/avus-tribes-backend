package org.gfa.avustribesbackend.services.Time;

import java.time.LocalDateTime;

public interface TimeService {
  LocalDateTime getCurrentTime();
  long calculateElapsedTime(LocalDateTime startTime, LocalDateTime endTime);
}
