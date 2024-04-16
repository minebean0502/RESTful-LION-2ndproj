

### 엮여있는 ERD
Reservation <-> Payment  
Reservation_ID(PK)와 Payment객체들의 reservation_ID(FK)는 연결되어 있다  

### 필요한 결제 관련 기능
1. 결제 진행 (/v1/payments/confirm)
   - 결제 진행이 완료된다면, Reservation의 status는 완료로 바뀐다
   - 결제 진행이 정상적으로 완료되지 않았다면, 취소 로직에 따라 Reservation의 status를 정의해줘야함   
     </br>
2. 결제 취소 (/v1/payments/{paymentKey}/cancel)
   - 이미 승인된 결제 정보를 바탕으로(paymentKey) 조회를 해서 (필요한 파라미터들 다 넣고)
   - 결제 취소를 진행한 뒤
     - 결제 취소가 정상적으로 되었다면, Reservation의 Status를 정의해주고
     - 결제 취소가 정상적으로 되지 않았더라면, Reservation의 Status또한 정의해줌  
       </br>
3. 결제 조회
    ####  orderID로 조회 : /v1/payments/orders/{orderId}
    #### paymentKey로 조회 : /v1/payments/{paymentKey}
   - mypage 혹은 사용자 개인 페이지에서 조회 목록을 바탕으로 결제 조회를 할 수 있도록 함

## 1. 결제 승인 요청시 Toss에서 필요로 하는 정보는 다음과 같습니다
- paymentKey : paymentKey 문자열(String)
- orderId : orderId 문자열(String)
- amount : 결제 금액

## 2. 송신은 (Request)
결제 : POST /v1/payments/confirm으로 송신 (10분 이내 만료될 것)

### RequestBody Params (Essential : 필수값)
- paymentKey : String || 결제의 키 값 (고유한 값, 중복되면 안됨)  
- orderId : String || 주문 번호, 고유한 주문 번호 필요함 (6 ~ 64자)  
- amount : 결제 금액 (number)  

## 3. 수신은 (Response)
Payment 객체가 들어옴
```
  "mId": "tosspayments",
  "lastTransactionKey": "9C62B18EEF0DE3EB7F4422EB6D14BC6E",
  "paymentKey": "5EnNZRJGvaBX7zk2yd8ydw26XvwXkLrx9POLqKQjmAw4b0e1",
  "orderId": "MC4wODU4ODQwMzg4NDk0",
  "orderName": "토스 티셔츠 외 2건",
  "taxExemptionAmount": 0,
  "status": "DONE",
  "requestedAt": "2024-02-13T12:17:57+09:00",
  "approvedAt": "2024-02-13T12:18:14+09:00",
  "useEscrow": false,
  "cultureExpense": false,
  "card": {
    "issuerCode": "71",
    "acquirerCode": "71",
    "number": "12345678****000*",
    "installmentPlanMonths": 0,
    "isInterestFree": false,
    "interestPayer": null,
    "approveNo": "00000000",
    "useCardPoint": false,
    "cardType": "신용",
    "ownerType": "개인",
    "acquireStatus": "READY",
    "receiptUrl": "https://dashboard.tosspayments.com/receipt/redirection?transactionId=tviva20240213121757MvuS8&ref=PX",
    "amount": 1000
  },
  "virtualAccount": null,
  "transfer": null,
  "mobilePhone": null,
  "giftCertificate": null,
  "cashReceipt": null,
  "cashReceipts": null,
  "discount": null,
  "cancels": null,
  "secret": null,
  "type": "NORMAL",
  "easyPay": {
    "provider": "토스페이",
    "amount": 0,
    "discountAmount": 0
  },
  "easyPayAmount": 0,
  "easyPayDiscountAmount": 0,
  "country": "KR",
  "failure": null,
  "isPartialCancelable": true,
  "receipt": {
    "url": "https://dashboard.tosspayments.com/receipt/redirection?transactionId=tviva20240213121757MvuS8&ref=PX"
  },
  "checkout": {
    "url": "https://api.tosspayments.com/v1/payments/5EnNZRJGvaBX7zk2yd8ydw26XvwXkLrx9POLqKQjmAw4b0e1/checkout"
  },
  "currency": "KRW",
  "totalAmount": 1000,
  "balanceAmount": 1000,
  "suppliedAmount": 909,
  "vat": 91,
  "taxFreeAmount": 0,
  "method": "카드",
  "version": "2022-11-16"
}
```