package com.example.demo.infra;

import lombok.Value;

import static com.example.demo.infra.EventType.CREATED;

@Value
public class ContactCreatedEvent extends Event {
  private ContactState contact;

  @Override
  public EventType getType() {
    return CREATED;
  }

  @Override
  public State apply(State state) {
    return new State(contact);
  }
}
