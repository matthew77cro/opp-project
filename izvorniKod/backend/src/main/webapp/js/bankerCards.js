$('#new-debit-card').hide();
$('#new-credit-card').hide();
$('#delete-card').hide();
$("#container").show();

$("#newDebitCardBtn").click(function(){
    $("#container").hide();
    $('#delete-card').hide();
    $('#new-credit-card').hide();
    $("#new-debit-card").show();
 });

$("#newCreditCardBtn").click(function(){
    $("#container").hide();
    $('#delete-card').hide();
    $('#new-debit-card').hide();
    $("#new-credit-card").show();
 });

 $(".cancel").click(function(){
    $('#delete-card').hide();
    $('#new-debit-card').hide();
    $("#new-credit-card").hide();
    $("#container").show();
 });

 $("#deleteCardBtn").click(function(){
	$('#new-debit-card').hide();
	$("#new-credit-card").hide();
    $("#container").hide();
    $('#delete-card').show();
 });