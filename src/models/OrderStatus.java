package models;
//This class contain different status of order
public enum OrderStatus {
    PLACED,           // Order has been placed
    IN_PREPARATION,   // Order is being prepared
    OUT_FOR_DELIVERY, // Order is out for delivery
    DELIVERED         // Order has been delivered
}
