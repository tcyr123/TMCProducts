package card;

import com.simplify.payments.PaymentsApi;
import com.simplify.payments.PaymentsMap;
import com.simplify.payments.domain.CardToken;
import com.simplify.payments.domain.Payment;
import com.simplify.payments.exception.ApiException;
import com.simplify.payments.exception.InvalidRequestException;

public class TestCreditCard {

    final static String publicKey = "sbpb_ZTM3ODkyMWMtODlhMi00N2ZhLTgzNGMtMDA3MDg5MzhiMDFk";
    final static String privateKey = "/lQg+Hveag704YgIAnfbTgHjtgUqs9Ntl8Esg99Axrl5YFFQL0ODSXAOkNtXTToq";

    public TestCreditCard() {
    }

    public String createCredit(String cardNum, String expDate, String cvc) {
        String specific = "error";
        try {
            //String token = generateToken("5555555555554444", "0321" ,"087");
            String token = generateToken(cardNum, expDate, cvc);
            PaymentsMap map = new PaymentsMap();
            Payment payment = Payment.create(new PaymentsMap()
                    .set("currency", "USD")
                    .set("token", token)
                    .set("amount", 840.88) // In cents e.g. $840.88
                    .set("description", "prod description")
            );

            if ("APPROVED".equals(payment.get("paymentStatus"))) {
                System.out.println("Payment approved ");
                return "Payment approved";
            } else {
                System.out.println("Payment failed: \n" + payment.toString() + "\nstatus: " + payment.get("paymentStatus"));
                return "Payment failed";
            }

        } catch (ApiException e) {
            System.out.println("Message:    " + e.getMessage());
            System.out.println("Reference:  " + e.getReference());
            System.out.println("Error code: " + e.getErrorCode());
            specific =e.getErrorCode();
            if (e instanceof InvalidRequestException) {
                InvalidRequestException e2 = (InvalidRequestException) e;
                if (e2.hasFieldErrors()) {
                    for (InvalidRequestException.FieldError fe : e2.getFieldErrors()) {
                        System.out.println(fe.getFieldName()
                                + ": '" + fe.getMessage()
                                + "' (" + fe.getErrorCode() + ")");
                        specific = fe.getErrorCode();
                    }
                }
            }
        }
        return specific;
    }

    private String generateToken(String cardNum, String expDate, String cvc) {
        PaymentsApi.PUBLIC_KEY = publicKey;
        PaymentsApi.PRIVATE_KEY = privateKey;
        int month = Integer.valueOf(expDate.substring(0, 2));
        int year = Integer.valueOf(expDate.substring(2, expDate.length()));
        CardToken cardToken = null;
        try {

            PaymentsMap pm = new PaymentsMap()
                    .set("card.addressCity", "lafayette")
                    .set("card.addressState", "LA")
                    .set("card.cvc", cvc)
                    .set("card.expMonth", month)
                    .set("card.expYear", year)
                    .set("card.number", cardNum);
            cardToken = CardToken.create(pm);
        } catch (Exception e) {
            System.err.println("token generation failed " + e.getMessage());
        }
        System.out.println("card token: " + cardToken);
        return cardToken.get("id").toString();

    }
//
//    public static void main(String[] args) {
//        TestCreditCard test = new TestCreditCard();
//        //test.createCredit("5555555555554444", "0321", "087"); //good
//        System.out.println("\n------------------\n");
//        //test.createCredit("5555555555557462", "0321", "087"); //declined
//        //test.createCredit("5555555555558742", "0321", "087"); //expired
//        //test.createCredit("5555555555558726", "0321", "087"); //invalid
//        //test.createCredit("5555555555550145", "0321", "087"); //system
//        //test.createCredit("5506920809243667", "0321", "087"); //under review
//        //i believe the last one won't work unless you set 'check cvc & exp date
//        //in simplify's setting
//
//    }
}
