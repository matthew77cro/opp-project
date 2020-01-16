$('#new-account').hide();
$('#delete-account').hide();
$("#container").show();

$("#addAccountBtn").click(function(){
    $("#container").hide();
    $('#delete-account').hide();
    $("#new-account").show();
 });

 $(".cancel").click(function(){
    $('#delete-account').hide();
    $("#new-account").hide();
    $("#container").show();
 });

 $("#deleteAccountBtn").click(function(){
    $("#new-account").hide();
    $("#container").hide();
    $('#delete-account').show();
 });