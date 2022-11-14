# EasyGo-Admin 
## An Admin Side Application for EasyGo

**EasyGo -** It is bus Ticket Booking system. For more details, [click here...](https://github.com/Ajinkya-Kardile/EasyGo-BusTicketBookingSystem).



**EasyGo-Admin ** Using this application the admin able to manage all data.

The application contain many features, such as 
- Add service Locations
- Add Buses
- View buses
- View Passengers
- View Users
- And many more...

Note- You have to add your own google-services.json file from your Firebase console. Otherwise the project don't run properly. 

### Firebase account configration required :
- Enable  Email/Password signIn method in firebase account. 
- create Realtime Database and modify rules like
```
  {
  "rules": {
    ".read": true,
    ".write": true
  }
}
```
note-This rule preferred for only testing perpose.
