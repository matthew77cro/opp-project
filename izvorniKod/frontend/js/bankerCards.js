$('#new-card').hide();
$('#delete-card').hide();
$("#container").show();

$("#newCardBtn").click(function(){
    $("#container").hide();
    $('#delete-card').hide();
    $("#new-card").show();
 });

 $("#cancel").click(function(){
    $('#delete-card').hide();
    $("#new-card").hide();
    $("#container").show();
 });

 $("#deleteCardBtn").click(function(){
    $("#new-card").hide();
    $("#container").hide();
    $('#delete-card').show();
 });