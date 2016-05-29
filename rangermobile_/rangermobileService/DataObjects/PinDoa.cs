
using EntityLibrary;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Web;

namespace rangermobileService.DataObjects
{
    public class PinDoa
    {
        public static String GetTimestamp(DateTime value)
        {
            return value.ToString("MMddHHmmssffff");
        }
        public Tuple<String, String, Distance, Boolean,String> createPin(String lat, String lon, String userid, int raidus, String Dangerid) {
            DatabaseConfig db = new DatabaseConfig();
            SqlConnection conn = db.getconnection();
            String status = "200";
            String description = "Response Okay";
            Boolean hasDistance = false;
            Distance distance = new Distance();
            String PinId = "";
            try
            {

                Random random = new Random();
                int randomnumber = random.Next(1, 9999999);
                String timeStamp = GetTimestamp(DateTime.Now);
                String groupID = randomnumber + userid + timeStamp;
               

                conn.Open();
                SqlCommand cmd = new SqlCommand();
                cmd.CommandType = System.Data.CommandType.StoredProcedure;
                cmd.CommandText = "ranger.createpin";
                cmd.Parameters.Add(new SqlParameter("@id", SqlDbType.VarChar, 50, ParameterDirection.Output, false, 0, 20, "", DataRowVersion.Default, null));
                cmd.Parameters.Add("@lat", SqlDbType.VarChar, 50).Value = lat;
                cmd.Parameters.Add("@lon", SqlDbType.VarChar, 50).Value = lon;
                cmd.Parameters.Add("@userId", SqlDbType.VarChar, 50).Value = userid;
                cmd.Parameters.Add("@radius", SqlDbType.VarChar, 50).Value = raidus;
                cmd.Parameters.Add("@DangerId", SqlDbType.VarChar, 50).Value = Dangerid;

                cmd.Parameters.Add("@PinGroup", SqlDbType.VarChar, 50).Value = groupID;
                cmd.Connection = conn;

                SqlDataReader dr = cmd.ExecuteReader();

                if (dr.HasRows)
                {
                    hasDistance = true;
                    while (dr.Read()) {

                        distance.lat = dr["Latitude"].ToString();
                        distance.lon = dr["Longitude"].ToString();
                        distance.distance = dr["Distance"].ToString();
                        distance.Group = dr["PinGroupID"].ToString();
                       // distance.DangerObj.id = dr["DangerTypeTable.id"].ToString();
                            distance.DangerObj.DangerName = dr["DangerName"].ToString();
                       
                    }
                    dr.Close();
                    PinId = cmd.Parameters["@id"].Value.ToString();
                    
                }
                else {
                    hasDistance = false;
                    dr.Close();
                    PinId = cmd.Parameters["@id"].Value.ToString();
                }

            }
            catch (Exception e)
            {
                description = e.ToString();
                status = "400";

            }

            finally
            {
                conn.Close();
                conn.Dispose();
            }

            return Tuple.Create<String, String, Distance, Boolean,String>(status, description, distance, hasDistance,PinId);
        }


        public Tuple<String, String> confirmpin(String pinid, String pingroup) {
            DatabaseConfig db = new DatabaseConfig();
            SqlConnection conn = db.getconnection();
            String status = "200";
            String description = "Response Okay";
            try
            {

                conn.Open();
                SqlCommand cmd = new SqlCommand("Update ranger.PinTable set PinGroup = @pingroup where id = @pinid",conn);
                cmd.Parameters.AddWithValue("@pinid", pinid);
                cmd.Parameters.AddWithValue("@pingroup", pingroup);

                cmd.ExecuteNonQuery();

            }
            catch (Exception e)
            {
                status = "500";
                description = e.ToString();

            }
            finally {

                conn.Close();
                conn.Dispose();
            }


            return Tuple.Create<String, String>(status, description);
        }


        public Tuple<String, String, List<Distance>> GetPins(String userId, String lat, String lng, int radius)
        {
            String status = "200";
            String description = "Success";
            List<Distance> list = new List<Distance>();

            try
            {

                DatabaseConfig db = new DatabaseConfig();
                SqlConnection conn = db.getconnection();
                SqlCommand cmd = new SqlCommand();
                conn.Open();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "ranger.getPins";

                cmd.Parameters.Add("@userID", SqlDbType.VarChar).Value = userId;
                cmd.Parameters.Add("@lat", SqlDbType.VarChar).Value = lat;
                cmd.Parameters.Add("@lon", SqlDbType.VarChar).Value = lng;
                cmd.Parameters.Add("@radius", SqlDbType.Int).Value = radius;


                cmd.Connection = conn;
                SqlDataReader rdr = cmd.ExecuteReader();
                while (rdr.Read())
                {
                    Distance pin = new Distance();
                    pin.PinId = rdr["id"].ToString();
                    pin.Group = rdr["PinGroupID"].ToString();
                    pin.lat = rdr["Latitude"].ToString();
                    pin.lon = rdr["Longitude"].ToString();
                    pin.DangerObj.id = rdr["DangerID"].ToString();
                    pin.DangerObj.DangerName = rdr["DangerName"].ToString();
                    pin.DangerObj.DangerType = rdr["DangerType"].ToString();

                    SqlConnection conn2 = db.getconnection();
                    conn2.Open();
                    SqlCommand cmd2 = new SqlCommand("Select count(id) as c from ranger.PinTable where PinGroup = @pingroup", conn2);
                    cmd2.Parameters.AddWithValue("@pingroup", pin.Group);
                    SqlDataReader dr2 = cmd2.ExecuteReader();

                    while (dr2.Read()) {
                        pin.PinCount = int.Parse(dr2["c"].ToString());
                    }

                   
                    pin.distance = Double.Parse(rdr["Distance"].ToString()) * 1609.34 + "";
                    list.Add(pin);


                    conn2.Close();
                    conn2.Dispose();


                }


            }
            catch (Exception ex)
            {
                status = "Failed";
                description = ex.Message;

            }

            return Tuple.Create<String, String, List<Distance>>(status, description, list);
        }


    }
}