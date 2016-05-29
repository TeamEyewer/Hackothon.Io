using EntityLibrary;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Web;

namespace RangerAdmin.Datalayer
{
    public class UserClass
    {
        static RangerConfig constring = new RangerConfig();


        public AdminUser LoginFunction(string username, string password)
        {

            AdminUser u = new AdminUser();

            try
            {
                using (SqlConnection con = new SqlConnection(constring.getconnection()))
                {
                    SqlCommand cmd = new SqlCommand("Select * From ranger.AdminTable Where Username=@Username AND Password=@Password ", con);
                    cmd.Parameters.AddWithValue("@Password", password);
                    cmd.Parameters.AddWithValue("@Username", username);
                    con.Open();
                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        u.Id = Convert.ToInt32(rdr["id"].ToString());
                        u.Username = rdr["Username"].ToString();
                        u.Password = rdr["Password"].ToString();
                        u.Name = rdr["Name"].ToString();
                    }
                }
            }
            catch (Exception ex)
            {
                u.Id = 0;
            }
            return u;
        }


        public User GetUserDetails(string id)
        {

            User u = new User();

            try
            {
                using (SqlConnection con = new SqlConnection(constring.getconnection()))
                {
                    SqlCommand cmd = new SqlCommand("Select * From ranger.UserTable  Where id=@id  ", con);
                    cmd.Parameters.AddWithValue("@id", id);

                    con.Open();
                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        u.id = rdr["id"].ToString();
                        u.MobileNumber = rdr["MobileNumber"].ToString();
                        u.ProfilePicture = rdr["ProfilePicture"].ToString();
                        if( u.ProfilePicture =="" || u.ProfilePicture==null ){
                   u.ProfilePicture= "http://publicdomainvectors.org/photos/generic-avatar.png";
                        }
                        u.Name = rdr["Name"].ToString();
                    }
                }
            }
            catch (Exception ex)
            {

            }
            return u;

        }

    }
}