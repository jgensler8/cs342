package Agents;

import java.io.Serializable;
import java.util.Date;

/**
 * Because socket stream between server and client is plain text we need this
 * data contract so server and client have a standard way of relaying messages.
 * 
 * @author Azita
 * 
 */
public class Message implements Serializable {

	 private static final long serialVersionUID = 1L;

     // final class and attributes for relay of message
     // organize by final class so attributes don't have name conflicts

     // common subjects
     public final static class Subject {
             public final static String ROOM_ASSIGNMENT = "$ROOM$";
             public final static String PLAYER_JOINED = "$JOINED$";
             public final static String PLAYER_EXITED = "$EXITED$";
             public final static String START_GAME = "$START$";
             public final static String DRAW_FROM_DECK = "$DRAW_DECK$";
             public final static String DRAW_FROM_DISCARD = "$DRAW_DISCARD$";
             public final static String PLAY_SET = "$PLAY_SET$";
             public final static String PLAY_RUN = "$PLAY_RUN$";
             public final static String PLAY_COLOR = "$PLAY_COLOR$";
             public final static String APPEND_SET = "$APPEND_SET$";
             public final static String APPEND_RUN = "$APPEND_RUN$";
             public final static String APPEND_COLOR = "$APPEND_COLOR$";
             public final static String PHASE_REJECTED = "$PHASE_REJECTED$";
             public final static String WINNER = "$WINNER$";
             public final static String TEXT = "$TEXT$";
     }

     // unique identifier of admin as sender
     public final static class Sender {
             public final static String ADMIN = "$ADMIN$";
     }

     // recipients
     public final static class Recipient {
             public final static String ALL_USERS_IN_ROOM = "$ROOM_USERS$";
             public final static String USER = "$USER$";
     }

     // data members
     private Object _sender;
     private Object _recipient;
     private Object _subject;
     private Object _body;

     /**
      * Class Constructor
      */
     public Message(Object sender, Object recipient, Object subject, Object body) {
             _sender = sender;
             _recipient = recipient;
             _subject = subject;
             _body = body;
     }

     /**
      * Get methods to retrieve message information
      *
      * @return request attribute
      */
     public Object getSender() {
             return _sender;
     }

     public Object getRecipient() {
             return _recipient;
     }

     public Object getSubject() {
             return _subject;
     }

     public Object getBody() {
             return _body;
     }

     /**
      * return string representation of instance for logging
      */
     public String toString() {
             Date d = new Date();
             return "getSender: " + _sender.toString() + "\n&" + "getRecipient: "
                             + _recipient.toString() + "\n&" + "getSubject: "
                             + _subject.toString() + "\n&" + "getBody: " + _body.toString()
                             + "\n" +  d.toString() + "\n";
     }
}
