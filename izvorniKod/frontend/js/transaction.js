$("#newTransaction").hide();

$("#newTransactionBtn").click(function(){
   $("#container").hide();
   $("#newTransactionBtn").hide();
   $("#newTransaction").show();
});

$("#finishTransaction").click(function(){
   $("#container").show();
   $("#newTransactionBtn").show();
   $("#newTransaction").hide();
});