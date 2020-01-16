$('#new-transaction').hide();
$("#container").show();

$("#newTransactionBtn").click(function(){
    $("#container").hide();
    $("#new-transaction").show();
 });

 $("#cancel").click(function(){
    $("#new-transaction").hide();
    $("#container").show();
 });

 