using RangerAdmin.Datalayer;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace RangerAdmin
{
    public partial class adminlogin : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (Request.Cookies["adminuserid@ranger"] != null && Request.Cookies["adminpass@ranger"] != null && Request.Cookies["adminname@ranger"] != null)
            {
                Response.Redirect("~/home.aspx");
            }
        }

        protected void btnlogin_ServerClick(object sender, EventArgs e)
        {
          string admininid=  inputuserID.Value;
          string pass = inputPassword.Value;

          if (admininid != "" || pass != "")
          {
              UserClass UC = new UserClass();
            var userobj=  UC.LoginFunction(admininid, pass);

              if(userobj.Id.Equals("0"))
              {
                    // Error 
              }
              else
              {
                  HttpCookie adminuserid = new HttpCookie("adminuserid@ranger");
                  adminuserid.Value = userobj.Id.ToString();
                  adminuserid.Expires = DateTime.Now.AddDays(1d);
                  Response.Cookies.Add(adminuserid);

                  HttpCookie adminpass = new HttpCookie("adminpass@ranger");
                  adminpass.Value = userobj.Password;
                  adminpass.Expires = DateTime.Now.AddDays(1d);
                  Response.Cookies.Add(adminpass);

                  HttpCookie adminusername = new HttpCookie("adminusername@ranger");
                  adminusername.Value = userobj.Username;
                  adminusername.Expires = DateTime.Now.AddDays(1d);
                  Response.Cookies.Add(adminusername);


                  HttpCookie adminname = new HttpCookie("adminname@ranger");
                  adminname.Value = userobj.Username;
                  adminname.Expires = DateTime.Now.AddDays(1d);
                  Response.Cookies.Add(adminname);

                  Response.Redirect("home.aspx");


              }
          
          }
        }
    }
}