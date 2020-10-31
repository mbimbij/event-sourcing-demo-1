package com.example.demo.infra;

import com.example.demo.Contact;

public interface EventStore {
  void put(String mail, Contact contact);
}
