


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
    public class UserController : ApiController
    {
        
        [HttpGet]
        [Route("api/getProfile")]
        public IHttpActionResult GetPins(String userId)
        {
            String status = "200";
            String description = "Response Okay";
            User user = new User();

            UserDoa doa = new UserDoa();
            Tuple<string, String, User> data = doa.getprofile(userId);
            status = data.Item1;
            description = data.Item2;
            user = data.Item3;  


            //operation
            return Ok(new { status , description ,user });
        }

        [HttpPost]
        [Route("api/SocialLogin")]
        public IHttpActionResult SocialLogin([FromBody] UserModel model)
        {
            String status = "200";
            String description = "Response Okay";

            //operation
            UserDoa doa = new UserDoa();
        Tuple<String,String,User,Boolean,String> data =    doa.login(model.Name, model.Provider, model.ProviderID, model.ProviderEmail, model.Picture,model.DeviceID);
            status = data.Item1;
            description = data.Item2;
            User user = data.Item3;
            Boolean isNewUser = data.Item4;
            String AccessToken = data.Item5;

            return Ok(new { status, description ,user,isNewUser,AccessToken });
        }

        [HttpPost]
        [Route("api/Logout")]
        public IHttpActionResult Logout([FromBody] LogoutModel model)
        {
            String status = "200";
            String description = "Response Okay";

            //operation
            UserDoa doa = new UserDoa();
            Tuple<String, String> data = doa.deleteAccessToken(model.userid, model.deviceid);
            status = data.Item1;
            description = data.Item2;
            

            return Ok(new { status, description});
        }

        [HttpPost]
        [Route("api/confirmaccount")]
        public IHttpActionResult confirmaccount([FromBody] confirmaccountmodel model)
        {
            String status = "200";
            String description = "Response Okay";

            //operation
            UserDoa doa = new UserDoa();
            Tuple<String, String> data = doa.confirmaccount(model.userid, model.MobileNumber);
            status = data.Item1;
            description = data.Item2;


            return Ok(new { status, description });
        }
    }
}