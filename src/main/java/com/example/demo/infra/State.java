package com.example.demo.infra;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class State {
  public static final State EMPTY = new State();
  private ContactState contactState;

  public State() {
  }

  public ContactState getContactState() {
    return contactState;
  }

  public State(ContactState contactState) {
    this.contactState = contactState;
  }
}
