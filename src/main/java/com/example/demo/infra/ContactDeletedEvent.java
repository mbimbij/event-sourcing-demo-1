package com.example.demo.infra;

public class ContactDeletedEvent extends Event {
  @Override
  public EventType getType() {
    return EventType.DELETED;
  }

  @Override
  public State apply(State currentState) {
    return new State();
  }
}
