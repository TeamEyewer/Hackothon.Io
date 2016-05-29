<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="profile.aspx.cs" Inherits="RangerAdmin.profile" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>User Profile</title>
        <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/loader-style.css">
    <link rel="stylesheet" href="assets/css/bootstrap.css">

    <link rel="stylesheet" type="text/css" href="assets/js/progress-bar/number-pb.css">
    <style>
        .notfiPanel {
            position: absolute;
            top: 26px;
            right: 24px;
            width: 24%;
            z-index: 10;
            background-color: rgba(255, 255, 255, 0.76);
        }

        .notfi_time_i {
            font-size: 10px;
            color: rgb(255, 255, 255);
            margin: 0 0 0 54px;
            position: relative;
            top: -25px;
        }
    </style>


 
    <link rel="shortcut icon" href="assets/ico/minus.png">
</head>
<body onload="initMap()">
    <form id="form1" runat="server">
        <!-- TOP NAVBAR -->
          <asp:ScriptManager ID="ScriptManager1" runat="server" EnablePageMethods="true" />
    <nav role="navigation" class="navbar navbar-fixed-top">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
            </div>


            <!-- Collect the nav links, forms, and other content for toggling -->
            <div id="bs-example-navbar-collapse-1" class="collapse navbar-collapse">
                <ul class="nav navbar-nav">

                    <li class="dropdown">

                        


                    </li>


                </ul>
                <div id="nt-title-container" class="navbar-left running-text visible-lg">
                    <ul class="date-top">
                        <li class="entypo-calendar" style="margin-right: 5px"></li>
                        <li id="Date"></li>


                    </ul>

                    <ul id="digital-clock" class="digital">
                        <li class="entypo-clock" style="margin-right: 5px"></li>
                        <li class="hour"></li>
                        <li>:</li>
                        <li class="min"></li>
                        <li>:</li>
                        <li class="sec"></li>
                        <li class="meridiem"></li>
                    </ul>
                  
                </div>

     <ul style="margin-right: 0;" class="nav navbar-nav navbar-right">
                    <li>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#" >
                            <img alt="" class="admin-pic img-circle" src="http://digitrainee.com/upload/images/administrator.png"><span id="adminname_span" runat="server">Shehan</span> <b class="caret"></b>
                        </a>
                        <ul style="margin-top: 14px;" role="menu" class="dropdown-setting dropdown-menu">
                            <li>
                                <a href="#">
                                    <span class="entypo-user"></span>&#160;&#160;My Profile</a>
                            </li>

                            <li class="divider"></li>
                            <li>
                                <a href="#" runat="server" id="btnlogout" runat="server" onserverclick="btnlogout_ServerClick">
                                    <span class="entypo-logout" ></span>&#160;&#160;Logout</a>
                            </li>
                        </ul>
                    </li>

                    <li class="hidden-xs">
                        <a class="toggle-left" href="#">
                            <span style="font-size: 20px;" class="entypo-list-add"></span>
                        </a>
                    </li>
                </ul>

            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>

    <!-- /END OF TOP NAVBAR -->

    <!-- SIDE MENU -->
    <div id="skin-select">
          <div id="logo">
                        <h1>
                            Ranger<span>Admins</span>
                           
                        </h1>
        </div>

        <a id="toggle" style="opacity:0">
            <span class="entypo-menu"></span>
        </a>







        <div class="skin-part" style="display: none">
        </div>
    </div>



    <div class="sb-slidebar" style="display: block;margin-top: 65px;">


     <div class="skin-part" style="visibility: visible;">
            <div id="tree-wrap">
                <div class="side-bar">
                    

                    

                    <div id="" class="" style="margin: 10px;">
                        
