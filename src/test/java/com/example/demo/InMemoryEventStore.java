package com.example.demo;

import com.example.demo.infra.Event;
import com.example.demo.infra.EventStore;

import java.util.*;
import java.util.stream.Stream;

public class InMemoryEventStore implements EventStore {
  Map<String, List<Event>> contacts = new HashMap<>();
  @Override
  public void put(String key, Event event) {
    contacts.putIfAbsent(key,new ArrayList<>());
    contacts.get(key).add(event);
  }

  @Override
  public Stream<Event> getByMail(String mail) {
    return contacts.getOrDefault(mail, Collections.emptyList()).stream();
  }

  public void clear(){
    contacts.clear();
  }
}
