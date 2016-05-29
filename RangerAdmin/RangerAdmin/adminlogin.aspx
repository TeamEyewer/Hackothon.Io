<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="adminlogin.aspx.cs" Inherits="RangerAdmin.adminlogin" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>

    
    <script type="text/javascript" src="assets/js/jquery.min.js"></script>


    <link rel="stylesheet" href="assets/css/loader-style.css" />
    <link rel="stylesheet" href="assets/css/bootstrap.css" />
    <link rel="stylesheet" href="assets/css/signin.css" />

    
 
    <link rel="shortcut icon" href="assets/ico/minus.png" />
</head>
<body>
    <form id="form1" runat="server">
   

            <div id="preloader">
        <div id="status">&nbsp;</div>
    </div>
    <div class="container">



    <div class="" id="login-wrapper">
            <div class="row">
                <div class="col-md-4 col-md-offset-4" style="margin-top: 100px;">
                    <div id="logo-login">
                        <h1>
                            Ranger<span>Admins</span>
                            <hr style="width: 304px;margin-left: -2px;">
                        </h1>
                    </div>
                </div>

            </div>

            <div class="row">
                <div class="col-md-4 col-md-offset-4" style="margin-top: -39px;">
                    <div class="account-box" style="height: 221px;color: white;background-color: rgba(60, 76, 106, 0);">

                        <div role="form">
                            <div class="form-group">

                                <label for="inputUsernameEmail">Username or email</label>
                                <input type="text" id="inputuserID" runat="server" class="form-control" />
                            </div>
                            <div class="form-group">

                                <label for="inputPassword">Password</label>
                                <input type="password" id="inputPassword" runat="server" class="form-control" />
                            </div>

                            <button class="btn btn btn-primary pull-right" runat="server" id="btnlogin" onserverclick="btnlogin_ServerClick" type="submit">
                                Log In
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>





        <div style="text-align:center;margin:0 auto;">
            <h6 style="color:#fff;">Release Candidate 1.0 Powered by © Eyewer 2016</h6>
        </div>

    </div>
    <div id="test1" class="gmap3"></div>




    </form>


        <script type="text/javascript" src="assets/js/preloader.js"></script>
    <script type="text/javascript" src="assets/js/bootstrap.js"></script>
    <script type="text/javascript" src="assets/js/app.js"></script>
    <script type="text/javascript" src="assets/js/load.js"></script>
    <script type="text/javascript" src="assets/js/main.js"></script>

    <script src="http://maps.googleapis.com/maps/api/js?sensor=false" type="text/javascript"></script>
    <script type="text/javascript" src="assets/js/map/gmap3.js"></script>
    <script type="text/javascript">
        $(function () {

            $("#test1").gmap3({
                marker: {
                    latLng: [-7.782893, 110.402645],
                    options: {
                        draggable: true
                    },
                    events: {
                        dragend: function (marker) {
                            $(this).gmap3({
                                getaddress: {
                                    latLng: marker.getPosition(),
                                    callback: function (results) {
                                        var map = $(this).gmap3("get"),
                                            infowindow = $(this).gmap3({
                                                get: "infowindow"
                                            }),
                                            content = results && results[1] ? results && results[1].formatted_address : "no address";
                                        if (infowindow) {
                                            infowindow.open(map, marker);
                                            infowindow.setContent(content);
                                        } else {
                                            $(this).gmap3({
                                                infowindow: {
                                                    anchor: marker,
                                                    options: {
                                                        content: content
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                            });
                        }
                    }
                },
                map: {
                    options: {
                        zoom: 15
                    }
                }
            });

        });
    </script>

</body>
</html>
