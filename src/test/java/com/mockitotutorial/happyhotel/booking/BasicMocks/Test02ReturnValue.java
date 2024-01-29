package com.mockitotutorial.happyhotel.booking.BasicMocks;

import com.mockitotutorial.happyhotel.booking.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class Test02ReturnValue {

    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    @BeforeEach
    void setup() {
        paymentServiceMock = mock(PaymentService.class);
        bookingDAOMock = mock(BookingDAO.class);
        roomServiceMock = mock(RoomService.class);
        mailSenderMock = mock(MailSender.class);

        bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
    }

    @Test
    void should_CountAvailablePlaces_When_OneRoomAvailable() {
        // Assert
        when(roomServiceMock.getAvailableRooms())
                .thenReturn(List.of(new Room("RoomOne", 2)));
        int expected = 2;
        // Act
        int actual = bookingService.getAvailablePlaceCount();
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void should_CountAvailablePlaces_When_MultipleRoomsAvailable() {
        // Assert
        List<Room> rooms = List.of(new Room("RoomOne", 2), new Room("RoomTwo", 5));
        when(roomServiceMock.getAvailableRooms())
                .thenReturn(rooms);
        int expected = rooms.stream().mapToInt(Room::getCapacity).sum();
        // Act
        int actual = bookingService.getAvailablePlaceCount();
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void should_CountAvailablePlaces_When_CalledMultipleTimes() {
        // Assert
        when(roomServiceMock.getAvailableRooms())
                .thenReturn(List.of(new Room("RoomOne", 5)))
                .thenReturn(Collections.emptyList());
        int expectedFirstCall = 5;
        int expectedSecondCall = 0;
        // Act
        int actualFirst = bookingService.getAvailablePlaceCount();
        int actualSecond = bookingService.getAvailablePlaceCount();
        // Assert
        assertAll(
                () -> assertEquals(expectedFirstCall, actualFirst),
                () -> assertEquals(expectedSecondCall, actualSecond)
        );
    }
}
