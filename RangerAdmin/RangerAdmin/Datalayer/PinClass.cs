using EntityLibrary;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Web;

namespace RangerAdmin.Datalayer
{
    public class PinClass
    {

        static RangerConfig constring = new RangerConfig();
        public List<Danger> GetDangerTypes()
        {

            List<Danger> list = new List<Danger>();

            try
            {
                using (SqlConnection con = new SqlConnection(constring.getconnection()))
                {
                    SqlCommand cmd = new SqlCommand("Select * From ranger.DangerTypeTable  ", con);
                    con.Open();
                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        Danger obj = new Danger();
                        obj.id = rdr["id"].ToString();
                        obj.DangerName = rdr["DangerName"].ToString();
                        obj.DangerPinIcon = rdr["DangerPinIcon"].ToString();
                        obj.DangerIcon = rdr["DangerIcon"].ToString();
                        list.Add(obj);

                    }
                }
            }
            catch (Exception ex)
            {


            }

            return list;

        }


        public int AddDangerByAdmin(string lat, string lon, string dangerid, string adminId)
        {
            int status = 0;
            try
            {



                string insertcomment = "INSERT INTO ranger.ConfirmedPins (Latitude,Longitude,ConfirmedTime,ConfirmedBy,DangerID,UserCreatedOn) VALUES (@Latitude,@Longitude,@ConfirmedTime,@ConfirmedBy,@DangerID,@UserCreatedOn)";

                using (SqlConnection con = new SqlConnection(constring.getconnection()))
                {

                    using (SqlCommand cmd = new SqlCommand(insertcomment, con))
                    {

                        cmd.Parameters.AddWithValue("@Latitude", lat);
                        cmd.Parameters.AddWithValue("@Longitude", lon);
                        cmd.Parameters.AddWithValue("@ConfirmedTime", DateTime.Now);
                        cmd.Parameters.AddWithValue("@ConfirmedBy", adminId);
                        cmd.Parameters.AddWithValue("@DangerID", dangerid);
                        cmd.Parameters.AddWithValue("@UserCreatedOn", DateTime.Now);
                        con.Open();
                        cmd.ExecuteNonQuery();
                        con.Close();
                        status = 1;
                    }
                }
            }
            catch (Exception ex)
            {

            }


            return status;

        }


        public List<Pin> ViewNotification()
        {
            List<Pin> list = new List<Pin>();
            try
            {
                using (SqlConnection con = new SqlConnection(constring.getconnection()))
                {
                    SqlCommand cmd = new SqlCommand("Select Top 5  dangericon=(Select Top 1 DangerIcon From ranger.PinTable as tbl3 Join ranger.DangerTypeTable ON DangerTypeTable.id=tbl3.DangerID  Where tbl3.PinGroup=PinTable.PinGroup),pindate=(Select Top 1 CreatedOn From ranger.PinTable as tbl3 Where tbl3.PinGroup=PinGroup ORDER BY tbl3.CreatedOn ),dangername=(Select Top 1 DangerName From ranger.PinTable as tbl2 Join ranger.DangerTypeTable ON DangerTypeTable.id=tbl2.DangerID  Where tbl2.PinGroup=PinTable.PinGroup),  PinGroup,count(id) as pincount From ranger.PinTable Where PinStatus=@PinStatus Group By  PinTable.PinGroup  ", con);
                    cmd.Parameters.AddWithValue("@PinStatus", 0);
                    con.Open();
                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        Pin obj = new Pin();
                        var now = Convert.ToDateTime(rdr["pindate"].ToString());
                        obj.CreatedOn =  now.ToString("g");
                        obj.PinGroup = rdr["PinGroup"].ToString();
                        obj.PinDanger.DangerName = rdr["dangername"].ToString();
                        obj.PinDanger.DangerIcon = rdr["dangericon"].ToString();
                        obj.PinCount = Convert.ToInt32(rdr["pincount"].ToString());
                        list.Add(obj);

                    }
                }
            }
            catch (Exception ex)
            {


            }

