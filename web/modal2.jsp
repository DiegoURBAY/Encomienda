<%-- 
    Document   : modal2
    Created on : 18/04/2019, 08:04:58 PM
    Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
                <script src="js/jquery-3.3.1.min.js" type="text/javascript"></script>

        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="js/bootstrap.min.js" type="text/javascript"></script>

<!------ Include the above in your HEAD tag ---------->

<style>
    .modal-header {
  padding: 0;
}
.modal-header .close {
  padding: 10px 15px;
}
.modal-header ul {
  border: none;
}
.modal-header ul li {
  margin: 0;
}
.modal-header ul li a {
  border: none;
  border-radius: 0;
}
.modal-header ul li.active a {
  color: #e12f27;
}
.modal-header ul li a:hover {
  border: none;
}
.modal-header ul li a span {
  margin-left: 10px;
}
.modal-body .form-group {
  margin-bottom: 10px;
}

</style>        
    </head>
    <body>
<div class="container">
	<div class="row">

                <div class="modal-dialog modal-sm">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">
                                <span class="glyphicon glyphicon-remove"></span>
                            </button>
                            <ul class="nav nav-tabs">
                                <li class="active"><a data-toggle="tab" href="#login-form"> Login <span class="glyphicon glyphicon-user"></span></a></li>
                                <li><a data-toggle="tab" href="#registration-form"> Register <span class="glyphicon glyphicon-pencil"></span></a></li>
                            </ul>
                        </div>
                        <div class="modal-body">
                            <div class="tab-content">
                                <div id="login-form" class="tab-pane fade in active">
                                    <form action="/">
                                        <div class="form-group">
                                            <label for="email">Email:</label>
                                            <input type="email" class="form-control" id="email" placeholder="Enter email" name="email">
                                        </div>
                                        <div class="form-group">
                                            <label for="pwd">Password:</label>
                                            <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="pwd">
                                        </div>
                                        <div class="checkbox">
                                            <label><input type="checkbox" name="remember"> Remember me</label>
                                        </div>
                                        <button type="submit" class="btn btn-default">Login</button>
                                    </form>
                                </div>
                                <div id="registration-form" class="tab-pane fade">
                                    <form action="/">
                                        <div class="form-group">
                                            <label for="name">Your Name:</label>
                                            <input type="text" class="form-control" id="name" placeholder="Enter your name" name="name">
                                        </div>
                                        <div class="form-group">
                                            <label for="newemail">Email:</label>
                                            <input type="email" class="form-control" id="newemail" placeholder="Enter new email" name="newemail">
                                        </div>
                                        <div class="form-group">
                                            <label for="newpwd">Password:</label>
                                            <input type="password" class="form-control" id="newpwd" placeholder="New password" name="newpwd">
                                        </div>
                                        <button type="submit" class="btn btn-default">Register</button>
                                    </form>
                                </div>

                            </div>
                        </div>
<!--                                    <div class="modal-footer">-->
<!--                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>-->
<!--                                    </div>-->
                    </div>
                </div>
       
	</div>
</div>
        <script src="https://code.jquery.com/jquery-2.2.2.min.js"></script>
<p class="auto-style3">
    <input name="pago1" type="radio" value="Ventanilla"/>
        &nbsp;
        <span class="auto-style4"> 
            Recoger en Ventanilla
        </span>
</p>
<p class="auto-style3">
    <input checked="checked" name="pago1" type="radio" value="Deposito"/>
    <span class="auto-style4"> 
        Deposito Bancario
    </span>
</p>


<div id="div1" style="display:;">
    <p class="auto-style3">
        <span class="auto-style4">
            CLAVE Bancaria:
        </span>
    </p>
    <p class="auto-style1">
        <input type="number" name="RECEIVER_BANK_ACCOUNT_NUMBER" id="RECEIVER_BANK_ACCOUNT_NUMBER" required class="auto-style5"/>
    </p>
    <p class="auto-style3">
        <span class="auto-style4">
            Confirma CLAVE Bancaria:
        </span>
    </p>
    <p class="auto-style1">
        <input type="number" name="RECEIVER_ACCOUNT_NUMBER_CONFIRMATION" id="RECEIVER_ACCOUNT_NUMBER_CONFIRMATION" required class="auto-style5"/> 
    </p>
</div>
        
<div id="div2" style="display:none;">
    <center>
      <span>Has seleccionado ventanilla</span>
    </center>
</div>
        <script>
            
            $(document).ready(function() {
    $("input[type=radio]").click(function(event){
        var valor = $(event.target).val();
        if(valor =="Deposito"){
            $("#div1").show();
            $("#div2").hide();
        } else if (valor == "Ventanilla") {
            $("#div1").hide();
            $("#div2").show();
        } else { 
            // Otra cosa
        }
    });
});
        </script>
    </body>
</html>
