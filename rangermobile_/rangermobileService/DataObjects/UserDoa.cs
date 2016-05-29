


using EntityLibrary;
using rangermobileService.Models;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Web;


namespace rangermobileService.DataObjects
{
    public class UserDoa
    {


        public Tuple<String, String, User, Boolean, String> login(String name, String provider, int providerid, String ProviderEmail, String picture, int deviceID)
        {

            String status = "200";
            String statusdetail = "Success";
            Boolean isUser = false;
            String accessToken = "";
            User user = new User();
            String userAccessToken = "";
            DatabaseConfig db = new DatabaseConfig();

            accessToken = GenerateAuthToken(providerid+"");
            SqlConnection conn = db.getconnection();
            try
            {
                //setting Sqlconnection

                conn.Open();
                SqlCommand cmd = new SqlCommand();

                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "ranger.login";

                cmd.Parameters.Add("@Name", SqlDbType.VarChar, 50).Value = name;
                cmd.Parameters.Add("@Provider ", SqlDbType.VarChar, 50).Value = provider;
                cmd.Parameters.Add("@ProviderId ", SqlDbType.VarChar, 50).Value = providerid;
                cmd.Parameters.Add("@ProviderEmail ", SqlDbType.VarChar, 50).Value = ProviderEmail;
                cmd.Parameters.Add("@Picture ", SqlDbType.VarChar, 50).Value = picture;
                cmd.Parameters.Add("@DeviceId  ", SqlDbType.VarChar, 50).Value = deviceID;

                cmd.Parameters.Add("@AccessToken  ", SqlDbType.VarChar, 50).Value = accessToken;
                cmd.Parameters.Add(new SqlParameter("@returnedAccessToken", SqlDbType.VarChar, 50, ParameterDirection.Output, false, 0, 20, "", DataRowVersion.Default, null));

                cmd.Parameters.Add(new SqlParameter("@isUSer", SqlDbType.NVarChar, 50, ParameterDirection.Output, false, 0, 20, "", DataRowVersion.Default, null));
                cmd.Parameters.Add(new SqlParameter("@id", SqlDbType.VarChar, 50, ParameterDirection.Output, false, 0, 20, "", DataRowVersion.Default, null));


                cmd.Connection = conn;

                // cmd.ExecuteNonQuery();


                SqlDataReader dr = cmd.ExecuteReader();

                //  cmd.ExecuteNonQuery();



                if (!dr.HasRows)
                {
                    dr.Close();
                    isUser = false;
                    //new user
                    isUser = true;
                    user.id = cmd.Parameters["@id"].Value.ToString();
                    user.Name = name;
                    if (provider.Equals("Facebook"))
                    {
                        user.FacebookEmail = ProviderEmail;
                        user.FacebookID = providerid+"";

                    }
                    else
                    {
                        user.GoogleEmail = ProviderEmail;
                        user.GoogleID = providerid+"";
                    }

                    user.ProfilePicture = picture;
                    user.pendingStatus = 1;
                    userAccessToken = accessToken;
                    user.JoinDate = DateTime.Now.ToString();



                }

                else
                {
                    //exsistin user
                    Boolean closereader = false;
                    isUser = true;
                    while (dr.Read())
                    {

                        user.id = dr["id"].ToString();
                        user.Name = dr["Name"].ToString();
                        user.FacebookID = dr["FacebookID"].ToString();
                        user.FacebookEmail = dr["FacebookEmail"].ToString();
                        user.GoogleID = dr["GoogleID"].ToString();
                        user.GoogleEmail = dr["GoogleEmail"].ToString();
                        user.ProfilePicture = dr["ProfilePicture"].ToString();
                        user.JoinDate = dr["JoinDate"].ToString();
                        user.pendingStatus = int.Parse(dr["PendingStatus"].ToString());
                        closereader = true;

                    }


                    if (closereader)
                    {
                        dr.Close();
                        userAccessToken = cmd.Parameters["@returnedAccessToken"].Value.ToString();
                    }

                }






            }
            catch (Exception e)
            {
                status = "501";
                statusdetail = e.ToString();
            }
            finally
            {
                conn.Close();
                conn.Dispose();
            }


            return Tuple.Create<String, String, User, Boolean, String>(status, statusdetail, user, isUser, userAccessToken);
        }


