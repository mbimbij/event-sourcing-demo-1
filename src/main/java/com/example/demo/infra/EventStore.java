package com.example.demo.infra;

import java.util.stream.Stream;

public interface EventStore {
  void put(String key, Event event);

  Stream<Event> getByMail(String mail);
}
