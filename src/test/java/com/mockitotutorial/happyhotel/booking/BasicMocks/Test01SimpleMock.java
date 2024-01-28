package com.mockitotutorial.happyhotel.booking.BasicMocks;

import com.mockitotutorial.happyhotel.booking.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class Test01SimpleMock {

    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    @BeforeEach
    void setup() {
        this.paymentServiceMock = mock(PaymentService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.roomServiceMock = mock(RoomService.class);
        this.mailSenderMock = mock(MailSender.class);

        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
    }

    @Test
    void should_CalculateCorrectPrice_When_CorrectInput() {
        // Assert
        BookingRequest testBookingRequest = new BookingRequest("UserId",
                LocalDate.of(2024,1,1),
                LocalDate.of(2024,1,5),
                2,
                false);
        double expected = 4 * 2 * 50.0;
        // Act
        double actual = bookingService.calculatePrice(testBookingRequest);
        // Assert
        assertEquals(expected, actual);
    }

}
