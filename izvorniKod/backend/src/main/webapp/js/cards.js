$("#newCardForm").hide();

$("#newCardBtn").click(function(){
   $("#container").hide();
   $("#newCardBtn").hide();
   $("#newCardForm").show();
});

$("#request").click(function(){
   $("#container").show();
   $("#newCardBtn").show();
   $("#newCardForm").hide();
});