            return list;

        }


        public Pin GetNotificationItem(string groupname)
        {
            var obj = new Pin();

            try
            {
                using (SqlConnection con = new SqlConnection(constring.getconnection()))
                {
                    SqlCommand cmd = new SqlCommand("Select Top 1 DangerPinIcon,DangerIcon,UserID,PinTable.id,Latitude,Longitude,CreatedOn,DangerID,DangerName,Name,MobileNumber From ranger.PinTable Join ranger.UserTable ON UserTable.id=PinTable.UserID Join ranger.DangerTypeTable ON DangerTypeTable.id=PinTable.DangerID  Where PinGroup=@PinGroup Order BY CreatedOn  ", con);
                    cmd.Parameters.AddWithValue("@PinGroup", groupname);

                    con.Open();
                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {

                        obj.id = rdr["id"].ToString();
                        obj.Lat = rdr["Latitude"].ToString();
                        obj.Lon = rdr["Longitude"].ToString();
                        obj.CreatedOn =   rdr["CreatedOn"].ToString();
                        obj.PinDanger.id = rdr["DangerID"].ToString();
                        obj.PinDanger.DangerName = rdr["DangerName"].ToString();
                        obj.PinDanger.DangerPinIcon = rdr["DangerPinIcon"].ToString();
                        obj.PinDanger.DangerIcon = rdr["DangerIcon"].ToString();
                        obj.PinUser.Name = rdr["Name"].ToString();
                        obj.PinUser.id = rdr["UserID"].ToString();
                        obj.PinUser.MobileNumber = rdr["MobileNumber"].ToString();
                        obj.PinGroup = groupname;


                    }
                }
            }
            catch (Exception ex)
            {


            }
            return obj;
        }

        public int AcceptPinFUnction(string groupname, string adminid)
        {
            int status = 0;

            var PinObj = GetNotificationItem(groupname);


            status = UpdateStausFromPinTable(groupname, 1);
            if (status == 1)
            {
                status = AddDanger(PinObj, adminid);
            }
            return status;

        }

        public int RejectPinFUnction(string groupname, string adminid)
        {
            int status = 0;

            var PinObj = GetNotificationItem(groupname);


            status = UpdateStausFromPinTable(groupname, -1);

            return status;

        }

        public int AddDanger(Pin Obj, string adminId)
        {
            int status = 0;
            try
            {



                string insertcomment = "INSERT INTO ranger.ConfirmedPins (Latitude,Longitude,ConfirmedTime,ConfirmedBy,DangerID,PinGroupID,UserCreatedOn) VALUES (@Latitude,@Longitude,@ConfirmedTime,@ConfirmedBy,@DangerID,@PinGroupID,@UserCreatedOn)";

                using (SqlConnection con = new SqlConnection(constring.getconnection()))
                {

                    using (SqlCommand cmd = new SqlCommand(insertcomment, con))
                    {

                        cmd.Parameters.AddWithValue("@Latitude", Obj.Lat);
                        cmd.Parameters.AddWithValue("@Longitude", Obj.Lon);
                        cmd.Parameters.AddWithValue("@ConfirmedTime", DateTime.Now);
                        cmd.Parameters.AddWithValue("@ConfirmedBy", adminId);
                        cmd.Parameters.AddWithValue("@DangerID", Obj.PinDanger.id);
                        cmd.Parameters.AddWithValue("@PinGroupID", Obj.PinGroup);
                        cmd.Parameters.AddWithValue("@UserCreatedOn", Obj.CreatedOn);
                        con.Open();
                        cmd.ExecuteNonQuery();
                        con.Close();
                        status = 1;
                    }
                }
            }
            catch (Exception ex)
            {

            }


            return status;

        }

        public int UpdateStausFromPinTable(string groupid, int pinstatus)
        {
            var isupdate = 0;

            try
            {
                using (SqlConnection con = new SqlConnection(constring.getconnection()))
                {
                    string updateQuery = "Update ranger.PinTable SET PinStatus=@PinStatus WHERE PinGroup=@PinGroup  ";
                    SqlCommand cmd = new SqlCommand(updateQuery, con);
                    SqlParameter paramter1 = new SqlParameter("@PinGroup", groupid);
                    cmd.Parameters.Add(paramter1);

                    SqlParameter paramterid = new SqlParameter("@PinStatus", pinstatus);
                    cmd.Parameters.Add(paramterid);





                    con.Open();

                    cmd.ExecuteNonQuery();

                    con.Close();
                    isupdate = 1;
                }
            }
            catch (Exception ex)
            {


            }
            return isupdate;

        }


        public int CurrentUserCount()
        {
            int count = 0;
            try
            {
                using (SqlConnection con = new SqlConnection(constring.getconnection()))
                {
                    SqlCommand cmd = new SqlCommand("Select Count(id) From ranger.UserTable   ", con);


                    con.Open();
                    count = (Int32)cmd.ExecuteScalar();

                    con.Close();

                }
            }
            catch (Exception ex)
            {


            }

            return count;
        }


        public int ApprovedPinCount()
        {
            int count = 0;
            try
            {
                using (SqlConnection con = new SqlConnection(constring.getconnection()))
                {
                    SqlCommand cmd = new SqlCommand("Select Count(id) From ranger.PinTable Where PinStatus=@PinStatus   ", con);

                    cmd.Parameters.Add("@PinStatus", SqlDbType.Int).Value = 1;

                    con.Open();
                    count = (Int32)cmd.ExecuteScalar();

                    con.Close();

                }
            }
            catch (Exception ex)
            {


            }

            return count;
        }


        public int RejectedPinCount()
        {
            int count = 0;
            try
            {
                using (SqlConnection con = new SqlConnection(constring.getconnection()))
                {
                    SqlCommand cmd = new SqlCommand("Select Count(id) From ranger.PinTable Where PinStatus=@PinStatus   ", con);

                    cmd.Parameters.Add("@PinStatus", SqlDbType.Int).Value = -1;


                    con.Open();
                    count = (Int32)cmd.ExecuteScalar();

                    con.Close();

                }
            }
            catch (Exception ex)
            {


            }

            return count;
        }


        public List<Pin> GetPinByUserID(string id)
        {
            List<Pin> list = new List<Pin>();

            try
            {
                using (SqlConnection con = new SqlConnection(constring.getconnection()))
                {
                    SqlCommand cmd = new SqlCommand("Select  * From ranger.PinTable Join ranger.DangerTypeTable ON DangerTypeTable.id=PinTable.DangerID Where PinStatus=@PinStatus AND UserID=@UserID Order BY CreatedOn Desc   ", con);
                    cmd.Parameters.AddWithValue("@PinStatus", 1);
                    cmd.Parameters.AddWithValue("@UserID", id);
                    con.Open();
                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        Pin obj = new Pin();
                        obj.PinDanger.DangerPinIcon = rdr["DangerPinIcon"].ToString();
                        obj.PinDanger.id = rdr["DangerID"].ToString();
                        obj.Lat = rdr["Latitude"].ToString();
                        obj.Lon = rdr["Longitude"].ToString();
                        list.Add(obj);

                    }
                }
            }
            catch (Exception ex)
            {


            }

            return list;


        }



        public List<Pin> GetPinsByDangerTypes(string id)
        {
            List<Pin> list = new List<Pin>();

            try
            {
                using (SqlConnection con = new SqlConnection(constring.getconnection()))
                {
                    SqlCommand cmd = new SqlCommand("Select  Top 15 * From ranger.ConfirmedPins Join ranger.DangerTypeTable ON DangerTypeTable.id=ConfirmedPins.DangerID   Where DangerID=@DangerID Order BY UserCreatedOn Desc   ", con);

                    cmd.Parameters.AddWithValue("@DangerID", id);
                    con.Open();
                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        Pin obj = new Pin();




                        obj.PinDanger.DangerPinIcon = rdr["DangerPinIcon"].ToString();
                        obj.PinDanger.id = rdr["DangerID"].ToString();
                        obj.Lat = rdr["Latitude"].ToString();
                        obj.Lon = rdr["Longitude"].ToString();


                        list.Add(obj);

                    }
                }
            }
            catch (Exception ex)
            {


            }

            return list;


        }

        public String time(DateTime posttime)
        {

            string current = DateTime.Now.ToString("h:mm:ss tt");
            TimeSpan time1 = TimeSpan.Parse(current);
            TimeSpan time2 = TimeSpan.Parse(Convert.ToString(posttime));
            TimeSpan difference = time1 - time2;

            int hours = difference.Hours;
            int minutes = difference.Minutes;

            if (minutes <= 1)
            {
                return "Just Now";
            }
            else
            {

                return hours + " Hours " + minutes + " Ago";

            }

        }
        

        public int ViewNotificationCount()
        {
            int count = 0;
            try
            {
                using (SqlConnection con = new SqlConnection(constring.getconnection()))
                {
                    SqlCommand cmd = new SqlCommand("Select  Count( DISTINCT PinGroup) From ranger.PinTable Where  PinStatus=@PinStatus ", con);
                    cmd.Parameters.AddWithValue("@PinStatus", 0);
                   

                    con.Open();
                    count = (Int32)cmd.ExecuteScalar();

                    con.Close();

                }
            }
            catch (Exception ex)
            {


            }

            return count;
        }


        

        public List<Pin> GetPinList()
        {
            List<Pin> list = new List<Pin>();

            try
            {
                using (SqlConnection con = new SqlConnection(constring.getconnection()))
                {
                    SqlCommand cmd = new SqlCommand("Select  Top 10 * From ranger.ConfirmedPins Join ranger.DangerTypeTable ON DangerTypeTable.id=ConfirmedPins.DangerID   Order BY UserCreatedOn Desc  ", con);
                   
                   
                    con.Open();
                    SqlDataReader rdr = cmd.ExecuteReader();
                    while (rdr.Read())
                    {
                        Pin obj = new Pin();
                        obj.PinDanger.DangerPinIcon = rdr["DangerPinIcon"].ToString();
                        obj.PinDanger.id = rdr["DangerID"].ToString();
                        obj.Lat = rdr["Latitude"].ToString();
                        obj.Lon = rdr["Longitude"].ToString();
                        list.Add(obj);

                    }
                }
            }
            catch (Exception ex)
            {


            }

            return list;


        }
    }
}