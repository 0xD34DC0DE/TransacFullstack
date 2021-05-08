import { ErrorMessage } from "../error-message/error-message.model";

export class EmailSendResult extends ErrorMessage {
  successful: boolean;
}