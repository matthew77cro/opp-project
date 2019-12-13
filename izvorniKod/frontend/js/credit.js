$("#newCreditForm").hide();
$("#payCreditForm").hide();

$("#newCreditBtn").click(function(){
   $("#container").hide();
   $("#newCreditForm").show();
});

$("#request").click(function(){
   $("#container").show();
   $("#newCreditForm").hide();
});

$("#cancel").click(function(){
   $("#container").show();
   $("#newCreditForm").hide();
});

$("#payCreditBtn").click(function(){
   $("#container").hide();
   $("#payCreditForm").show();
});

$("#pay").click(function(){
   $("#container").show();
   $("#payCreditForm").hide();
});

$("#cancelPayment").click(function(){
   $("#container").show();
   $("#payCreditForm").hide();
});