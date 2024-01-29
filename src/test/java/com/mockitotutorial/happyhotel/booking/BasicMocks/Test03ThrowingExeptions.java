package com.mockitotutorial.happyhotel.booking.BasicMocks;

import com.mockitotutorial.happyhotel.booking.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import static  org.mockito.ArgumentMatchers.*;

public class Test03ThrowingExeptions {

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
    void should_ThrowException_When_NoRoomAvailable() {
        // Assert
        BookingRequest testBookingRequest = new BookingRequest("UserId",
                LocalDate.of(2024,1,1),
                LocalDate.of(2024,1,5),
                2,
                false);
        when(roomServiceMock.findAvailableRoomId(testBookingRequest))
                .thenThrow(BusinessException.class);
        // Act
        Executable executable = () -> bookingService.makeBooking(testBookingRequest);
        // Assert
        assertThrows(BusinessException.class, executable);
    }

    @Test
    void should_NotCompleteBooking_When_PriceTooHigh() {
        // Assert
        BookingRequest testBookingRequest = new BookingRequest("UserId",
                LocalDate.of(2024,1,1),
                LocalDate.of(2024,1,5),
                2,
                true);
        when(paymentServiceMock.pay(any(), eq(400.0)))
                .thenThrow(BusinessException.class);
        // Act
        Executable executable = () -> bookingService.makeBooking(testBookingRequest);
        // Assert
        assertThrows(BusinessException.class, executable);
    }

}
