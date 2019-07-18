use HotelBooking;

Insert Into Guest
Values (1, 'Paul', 'Fore', 123456789, 'Adult', '123 Meow Meow, KittyTown MN');

Insert Into Booking
Values (1, '2023-02-02', "2023-02-04",1,2);


DELETE FROM Booking
WHERE BookingID = 8;

DELETE FROM bookingroom
WHERE BililngID = 8;

DELETE FROM Billing
WHERE BillingID = 8;

DELETE From GUEST
WHERE GuestID = 8;

