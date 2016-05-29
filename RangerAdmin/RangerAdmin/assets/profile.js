var markers = [];

var map;


function initMap() {


    var myLatLng = { lat: 7.8731, lng: 80.7718 };

    var mapOptions = {

        zoom: 10,


        center: myLatLng,


        styles: [{ "featureType": "landscape.man_made", "elementType": "all", "stylers": [{ "color": "#949191" }, { "lightness": 83 }, { "saturation": "7" }] }, { "featureType": "landscape.natural", "elementType": "geometry.fill", "stylers": [{ "lightness": 77 }] }, { "featureType": "poi.park", "elementType": "geometry.fill", "stylers": [{ "color": "#34ad6b" }, { "lightness": "40" }] }, { "featureType": "poi.school", "elementType": "geometry.fill", "stylers": [{ "lightness": "39" }, { "gamma": "0.76" }, { "hue": "#fff700" }, { "saturation": "67" }] }, { "featureType": "poi.sports_complex", "elementType": "geometry.fill", "stylers": [{ "color": "#c15e1f" }, { "lightness": "12" }, { "saturation": "45" }, { "gamma": "2.07" }] }, { "featureType": "road.highway", "elementType": "geometry", "stylers": [{ "color": "#f1b127" }] }, { "featureType": "water", "elementType": "geometry.fill", "stylers": [{ "color": "#2f78c4" }, { "gamma": "1.50" }, { "lightness": "12" }] }]
    };

    map = new google.maps.Map(document.getElementById('test1'), mapOptions);

    viewusermark();







}

function viewusermark() {
    PageMethods.set_path('/profile.aspx');
    PageMethods.GetTopMarkPins(onSucess, onError);


    function onSucess(val) {


        var obj = JSON.parse(val);

        obj.forEach(function (pins) {


            var latlong = new google.maps.LatLng(pins.Lat, pins.Lon);

            var marker = new google.maps.Marker({
                position: latlong,
                icon: pins.PinDanger.DangerPinIcon,
                map: map
            });
        });

    }
    function onError() {


    }

}



function addMarker(location, map) {




    var marker = new google.maps.Marker({
        position: location,
        icon: "http://i.imgur.com/c8c9aiz.png",
        map: map
    });

    //  markers.push(marker);



}
