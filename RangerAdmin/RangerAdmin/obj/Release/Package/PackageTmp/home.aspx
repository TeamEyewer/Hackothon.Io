<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="home.aspx.cs" Inherits="RangerAdmin.home" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Dashborad</title>

        <link rel="stylesheet" href="assets/css/style.css" />
    <link rel="stylesheet" href="assets/css/loader-style.css" />
    <link rel="stylesheet" href="assets/css/bootstrap.css" />

    <link rel="stylesheet" type="text/css" href="assets/js/progress-bar/number-pb.css" />
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

</head>
<body onload="initMap()" style="overflow: hidden;">
    <form id="form1" runat="server">
   <asp:ScriptManager ID="ScriptManager1" runat="server" EnablePageMethods = "true"> </asp:ScriptManager>
            <nav role="navigation" class="navbar navbar-fixed-top">
        <div class="container-fluid">
         
            <div class="navbar-header">
            </div>


         
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
                        <li id="sec_id" class="sec" ></li>
                   
                        <li class="meridiem"></li>
                    </ul>

                </div>

                <ul style="margin-right: 0;" class="nav navbar-nav navbar-right">
                    <li>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <img alt="" class="admin-pic img-circle" src="http://digitrainee.com/upload/images/administrator.png"><span id="login_name" runat="server"></span> <b class="caret"></b>
                        </a>
                        <ul style="margin-top: 14px;" role="menu" class="dropdown-setting dropdown-menu">
                           
                            <li>
                                <a href="#" id="btnlogout" runat="server"  onserverclick="btnlogout_ServerClick" >
                                    <span class="entypo-logout" ></span>&#160;&#160;Logout
                                </a>
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
   

        <div style="margin-top: 0;" class="right-wrapper">
            <div class="row">
                <h3 >
                    <span onclick="reNotification()" ><i class="entypo-bell"></i>&nbsp;&nbsp;Notifications
                     
                    </span>
                   
                </h3>
                   
                <div class="col-sm-12">
                   

                    <ul id="notlification_list" class="chat">


                     

                    </ul>



                </div>
            </div>
        </div>


        <div style="margin-top: 0;min-height: 350px;" class="right-wrapper">
            <div class="row">
                <h3>
                    <span style="padding: 17px 65px 17px;"><i class="maki-fire-station"></i>&nbsp;Warning</span>
                </h3>
                <div class="col-sm-12">

                    <ul class="chat" style="margin: 9px 7px 35px 4px;width: 100%;">
                        <li class="warningli">
                            <a href="#" id="dangerlist_id">
                         
                            </a>
                        </li>
                      


                    </ul>



                </div>
            </div>
        </div>
    </div>
   
    <div class="wrap-fluid">
        <div class="container-fluid paper-wrap bevel tlbr">





            <!-- CONTENT -->
            <!--TITLE -->
            <div class="row">
                <div id="paper-top">
                    <div class="col-sm-3">
                        <h2 class="tittle-content-header">
                            
                            <div class="input-group input-widget" style="width: 100%">

                        <input  type="text" id="pac-input" placeholder="Search..." class="form-control" style="background-color: white;"/>
                    </div>

                        </h2>

                    </div>

                    <div class="col-sm-7">
                        <div class="devider-vertical visible-lg"></div>
                        <div class="tittle-middle-header">

                            <div>

                                  <div class="col-sm-3 headline_height">


                                    <h2 class="tittle-content-header">

                                        <span id="Notification_count" runat="server">
                                            99
                                        </span>

                                    </h2>
                                    &nbsp;Notifications


                                </div>

                                <div class="col-sm-3 headline_height">

                                      <div class="devider-vertical visible-lg" style="    min-height: 88px;"></div>
                                    <h2 class="tittle-content-header">

                                        <span id="txtcurrentUser" runat="server">
                                            99
                                        </span>

                                    </h2>
                                    &nbsp;Current Users


                                </div>

                                <div class="col-sm-3 headline_height">
                                    <div class="devider-vertical visible-lg" style="    min-height: 88px;"></div>

                                    <h2 class="tittle-content-header">

                                        <span id="txtAcceptpins" runat="server">
                                            97
                                        </span>

                                    </h2>
                                    &nbsp;Approved Pins
                                </div>

                                <div class="col-sm-3 headline_height">

                                    <div class="devider-vertical visible-lg" style="    min-height: 88px;"></div>
                                    <h2 class="tittle-content-header">

                                        <span  id="txtRejectedPins" runat="server">
                                            10
                                        </span>

                                    </h2>
                                    &nbsp;Rejected Pins
                                </div>



                            </div>


                        </div>

                    </div>


                    <div class="col-sm-2">
                        <div class="devider-vertical visible-lg"></div>
                        <div class="btn-group btn-wigdet pull-right visible-lg">
                            <div class="btn">
                                <a href="#" onclick="addwarningPanel()">
                                    <span class="entypo-plus-circled margin-iconic"></span>Add Warning
                                </a>
                            </div>


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



                                <div id="test1" class="gmap" style="    width: 103%;height: 630px; position: relative;margin-left: -23px;overflow: hidden;transform: translateZ(0px);background-color: rgb(229, 227, 223); "></div>



                            </div>
                        </div>

                        <div id="Div4" class="nest notfiPanel" style="display:none">

                            <div id="Div5" style="min-height: 296px; padding-top: 20px; display: block; background: none 0px 0px repeat scroll rgb(38, 56, 76);" class="body-nest">
                                <div id="notfication_panel" class="timeline-item">


                                    <div class="timeline-body">
                                     <ul class="list-group">
                                            <li class="list-group-item text-center">
                       <!--/////////////////////////////    <span class="direction-icon maki-fuel" style="background: #FF6B6B; left: 0px"></span>-->
                                                <span>
                                                    <img style="border-radius:50%;width:83px;" id="dangerimg_notifi" alt="" src="http://i.imgur.com/Udktivt.png">
                                                </span>
                                                <h3 style="margin-bottom: 0px;">
                                                    <span id="dangername_notifi_span">Accident</span>
                                                </h3>
                                                <p style="margin: 0px 0 0px;" id="noti_count_span">1 Person Marked  </p>
                                                <p id="notifi_time_span">&nbsp;&nbsp;5/14/2016 5:41:47 PM</p>
                                            
                                            </li>





                                            <li class="list-group-item text-center">
                                             <span id="noti_latlon_span"></span>
                                            </li>
                                            <li class="list-group-item text-right">
                                                <span class="pull-left">
                                                    <strong>Pin User</strong>
                                                </span><a id="noti_pinuser_span" href="12"></a>
                                            </li>
                                            <li class="list-group-item text-right">
                                                <span class="pull-left">
                                                    <strong>Mobile Number</strong>
                                                </span><span id="noti_pinusermobile_span">0</span>
                                            </li>

                                        </ul>

                                    </div>
                                    <div class="timeline-footer" style="margin-top: -16px;margin-left: 54px;">
                                          <a class="btn btn-success btn-xs" onclick="AcceptPin()">Accept</a>
                                        <a class="btn btn-danger btn-xs" onclick="RejectPin()">Delete</a>
                                    </div>
                                </div>



                                <div id="add_warning_panel" class=" personal-info" style="display: none">
                                    <div class="alert alert-info alert-dismissable" id="addpin_status_span" style="opacity:0">
                                       
                                       
                                      
                                    </div>


                                    <div class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <label class="col-lg-3 control-label">Latitude</label>
                                            <div class="col-lg-8">
                                                <input class="form-control" id="addtxt_lati" value="Latitude" type="text" />
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-lg-3 control-label">Longitude</label>
                                            <div class="col-lg-8">
                                                <input class="form-control" id="addtxt_longi" value="Longitude" type="text">
                                            </div>
                                        </div>


                                        <div class="form-group">
                                            <label class="col-lg-3 control-label">Warning </label>
                                            <div class="col-lg-8">
                                                <div class="ui-select">
                                                    <select id="user_warning_ddl" class="form-control">
                                                    
                                                    </select>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">

                                            <div class="col-md-8">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-md-3 control-label"></label>
                                            <div>
                                                <input class="btn btn-primary" onclick="SaveMark()" value="Save Changes" type="button">
                                                <span></span>
                                                <input class="btn btn-default" value="Cancel" onclick="CancelAddPin()" type="reset" />
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>

                </div>
            </div>














        </div>
    </div>
   
 <div id="footer">
               <div class="devider-footer-left"></div>
               <div class="time">
                   <p id="spanDate"></p>
                   <p id="clock"></p>
               </div>
               <div class="copyright">
                   Release Candidate 1.0 
                   Powered by © <a href="#">Eyewer </a> 2016 All Rights Reserved
               </div>
               <div class="devider-footer"></div>
           </div>







    </form>
    <script src="assets/home.js"></script>


        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.js"></script>
          <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAdW7gjCNHbW1eqOcxIYsV3TS4AVC538Uo&libraries=places&callback=initMap"></script>


    <!-- MAIN EFFECT -->
    <%--<script type="text/javascript" src="assets/js/preloader.js"></script>--%>
    <script type="text/javascript" src="assets/js/bootstrap.js"></script>
    <script type="text/javascript" src="assets/js/app.js"></script>
    <script type="text/javascript" src="assets/js/load.js"></script>
    <script type="text/javascript" src="assets/js/main.js"></script>






</body>
</html>
