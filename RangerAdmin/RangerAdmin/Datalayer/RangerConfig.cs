using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RangerAdmin.Datalayer
{
    public class RangerConfig
    {

        string conString = "";

        public string getconnection()
        {

            try
            {
                System.Data.SqlClient.SqlConnectionStringBuilder builder = new System.Data.SqlClient.SqlConnectionStringBuilder();
                builder["Server"] = "tcp:dmgc57lzfu.database.windows.net,1433";
                builder["User ID"] = "eyeweradmin@dmgc57lzfu";
                builder["Password"] = "Solantraadmin123@";
                builder["Database"] = "rangerdb";
                builder["Trusted_Connection"] = false;
                builder["Integrated Security"] = false;
                builder["Encrypt"] = true;
                builder["Connection Timeout"] = 60;
                conString = builder.ConnectionString;

            }
            catch (Exception)
            {

                throw;
            }

            return conString;
        }
    }
}