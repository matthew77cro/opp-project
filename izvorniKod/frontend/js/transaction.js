$("#newTransaction").hide();

$("#newTransactionBtn").click(function(){
   $("#container").hide();
   $("#newTransaction").show();
});

$("#finishTransaction").click(function(){
   $("#container").show();
   $("#newTransaction").hide();
});

$("#cancel").click(function(){
   $("#container").show();
   $("#newTransaction").hide();
});