$("#addProfileForm").hide();
$("#updateProfileForm").hide();
$("#deleteProfileForm").hide();
$("#userAccounts").hide();
$("#addAccountForm").hide();
$("#updateAccountForm").hide();

$("#addProfileBtn").click(function(){
   $("#container").hide();
   $("#addProfileForm").show();
});

$("#cancel").click(function(){
   $("#container").show();
   $("#addProfileForm").hide();
});

$("#updateProfileBtn").click(function(){
   $("#container").hide();
   $("#updateProfileForm").show();
});

$("#cancel2").click(function(){
   $("#container").show();
   $("#updateProfileForm").hide();
});

$("#deleteProfileBtn").click(function(){
   $("#container").hide();
   $("#deleteProfileForm").show();
});

$("#cancel3").click(function(){
   $("#container").show();
   $("#deleteProfileForm").hide();
});

$("#showAccounts").click(function(){
   $("#userAccounts").show();
});

$("#hideAccounts").click(function(){
   $("#userAccounts").hide();
});

$("#createAccount").click(function(){
   $("#container").hide();
   $("#addAccountForm").show();
});

$("#cancel4").click(function(){
   $("#container").show();
   $("#addAccountForm").hide();
});

$(".updateBtn").click(function(){
   $("#container").hide();
   $("#updateAccountForm").show();
});

$("#cancel5").click(function(){
   $("#container").show();
   $("#updateAccountForm").hide();
});