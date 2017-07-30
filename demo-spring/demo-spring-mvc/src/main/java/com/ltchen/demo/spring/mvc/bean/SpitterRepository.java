package com.ltchen.demo.spring.mvc.bean;

public interface SpitterRepository {

  Spitter save(Spitter spitter);
  
  Spitter findByUsername(String username);

}
