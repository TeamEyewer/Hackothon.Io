
using EntityLibrary;
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
    public partial class home : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

            if (HttpContext.Current.Request.Cookies["adminname@ranger"] != null || HttpContext.Current.Request.Cookies["adminuserid@ranger"] != null || HttpContext.Current.Request.Cookies["adminpass@ranger"] != null)
            {
                login_name.InnerText = HttpContext.Current.Request.Cookies["adminname@ranger"].Value;
                PinClass p = new PinClass();
                txtcurrentUser.InnerText = p.CurrentUserCount().ToString();
                txtAcceptpins.InnerText = p.ApprovedPinCount().ToString();
                txtRejectedPins.InnerText = p.RejectedPinCount().ToString();
            }
            else
            {
                Response.Redirect("~/adminlogin.aspx");
            }
        }
     
        [WebMethod]
        public static string GetDangerList()
        {

            string json = "";
            PinClass P = new PinClass();

          var list = P.GetDangerTypes();




            JavaScriptSerializer oSerializer =
              new JavaScriptSerializer();

            json = oSerializer.Serialize(list);

            return json;
        }

   




        [WebMethod]
        public static int AddDangerByAdmin(string lat, string lon, string dangerid)
        {
            int i = 0;
            if (lat != null || lon != null || dangerid != null)
            {
                var adminid = HttpContext.Current.Request.Cookies["adminuserid@ranger"].Value;

                PinClass p = new PinClass();
                i = p.AddDangerByAdmin(lat, lon, dangerid, adminid.ToString());

            }

            return i;


        }
        [WebMethod]
        public static string ViewNotificationlist()
        {

            string json = "";
            var adminid = HttpContext.Current.Request.Cookies["adminuserid@ranger"].Value;

            PinClass p = new PinClass();
            var list = p.ViewNotification();


            JavaScriptSerializer oSerializer =
         new JavaScriptSerializer();

            json = oSerializer.Serialize(list);

            return json;


        }


        [WebMethod]
        public static string GetNotificationItem(string groupname)
        {

            string json = "";
            // var adminid = HttpContext.Current.Request.Cookies["id@eyewer"].Value;

            PinClass p = new PinClass();
            var list = p.GetNotificationItem(groupname);


            JavaScriptSerializer oSerializer =
         new JavaScriptSerializer();

            json = oSerializer.Serialize(list);

            return json;


        }


        [WebMethod]
        public static int AcceptNotification(string groupname)
        {

            int json = 0;
           
            var adminid = HttpContext.Current.Request.Cookies["adminuserid@ranger"].Value;
            PinClass p = new PinClass();
            json = p.AcceptPinFUnction(groupname, adminid);




            return json;


        }

        [WebMethod]
        public static string GetPinsByDangerTypes(string dangerid)
        {

            string json = "";


            PinClass p = new PinClass();
            var list = p.GetPinsByDangerTypes(dangerid);

            JavaScriptSerializer oSerializer = new JavaScriptSerializer();

            json = oSerializer.Serialize(list);




            return json;


        }

        //

        [WebMethod]
        public static int RejectNotification(string groupname)
        {

            int json = 0;
            // var adminid = HttpContext.Current.Request.Cookies["id@eyewer"].Value;
            var adminid = HttpContext.Current.Request.Cookies["adminuserid@ranger"].Value;
            PinClass p = new PinClass();
            json = p.RejectPinFUnction(groupname, adminid);




            return json;


        }
               [WebMethod]
        public static int ViewNotificationCount()
        {

            int json = 0;

          
            PinClass p = new PinClass();
            json = p.ViewNotificationCount();




            return json;


        }
        

        protected void btnlogout_ServerClick(object sender, EventArgs e)
        {
            Response.Cookies["adminusername@ranger"].Expires = DateTime.Now.AddDays(-1);
            Response.Cookies["adminpass@ranger"].Expires = DateTime.Now.AddDays(-1);
            Response.Cookies["adminuserid@ranger"].Expires = DateTime.Now.AddDays(-1);


            Response.Redirect("~/adminlogin.aspx");
        }

  

        [WebMethod]
        public static string GetPinList()
        {

            string json = "";


            PinClass p = new PinClass();
            var list = p.GetPinList();

            JavaScriptSerializer oSerializer = new JavaScriptSerializer();

            json = oSerializer.Serialize(list);




            return json;


        }
       

    }
}