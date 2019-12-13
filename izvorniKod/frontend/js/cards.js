$("#newCardForm").hide();

$("#newCardBtn").click(function(){
   $("#container").hide();
   $("#newCardForm").show();
});

$("#request").click(function(){
   $("#container").show();
   $("#newCardForm").hide();
});

$("#cancel").click(function(){
   $("#container").show();
   $("#newCardForm").hide();
});