package com.example.demo.infra;

import com.example.demo.Contact;
import lombok.Value;

@Value
public class ContactState {
  private String mail;
  private String username;
  private String address;
  private String phoneNumber;

  public static ContactState fromContact(Contact contact){
    return new ContactState(contact.getMail(), contact.getUsername(), contact.getAddress(), contact.getPhoneNumber());
  }

  public Contact toContact(){
    return Contact.builder()
        .mail(this.getMail())
        .username(this.getUsername())
        .address(this.getAddress())
        .phoneNumber(this.getPhoneNumber())
        .build();
  }
}
