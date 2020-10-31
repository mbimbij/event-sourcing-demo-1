package com.example.demo.infra;

import com.example.demo.Contact;
import com.example.demo.ContactRepository;

import java.util.Optional;

public class InMemoryEventSourcingRepository implements ContactRepository {
  private EventStore eventStore;

  public InMemoryEventSourcingRepository(EventStore eventStore) {
    this.eventStore = eventStore;
  }

  @Override
  public void create(Contact contact) {
    eventStore.put(contact.getMail(), contact);
  }

  @Override
  public Optional<Contact> getByMail(String mail) {
    return Optional.empty();
  }
}
