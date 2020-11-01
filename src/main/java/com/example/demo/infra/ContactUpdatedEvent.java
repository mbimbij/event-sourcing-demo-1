package com.example.demo.infra;

import lombok.Value;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

@Value
public class ContactUpdatedEvent extends Event {

  private Map<String, Object> updatedFields;

  public ContactUpdatedEvent(Map<String, Object> updatedFields) {
    this.updatedFields = updatedFields;
  }

  public ContactUpdatedEvent(UUID id, ZonedDateTime dateTime, Map<String, Object> updatedFields) {
    super(id, dateTime);
    this.updatedFields = updatedFields;
  }

  @Override
  public State apply(State currentState) {
    ContactState previousState = currentState.getContactState();
    String mail = (String) updatedFields.getOrDefault("mail", previousState.getMail());
    String username = (String) updatedFields.getOrDefault("username", previousState.getUsername());
    String address = (String) updatedFields.getOrDefault("address", previousState.getAddress());
    String phoneNumber = (String) updatedFields.getOrDefault("phoneNumber", previousState.getPhoneNumber());
    ContactState newState = new ContactState(mail, username, address, phoneNumber);
    return new State(newState);
  }
}
