package com.tp2.model.web.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Credentials {
   String login;
   String password;

   public Credentials(String login, String password) {
      this.login = login;
      this.password = password;
   }
}
