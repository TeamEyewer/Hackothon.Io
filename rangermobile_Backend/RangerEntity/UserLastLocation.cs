using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RangerEntity
{
    public  class UserLastLocation
    {

        public int id { get; set; }

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

        User user = new User();

        public String Latitude { get; set; }

        public String Longitude { get; set; }

        public String Time { get; set; }

    }
}
