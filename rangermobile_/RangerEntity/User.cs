using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RangerEntity
{
   public class User
    {
        public int id { get; set;  }
        public String Name { get; set; }
        public String ProfilePicture { get; set; }

        public String MobileNumber { get; set; }

        public string FacebookID { get; set;  }

        public String FacebookEmail { get; set; }

        public String GoogleID { get; set; }

        public String GoogleEmail { get; set;  }
         
        public String JoinDate { get; set; }

        public int Pendingstatus { get; set; }
    }
}
