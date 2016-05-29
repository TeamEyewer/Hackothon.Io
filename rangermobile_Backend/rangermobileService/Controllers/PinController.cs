


using EntityLibrary;
using rangermobileService.DataObjects;
using rangermobileService.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;

namespace rangermobileService.Controllers
{
    public class PinController : ApiController 

    {

        [HttpGet]
        [Route("api/getpins")]
        public IHttpActionResult GetPins(String userId,String lat,String lon,int radiusinmeters)
        {
            String status = "200";
            String description = "Response Okay";
            List<Distance> pins = new List<Distance>();

             PinDoa doa = new PinDoa();

            Tuple<string, String, List<Distance>> data = doa.GetPins(userId, lat, lon, radiusinmeters);
            status = data.Item1;
            description = data.Item2;
            pins = data.Item3;


            //operation
            return Ok(new { status, description, pins });
        }

        [HttpPost]
        [Route("api/createpin")]
        public IHttpActionResult CreatePin([FromBody] CreatePinModel model)
        {
            String status = "200";
            String description = "Response Okay";
            Distance nearByPins = new Distance();
            Boolean hasNearByPins = false;
            String BasePinID = "";
            PinDoa doa = new PinDoa();
            //operation
            Tuple<String, String, Distance, Boolean,String> data = doa.createPin(model.Latitude, model.Longitude, model.UserId, 5000, model.DangerId);
            status = data.Item1;
            description = data.Item2;
            nearByPins = data.Item3;
            hasNearByPins = data.Item4;
            BasePinID = data.Item5; 
            return Ok(new { status, description, nearByPins, hasNearByPins ,BasePinID});
        }

        [HttpPost]
        [Route("api/confirmpinlocation")]
        public IHttpActionResult confirmPinLocation([FromBody] confirmpinModel model)
        {
            String status = "200";
            String description = "Response Okay";
            

            PinDoa doa = new PinDoa();
            //operation
            Tuple<String, String> data = doa.confirmpin(model.PinId, model.GroupID);
            status = data.Item1;
            description = data.Item2;


            return Ok(new { status, description });
        }

    }
}