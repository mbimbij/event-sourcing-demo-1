package com.example.demo.infra;

import com.example.demo.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class StateTest {

  private State initialState;

  @BeforeEach
  void setUp() {
    initialState = new State();
  }

  @Test
  void initialState_shouldBeEmpty() {
    assertThat(initialState).isEqualTo(State.EMPTY);
  }

  @Test
  void givenInitialState_whenApplyCreate_thenStateIsCorrect() {
    ContactState contactState = ContactState.fromContact(Contact.builder()
        .mail("mail")
        .username("username")
        .address("address")
        .phoneNumber("phoneNumber")
        .build());
    ContactCreatedEvent contactCreatedEvent = new ContactCreatedEvent(contactState);
    State newState = contactCreatedEvent.apply(initialState);
    ContactState newStateContact = newState.getContactState();
    assertThat(contactState).isEqualTo(newStateContact);
  }

  @Test
  void givenCreatedEvent_whenApplyUpdate_thenStateUpdated() {
    ContactState contactState = ContactState.fromContact(Contact.builder()
        .mail("mail")
        .username("username")
        .address("address")
        .phoneNumber("phoneNumber")
        .build());
    ContactCreatedEvent contactCreatedEvent = new ContactCreatedEvent(contactState);
    State state1 = contactCreatedEvent.apply(initialState);

    Map<String, Object> updatedFields = Map.of("address", "address2", "phoneNumber", "phoneNumber2");
    ContactUpdatedEvent contactUpdatedEvent = new ContactUpdatedEvent(updatedFields);
    State state2 = contactUpdatedEvent.apply(state1);

    ContactState actualContactState = state2.getContactState();
    ContactState expectedContactState = ContactState.fromContact(Contact.builder()
        .mail("mail")
        .username("username")
        .address("address2")
        .phoneNumber("phoneNumber2")
        .build());
    assertThat(actualContactState).isEqualTo(expectedContactState);
  }

}