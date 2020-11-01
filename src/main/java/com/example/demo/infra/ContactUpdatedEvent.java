package com.example.demo.infra;

import lombok.Value;

import java.util.Map;

@Value
public class ContactUpdatedEvent extends Event {

  private Map<String, Object> updatedFields;

  @Override
  public EventType getType() {
    return EventType.UPDATED;
  }

  @Override
  public State apply(State state) {
    ContactState previousState = state.getContactState();
    String mail = (String) updatedFields.getOrDefault("mail", previousState.getMail());
    String username = (String) updatedFields.getOrDefault("username", previousState.getUsername());
    String address = (String) updatedFields.getOrDefault("address", previousState.getAddress());
    String phoneNumber = (String) updatedFields.getOrDefault("phoneNumber", previousState.getPhoneNumber());
    ContactState newState = new ContactState(mail, username, address, phoneNumber);
    return new State(newState);
  }
}
