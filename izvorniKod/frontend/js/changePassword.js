$("#changePasswordForm").hide();

$("#changePasswordBtn").click(function(){
   $("#container").hide();
   $("#changePasswordForm").show();
});

$("#goBackBtn").click(function(){
   $("#container").show();
   $("#changePasswordForm").hide();
});