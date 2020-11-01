package com.example.demo.infra;

import com.example.demo.Contact;
import com.example.demo.ContactRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class InMemoryEventSourcingRepository implements ContactRepository {
  private EventStore eventStore;

  public InMemoryEventSourcingRepository(EventStore eventStore) {
    this.eventStore = eventStore;
  }

  @Override
  public void create(Contact contact) {
    ContactState contactState = ContactState.fromContact(contact);
    ContactCreatedEvent contactCreatedEvent = new ContactCreatedEvent(contactState);
    eventStore.put(contact.getMail(), contactCreatedEvent);
  }

  @Override
  public Optional<Contact> getByMail(String mail) {
    Stream<Event> eventStream = eventStore.getByMail(mail);
    State finalState = eventStream.reduce(State.EMPTY,
        (state, event) -> event.apply(state),
        (state, state2) -> state2);
    return Optional.ofNullable(finalState.getContactState())
        .map(ContactState::toContact);
  }

  @Override
  public void update(Contact newContact) {
    String mail = newContact.getMail();
    Optional<Contact> oldContact = getByMail(mail);
    assert oldContact.isPresent();
    Map<String, Object> diff = diff(oldContact.get(), newContact);
    ContactUpdatedEvent contactUpdatedEvent = new ContactUpdatedEvent(diff);
    eventStore.put(mail,contactUpdatedEvent);
  }

  private Map<String, Object> diff(Contact oldContact, Contact newContact) {
    Map<String,Object> diff = new HashMap<>();
    if(!Objects.equals(oldContact.getUsername(),newContact.getUsername())){
      diff.put("username", newContact.getUsername());
    }
    if(!Objects.equals(oldContact.getAddress(),newContact.getAddress())){
      diff.put("address", newContact.getAddress());
    }
    if(!Objects.equals(oldContact.getPhoneNumber(),newContact.getPhoneNumber())){
      diff.put("phoneNumber", newContact.getPhoneNumber());
    }
    return diff;
  }
}
