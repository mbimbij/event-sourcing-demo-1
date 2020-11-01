package com.example.demo;

import java.util.Optional;

public interface ContactRepository {
  void create(Contact contact);

  Optional<Contact> getByMail(String mail);

  void update(Contact contact);
}
