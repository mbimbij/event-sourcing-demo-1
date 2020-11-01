package com.example.demo.infra;

import lombok.Value;

import java.time.ZonedDateTime;
import java.util.UUID;

@Value
public class ContactCreatedEvent extends Event {
  private ContactState contactState;

  public ContactCreatedEvent(ContactState contactState) {
    this.contactState = contactState;
  }

  public ContactCreatedEvent(UUID id, ZonedDateTime dateTime, ContactState contactState) {
    super(id, dateTime);
    this.contactState = contactState;
  }

  @Override
  public State apply(State currentState) {
    return new State(contactState);
  }
}
