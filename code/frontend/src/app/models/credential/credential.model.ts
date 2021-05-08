import { ErrorMessage } from "../error-message/error-message.model";

export class CredentialModel extends ErrorMessage {
  login: string;
  password: string;

  constructor(login: string, password: string) {
    super();
    this.login = login;
    this.password = password;
  }
}