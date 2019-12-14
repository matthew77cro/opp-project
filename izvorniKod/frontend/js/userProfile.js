$("#addProfileForm").hide();
$("#updateProfileForm").hide();

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