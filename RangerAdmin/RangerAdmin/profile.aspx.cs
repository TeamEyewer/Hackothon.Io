using RangerAdmin.Datalayer;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Script.Serialization;
using System.Web.Services;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace RangerAdmin
{
    public partial class profile : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {




            UserClass c = new UserClass();

            string person_username = Request.Url.AbsolutePath.ToString().Replace("/", "");

            double num;

            if (double.TryParse(person_username, out num))
            {

                var adminid = HttpContext.Current.Request.Cookies["adminname@ranger"].Value;
                adminname_span.InnerText = adminid.ToString();



                var pubuser = c.GetUserDetails(person_username);

                profilepic_img.Src ="http://publicdomainvectors.org/photos/generic-avatar.png"; //"https://" + pubuser.ProfilePicture;
                profilename_text.InnerText = pubuser.Name;
                usermobile_span.InnerText = pubuser.MobileNumber;


                PinClass p = new PinClass();
                txtcurrentUser.InnerText = p.CurrentUserCount().ToString();
                txtAcceptpins.InnerText = p.ApprovedPinCount().ToString();
                txtRejectedPins.InnerText = p.RejectedPinCount().ToString();
            }
            else
            {
                Response.Redirect("~/home.aspx");
            }



        }

        [WebMethod]
        public static string GetTopMarkPins()
        {
            string person_username = HttpContext.Current.Request.UrlReferrer.AbsolutePath.ToString().Replace("/", "");

            string json = "";
            // var adminid = HttpContext.Current.Request.Cookies["id@eyewer"].Value;

            PinClass p = new PinClass();
            var list = p.GetPinByUserID(person_username);


            JavaScriptSerializer oSerializer =
         new JavaScriptSerializer();

            json = oSerializer.Serialize(list);

            return json;


        }
        protected void btnlogout_ServerClick(object sender, EventArgs e)
        {




            Response.Cookies["adminusername@ranger"].Expires = DateTime.Now.AddDays(-1);
            Response.Cookies["adminpass@ranger"].Expires = DateTime.Now.AddDays(-1);
            Response.Cookies["adminuserid@ranger"].Expires = DateTime.Now.AddDays(-1);
            Response.Cookies["adminname@ranger"].Expires = DateTime.Now.AddDays(-1);

            Response.Redirect("~/adminlogin.aspx");
        }
    }
}