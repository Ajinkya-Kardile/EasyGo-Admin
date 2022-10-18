package com.ajinkya.easygo_admin.Model;

public class BusModel {
    public String ToLocation;
    public String arrivalTime;
    public String busNo;
    public String date;
    public String FromLocation;
    public String departureTime;
    public String typeSit;
    public String ticketPrice;
    public String AvailableSeat;




    public BusModel(){

    }

    public BusModel(String arrivalPin, String arrivalTime, String busNo,
                    String date, String departurePin, String departureTime,
                    String typeSit, String ticketPrice) {
        this.ToLocation = arrivalPin;
        this.arrivalTime = arrivalTime;
        this.busNo = busNo;
        this.date = date;
        this.FromLocation = departurePin;
        this.departureTime = departureTime;
        this.typeSit = typeSit;
        this.ticketPrice = ticketPrice;
    }


    public String getAvailableSeat() {
        return AvailableSeat;
    }

    public void setAvailableSeat(String availableSeat) {
        AvailableSeat = availableSeat;
    }

    public String getToLocation() {
        return ToLocation;
    }

    public void setToLocation(String toLocation) {
        this.ToLocation = toLocation;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }


    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFromLocation() {
        return FromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.FromLocation = fromLocation;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getTypeSit() {
        return typeSit;
    }

    public void setTypeSit(String typeSit) {
        this.typeSit = typeSit;
    }

    public String getTicketPrice(){
        return ticketPrice;
    }

    public void setTicketPrice(String  ticketPrice){
        this.ticketPrice = ticketPrice;

    }

}
