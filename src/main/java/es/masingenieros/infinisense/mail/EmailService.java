package es.masingenieros.infinisense.mail;

public interface EmailService {
	void sendSimpleMessage(String to, String subject, String text);
}
