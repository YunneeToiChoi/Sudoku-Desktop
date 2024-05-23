/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mail;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
/**
 *
 * @author Tran Giap
 */
public class Email {
    
    public static final String Gmail_Host ="smtp.gmail.com";
    public static final String Sender_Email_Address ="pettertran1121@gmail.com";
    public static final String Sender_Email_Password ="osrz ukpf adry gxpg";
    
 
     public static void sendEmail(String otp, String receiverEmail) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", Gmail_Host);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Sender_Email_Address, Sender_Email_Password);
            }
        });

        MimeMessage message = new MimeMessage(session);
        try {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
            message.setSubject("Xác thực tài khoản với mã OTP");

            // Định dạng nội dung email
            String emailContent = "Chào bạn,\n\n"
                    + "Bạn đã yêu cầu xác thực tài khoản của mình. Đây là mã OTP của bạn:\n\n"
                    + "Mã OTP: " + otp + "\n\n"
                    + "Vui lòng nhập mã này vào trang web hoặc ứng dụng của chúng tôi để hoàn tất quy trình xác thực.\n\n"
                    + "Lưu ý: Mã OTP này chỉ có hiệu lực trong vòng 1 phút kể từ khi bạn nhận được email này.\n\n"
                    + "Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email này. Nếu bạn vẫn gặp vấn đề hoặc cần sự trợ giúp, đừng ngần ngại liên hệ với chúng tôi.\n\n"
                    + "Trân trọng,\n"
                    + "Java Huflit";

            message.setText(emailContent);

            // Gửi email trên một thread mới
            new Thread(() -> {
                try {
                    Transport.send(message);
                    System.out.println("Email sent successfully!");
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
}
