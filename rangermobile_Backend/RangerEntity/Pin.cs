using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RangerEntity
{
    public  class Pin
    {
        public String id { get; set; }
        DangerType dangertype = new DangerType();
        User user = new User();
     
        public int PinStatus { get; set; }
        public String CreatedOn { get; set; }
        public String Latitude { get; set; }
        public String Longitude { get; set; }
        public String PinGroup { get; set; }

        public User User
        {
            get
            {
                return user;
            }

            set
            {
                user = value;
            }
        }

        public DangerType Dangertype
        {
            get
            {
                return dangertype;
            }

            set
            {
                dangertype = value;
            }
        }
    }
}