<div class="">

                                    <ul class="list-group" style="color:white">
                                        <li class="list-group-item text-left">
                                            <span class="entypo-user"></span>&nbsp;&nbsp;Profile</li>
                                        <li class="list-group-item text-center">
                                            <img src="http://stanlemmens.nl/wp/wp-content/uploads/2014/07/bill-gates-wealthiest-person.jpg" alt="" class="img-circle img-responsive img-profile" id="profilepic_img" runat="server">

                                        </li>
                                   

                                        <li class="list-group-item text-right">
                                            <span class="pull-left">
                                                <strong>Name</strong>
                                            </span><span id="profilename_text" runat="server">Saman Persad</span> </li>
                                        <li class="list-group-item text-right">
                                            <span class="pull-left">
                                                <strong>Mobile </strong>
                                            </span><span id="usermobile_span" runat="server">02121333</span></li>
                                        <li class="list-group-item text-right">
                                            <span class="pull-left">
                                                <strong>Pins</strong>
                                            </span><span id="userpinscount" runat="server">Saman Persad</span></li>

                                    </ul>

                                </div>

                        
                        
                        

                        
                    </div>


                    
                </div>
            </div>
        </div>
    </div>
    <!-- END OF SIDE MENU -->



    <!--  PAPER WRAP -->
    <div class="wrap-fluid">
        <div class="container-fluid paper-wrap bevel tlbr">





            <!-- CONTENT -->
            <!--TITLE -->
            <div class="row">
                <div id="paper-top">
                    <div class="col-sm-3">
                        <h2 class="tittle-content-header">
                            <i class="icon-window"></i>
                            <a href="home.aspx">Dashboard
                            </a>

                        </h2>

                    </div>

                       <div class="col-sm-7">
                        <div class="devider-vertical visible-lg"></div>
                        <div class="tittle-middle-header">

                            <div>

                                <div class="col-sm-3 headline_height" >


                                    <h2 class="tittle-content-header">

                                        <span id="txtcurrentUser" runat="server">99
                            </span>

                                    </h2>
                                  &nbsp;Current Users


                                </div>

                                <div class="col-sm-3 headline_height">
                                    <div class="devider-vertical visible-lg" style="    min-height: 88px;"></div>

                                    <h2 class="tittle-content-header">

                                        <span id="txtAcceptpins" runat="server">97
                            </span>

                                    </h2>
                                 &nbsp;Approved Pins
                                </div>

                                <div class="col-sm-3 headline_height">

                                    <div class="devider-vertical visible-lg" style="    min-height: 88px;"></div>
                                    <h2 class="tittle-content-header">

                                        <span id="txtRejectedPins" runat="server">10
                            </span>

                                    </h2>
                                 &nbsp;Rejected Pins
                                </div>



                            </div>


                        </div>

                    </div>


                    <div class="col-sm-2">
                        <div class="devider-vertical visible-lg"></div>
                        <div class="btn-group btn-wigdet pull-right visible-lg" >
                    


                        </div>


                    </div>
                </div>
            </div>
            <!--/ TITLE -->








            <div class="content-wrap">
                <div class="row">


                    <div class="col-sm-12">
                        <div class="nest" id="GmapClose">


                            <div style="padding: 0;" class="body-nest" id="Gmap">



                                         <div id="test1" class="gmap" style="width: 103%; height: 590px; position: relative;margin-left:-23px "></div>



                            </div>
                        </div>

                        <div id="Div4" class="nest notfiPanel">

                     
                        </div>
                    </div>

                </div>
            </div>




          









        </div>
    </div>
    <!--  END OF PAPER WRAP -->
    <div id="footer">
      <div class="container">
        <p class="muted credit">Example courtesy <a href="http://martinbean.co.uk">Martin Bean</a> and <a href="http://ryanfait.com/sticky-footer/">Ryan Fait</a>.</p>
      </div>
    </div>
    </form>
    <script src="js/profile.js"></script>
  
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.js"></script>
    
      <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAdW7gjCNHbW1eqOcxIYsV3TS4AVC538Uo&callback=initMap"></script>


    <!-- MAIN EFFECT -->
    <script type="text/javascript" src="assets/js/preloader.js"></script>
    <script type="text/javascript" src="assets/js/bootstrap.js"></script>
    <script type="text/javascript" src="assets/js/app.js"></script>
    <script type="text/javascript" src="assets/js/load.js"></script>
    <script type="text/javascript" src="assets/js/main.js"></script>




   

</body>
</html>

