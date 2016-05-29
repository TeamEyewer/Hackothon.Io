var map;
var  markers=[];
var hasaddpin = false;
var hasloaddistance = false;
var notification_groupid = null;

function initMap() {
    ViewNotificationlist();
    ViewNotificationCount();
    GetPinList();

    if (hasloaddistance == false) {
        viewDangerList();
    }
    var myLatLng = { lat: 7.8731, lng: 80.7718 };
     //map = new google.maps.Map(document.getElementById('test1'), {
     //   zoom: 12,
     //   center: bangalore
     //});


     var mapOptions = {

         zoom: 8,


         center: myLatLng,


         styles: [{ "featureType": "landscape.man_made", "elementType": "all", "stylers": [{ "color": "#949191" }, { "lightness": 83 }, { "saturation": "7" }] }, { "featureType": "landscape.natural", "elementType": "geometry.fill", "stylers": [{ "lightness": 77 }] }, { "featureType": "poi.park", "elementType": "geometry.fill", "stylers": [{ "color": "#34ad6b" }, { "lightness": "40" }] }, { "featureType": "poi.school", "elementType": "geometry.fill", "stylers": [{ "lightness": "39" }, { "gamma": "0.76" }, { "hue": "#fff700" }, { "saturation": "67" }] }, { "featureType": "poi.sports_complex", "elementType": "geometry.fill", "stylers": [{ "color": "#c15e1f" }, { "lightness": "12" }, { "saturation": "45" }, { "gamma": "2.07" }] }, { "featureType": "road.highway", "elementType": "geometry", "stylers": [{ "color": "#f1b127" }] }, { "featureType": "water", "elementType": "geometry.fill", "stylers": [{ "color": "#2f78c4" }, { "gamma": "1.50" }, { "lightness": "12" }] }]
     };


     map = new google.maps.Map(document.getElementById('test1'), mapOptions);

     var input = document.getElementById('pac-input');
     var autocomplete = new google.maps.places.Autocomplete(input);


     google.maps.event.addListener(autocomplete, 'place_changed',
                   function () {
                       var place = autocomplete.getPlace();
                       var lat = place.geometry.location.lat();
                       var lng = place.geometry.location.lng();

                       var myLatlng11 = new google.maps.LatLng(lat, lng);

                       map.setCenter(myLatlng11);
                       map.setZoom(15);
                       // document.getElementById("output").innerHTML = "Lat: " + lat + "<br />Lng: " + lng;
                   }
               );


     if (hasaddpin == true) {
         google.maps.event.addListener(map, 'click', function (event) {
             addMarker(event.latLng, map);
             document.getElementById("addtxt_lati").value = event.latLng.lat();
             document.getElementById("addtxt_longi").value = event.latLng.lng();

         });
     }
  
}



function addMarker(location, map) {
  
    setMapOnAll(null);
    var marker = new google.maps.Marker({
        position: location,
      
        map: map
    });
    markers.push(marker);

}


function setMapOnAll(map) {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
    }
}


function addwarningPanel()
{
    document.getElementById("Div4").style.display = "block";
    document.getElementById("add_warning_panel").style.display = "block";
    document.getElementById("notfication_panel").style.display = "none";

    hasaddpin = true;
    initMap();

   
}

function CancelAddPin() {

    document.getElementById("add_warning_panel").style.display = "none";
    document.getElementById("notfication_panel").style.display = "none";
    document.getElementById("Div4").style.display = "none";
    
    hasaddpin = false;
    initMap();
}



function ViewNotificationlist() {

    
        PageMethods.ViewNotificationlist(onSucess, onError);


        function onSucess(val) {
            document.getElementById("notlification_list").innerHTML = "";
            //   hasloadnoti = true;
            var obj = JSON.parse(val);

            obj.forEach(function (notification) {

                var notification_danger_img = document.createElement("img");
                notification_danger_img.src = notification.PinDanger.DangerIcon;
                notification_danger_img.classList.add("img-chat");


                var notification_danger_span = document.createElement("span");
                notification_danger_span.appendChild(notification_danger_img);

                var notification_danger_spanname_text = document.createTextNode(notification.PinDanger.DangerName + " - " + notification.PinCount);
                var notification_danger_spanname = document.createElement("span");
                notification_danger_spanname.classList.add("notitype");
                notification_danger_spanname.appendChild(notification_danger_spanname_text);

                var notification_br = document.createElement("br");

                var notification_i = document.createElement("i");
                var notification_i_text = document.createTextNode(notification.CreatedOn);
                notification_i.appendChild(notification_i_text);

                var notification_a = document.createElement("a");

                notification_a.appendChild(notification_danger_span);
                notification_a.appendChild(notification_danger_spanname);
                notification_a.appendChild(notification_br);
                notification_a.appendChild(notification_i);


                var notification_li = document.createElement("li");
                notification_li.id = notification.PinGroup;
                notification_li.classList.add("notifications");
                notification_li.setAttribute("onclick", "viewNotificationItem(this)");

                notification_li.appendChild(notification_a);


                document.getElementById("notlification_list").appendChild(notification_li);
                document.getElementById("notlification_list").appendChild(notification_li);


            });

        }
        function onError() {

        }
   
   





}


