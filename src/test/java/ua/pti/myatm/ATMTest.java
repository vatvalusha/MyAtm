/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pti.myatm;

import org.junit.Test;

import java.security.PublicKey;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author andrii
 */
public class ATMTest {

    @Test
    public void testGetMoneyInATMExpectedEquals() {
        ATM atm = new ATM(100);
        double expectedResult = 100;
        assertEquals(expectedResult, atm.getMoneyInATM(), 0.0);
    }

    @Test (expected = NullPointerException.class)
    public void testValidateCardNullCard()  {
    ATM atm = new ATM(100);
        int pinCode = 1111;
        atm.validateCard(null, pinCode );
    }

    @Test
    public  void  testValidateCard() {
        ATM atm = new ATM(100);
        int pinCode = 1111;

        Card card = mock(Card.class);
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(pinCode)).thenReturn(true);

        assertTrue(atm.validateCard(card,pinCode));
    }

    @Test (expected = NoEnoughMoneyInATMExeption.class)
    public void testGetCashNoEnoughMoneyInATMExeption() throws NoCardInsertExeption, NoEnoughMoneyInATMExeption, NoEnoughMoneyOnCardExeption {
        ATM atm = new ATM(0);


        Account account = mock(Account.class);
        Card card = mock(Card.class);
        when(card.getAccount()).thenReturn(account);
        when(account.getBalance()).thenReturn(3100.0);
        when(card.checkPin(1111)).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);

        atm.validateCard(card, 1111);
        atm.getCash(1999);


    }
    @Test
    public void testCheckBalance() throws NoCardInsertExeption{
        ATM atm = new ATM(100);
        int pinCode = 1111;
        double actualValue = 1000;

        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(actualValue);

       Card card = mock(Card.class);
        when(card.getAccount()).thenReturn(account);
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(pinCode)).thenReturn(true);

        atm.validateCard(card,pinCode);
        atm.checkBalance();

        double expectedResult = 1000;

        assertEquals(atm.checkBalance(), expectedResult, 0.0);
    }


    @Test
    public void testGetCash() throws NoCardInsertExeption, NoEnoughMoneyInATMExeption, NoEnoughMoneyOnCardExeption
    {
        double amount = 1000;
        ATM atm = new ATM(1000);
        int pinCode = 1111;
        double actualValue = 1000;

        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(actualValue);

        Card card = mock(Card.class);
        when(card.checkPin(pinCode)).thenReturn(true);
        when(card.getAccount()).thenReturn(account);
        when(card.isBlocked()).thenReturn(false);

        atm.validateCard(card, pinCode);
        atm.getCash(amount);
    }

    @Test(expected = IllegalArgumentException.class)
    public  void testSetNegativeMoneyInATMThrownIllegalArgumentException()  {new ATM(-100);}

    @Test (expected = IllegalArgumentException.class)
    public void testSrtNullValueMoneyInATMThrowIllegalArgumentException() { new ATM(0);}


}
