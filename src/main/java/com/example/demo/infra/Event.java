package com.example.demo.infra;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public abstract class Event {
  protected final UUID id;
  protected final ZonedDateTime dateTime;

  public Event() {
    id = UUID.randomUUID();
    dateTime = ZonedDateTime.now();
  }

  public abstract State apply(State currentState);
}
