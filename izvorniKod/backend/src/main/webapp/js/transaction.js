$("#newTransaction").hide();

$("#newTransactionBtn").click(function(){
   $("#container").hide();
   $("#newTransaction").show();
});

$("#cancel").click(function(){
   $("#container").show();
   $("#newTransaction").hide();
});