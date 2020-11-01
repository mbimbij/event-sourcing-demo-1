package com.example.demo.infra;

import java.time.ZonedDateTime;
import java.util.UUID;

public class ContactDeletedEvent extends Event {
  public ContactDeletedEvent() {
  }

  public ContactDeletedEvent(UUID id, ZonedDateTime dateTime) {
    super(id, dateTime);
  }

  @Override
  public State apply(State currentState) {
    return new State();
  }
}
