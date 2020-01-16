$("#newCardForm").hide();

$("#newCardBtn").click(function(){
   $("#container").hide();
   $("#newCardForm").show();
});

$("#cancel").click(function(){
   $("#container").show();
   $("#newCardForm").hide();
});