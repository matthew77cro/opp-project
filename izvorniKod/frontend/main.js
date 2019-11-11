$("#login-form").show();
$("#register-form").hide();

$("#nav-login").click(function(){
   $("#register-form").hide();
   $("#login-form").show();
   console.log('prijava');
});

$("#nav-register").click(function(){
    $("#login-form").hide();
    $("#register-form").show();
});