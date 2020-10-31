package com.example.demo;

import com.example.demo.infra.EventStore;
import com.example.demo.infra.InMemoryEventSourcingRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class InMemoryEventSourcingRepositoryTest {

  private InMemoryEventSourcingRepository repository;
  private EventStore eventStore;
  private final String mail = "mail";

  @BeforeEach
  void setUp() {
    eventStore = new InMemoryEventStore();
    repository = new InMemoryEventSourcingRepository(eventStore);
  }

  @Test
  void givenEmptyEventStore_whenGetContactByMail_thenReturnsOptionalEmpty() {
    assertThat(repository.getByMail(mail)).isEmpty();
  }

  @Test
  void givenEmptyEventStore_whenCreate_then1CreateEventIsPresent() {
    Contact contact = Contact.builder()
        .mail(mail)
        .username("username")
        .address("address")
        .phoneNumber("phoneNumber")
        .build();
    repository.create(contact);
    Optional<Contact> contactOptional = repository.getByMail(mail);
    assertThat(contactOptional).isNotEmpty();
  }

}
