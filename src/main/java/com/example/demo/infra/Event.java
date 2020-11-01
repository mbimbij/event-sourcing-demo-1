package com.example.demo.infra;

import java.time.ZonedDateTime;
import java.util.UUID;

public abstract class Event {
  protected UUID id;
  protected ZonedDateTime dateTime;
  public abstract EventType getType();
  public abstract State apply(State currentState);
}