function viewNotificationItem(x)
{
   


    initMap();
    var groupid = x.id;
    document.getElementById("Div4").style.display = "block";
    document.getElementById("add_warning_panel").style.display = "none";
    document.getElementById("notfication_panel").style.display = "block";

    PageMethods.GetNotificationItem(groupid, onSucess, onError);


    function onSucess(val) {
        var obj = JSON.parse(val);
        notification_groupid = groupid;

      


        var latlong = new google.maps.LatLng(obj.Lat, obj.Lon);

        notificationMarks(latlong, obj.PinDanger.DangerPinIcon);


        document.getElementById("dangerimg_notifi").src = obj.PinDanger.DangerIcon;
        document.getElementById("dangername_notifi_span").innerText = obj.PinDanger.DangerName;
        document.getElementById("noti_count_span").innerText = "1 Person Marked  ";
        document.getElementById("notifi_time_span").innerText = obj.CreatedOn;
        document.getElementById("noti_latlon_span").innerText = obj.Lat + " , " + obj.Lon;
        document.getElementById("noti_pinuser_span").innerText = obj.PinUser.Name;
        document.getElementById("noti_pinuser_span").href = obj.PinUser.id;
        document.getElementById("noti_pinusermobile_span").innerText = obj.PinUser.MobileNumber;
    }
    function onError(val) {

    }




    
    
}


function viewDangerList()
{

    PageMethods.GetDangerList(onSucess, onError);


    function onSucess(val) {

        hasloaddistance = true;
        var obj = JSON.parse(val);

        obj.forEach(function (dangertype) {


            var options_dd = document.createElement("option")
            options_dd.value = dangertype.id;

            var option_span_text = document.createTextNode(dangertype.DangerName);
            options_dd.appendChild(option_span_text);
            document.getElementById("user_warning_ddl").appendChild(options_dd);


            var danger_span_img = document.createElement("img");
            danger_span_img.classList.add("warnings");
            danger_span_img.src = dangertype.DangerIcon;

            var danger_span = document.createElement("span");
            danger_span.id = dangertype.id;
            danger_span.appendChild(danger_span_img);
            danger_span.setAttribute("onclick", "ViewDangerTypePins(" + dangertype.id + ")");
         
            document.getElementById("dangerlist_id").appendChild(danger_span);

        });

    }
    function onError() {

    }


    

}


function SaveMark()
{

  
    var lat = document.getElementById("addtxt_lati").value;
    var long = document.getElementById("addtxt_longi").value;

    var select = document.getElementById("user_warning_ddl");
    var answer = select.options[select.selectedIndex].value;

    if (lat != "" || long != "") {
        PageMethods.AddDangerByAdmin(lat, long, answer, onSucess, onError);


        function onSucess(val) {

            if (val == 1) {
                document.getElementById("addtxt_lati").value = null;
                document.getElementById("addtxt_longi").value = null;
                document.getElementById("addpin_status_span").innerText = "Add Succesfully";
                document.getElementById("addpin_status_span").style.opacity = 1;

            }


        }
        function onError(val) {

            document.getElementById("addpin_status_span").style.opacity = 1;
            document.getElementById("addpin_status_span").innerText = "Error";
        }

    }

}



function notificationMarks(location, imgicon) {

    setMapOnAll(null);
    // alert(location.lo);
    map.setCenter(location);
    var marker = new google.maps.Marker({
        position: location,
        icon: imgicon,
        map: map,

    });

    markers.push(marker);



}


function DangerMarks(location, imgicon) {


    // alert(location.lo);
    map.setCenter(location);
    var marker = new google.maps.Marker({
        position: location,
        icon: imgicon,
        map: map,

    });

    markers.push(marker);



}
function ViewDangerTypePins(x) {
    setMapOnAll(null);

    document.getElementById("add_warning_panel").style.display = "none";
    document.getElementById("notfication_panel").style.display = "none";
    document.getElementById("Div4").style.display = "none";

   // hasaddpin = false;
   // initMap();
  
    PageMethods.GetPinsByDangerTypes(x, onSucess, onError);


    function onSucess(val) {
        var obj = JSON.parse(val);

        obj.forEach(function (danger) {


            var latlong = new google.maps.LatLng(danger.Lat, danger.Lon);

            DangerMarks(latlong, danger.PinDanger.DangerPinIcon);



        });

    }
    function onError() {


    }
}


function AcceptPin() {
    if (notification_groupid != null) {
        PageMethods.AcceptNotification(notification_groupid, onSucess, onError);


        function onSucess(val) {

            setMapOnAll(null);
            var obj = JSON.parse(val);


            document.getElementById("Div4").style.display = "none";
            document.getElementById("notfication_panel").style.display = "none";


            element = document.getElementById(notification_groupid);
            element.parentNode.removeChild(element);

            notification_groupid = null;



        }
        function onError(val) {

        }

        ViewNotificationlist();

    }
}

function RejectPin() {

    if (notification_groupid != null) {
        PageMethods.RejectNotification(notification_groupid, onSucess, onError);


        function onSucess(val) {
            var obj = JSON.parse(val);
            //notification_groupid = groupid;
            document.getElementById("Div4").style.display = "none";
            document.getElementById("notfication_panel").style.display = "none";

            element = document.getElementById(notification_groupid);
            element.parentNode.removeChild(element);

            notification_groupid = null;


        }
        function onError(val) {

        }

        ViewNotificationlist();
    }
}

function ViewNotificationCount() {

 
    PageMethods.ViewNotificationCount( onSucess, onError);


        function onSucess(val) {
          
            //notification_groupid = groupid;
            document.getElementById("Notification_count").innerText = val;
            

        }
        function onError(val) {

        }

    
   
}

function reNotification()
{

    ViewNotificationCount();
    ViewNotificationlist();
}


function GetPinList()
{
    PageMethods.GetPinList(onSucess, onError);


    function onSucess(val) {


        var obj = JSON.parse(val);

        obj.forEach(function (pins) {


            var latlong = new google.maps.LatLng(pins.Lat, pins.Lon);

            var marker = new google.maps.Marker({
                position: latlong,
                icon: pins.PinDanger.DangerPinIcon,
                map: map
            });

            markers.push(marker);
        });

    }
    function onError() {


    }


}