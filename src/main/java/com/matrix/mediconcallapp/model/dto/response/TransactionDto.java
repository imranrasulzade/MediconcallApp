package com.matrix.mediconcallapp.model.dto.response;

import lombok.Data;

@Data
public class TransactionDto {
    private Integer id;
    private Integer senderId;
    private Integer receiverId;
    private String senderName;
    private String receiverName;
    private Integer amount;
    private String senderCard;
    private String receiverCard;
    private String timestamp;
    private Integer status;
}
