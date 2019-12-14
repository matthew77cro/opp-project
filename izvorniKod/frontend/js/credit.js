$("#newCreditForm").hide();
$("#payCreditForm").hide();

$("#newCreditBtn").click(function(){
   $("#container").hide();
   $("#newCreditForm").show();
});

$("#cancel").click(function(){
   $("#container").show();
   $("#newCreditForm").hide();
});

$("#payCreditBtn").click(function(){
   $("#container").hide();
   $("#payCreditForm").show();
});

$("#cancelPayment").click(function(){
   $("#container").show();
   $("#payCreditForm").hide();
});