        public Tuple<String, String, User> getprofile(String id)
        {

            Tuple<String, String, User> data;
            DatabaseConfig db = new DatabaseConfig();
            SqlConnection conn = db.getconnection();
            User user = new User();
            String status = "202";
            String description = "Success";


            try
            {
                conn.Open();

                SqlCommand cmd = new SqlCommand("Select * from ranger.UserTable where id =  @id", conn);
                cmd.Parameters.AddWithValue("@id", SqlDbType.VarChar).Value = id;

                SqlDataReader dr = cmd.ExecuteReader();

                if (!dr.HasRows)
                {
                    //error getting the user 
                    status = "400";
                    description = "User Not found ";

                }
                else
                {

                    while (dr.Read())
                    {
                        user.id = dr["id"].ToString();
                        user.Name = dr["Name"].ToString();
                        user.ProfilePicture = dr["ProfilePicture"].ToString();
                        user.MobileNumber = dr["MobileNumber"].ToString();
                        user.FacebookID = dr["FacebookID"].ToString();
                        user.FacebookEmail = dr["FacebookEmail"].ToString();
                        user.GoogleID = dr["GoogleID"].ToString();
                        user.GoogleEmail = dr["GoogleEmail"].ToString();
                        user.JoinDate = dr["JoinDate"].ToString();
                        user.pendingStatus = int.Parse(dr["PendingStatus"].ToString());




                    }


                }




            }
            catch (Exception e)
            {
                status = "400";
                description = e.Message;
                conn.Close();
                conn.Dispose();

            }

            finally
            {
                conn.Close();
                conn.Dispose();
            }


            return Tuple.Create<String, String, User>(status, description, user);

        }

        public Tuple<String, String> confirmaccount(String userid, String mobilenumber)
        {
            DatabaseConfig db = new DatabaseConfig();
            SqlConnection conn = db.getconnection();
            string status = "200";
            String des = "Success";
            try
            {


               
                conn.Open();
                SqlCommand cmd = new SqlCommand("Update ranger.UserTable set MobileNumber = @mobilenumber where id = @userid", conn);
                cmd.Parameters.AddWithValue("@mobilenumber", mobilenumber);
                cmd.Parameters.AddWithValue("@userid", userid);


                int rows = cmd.ExecuteNonQuery();

                if (rows != 1)
                {
                    status = "400";
                    des = "User not found";

                }


            }
            catch (Exception e)
            {
                status = "400";
                des = e.Message;

            }
            finally
            {

                conn.Close();
            }




            return Tuple.Create<String, String>(status, des);

        }

        public Tuple<String, String> deleteAccessToken(String UserId, String DeviceId)
        {
            String response = "200";
            String description = "OKAY";
            DatabaseConfig db = new DatabaseConfig();
            SqlConnection conn = db.getconnection();
            try
            {

                conn.Open();
                SqlCommand cmd = new SqlCommand("Delete from ranger.AccessTokens where UserID =@id and DeviceID =@did ", conn);
                cmd.Parameters.AddWithValue("@id", UserId);
                cmd.Parameters.AddWithValue("@did", DeviceId);
                int rows = cmd.ExecuteNonQuery();

                if (rows > 0)
                {
                    response = "200";
                    description = "OKAY";
                }

                else
                {

                    response = "500";
                    description = "No Rows effected";
                }


            }
            catch (Exception e)
            {
                response = "500";
                description = e.Message;

            }
            finally
            {
                conn.Close();
                conn.Dispose();
            }
            return Tuple.Create<String, String>(response, description);
        }

        private string GenerateAuthToken(string user_id)
        {
            string tempToken = new Random().Next(0, 99999999) + user_id;

            string hash1 = "";

            var sha1 = SHA1.Create();
            var inputBytes = Encoding.ASCII.GetBytes(tempToken);
            var hash = sha1.ComputeHash(inputBytes);
            var sb = new StringBuilder();
            for (var i = 0; i < hash.Length; i++)
            {
                sb.Append(hash[i].ToString("X2"));
            }

            hash1 = new Random().Next(0, 999) + sb.ToString() + new Random().Next(0, 99999999);

            inputBytes = Encoding.ASCII.GetBytes(hash1);
            hash = sha1.ComputeHash(inputBytes);
            for (var i = 0; i < hash.Length; i++)
            {
                sb.Append(hash[i].ToString("X2"));
            }

            return hash1 + sb.ToString();
        }
    }
}