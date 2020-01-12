$("#newCardForm").hide();
$("#payCardForm").hide();

$("#newCardBtn").click(function(){
   $("#container").hide();
   $("#payCardForm").hide();
   $("#newCardForm").show();
});

$("#payCardBtn").click(function(){
   $("#container").hide();
   $("#payCardForm").show();
   $("#newCardForm").hide();
});

$("#cancelNew").click(function(){
   $("#container").show();
   $("#newCardForm").hide();
   $("#payCardForm").hide();
});

$("#cancelPay").click(function(){
   $("#container").show();
   $("#newCardForm").hide();
   $("#payCardForm").hide();
});