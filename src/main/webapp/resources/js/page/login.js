
$(function() {
$('#signUpForm').hide();

    $('#registerClick').click(function(){

        $('#login').hide();
        $('#signUpForm').show();

    });
/*$( "#save" ).click(function() {
  console.log("Clicked ");
  ajaxTest();
});*/

// var val = $("#state").val();
// val.ucfirst();
$.validator.addMethod("customrule", function(value, element, param) {
    console.log(param);
       return this.optional(element) || value === param;
   }, "Currently we provide service in Kerala only");
jQuery.validator.setDefaults({
  debug: true,
  success: "valid"
});
$('#user').validate({
    rules:{
    name:{
            required:true

            },
    email:{
            required:true
            },
        signUpPassword:{
            required:true
        },
    phone:{
            required:true
            },

            state:{
                    required:true,
                    customrule: 'Kerala',
                    customrule: 'kerala'

                    },
},



          highlight: function(element) {
            $(element).closest('.form-group').addClass('has-error');

        },
        unhighlight: function(element) {
            $(element).closest('.form-group').removeClass('has-error');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function(error, element) {
            if(element.parent('.input-group').length) {
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }
        },

         submitHandler: function(form) {
            addUser();
          }

});


    $('#loginForm').validate({
        rules:{
            username:{
                required:true

            },

            password:{
                required:true
            }

        },



        highlight: function(element) {
            $(element).closest('.form-group').addClass('has-error');

        },
        unhighlight: function(element) {
            $(element).closest('.form-group').removeClass('has-error');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function(error, element) {
            if(element.parent('.input-group').length) {
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }
        },

        submitHandler: function(form) {
form.submit()
        }

    });
});


function addUser() {

  var stack_center = {"dir1": "down", "dir2": "right", "firstpos1": 25, "firstpos2": ($(window).width() / 2) - (Number(PNotify.prototype.options.width.replace(/\D/g, '')) / 2)};
  $(window).resize(function(){
      stack_center.firstpos2 = ($(window).width() / 2) - (Number(PNotify.prototype.options.width.replace(/\D/g, '')) / 2);
  });
  PNotify.prototype.options.styling = "bootstrap3";

    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");

    var JsonData = { userName :$('#name').val(),
                     email :$('#email').val(),
                     phone :$('#phone').val(),
                     city :$('#city').val(),
                     address :$('#address').val(),
                     pin :$('#pin').val(),
                     state :$('#state').val(),
                     district :$('#district').val(),
                     password :$('#signUpPassword').val()

                   };


	$.ajax({
            url:contextPath +'/user/addUser',
            type: 'POST',

            contentType: "application/json",
              dataType: 'json',
            data: JSON.stringify(JsonData),
        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
        },
            success: function(data) {
              new PNotify({
                  text: data.result,stack: stack_center,hide: true,delay: 100
              });

                }
        });

}

String.prototype.ucfirst = function()
{
    return this.charAt(0).toUpperCase() + this.substr(1);
}
