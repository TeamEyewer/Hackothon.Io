using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Web;

namespace rangermobileService
{
    public class DatabaseConfig
    {

        public string getConnectionString()
        {
            string conString = "";

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
            catch (Exception e)
            {
                Console.Out.WriteLine(e.Message);
            }

            return conString;
        }

        public string getStorageString()
        {
            string conString = "DefaultEndpointsProtocol=https;AccountName=eyewerstorage;AccountKey=N0GRu8L44z3t8FN4Xej3O8r6vRDGfMu8oKxPd+8XRCV5dWcjvsCpoBxeCIbm4yXCFNP072dJY3Et+j6lvJ1X4w==";

            return conString;
        }

        public SqlConnection getconnection() {

            SqlConnection conn = new SqlConnection(getConnectionString());
            return conn; 

        }
    }
}