Use HotelBooking;

-- 1 Write a query that returns a list of reservations that end in July 2023, including the name of 
-- the guest, the room number(s), and the reservation dates.

select booking.BookingEndDate, room.roomNumber, guest.firstName, guest.LastName
From Booking
Inner Join BookingRoom on Booking.BookingId = BookingRoom.BookingId
Inner Join Room on BookingRoom.RoomId = Room.RoomId
Inner Join Guest on Booking.GuestId = Guest.GuestId

Where BookingEndDate Between "2023-07-01" And "2023-07-31";

-- 2 Write a query that returns a list of all reservations for rooms with a jacuzzi, displaying the 
-- guest's name, the room number, and the dates of the reservation.

SELECT roomNumber, firstName, lastName, BookingEndDate, BookingStartDate, AmenityType
From Booking
Inner Join BookingRoom on Booking.BookingId = BookingRoom.BookingId
Inner Join Room on BookingRoom.RoomId = Room.RoomId
Inner Join Guest on Booking.GuestId = Guest.GuestId
Inner Join RoomAmenities on Room.RoomId = roomamenities.RoomId
Inner Join Amenities on RoomAmenities.AmenitiesId =  Amenities.AmenitiesId

Where AmenityType = 'Jacuzzi';

-- 3 Write a query that returns all the rooms reserved for a specific guest, including the guest's 
-- name, the room(s) reserved, the starting date of the reservation, and how many people were 
-- included in the reservation. (Choose a guest's name from the existing data.)

SELECT FirstName, LastName, RoomNumber, BookingStartDate, ExtraAdult, ExtraChild
From Booking
Inner Join BookingRoom on Booking.BookingId = BookingRoom.BookingId
Inner Join Room on BookingRoom.RoomId = Room.RoomId
Inner Join Guest on Booking.GuestId = Guest.GuestId
Inner Join Billing on Booking.BillingId =  Billing.BillingId
Where FirstName = 'Mack';

-- 4 Write a query that returns a list of rooms, reservation ID, and per-room cost for each 
-- reservation. The results should include all rooms, whether or not there is a reservation 
-- associated with the room.

Select Room.RoomNumber, 
IFNULL(BookingId, '[None]'), Rate
From Room
RIGHT OUTER Join RoomType on RoomType.RoomTypeId = Room.RoomtypeId
LEFT OUTER JOIN BookingRoom on BookingRoom.RoomId = Room.RoomId
ORDER BY RoomNumber;



-- 5 Write a query that returns all the rooms accommodating at least three guests and that are reserved on any date in April 2023.

Select Room.RoomNumber
From Room
INNER Join RoomType on RoomType.RoomTypeId = Room.RoomtypeId
INNER JOIN BookingRoom on BookingRoom.RoomId = Room.RoomId
INNER JOIN Booking on Booking.BookingId = BookingRoom.BookingId
WHERE MaxOccupancy >= 3 AND BookingEndDate BETWEEN '2023-04-01' AND '2023-04-30';
 

-- 6 Write a query that returns a list of all guest names and the number of reservations per guest, 
-- sorted starting with the guest with the most reservations and then by the guest's last name.
Select Guest.FirstName, Guest.LastName,
Count(Booking.BookingId) BookingIdCount
from Booking
Inner Join Guest on Guest.GuestId = Booking.GuestId
Group By Guest.FirstName, Guest.LastName
ORDER By BookingIdCount DESC, LastName;



-- 7. Write a query that displays the name, address, and phone number of a guest based on their phone number. 
-- (Choose a phone number from the existing data.)
Select Address, FirstName, LastName, Phone
from Guest
Order By phone;






