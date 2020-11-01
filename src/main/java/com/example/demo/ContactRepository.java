package com.example.demo;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface ContactRepository {
  void create(Contact contact);

  Optional<Contact> getByMail(String mail);

  Optional<Contact> getByMailAtDateTime(String mail, ZonedDateTime dateTime);

  void update(Contact contact);

  void deleteByMail(String mail);
}
