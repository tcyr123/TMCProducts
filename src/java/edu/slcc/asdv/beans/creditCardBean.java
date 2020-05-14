package edu.slcc.asdv.beans;

import java.io.Serializable;
import javax.inject.Named;
import javax.faces.flow.FlowScoped;
import card.TestCreditCard;
import javax.el.ELContext;
import javax.faces.context.FacesContext;

@Named(value = "creditCardBean")
@FlowScoped("creditCard")
public class creditCardBean implements Serializable{
    public creditCardBean() {
    }
    
    private String firstName, lastName, Address ,City, ZIP, State, phoneNumber;
    private String cardNum, cvc, expDate; 
    private String mess = "Card declined";
    TestCreditCard tcc = new TestCreditCard();
    


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getZIP() {
        return ZIP;
    }

    public void setZIP(String ZIP) {
        this.ZIP = ZIP;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getMess() {
        return mess;
    }
    
     public String getReturnValue()
    {
        return "/done";
    }
     
      public String getCartValue()
    {
        return "/faces/cart";
    }
     
     
    public String purchase()
    {
        //https://www.simplify.com/commerce/docs/testing/test-card-numbers

            String answer = tcc.createCredit(cardNum, expDate, cvc);
            System.out.println("ANSWER: "+ answer);
            if(answer.contains("expired"))
                mess = "Card has expired";
            else if(answer.contains("invalid"))
                mess = "card is invalid";

            //System.out.println("MESSAGE: " + mess);
            if (answer.contains("failed"))
                return "failed";
            else if (answer.contains("approved")){
                return "thanks";
            }
        return "error";
    }
    
    


    
}
