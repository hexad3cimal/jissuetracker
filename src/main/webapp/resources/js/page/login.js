
$(function() {
$('#signUpForm').hide();


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




String.prototype.ucfirst = function()
{
    return this.charAt(0).toUpperCase() + this.substr(1);
};
