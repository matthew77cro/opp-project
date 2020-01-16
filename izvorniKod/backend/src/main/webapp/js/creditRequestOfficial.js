$('#credit-request').hide();
$('#container').show();

$('#credit-selector').click(function(){
    $('#container').hide();
    $('#credit-request').show();
});

$('#cancel').click(function(){
    $('#credit-request').hide();
    $('#container').show();
});