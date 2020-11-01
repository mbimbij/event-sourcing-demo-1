package com.example.demo;

import com.example.demo.infra.ContactDeletedEvent;
import com.example.demo.infra.ContactUpdatedEvent;
import com.example.demo.infra.EventStore;
import com.example.demo.infra.InMemoryEventSourcingRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class InMemoryEventSourcingRepositoryTest {

  private InMemoryEventStore eventStore = new InMemoryEventStore();
  private InMemoryEventSourcingRepository repository = new InMemoryEventSourcingRepository(eventStore);
  private final String mail = "mail";
  private Contact contact;

  @BeforeEach
  void setUp() {
    eventStore.clear();
    contact = Contact.builder()
        .mail(mail)
        .username("username")
        .address("address")
        .phoneNumber("phoneNumber")
        .build();
  }

  @Test
  void givenEmptyEventStore_whenGetContactByMail_thenReturnsOptionalEmpty() {
    assertThat(repository.getByMail(mail)).isEmpty();
  }

  @Test
  void givenEmptyEventStore_whenCreate_then1CreateEventIsPresent() {
    repository.create(contact);
    Optional<Contact> contactOptional = repository.getByMail(mail);
    assertThat(contactOptional).isNotEmpty();
    Contact expectedContact = Contact.builder()
        .mail(contact.getMail())
        .username(contact.getUsername())
        .address(contact.getAddress())
        .phoneNumber(contact.getPhoneNumber())
        .build();

    assertThat(contactOptional.get()).isEqualTo(expectedContact);
  }

  @Test
  void givenEmptyEventStore_whenCreateAndUpdateMail_thenStateIsCorrect() {
    repository.create(contact);
    Contact newContact = Contact.builder()
        .mail(contact.getMail())
        .username(contact.getUsername())
        .address(contact.getAddress())
        .phoneNumber(contact.getPhoneNumber())
        .build();
    newContact.setAddress("address2");
    newContact.setPhoneNumber("phoneNumber2");
    repository.update(newContact);
    Contact updatedContact = repository.getByMail(mail).get();
    Contact expectedUpdatedContact = Contact.builder()
        .mail(mail)
        .username("username")
        .address("address2")
        .phoneNumber("phoneNumber2")
        .build();
    SoftAssertions.assertSoftly(softAssertions -> {
      softAssertions.assertThat(updatedContact).isEqualTo(expectedUpdatedContact);
      softAssertions.assertThat(updatedContact).isNotEqualTo(contact);
    });
  }

  @Test
  void givenEntityCreated_whenDelete_thenEntityDeleted() {
    repository.create(contact);
    repository.deleteByMail(mail);
    Optional<Contact> contactOptional = repository.getByMail(mail);
    assertThat(contactOptional).isEmpty();
  }

  @Test
  void given2Updates_whenQueryStateBetweenFirstAndSecondUpdate_thenReturnsStateAfterFirstUpdate() {
    // GIVEN
    Contact expectedContact = Contact.builder()
        .mail("mail")
        .username("username")
        .address("address2")
        .phoneNumber("phoneNumber2")
        .build();

    repository.create(contact);
    ContactUpdatedEvent event1 = new ContactUpdatedEvent(UUID.randomUUID(),
        ZonedDateTime.now().plusDays(1),
        Map.of("address", "address2", "phoneNumber", "phoneNumber2"));

    eventStore.put(mail, event1);
    ContactUpdatedEvent event2 = new ContactUpdatedEvent(UUID.randomUUID(),
        ZonedDateTime.now().plusDays(2),
        Map.of("phoneNumber", "phoneNumber3"));
    eventStore.put(mail, event2);

    ContactDeletedEvent event3 = new ContactDeletedEvent(UUID.randomUUID(),
        ZonedDateTime.now().plusDays(3));
    eventStore.put(mail, event3);

    // WHEN
    Optional<Contact> contactOptional = repository.getByMailAtDateTime(mail, ZonedDateTime.now().plusDays(1).plusHours(1));

    // THEN
    assertThat(repository.getByMail(mail)).isNotPresent();
    assertThat(contactOptional).isPresent();
    assertThat(contactOptional.get()).isEqualTo(expectedContact);
  }
}
