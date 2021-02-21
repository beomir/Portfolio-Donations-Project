document.addEventListener("DOMContentLoaded", function() {
$('#username').on('keyup', function() {
    if($('#username').val().includes('admin')) {
    $('#adminError').html('Konto ze słowem admin jest zarezerwowane').css('color', 'red');
}
    if ($('#username').val() == '') {
    $('#adminError').html('Pole imię nie może być puste').css('color', 'red');
}
    else if(!$('#username').val().includes('admin') && !$('#username').val() == '')
    $('#adminError').html('Imie dozwolone').css('color', 'green');
})

    $('#lastname').on('keyup', function() {
    if ($('#lastname').val() == '') {
    $('#lastnameError').html('Pole Nazwisko nie może być puste').css('color', 'red');
}
    else
    $('#lastnameError').html('').css('color', 'green');
})

    $('#email').on('keyup', function() {
    if ($('#email').val() == '') {
    $('#emailError').html('Pole Email nie może być puste').css('color', 'red');
}
    else if (!$('#email').val().match(/([@])/)){
    $('#emailError').html('To nie jest adres mailowy').css('color', 'red');
}
    else
    $('#emailError').html('').css('color', 'green');
})


    $('#password, #password2').on('keyup', function () {
    if($('#password').val() =='')
{
    $('#message').html('Hasło powinno zawierac minimum 8 znaków oraz składać się z przynajmniej jednej litery, cyfry oraz znaku specjalnego').css('color', 'red')
}
    else if ($('#password').val() != $('#password2').val()) {
    $('#message').html('Jest różnica względem pierwszego hasła, popraw').css('color', 'red')
}
    else if($('#password').val().length<8) {
    $('#message').html('Hasło jest za krótkie').css('color', 'red');
}
    else if (!$('#password').val().match(/([a-z])/) || !$('#password').val().match(/([0-9])/) ){
    $('#message').html('Hasło nie posiada liter i liczb').css('color', 'red');
}
    else if (!$('#password').val().match(/([A-Z])/)){
    $('#message').html('Hasło nie posiada wielkiej litery').css('color', 'red');
}
    else if (!$('#password').val().match(/([!,%,&,@,#,$,^,*,?,_,~])/)){
    $('#message').html('Hasło nie zawiera znaku specjalnego').css('color', 'red');
}
    else {
    $('#message').html('Jest ok').css('color', 'green');
}
});


    function checkPasswordAndName() {
    var pass1 = document.getElementById("password").value;
    var pass2 = document.getElementById("password2").value;
    var username = document.getElementById("username").value;
    var lastname = document.getElementById("lastname").value;
    var email = document.getElementById("email").value;

    if(pass1 != pass2 )
{
    alert("Hasła różnią się od siebie")
    returnToPreviousPage();
    return false;
}
    else if(pass1.length<8)
{
    alert("Ilość znaków w haśle jest mniejsza niż wymagane 8")
    returnToPreviousPage();
    return false;
}
    else if(!pass1.match(/([a-z])/) || !pass1.match(/([0-9])/))
{
    alert("Hasło nie posiada liter i liczb")
    returnToPreviousPage();
    return false;
}
    else if(!pass1.match(/([A-Z])/))
{
    alert("Hasło nie posiada wielkiej litery")
    returnToPreviousPage();
    return false;
}
    else if(!pass1.match(/([!,%,&,@,#,$,^,*,?,_,~])/))
{
    alert("Hasło nie zawiera znaku specjalnego")
    returnToPreviousPage();
    return false;
}
    if(username.includes('admin')){
    alert("Konto ze słowem admin jest zarezerwowane")
    returnToPreviousPage();
    return false;
}
    else if(username == ""){
    alert("Pole Imię nie może być puste")
    returnToPreviousPage();
    return false;
}
    if(lastname ==""){
    alert("Pole Nazwisko nie może być puste")
    returnToPreviousPage();
    return false;
}
    if(email ==""){
    alert("Pole Email nie może być puste")
    returnToPreviousPage();
    return false;
}
}

    function returnToPreviousPage() {
    window.history.forward(-1)
}

});
